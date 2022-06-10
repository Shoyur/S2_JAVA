import java.io.*;
import javax.swing.*;

public class GestionProduits {

    static final int NB_MAX_PRODUITS = 40;
    static final String FICHIER_PRODUITS = "src/donnees/produits.txt";
    static Produit tabProduits[] = new Produit[NB_MAX_PRODUITS];
    static BufferedReader tempProduits;

    public static void chargerProduits() throws Exception {
        int i=0;
        String ligne;
        String elems[] = new String[2];
        tempProduits = new BufferedReader(new FileReader(FICHIER_PRODUITS));
        ligne = tempProduits.readLine();
        while (i < NB_MAX_PRODUITS && ligne != null) {
            elems = ligne.split(";");
            tabProduits[i++] = new Produit(Integer.parseInt(elems[0]), Double.parseDouble(elems[1]));
            ligne = tempProduits.readLine();
        }
    }
    
    public static void afficherListeProduits() throws Exception {
        JTextArea sortie = new JTextArea();
		sortie.append("Numéro\tPrix\n\n");
		for (int i = 0; i < Produit.nbTotalProduits; i++) {
			sortie.append(tabProduits[i].getNumero() + "\t" + tabProduits[i].getPrix() +"\n");
		}
		JOptionPane.showMessageDialog(null, sortie, "Inventaire", JOptionPane.PLAIN_MESSAGE);
    }

    public static void main(String[] args) throws Exception {
        chargerProduits();
        afficherListeProduits();
    }

}
