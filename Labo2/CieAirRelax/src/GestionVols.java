import java.io.*;
import javax.swing.*;
import java.awt.*;

import java.util.ArrayList;
import java.util.List;
//import java.util.Arrays;
import java.util.stream.IntStream;


public class GestionVols {

    final static int MAX_PLACES = 340;
    final static int MAX_VOLS = 20;
    static final String FICHIER_VOLS = "src/donnees/Cie_Air_Relax.txt";
    static final String NOM_DE_CIE = "Cie Air Relax";
    static final String SEPARATEUR = "[;/]";

    //static ArrayList<Vol> tabVols; 
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
                // Vol(int numeroVol, String destination, Date date, int nbReservations)
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
        message.append("\n");
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
                String destination = JOptionPane.showInputDialog(null, "Destination :", "AJOUT D'UN VOL", JOptionPane.PLAIN_MESSAGE);
                if (destination == null) { return; }
                String jour = JOptionPane.showInputDialog(null, "Jour :", "AJOUT D'UN VOL", JOptionPane.PLAIN_MESSAGE);
                if (jour == null) { return; }
                String mois = JOptionPane.showInputDialog(null, "Mois :", "AJOUT D'UN VOL", JOptionPane.PLAIN_MESSAGE);
                if (mois == null) { return; }
                String an = JOptionPane.showInputDialog(null, "Année :", "AJOUT D'UN VOL", JOptionPane.PLAIN_MESSAGE);
                if (an == null) { return; }
                tabVols.add(new Vol(Integer.parseInt(numeroVol), destination, new Date(Integer.parseInt(jour), Integer.parseInt(mois), Integer.parseInt(an)), 0));
            }
        }
    }

    public static void retirerVol() throws Exception {
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

    public static void modifierDate() {

    }

    public static void reserverVol() {
        String message = "Numéro du vol :";
        String numeroVol = JOptionPane.showInputDialog(null, message, "RÉSERVATION D'UN VOL", JOptionPane.PLAIN_MESSAGE);
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

    public static void terminer() throws Exception{
        boucleMain = false;
        ecrireFichier();
        JOptionPane.showMessageDialog(null, "\nLe fichier a été mis à jour.\n\nAu revoir!\n\n", "FIN", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) throws Exception {
        chargerVols();
        JTextArea messageMenuPrincipal = new JTextArea();
        messageMenuPrincipal.append("\n\tGESTION DES VOLS\n\n");
        messageMenuPrincipal.append("(1) Liste des vols.\n");
        messageMenuPrincipal.append("(2) Ajouter un vol.\n");
        messageMenuPrincipal.append("(3) Retirer un vol.\n");
        messageMenuPrincipal.append("(4) Modifier une date de départ.\n");
        messageMenuPrincipal.append("(5) Réservation d'un vol.\n");
        messageMenuPrincipal.append("(0) Quitter ce programme.\n");
        messageMenuPrincipal.append("\n\tFaites votre choix :");
        while (boucleMain) {
            String choix = JOptionPane.showInputDialog(null, messageMenuPrincipal, NOM_DE_CIE, JOptionPane.PLAIN_MESSAGE);
            if (choix == null) {
                terminer();
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
                        terminer();
                    }
                }
            }
        }
    }
}
