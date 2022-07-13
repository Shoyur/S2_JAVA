import java.awt.Font;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.swing.*;

import java.awt.Font;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.swing.*;

public class GestionLivres {
	static final String FICHIER_LIVRES_TEXTE = "src/donnees/livres.txt";
	static final String FICHIER_LIVRES_OBJ = "src/donnees/livres.obj";
	static JTextArea sortie;
	static List<Livre> biblioList = new ArrayList<Livre>();
	//static Map<Integer, Livre> biblioMap = new TreeMap<Integer, Livre>();
	static Map<Integer, Livre> biblioMap = new HashMap<Integer, Livre>();

	static List<Livre> listeLivres = new ArrayList<Livre>();
	static BufferedReader tmpLivresReadTxt;
	static BufferedWriter tmpLivresWriteTxt;

	public static int menuGeneral() {
        String choix = "";
		String contenu = "\n";
        contenu += "1) Afficher tous les livres\n";
        contenu += "2) Afficher tous les livres triés\n";
        contenu += "3) Afficher par numéro\n";
        contenu += "4) Pour un auteur donné\n";
        contenu += "5) Supprimer un livre\n";
        contenu += "6) Terminer\n\n";
		contenu += "Faites votre choix : ";
		choix = JOptionPane.showInputDialog(null, contenu, "MENU GESTION LIVRES", JOptionPane.PLAIN_MESSAGE);
        if (choix == null) { return 0; }
        else { return Integer.parseInt(choix); }
	}

    public static void chargerLivresTexte() throws Exception {
		try {
			String ligne;
			String elems[] = new String[6];
			listeLivres = new ArrayList<Livre>();
			tmpLivresReadTxt = new BufferedReader(new FileReader(FICHIER_LIVRES_TEXTE));
			// tmpLivresReadTxt = new BufferedReader(new FileReader(FICHIER_LIVRES_TEXTE, StandardCharsets.UTF_8));
			ligne = tmpLivresReadTxt.readLine();
			while (ligne != null) {
				elems = ligne.split(";");
				listeLivres.add(new Livre(Integer.parseInt(elems[0]), elems[1], elems[2], Integer.parseInt(elems[3]), Integer.parseInt(elems[4]), elems[5]));
				ligne = tmpLivresReadTxt.readLine();
			}
		}
        catch (Exception e) { 
			System.out.println("Un probléme est survenu lors du chargement du fichier texte.");
        }
        finally {
			tmpLivresReadTxt.close();
		}
	}
	

	public static void afficherEntete() {
		sortie = new JTextArea(20, 50);
		sortie.setFont(new Font("monospace", Font.PLAIN, 12));
		sortie.append("\t\tLISTE DES LIVRES\n\n");
		sortie.append("NUMÉRO\tTITRE\t\tPAGES\tCATÉGORIE\n");
	}

	public static ArrayList<Integer> trierClesHashMap(Map<Integer, Livre> leMapAtrier){
		ArrayList<Integer> listeCles = new ArrayList<Integer>();
		for(Integer cle : leMapAtrier.keySet()) {
			listeCles.add(cle);
		}
		Collections.sort(listeCles); 
		return listeCles;
	}

	public static void listerLivresMapTrie() {
		ArrayList<Integer> listeClesTriees = new ArrayList<Integer>();
		listeClesTriees = trierClesHashMap(biblioMap);
		afficherEntete();
		for(Integer cle : listeClesTriees){
			sortie.append(biblioMap.get(cle).toString());
		}

		sortie.append("Nombre de livres = " + biblioMap.size());
		JOptionPane.showMessageDialog(null, sortie, null, JOptionPane.PLAIN_MESSAGE);
	}

	public static void listerLivresMap() {
		afficherEntete();
		for(Integer cle : biblioMap.keySet()) {
			sortie.append(biblioMap.get(cle).toString());
		}

		sortie.append("Nombre de livres = " + biblioMap.size());
		JOptionPane.showMessageDialog(null, sortie, null, JOptionPane.PLAIN_MESSAGE);
	}

	public static void afficherMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg, "MESSAGES", JOptionPane.PLAIN_MESSAGE);
	}

	public static void enleverUnLivreMap() {
		int num = Integer
				.parseInt(JOptionPane.showInputDialog(null, "Entrez le numéro", "ENLEVER UN LIVRE",
						JOptionPane.PLAIN_MESSAGE));
		if(biblioMap.containsKey(num)){
			biblioMap.remove(num);
		} else {
			afficherMessage("Livre introuvable");
		}
	}

	static int cptCategs = 0;



	public static Livre rechercherLivreList(int num) {
		int pos;
		Livre livreBidon = new Livre();
		livreBidon.setNumero(num);
		pos = biblioList.indexOf(livreBidon);// -1 si pas trouvé
		if (pos == -1) {
			afficherMessage("Le numéro du livre est introuvable.");
			return null;
		}
		Livre livreTrouve = biblioList.get(pos);
		return livreTrouve;
	}		

	static String ligne = "";

	public static void chargerFichierPourUnMap() {
		try{
			File f = new File(FICHIER_LIVRES_OBJ);

			if (f.exists()) {
				biblioMap = (HashMap<Integer, Livre>) Utilitaires.chargerFichierObjets(FICHIER_LIVRES_OBJ);
			} else {
				ArrayList<ArrayList<String>> listeAttributs = Utilitaires.chargerFichierTexte(FICHIER_LIVRES_TEXTE, ";");
				listeAttributs.forEach((donneesLivre) -> {
					int numero;
					String titre;
                    String auteur;
                    int annee;
					int pages;
					String categorie;
					numero = Integer.parseInt(donneesLivre.get(0));
					titre = donneesLivre.get(1);
					auteur = donneesLivre.get(2);
					annee = Integer.parseInt(donneesLivre.get(3));
                    pages = Integer.parseInt(donneesLivre.get(4));
                    categorie = donneesLivre.get(5);
                    // numéro;titre;auteur;année;pages;catégorie
					biblioMap.put(numero, new Livre(numero, titre, auteur, annee, pages, categorie));
				});
				biblioMap = ((TreeMap<Integer, Livre>) biblioMap).descendingMap();//Notre Map en ordre décroissant
			}
		}catch(Exception e){
			System.out.println("Problème");
		}
	}

	public static void afficherUnNumero() {
        String numero= JOptionPane.showInputDialog(null, "Entrez le numéro du livre", "AFFICHAGE PAR NUMERO", JOptionPane.PLAIN_MESSAGE);
        JTextArea message = new JTextArea();
        message.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        // numéro;titre;auteur;année;pages;catégorie
        message.append("\nNo.\tTitre\t\t\t\t\t\t\t\tAuteur\tAnnée\tPages\tCatégorie\t\t\n\n");
		listeLivres.forEach((unLivre) -> {
            if (unLivre.getNumero() == Integer.parseInt(numero)) {
                message.append(unLivre.toString());
                message.append("\n");
            }
		});
        JOptionPane.showMessageDialog(null, message, "INFO POUR LE LIVRE NO." + numero, JOptionPane.PLAIN_MESSAGE);
    }

    public static void afficherParAuteur() {
        String auteur = JOptionPane.showInputDialog(null, "Entrez le numéro de l'auteur", "AFFICHAGE PAR AUTEUR", JOptionPane.PLAIN_MESSAGE);
        // int total_auteur = 0;
        JTextArea message = new JTextArea();
        message.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        // numéro;titre;auteur;année;pages;catégorie
        message.append("\nNo.\tTitre\t\t\t\t\t\t\t\tAuteur\tAnnée\tPages\tCatégorie\t\t\n\n");
		listeLivres.forEach((unLivre) -> {
            if (unLivre.getAuteur().equals(auteur)) {
                message.append(unLivre.toString());
                message.append("\n");
                // total_auteur += 1;
            }
		});
        // message.append("\n\n  Nombre total de livres pour cet auteur = " + total_auteur + ".\n\n");
        JOptionPane.showMessageDialog(null, message, "LISTE DES LIVRES PAR L'AUTEUR " + auteur, JOptionPane.PLAIN_MESSAGE);
    }

	public static void main(String[] args) throws Exception {
		int choix;
		File f = new File(FICHIER_LIVRES_OBJ);
		if (f.exists()) { chargerFichierPourUnMap(); }
		else { chargerLivresTexte(); }
		do {
			choix = menuGeneral();
			if (choix == 0) {
				JOptionPane.showMessageDialog(null, "Au revoir!", "FIN", JOptionPane.PLAIN_MESSAGE);
				System.exit(0);
			}
			switch (choix) {
				case 1:
					listerLivresMap();
					break;
				case 2:
					listerLivresMapTrie();
					break;
				case 3:
					afficherUnNumero();
					break;
				case 4:
					afficherParAuteur();
					break;
				case 5:
					enleverUnLivreMap();
					break;
				case 6:
					Utilitaires.sauvegarderFichierObjets(biblioMap, FICHIER_LIVRES_OBJ);
					JOptionPane.showMessageDialog(null, "Au revoir!", "FIN", JOptionPane.PLAIN_MESSAGE);
					System.exit(0);
				default:
					JOptionPane.showMessageDialog(null, "Choix invalide. Les option sont de 1 à 6...", "Erreur", JOptionPane.PLAIN_MESSAGE);
					break;
			}
		} while (choix != 6);
	}
}