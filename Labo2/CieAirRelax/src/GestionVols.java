import java.io.*;
import javax.swing.*;

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

    public static void insererVol(int numeroVol, String destination, Date date, int nbReservations) {
        if (Vol.nombreVols < MAX_VOLS) {
            
        }
        else {
            // Erreur nombre maximal de vols atteint.
        }
    }

    public static void main(String[] args) throws Exception {
        chargerVols();
    }
}
