import java.io.*;
import javax.swing.*;

import java.util.ArrayList;
import java.util.Arrays;


public class GestionVols {

    final static int MAX_PLACES = 340;
    final static int MAX_VOLS = 20;
    static final String FICHIER_VOLS = "src/donnees/Cie_Air_Relax.txt";
    static final String NOM_DE_CIE = "CIE AIR RELAX";
    static ArrayList<Vol> tabVols; 
    static BufferedReader tempVols;

    public static void chargerVols() throws Exception {
        int i=0;
        String ligne;
        String elems[] = new String[6];
        tempVols = new BufferedReader(new FileReader(FICHIER_VOLS));
        ligne = tempVols.readLine();
        while (i < MAX_VOLS && ligne != null) {
            elems = ligne.split(" ");
            // Vol(int numeroVol, String destination, Date date, int nbReservations)
            tabVols.add(new Vol(Integer.parseInt(elems[0]), elems[1], new Date(Integer.parseInt(elems[2]), Integer.parseInt(elems[3]), Integer.parseInt(elems[4])), Integer.parseInt(elems[5])));
            ligne = tempVols.readLine();
        }

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

    public static void afficherVols() {
        JTextArea message = new JTextArea();
        message.append("\nNo. de vol\tDate\tNb. de réservations\tDestination\n\n");
        for (Vol vol : tabVols) {
            message.append(vol.getNumeroVol() + "\t" + vol.getDate() + "\t" + vol.getNbReservations() + "\t\t" + vol.getDestination() + "\n");
        }
        message.append("\n");
        JOptionPane.showMessageDialog(null, message, "ERREUR", JOptionPane.PLAIN_MESSAGE);
    }

    public static void insererVol() {
        if (tabVols.size() >= MAX_VOLS) {
            String message = "Le nombre maximal de vols a été atteint!";
            JOptionPane.showMessageDialog(null, message, "ERREUR", JOptionPane.PLAIN_MESSAGE);
        }
        else {
            String numeroVol = JOptionPane.showInputDialog(null, "Numéro du vol :", "AJOUT D'UN VOL", JOptionPane.PLAIN_MESSAGE);
            if (rechercherVol(Integer.parseInt(numeroVol)) != -1) {
                String message = "Ce numéro de vol existe déjà!";
                JOptionPane.showMessageDialog(null, message, "ERREUR", JOptionPane.PLAIN_MESSAGE);
            }
            else {
                tabVols.add(new Vol(numeroVol, destination, new Date(jour, mois, an), nbReservations));
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
            // J'ai compris que le 1er bouton est 0, et les autres... et peser X retourne -1
            // sur https://hajsoftutorial.com/showoptiondialog-returns/
            if (choix == 0) {
                tabVols.remove(position);             
            }
        }
    }

    public static void modifierDate() {
        
    }

    public static void reserverVol() {
        
    }

    public static void main(String[] args) throws Exception {
        chargerVols();
        JTextArea messageMenuPrincipal = new JTextArea();
        messageMenuPrincipal.append("\n\tGESTION DES VOLS\n\n");
        messageMenuPrincipal.append("(1) Liste des vols.\n");
        messageMenuPrincipal.append("(2) Ajouter un vol.\n");
        messageMenuPrincipal.append("(3) Retirer un vol.\n");
        messageMenuPrincipal.append("(4) Modification de la date de départ.\n");
        messageMenuPrincipal.append("(5) Réservation d'un vol.\n");
        messageMenuPrincipal.append("(0) Quitter ce programme.\n");
        messageMenuPrincipal.append("\n\tFaites votre choix :");
        boolean boucleMain = true;
        while (boucleMain) {
            String choix = JOptionPane.showInputDialog(null, messageMenuPrincipal, NOM_DE_CIE, JOptionPane.PLAIN_MESSAGE);
            switch (choix) {
                case "1": {
                    afficherVols();
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
                case "0": {
                    boucleMain = false;
                    break;
                }
                default: {
                    JOptionPane.showMessageDialog(null, "Choix invalide...", "ERREUR", JOptionPane.PLAIN_MESSAGE);
                }
            }
        }
        retirerVol();
    }
}
