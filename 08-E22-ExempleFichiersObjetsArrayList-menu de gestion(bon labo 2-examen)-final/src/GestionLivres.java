import java.awt.Font;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

public class GestionLivres {
	static final String FICHIER_LIVRES = "src/donnees/livres.txt";
	static ArrayList<Livre> biblio; 
	static BufferedReader tmpLivresRead;
	static BufferedWriter tmpLivresWrite; 
	static JTextArea sortie;

	public static int menuGeneral(){
		String contenu="1-Lister\n2-Ajouter un livre\n3-Enlever un livre\n4-Lister par catégorie\n5-Modifier un livre\n6-Terminer\n\n";
		contenu+="Entrez votre choix parmis[1-6] : ";
		return Integer.parseInt(JOptionPane.showInputDialog(null, contenu, "MENU GESTION LIVRES",JOptionPane.PLAIN_MESSAGE));
	}
	
	public static int menuModifier() {
		String contenu = "1-Titre\n2-Pages\n3-Catégorie\n4-Terminer\n\n";
		contenu += "Entrez votre choix parmis[1-4] : ";
		return Integer
				.parseInt(JOptionPane.showInputDialog(null, contenu, "MENU GESTION LIVRES", JOptionPane.PLAIN_MESSAGE));
	}

	public static void chargerLivres() throws Exception {
		try {
			String ligne;
			String elems[] = new String[4];
			biblio = new ArrayList<Livre>();
			tmpLivresRead = new BufferedReader(new FileReader(FICHIER_LIVRES));
			ligne = tmpLivresRead.readLine();//Lire la premiére ligne du fichier
			while (ligne != null) {//Si ligne == null alors ont a atteint la fin du fichier
				elems = ligne.split(";");//elems[0] contient le num�ro du livre;elems[1] le titre et elems[2] les pages
				biblio.add(new Livre(Integer.parseInt(elems[0]), elems[1], Integer.parseInt(elems[2]), Integer.parseInt(elems[3])));
				ligne = tmpLivresRead.readLine();
			}//fin while
			
		} catch (FileNotFoundException e) {
			System.out.println("Fichier introuvable. Vérifiez le chemin et nom du fichier.");
		}
		catch (IOException e) {
			System.out.println("Un probléme est arrivé lors de la manipulation du fichier. V�rifiez vos donn�es.");
		}catch (Exception e) { 
			System.out.println("Un probléme est arrivé lors du chargement du fichier. Contactez l'administrateur.");
		}finally {//Exécuté si erreur ou pas
			tmpLivresRead.close();
		}
	}
	
	public static void afficherEntete(){
		sortie = new JTextArea();
		sortie.setFont(new Font("monospace", Font.PLAIN, 12));
		sortie.append("\t\tLISTE DES LIVRES\n\n");
		sortie.append("NUMÉRO\tTITRE\t\tPAGES\tCATÉGORIE\n");
	}

	public static void listerLivres() {
		//double total=0;
		afficherEntete();
		biblio.forEach((unLivre) -> {
			sortie.append(unLivre.toString());
			//Code de la fonction. Pour obtenir une valeur de l'objet unLivre
			//vous devez faire appel à une des méthodes getXXX de la classe.
			//Exemple si un livre avait un prix. Calculer le total de tous les livres
			//total += unLivre.getPrix();
			//Pour calculer le total de tous les livres Jeunesse
			// if(unLivre.getCategorie() == 3){total += unLivre.getPrix();}
		});
		sortie.append("Nombre de livres = "+biblio.size());	
		JOptionPane.showMessageDialog(null, sortie, null, JOptionPane.PLAIN_MESSAGE);
	}

	public static void afficherMessage(String msg){
		JOptionPane.showMessageDialog(null, msg, "MESSAGES", JOptionPane.PLAIN_MESSAGE);
	}

	public static void ajouterUnLivre() {
		int num = Integer
				.parseInt(JOptionPane.showInputDialog(null, "Entrez le numéro", "AJOUTER UN LIVRE", JOptionPane.PLAIN_MESSAGE));
		String titre = JOptionPane.showInputDialog(null, "Entrez le titre", "AJOUTER UN LIVRE",
						JOptionPane.PLAIN_MESSAGE);
		int pages = Integer
				.parseInt(JOptionPane.showInputDialog(null, "Entrez le nombre de pages", "AJOUTER UN LIVRE", JOptionPane.PLAIN_MESSAGE));
		int categorie = Integer
				.parseInt(JOptionPane.showInputDialog(null, "Entrez la catégorie", "AJOUTER UN LIVRE", JOptionPane.PLAIN_MESSAGE));
		biblio.add(new Livre(num, titre, pages, categorie));
	} 

	public static void enleverUnLivre() {
		int num = Integer
				.parseInt(JOptionPane.showInputDialog(null, "Entrez le numéro", "ENLEVER UN LIVRE", JOptionPane.PLAIN_MESSAGE));
		biblio.removeIf((unLivre) -> unLivre.getNum() == num);
	}

	static int cptCategs = 0;

	public static void listerLivresParCategorie() {
		int categorie = Integer
				.parseInt(JOptionPane.showInputDialog(null, "Entrez la catégorie", "LISTER DES LIVRES PAR CATÉGORIE",
						JOptionPane.PLAIN_MESSAGE));
		afficherEntete();

		biblio.forEach((unLivre) -> {
			if(unLivre.getCategorie() == categorie){
				sortie.append(unLivre.toString());
				++cptCategs;
			}
		});	
		sortie.append("Nombre de livres = " + cptCategs);
		JOptionPane.showMessageDialog(null, sortie, null, JOptionPane.PLAIN_MESSAGE);
	}

	public static Livre rechercherLivre(int num){
		int pos;
		Livre livreBidon = new Livre();
		livreBidon.setNum(num);
		pos = biblio.indexOf(livreBidon);// -1 si pas trouvé
		if (pos == -1){
			afficherMessage("Le numéro du livre est introuvable.");
			return null;
		}
		Livre livreTrouve = biblio.get(pos);
		return livreTrouve;
	}

	public static void modifierUnLivre(){
		int choix;
		int num = Integer
				.parseInt(JOptionPane.showInputDialog(null, "Entrez le numéro", "MODIFIER UN LIVRE", JOptionPane.PLAIN_MESSAGE));
		Livre livreTrouve = rechercherLivre(num);
		if(livreTrouve == null){
			return;
		}
		do{
			choix = menuModifier();
			switch(choix){
				case 1 :
				   String titre = JOptionPane.showInputDialog(null, "Entrez le nouveau titre", "AJOUTER UN LIVRE",
						JOptionPane.PLAIN_MESSAGE);
					livreTrouve.setTitre(titre);
					break;
				case 2 :
					int pages = Integer
					.parseInt(JOptionPane.showInputDialog(null, "Entrez le nouveau nombre de pages", "MODIFIER UN LIVRE", JOptionPane.PLAIN_MESSAGE));
					livreTrouve.setPages(pages);
					break;
				case 3 :
					int categorie = Integer
					.parseInt(JOptionPane.showInputDialog(null, "Entrez la nouvelle catégorie", "MODIFIER UN LIVRE", JOptionPane.PLAIN_MESSAGE));
					livreTrouve.setCategorie(categorie);
					break;
				case 4:
					break;
				default :
					afficherMessage("Choix invalide. Les option sont [1-4] !");
					break;

			}
		} while(choix != 4);
	}
	
	static String ligne="";
	public static void sauvegarderLivres() throws IOException{
		
		// Utiliser ceci pour ajouter des données à la fin du fichier
		// (FICHIER_LIVRES, true)
		tmpLivresWrite = new BufferedWriter(new FileWriter(FICHIER_LIVRES));
		biblio.forEach((unLivre) -> {
			ligne=unLivre.getNum()+";"+unLivre.getTitre()+";"+unLivre.getPages()+";"+unLivre.getCategorie();
			try {
				tmpLivresWrite.write(ligne);
				tmpLivresWrite.newLine();
			} catch (IOException e) {
				System.out.println("Problème d'écriture dans le fichier");
			}
		});
		tmpLivresWrite.close();
	}

	public static void main(String[] args) throws Exception {
		int choix;
		chargerLivres();
		do {
			choix = menuGeneral();
			switch(choix){
				case 1 : 
					listerLivres();
					break;
				case 2 : 
					ajouterUnLivre();
					break;
				case 3 : 
					enleverUnLivre();
					break;
				case 4 : 
					listerLivresParCategorie();
					break;
				case 5 : 
					modifierUnLivre();
					break;
				case 6 :
					sauvegarderLivres();
					afficherMessage("Merci d'avoir utilisé notre système");
					break;
				default :
					afficherMessage("Choix invalide. Les option sont [1-6] !");
					break;
			}
		} while(choix != 6);
	}
}
