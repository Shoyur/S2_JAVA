import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.time.*;

public class GestionVols {
    
    final static int MAX_PLACES = 340;
    final static int MAX_VOLS = 20;
    final static String FICHIER_VOLS = "src/donnees/Cie_Air_Relax.txt";
    final static String NOM_DE_CIE = "Cie Air Relax";
    final static String SEPARATEUR = "[;/]";
    final static String[] LES_MOIS = {"Janvier", "Février", "Mars", "Avril",  "Mai",  "Juin",  "Juillet",  "Août",  "Septembre",  "Octobre",  "Novembre",  "Décembre"};

    static ArrayList<Vol> tabVols = new ArrayList<Vol>();
    static BufferedReader tempVols;

    static boolean boucleMain = true;

    public static void chargerVols() throws Exception {
        try {
            String ligne;
            String elems[] = new String[6];
            tempVols = new BufferedReader(new FileReader(FICHIER_VOLS));
            ligne = tempVols.readLine();
            while (ligne != null) {
                elems = ligne.split(SEPARATEUR);
                tabVols.add(new Vol(Integer.parseInt(elems[0]), elems[1], new Date(Integer.parseInt(elems[2]), Integer.parseInt(elems[3]), Integer.parseInt(elems[4])), Integer.parseInt(elems[5])));
                ligne = tempVols.readLine();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "\nUne erreur est survenu lors du chargement des vols...\n\nCe programme va s'arrêter.\n\n", "ERREUR", JOptionPane.PLAIN_MESSAGE);
            boucleMain = false;
        }
        tempVols.close();
    }

    public static int rechercherVol(int numeroVol) {
        int resultat = -1;
        for (int i = 0; i < tabVols.size(); i++) {
            if (tabVols.get(i).getNumeroVol() == numeroVol) {
                resultat = i;
                break;
            }
        }
        return resultat;
    }

    public static void listeVols() {
        JTextArea message = new JTextArea();
        message.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 11));
        message.append("\n\t\t\tLISTE DES VOLS\n\nNo. de vol\tDestination\t\tDate de départ\tRéservations\n\n");
        for (Vol vol : tabVols) {
            message.append(vol.getNumeroVol() + "\t\t" + vol.getDestination() + "\t" + vol.getDate() + "\t" + vol.getNbReservations() + "\n");
        }
        message.append("\n\n Nombre total de vols = " + tabVols.size() + ".\n\n");
        JOptionPane.showMessageDialog(null, message, NOM_DE_CIE, JOptionPane.PLAIN_MESSAGE);
    }

    public static void insererVol() {
        if (tabVols.size() >= MAX_VOLS) {
            String message = "Le nombre maximal de vols a été atteint!";
            JOptionPane.showMessageDialog(null, message, "ERREUR", JOptionPane.PLAIN_MESSAGE);
        }
        else {
            String numeroVol = JOptionPane.showInputDialog(null, "Numéro du vol :", "AJOUT D'UN VOL", JOptionPane.PLAIN_MESSAGE);
            if (numeroVol == null) { return; }
            if (rechercherVol(Integer.parseInt(numeroVol)) != -1) {
                String message = "Ce numéro de vol existe déjà!";
                JOptionPane.showMessageDialog(null, message, "ERREUR", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                JFrame frame = new JFrame();
                LocalDate maintenant = LocalDate.now();
                String titre = "AJOUT D'UN VOL";

                // Demander destination.
                String destination = JOptionPane.showInputDialog(null, "Destination :", titre, JOptionPane.PLAIN_MESSAGE);
                if (destination == null) { return; }
                else { destination = String.format("%-21s", destination); }

                // Demander l'année.
                String [] options_an = new String [10];
                int opt_defaut = 0;
                for (int i = 0; i < 10; i++) { options_an[i] = String.valueOf(maintenant.getYear() + i); }
                String message2 = "Choisissez la nouvelle année : \n\n";
                String an = (String)JOptionPane.showInputDialog(frame, message2, titre, JOptionPane.PLAIN_MESSAGE, null, options_an, options_an[opt_defaut]);
                if (an == null || Integer.parseInt(an) == -1) { return; }

                // Demander le mois.
                int nbDeMois = 12;
                int premierMois = 1;
                if (Integer.parseInt(an) == maintenant.getYear()) { 
                    premierMois = maintenant.getMonthValue();
                    nbDeMois -= (premierMois - 1);
                }
                String [] options_mois = new String [nbDeMois];
                opt_defaut = 0;
                for (int i = 0; i < nbDeMois; i++) {
                    options_mois[i] = String.valueOf(premierMois + i);
                    // if (tabVols.get(position).getDate().getMois() == (premierMois + i)) { opt_defaut = i-1; }
                }
                message2 = "Choisissez le nouveau mois : \n\n" + "Nouvelle année = " + an + "\n\n";
                String mois = (String)JOptionPane.showInputDialog(frame, message2, titre, JOptionPane.PLAIN_MESSAGE, null, options_mois, options_mois[opt_defaut]);
                if (mois == null || Integer.parseInt(mois) == -1) { return; }

                // Demander le jour.
                int nbDeJour = nbJourParMois(Integer.parseInt(an), Integer.parseInt(mois));
                int premierJour = 1;
                if ((Integer.parseInt(an) == maintenant.getYear()) && (Integer.parseInt(mois) == maintenant.getMonthValue())) { 
                    premierJour = maintenant.getDayOfMonth();
                    nbDeJour -= (premierJour - 1);
                }
                String [] options_jour = new String [nbDeJour];
                opt_defaut = 0;
                for (int i = 0; i < nbDeJour; i++) {
                    options_jour[i] = String.valueOf(premierJour + i);
                    // if (tabVols.get(position).getDate().getJour() == i) { opt_defaut = i-1; }
                }
                message2 = "Choisissez le nouveau jour : \n\n" + "Nouvelle année = " + an + "\n";
                message2 += "Nouveau mois = " + mois + " (" + LES_MOIS[Integer.parseInt(mois) - 1] + ")\n\n";
                String jour = (String)JOptionPane.showInputDialog(frame, message2, titre, JOptionPane.PLAIN_MESSAGE, null, options_jour, options_jour[opt_defaut]);
                if (jour == null || Integer.parseInt(jour) == -1) { return; }


                tabVols.add(new Vol(Integer.parseInt(numeroVol), destination, new Date(Integer.parseInt(jour), Integer.parseInt(mois), Integer.parseInt(an)), 0));
            }
        }
    }

    public static void retirerVol() throws Exception {
        JFrame frame = new JFrame();
        

        String numeroVol = JOptionPane.showInputDialog(null, "Numéro du vol :", "RETRAIT D'UN VOL", JOptionPane.PLAIN_MESSAGE);
        int position = rechercherVol(Integer.parseInt(numeroVol));
        if (position == -1) {
            String message = "Ce numéro de vol n'existe pas!";
            JOptionPane.showMessageDialog(null, message, "ERREUR", JOptionPane.PLAIN_MESSAGE);
        }
        else {
            JTextArea message = new JTextArea();
            message.append("Numéro de vol : \t" + numeroVol + "\n");
            message.append("Destination : \t" + tabVols.get(position).getDestination() + "\n");
            message.append("Date : \t" + tabVols.get(position).getDate() + "\n");
            message.append("Nb. de réservations : \t" + tabVols.get(position).getNbReservations() + "\n\n");
            message.append("Êtes vous certain de vouloir retirer ce vol ?\n");            
            Object[] options = { "Oui", "Non" };
            // J'ai compris toutes les options de JOptionPane.showOptionDialog
            // sur https://docs.oracle.com/javase/7/docs/api/javax/swing/JOptionPane.html
            int choix = JOptionPane.showOptionDialog(null, message, "CONFIRMER", JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options, null);
            // J'ai compris que le 1er bouton est 0, et les autres..1.2.. Et peser X retourne -1
            // sur https://hajsoftutorial.com/showoptiondialog-returns/
            if (choix == 0) {
                tabVols.remove(position);             
            }
        }
    }
		
	public static int nbJourParMois(int an, int mois) {
        boolean estBissextile = ((an % 4) == 0 && (an % 100) != 0) || (an % 400 == 0);
		int tabJourParMois[] = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};
		if (mois == 2 && estBissextile) { return 29; }
		else { return tabJourParMois[mois]; }
	}

    public static void modifierDate() {
        JFrame frame = new JFrame();
        LocalDate maintenant = LocalDate.now();

        String titre = "MODIFICATION DE LA DATE DE DÉPART";
        String message = "Choisissez le numéro du vol :";
        String [] options_vol = new String [tabVols.size()];
        int opt_defaut = 0;
        for (int i = 0; i < tabVols.size(); i++) {
            options_vol[i] = String.valueOf(tabVols.get(i).getNumeroVol());
            // if (tabVols.get(position).getDate().getMois() == (premierMois + i)) { opt_defaut = i-1; }
        }
        String numeroVol = (String)JOptionPane.showInputDialog(frame, message, titre, JOptionPane.PLAIN_MESSAGE, null, options_vol, options_vol[opt_defaut]);

        
        if (numeroVol == null || Integer.parseInt(numeroVol) == -1) { return; }
        int position = rechercherVol(Integer.parseInt(numeroVol));
        message = "No. de vol : " + numeroVol + "\n";
        message += "Destination : " + tabVols.get(position).getDestination() + "\n";
        message += "Date de départ : " + tabVols.get(position).getDate() + "\n\n";


        // Demander l'année.
        String [] options_an = new String [10];
        opt_defaut = 0;
        for (int i = 0; i < 10; i++) {
            options_an[i] = String.valueOf(maintenant.getYear() + i);
            if (tabVols.get(position).getDate().getAn() == (maintenant.getYear() + i)) { opt_defaut = i; }
        }
        String message2 = "Choisissez la nouvelle année : \n\n";
        String an = (String)JOptionPane.showInputDialog(frame, message + message2, titre, JOptionPane.PLAIN_MESSAGE, null, options_an, options_an[opt_defaut]);
        if (an == null || Integer.parseInt(an) == -1) { return; }


        // Demander le mois.
        int nbDeMois = 12;
        int premierMois = 1;
        if (Integer.parseInt(an) == maintenant.getYear()) { 
            premierMois = maintenant.getMonthValue();
            nbDeMois -= (premierMois - 1);
        }
        String [] options_mois = new String [nbDeMois];
        opt_defaut = 0;
        for (int i = 0; i < nbDeMois; i++) {
            options_mois[i] = String.valueOf(premierMois + i);
            // if (tabVols.get(position).getDate().getMois() == (premierMois + i)) { opt_defaut = i-1; }
        }
        message2 = "Choisissez le nouveau mois : \n\n" + "Nouvelle année = " + an + "\n\n";
        String mois = (String)JOptionPane.showInputDialog(frame, message + message2, titre, JOptionPane.PLAIN_MESSAGE, null, options_mois, options_mois[opt_defaut]);
        if (mois == null || Integer.parseInt(mois) == -1) { return; }


        // Demander le jour.

        int nbDeJour = nbJourParMois(Integer.parseInt(an), Integer.parseInt(mois));
        int premierJour = 1;
        if ((Integer.parseInt(an) == maintenant.getYear()) && (Integer.parseInt(mois) == maintenant.getMonthValue())) { 
            premierJour = maintenant.getDayOfMonth();
            nbDeJour -= (premierJour - 1);
        }
        String [] options_jour = new String [nbDeJour];
        opt_defaut = 0;
        for (int i = 0; i < nbDeJour; i++) {
            options_jour[i] = String.valueOf(premierJour + i);
            // if (tabVols.get(position).getDate().getJour() == i) { opt_defaut = i-1; }
        }
        message2 = "Choisissez le nouveau jour : \n\n" + "Nouvelle année = " + an + "\n";
        message2 += "Nouveau mois = " + mois + " (" + LES_MOIS[Integer.parseInt(mois) - 1] + ")\n\n";
        String jour = (String)JOptionPane.showInputDialog(frame, message + message2, titre, JOptionPane.PLAIN_MESSAGE, null, options_jour, options_jour[opt_defaut]);
        if (jour == null || Integer.parseInt(jour) == -1) { return; }

        tabVols.get(position).getDate().setAn(Integer.parseInt(an)); 
        tabVols.get(position).getDate().setJour(Integer.parseInt(jour)); 
        tabVols.get(position).getDate().setJour(Integer.parseInt(jour)); 

    }

    public static void reserverVol() {
        String message = "Numéro du vol :";
        String numeroVol = JOptionPane.showInputDialog(null, message, "RÉSERVATION D'UN VOL", JOptionPane.PLAIN_MESSAGE);
        if (numeroVol == null) { return; }
        int position = rechercherVol(Integer.parseInt(numeroVol));
        if (position == -1) {
            message = "Ce numéro de vol n'existe pas!";
            JOptionPane.showMessageDialog(null, message, "RÉSERVATION D'UN VOL", JOptionPane.PLAIN_MESSAGE);
        }
        else {
            int places = MAX_PLACES - tabVols.get(position).getNbReservations();
            if (places == 0) {
                message = "\nDésolé, il ne reste plus de place pour ce vol.\n";
                JOptionPane.showMessageDialog(null, message, "RÉSERVATION D'UN VOL", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                message = "No. de vol : " + numeroVol + "\n";
                message += "Destination : " + tabVols.get(position).getDestination() + "\n";
                message += "Date de départ : " + tabVols.get(position).getDate() + "\n\n";
                message += "Places restantes : " + places + "\n\n";
                message += "Combien de places voulez-vous réserver ? : \n\n";
                JFrame frame = new JFrame();
                String [] options = new String [places];
                for (int i = 1; i <= places; i++) {
                    options[i-1] = String.valueOf(i);
                }
                String combien = (String)JOptionPane.showInputDialog(frame, message, "RÉSERVATION D'UN VOL", JOptionPane.PLAIN_MESSAGE, null, options, options[0]);
                if (Integer.parseInt(combien) == -1) { return; }
                tabVols.get(position).setNbReservations(tabVols.get(position).getNbReservations() + Integer.parseInt(combien));

            }
        }
    }

    public static void ecrireFichier() throws Exception {
        try {
            BufferedWriter tempVols = new BufferedWriter(new FileWriter(FICHIER_VOLS));
            for (Vol vol : tabVols) {
                String ligne = vol.getNumeroVol() + ";" + vol.getDestination() + ";" + vol.getDate() + ";" + vol.getNbReservations();
                tempVols.write(ligne);
                tempVols.newLine();
            }
            tempVols.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Une erreur est survenu lors de la sauvegarde des vols...", "ERREUR", JOptionPane.PLAIN_MESSAGE);
        }
        tempVols.close();
    }

    public static void main(String[] args) throws Exception {
        chargerVols();
        JTextArea messageMenuPrincipal = new JTextArea();
        messageMenuPrincipal.append("\n\tGESTION DES VOLS\n\n");
        messageMenuPrincipal.append("  (1) Liste des vols.\n");
        messageMenuPrincipal.append("  (2) Ajouter un vol.\n");
        messageMenuPrincipal.append("  (3) Retirer un vol.\n");
        messageMenuPrincipal.append("  (4) Modifier une date de départ.\n");
        messageMenuPrincipal.append("  (5) Réservation d'un vol.\n");
        messageMenuPrincipal.append("  (0) Sauvegarder + Quitter ce programme.                 \n");
        messageMenuPrincipal.append("\n\tFaites votre choix :");
        while (boucleMain == true) {
            String choix = JOptionPane.showInputDialog(null, messageMenuPrincipal, NOM_DE_CIE, JOptionPane.PLAIN_MESSAGE);
            if (choix == null) {
                JOptionPane.showMessageDialog(null, "\nAu revoir!\n\n", "FIN", JOptionPane.PLAIN_MESSAGE);
                boucleMain = false;
            }
            else {
                switch (choix) {
                    case "1": {
                        listeVols();
                        break;
                    }
                    case "2": {
                        insererVol();
                        break;
                    }
                    case "3": {
                        retirerVol();
                        break;
                    }
                    case "4": {
                        modifierDate();
                        break;
                    }
                    case "5": {
                        reserverVol();
                        break;
                    }
                    default: {
                        JOptionPane.showMessageDialog(null, "Choix invalide...", "ERREUR", JOptionPane.PLAIN_MESSAGE);
                        break;
                    }
                    case "0": {
                        ecrireFichier();
                        JOptionPane.showMessageDialog(null, "\nLe fichier a été mis à jour.\n\nAu revoir!\n\n", "FIN", JOptionPane.PLAIN_MESSAGE);
                        boucleMain = false;
                    }
                }
            }
        }
    }
}
