import java.io.*;
import javax.swing.*;
import java.util.Arrays;

public class GestionVols {

    final static int MAX_PLACES = 340;
    final static int MAX_VOLS = 20;
    static final String FICHIER_VOLS = "src/donnees/Cie_Air_Relax.txt";
    static Vol tabVols[] = new Vol[MAX_VOLS];
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
            tabVols[i++] = new Vol(Integer.parseInt(elems[0]), elems[1], new Date(Integer.parseInt(elems[2]), Integer.parseInt(elems[3]), Integer.parseInt(elems[4])), Integer.parseInt(elems[5]));
            ligne = tempVols.readLine();
        }

    }

    public static void insererVol() {
        if (Vol.nombreVols >= MAX_VOLS) {
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
                tabVols[Vol.nombreVols + 1] = new Vol(numeroVol, destination, new Date(jour, mois, an), nbReservations);
            }
        }
    }

    public static void retirerVol() {
        String numeroVol = JOptionPane.showInputDialog(null, "Numéro du vol :", "RETRAIT D'UN VOL", JOptionPane.PLAIN_MESSAGE);
        int position = rechercherVol(Integer.parseInt(numeroVol));
        if (position == -1) {
            String message = "Ce numéro de vol n'existe pas!";
            JOptionPane.showMessageDialog(null, message, "ERREUR", JOptionPane.PLAIN_MESSAGE);
        }
        else {
            JTextArea message = new JTextArea();
            message.append("Numéro de vol : " + numeroVol + "\n");

            if JOptionPane.showInputDialog
            /*

            // enlever l'element 

            tabVols[position] = 0; 

             */
            
            Vol.nombreVols--;
        }
    }

    public static int rechercherVol(int numeroVol) {
        int resultat = -1;
        for (int i = 0; i < Vol.nombreVols; i++) {
            if (tabVols[i].getNumeroVol() == numeroVol) {
                resultat = i;
                break;
            }
        }
        return resultat;
    }

    public static void main(String[] args) throws Exception {
        chargerVols();
    }
}
