import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GestionRadios {
    
    final static int MAX_RADIOS = 15;
    final static String FICHIER_RADIOS = "src/donnees/radios.txt";
    //final static String NOM_DE_CIE = "Cie Air Relax";
    final static String SEPARATEUR = ";";
    //final static String[] LES_MOIS = {"Janvier", "Février", "Mars", "Avril",  "Mai",  "Juin",  "Juillet",  "Août",  "Septembre",  "Octobre",  "Novembre",  "Décembre"};

    static ArrayList<Radio> listeRadios = new ArrayList<Radio>();
    static BufferedReader tempRadios;

    static boolean boucleMain = true;

    public static void chargerRadios() throws Exception {
        try {
            String ligne;
            String elems[] = new String[6];
            tempRadios = new BufferedReader(new FileReader(FICHIER_RADIOS));
            ligne = tempRadios.readLine();
            while (ligne != null) {
                elems = ligne.split(SEPARATEUR);
                listeRadios.add(new Radio(elems[0], elems[1], (elems[2].equals("1") ? true : false), (elems[3].equals("1") ? true : false), (elems[4].equals("1") ? true : false), Double.parseDouble(elems[5])));
                ligne = tempRadios.readLine();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "\nUne erreur est survenu lors du chargement des radios...\n\nCe programme va s'arrêter.\n\n", "ERREUR", JOptionPane.PLAIN_MESSAGE);
            boucleMain = false;
        }
        tempRadios.close();
    }

    public static void listerRadios() {
        JTextArea message = new JTextArea();
        message.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        message.append("\n\t\t\tINVENTAIRE DES RADIOS\n\nMarque\t\tModèle\t\tCD?\tCass.?\tmp3?\t\tPrix\t\t\n\n");
        for (Radio radio : listeRadios) {
            message.append(radio.toString() + "\n");
        }
        message.append("\n\n Nombre total de radios = " + listeRadios.size() + ".\n\n");
        JOptionPane.showMessageDialog(null, message, "LISTE RADIOS", JOptionPane.PLAIN_MESSAGE);
    }

    public static void listerRadiosParMarque(String uneRadio) {
        JTextArea message = new JTextArea();
        int nbTrouvees = 0;
        message.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        message.append("\n\t\t\tINVENTAIRE DES RADIOS\n\nAyant comme marque '" + uneRadio + "'\n\nMarque\t\tModèle\t\tCD?\tCass.?\tmp3?\t\tPrix\t\t\n\n");
        for (Radio radio : listeRadios) {
            if (radio.getMarque().equals(uneRadio)) {
                message.append(radio.toString() + "\n");
                nbTrouvees++;
            }
        }
        message.append("\n\n Nombre total de radios trouvées = " + nbTrouvees + ".\n\n");
        JOptionPane.showMessageDialog(null, message, "LISTE RADIOS (PAR MARQUE)", JOptionPane.PLAIN_MESSAGE);
    }

    public static void modifierPrixRadio() {
        String titre = "MODIFICATION D'UN PRIX'";

        // Demander la marque
        String message = "Entrez la marque :";
        String marqueS = JOptionPane.showInputDialog(null, message, titre, JOptionPane.PLAIN_MESSAGE);
        
        // Demander le multiplicateur.
        message = "Entrez le taux de changement de prix :\nEx. 0.15 pour une augmentation de 15%,\nou -0.07 pour une diminution de 7%...";
        String prixS = JOptionPane.showInputDialog(null, message, titre, JOptionPane.PLAIN_MESSAGE);

        // Faire les changements.
        for (Radio radio : listeRadios) {
            if (radio.getMarque().equals(marqueS)) {
                radio.setPrix(radio.getPrix() * (1 + Double.parseDouble(prixS)));
            }
        }
    }

    public static void ajouterRadio() {
        if (listeRadios.size() >= MAX_RADIOS) {
            String message = "Le nombre maximal de radios a été atteint!";
            JOptionPane.showMessageDialog(null, message, "ERREUR", JOptionPane.PLAIN_MESSAGE);
        }
        else {
            String titre = "AJOUT D'UNE RADIO";

            // Demander la marque.
            String message = "Entrez la marque :";
            String marque = JOptionPane.showInputDialog(null, message, titre, JOptionPane.PLAIN_MESSAGE);
            if (marque == null) { return; }

            // Demander le modèle.
            message = "Entrez le no. de modèle :";
            String modele = JOptionPane.showInputDialog(null, message, titre, JOptionPane.PLAIN_MESSAGE);
            if (modele == null) { return; }

            // Frame pour les drop-drown menus.
            JFrame frame = new JFrame();

            // Demander lecteur cd.
            message = "Cette radio a-t-elle un lecteur de CD ?";
            String[] options_lecteurs = {"Oui", "Non"};
            String cdS = (String)JOptionPane.showInputDialog(frame, message, titre, JOptionPane.PLAIN_MESSAGE, null, options_lecteurs, options_lecteurs[0]);
            if (cdS == null) { return; }

            // Demander lecteur cassette.
            message = "Cette radio a-t-elle un lecteur de cassette ?";
            String cassetteS = (String)JOptionPane.showInputDialog(frame, message, titre, JOptionPane.PLAIN_MESSAGE, null, options_lecteurs, options_lecteurs[0]);
            if (cassetteS == null) { return; }

            // Demander décodeur mp3.
            message = "Cette radio a-t-elle un décodeur mp3 ?";
            String mp3S = (String)JOptionPane.showInputDialog(frame, message, titre, JOptionPane.PLAIN_MESSAGE, null, options_lecteurs, options_lecteurs[0]);
            if (mp3S == null) { return; }

            // Demander décodeur mp3.
            message = "Entrez le prix de cette radio ex. 123.45 :";
            String prixS = JOptionPane.showInputDialog(null, message, titre, JOptionPane.PLAIN_MESSAGE);
            if (prixS == null) { return; }

            // Ajouter la nouvelle radio.
            listeRadios.add(new Radio(marque, modele, 
                (cdS == "Oui" ? true : false), 
                (cassetteS == "Oui" ? true : false), 
                (mp3S == "Oui" ? true : false), 
                Double.parseDouble(prixS)));

        }
    }

    public static void chercherRadio() {
        for (Radio radio : listeRadios) {
            if (radio.getMarque().equals("Sony") && radio.getModele().equals("WM823")) {
                radio.setMp3(true);
                radio.setPrix(169.99);
                return;
            }
        }
    }

    public static void main(String[] args) throws Exception {
        chargerRadios();
        JTextArea texte_menu = new JTextArea();
        texte_menu.append("\n\t*** Gestion des Radios ***\n\n");
        texte_menu.append("  1 - Lister les radios.\n");
        texte_menu.append("  2 - Lister les radios d'une seule marque.\t\t\n");
        texte_menu.append("  3 - Modifier le prix d'une radio.\n");
        texte_menu.append("  4 - Ajouter une radio.\n");
        texte_menu.append("  5 - Fonction test E (chercher, modifier).\n");
        texte_menu.append("  6 - Terminer.                 \n");
        texte_menu.append("\n\tEntrez votre choix [1-6] :");
        while (boucleMain == true) {
            String choix = JOptionPane.showInputDialog(null, texte_menu, "MENU PRINCIPAL", JOptionPane.PLAIN_MESSAGE);
            if (choix == null) {
                JOptionPane.showMessageDialog(null, "\nAu revoir!\n\n", "FIN", JOptionPane.PLAIN_MESSAGE);
                boucleMain = false;
            }
            else {
                switch (choix) {
                    case "1": {
                        listerRadios();
                        break;
                    }
                    case "2": {
                        String marque = JOptionPane.showInputDialog(null, 
                            "\nVous voulez lister toutes les radios\npour une marque en particulier.\n\nEntrez la marque :\n", 
                            "LISTE PAR MARQUE", JOptionPane.PLAIN_MESSAGE);
                        if (marque == null) { break; }
                        listerRadiosParMarque(marque);
                        break;
                    }
                    case "3": {
                        modifierPrixRadio();
                        break;
                    }
                    case "4": {
                        ajouterRadio();
                        break;
                    }
                    case "5": {
                        chercherRadio();
                        break;
                    }
                    case "6": {
                        JOptionPane.showMessageDialog(null, "\nAu revoir!\n\n", "FIN", JOptionPane.PLAIN_MESSAGE);
                        boucleMain = false;
                    }
                    default: {
                        JOptionPane.showMessageDialog(null, "Choix invalide...", "ERREUR", JOptionPane.PLAIN_MESSAGE);
                        break;
                    }
                }
            }
        }
    }
}
