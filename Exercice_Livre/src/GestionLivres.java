import java.io.*;

public class GestionLivres {
    static final int NBLIVRES = 30;
    static final String FICHIER_LIVRES = "src/donnees/livres.txt";
    static Livre biblio[] = new Livre[NBLIVRES];
    static BufferedReader tmpLivres;

    public static void chargerLivres() throws Exception {
        try {
            int i=0;
            String ligne;
            String elems[] = new String[3];
            tmpLivres = new BufferedReader(new FileReader(FICHIER_LIVRES));
            ligne = tmpLivres.readLine();
            while (i < NBLIVRES && ligne != null) {
                elems = ligne.split(";");
                biblio[i++] = new Livre(Integer.parseInt(elems[0]), elems[1], Integer.parseInt(elems[2]));
                ligne = tmpLivres.readLine();
            }
        } catch (Exception e) {
            System.out.println("Erreur : " + e);
        } finally {
            tmpLivres.close();
        }
    }
    
    public static void afficherListeLivres() throws Exception {
    
    }

    public static void main(String[] args) throws Exception {
        chargerLivres();
        afficherListeLivres();
    }
}
