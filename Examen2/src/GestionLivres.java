import java.awt.Font;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;
import javax.swing.*;


public class GestionLivres {
	static final String FICHIER_LIVRES_TEXTE = "src/donnees/livres.txt";
	static final String FICHIER_LIVRES_OBJ = "src/donnees/livres.obj";
	static ArrayList<Livre> listeLivres;
    // static List<Livre> listeLivres; = new ArrayList<Livre>();
	static BufferedReader tmpLivresReadTxt;
	static BufferedWriter tmpLivresWriteTxt;
	static ObjectInputStream tmpLivresReadObj;
	static ObjectOutputStream tmpLivresWriteObj;

    static Map<Integer, Livre> biblioMap = new HashMap<Integer, Livre>();

    static Object obj;


    public static void chargerLivresObj() throws Exception {
		try {
			tmpLivresReadObj = new ObjectInputStream (new FileInputStream (FICHIER_LIVRES_OBJ));
			listeLivres = (ArrayList<Livre>) tmpLivresReadObj.readObject();
		}
        catch (Exception e) { 
			System.out.println("Un probléme est survenu lors du chargement du fichier objet.");
        }
        finally {
			tmpLivresReadObj.close();
		}
	}

    public static void chargerLivresTexte() throws Exception {
		try {
			String ligne;
			String elems[] = new String[6];
			listeLivres = new ArrayList<Livre>();
			tmpLivresReadTxt = new BufferedReader(new FileReader(FICHIER_LIVRES_TEXTE, StandardCharsets.UTF_8));
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

	public static void chargerFichierPourUnMap() {
		try{
			File f = new File(FICHIER_LIVRES_OBJ);

			if (f.exists()) {
				biblioMap = (HashMap<Integer, Livre>) chargerFichierObjets(FICHIER_LIVRES_OBJ);
			} else {
				ArrayList<ArrayList<String>> listeAttributs = chargerFichierTexte(FICHIER_LIVRES_TEXTE, ";");
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

    public static Object chargerFichierObjets(String fichier) throws Exception {
        try {
            tmpLivresReadObj = new ObjectInputStream(new FileInputStream(fichier));
            obj = tmpLivresReadObj.readObject();
            tmpLivresReadObj.close();
            return obj;
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable. Vérifiez le chemin et nom du fichier.");
        } catch (IOException e) {
            System.out.println("Un probléme est arrivé lors de la manipulation du fichier. Vérifiez vos données.");
        } catch (Exception e) {
            System.out.println("Un probléme est arrivé lors du chargement du fichier. Contactez l'administrateur.");
        } finally {// Exécuté si erreur ou pas
            tmpLivresReadObj.close();
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> chargerFichierTexte(String fichier, String delimiteurs) throws Exception {
        String ligne;
        // String elems[] = new String[];
        ArrayList<ArrayList<String>> listeAttributs = new ArrayList<ArrayList<String>>();
        ArrayList<String> attributs;
        try {
            tmpLivresReadTxt = new BufferedReader(new FileReader(fichier));
            StringTokenizer st;
            ligne = tmpLivresReadTxt.readLine();// Lire la premiére ligne du fichier
            while (ligne != null) {// Si ligne == null alors ont a atteint la fin du fichier
                st= new StringTokenizer(ligne, delimiteurs);
                attributs = new ArrayList<String>();
                while(st.hasMoreTokens()){
                     attributs.add(st.nextToken());
                }
                listeAttributs.add(attributs);// elems[0] contient le num�ro du livre;elems[1] le titre et elems[2] les pages
                // biblio.add(new Object(Integer.parseInt(elems[0]), elems[1], Integer.parseInt(elems[2]),
                //         Integer.parseInt(elems[3])));
                ligne = tmpLivresReadTxt.readLine();
            } // fin while

        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable. Vérifiez le chemin et nom du fichier.");
        } catch (IOException e) {
            System.out.println("Un probléme est arrivé lors de la manipulation du fichier. V�rifiez vos donn�es.");
        } catch (Exception e) {
            System.out.println("Un probléme est arrivé lors du chargement du fichier. Contactez l'administrateur.");
        } finally {// Exécuté si erreur ou pas
            tmpLivresReadTxt.close();
           
        }
        return listeAttributs;
    }





    
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

    public static void afficherLivres() {
        JTextArea message = new JTextArea(20, 120);
        JScrollPane scroll = new JScrollPane(message);
        message.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        // numéro;titre;auteur;année;pages;catégorie
        message.append("\nNo.\tTitre\t\t\t\t\t\t\t\tAuteur\tAnnée\tPages\tCatégorie\t\t\n\n");
        // if (trie == 1) { Collections.sort(listeLivres); }
		listeLivres.forEach((unLivre) -> {
			message.append(unLivre.toString());
            message.append("\n");
		});
        message.append("\n\n  Nombre total de livres = " + listeLivres.size() + ".\n\n");
        JOptionPane.showMessageDialog(null, scroll, "LISTE DES LIVRES", JOptionPane.PLAIN_MESSAGE);
    }

    public static void afficherLivresTries() {

        JTextArea message = new JTextArea(20, 120);
        JScrollPane scroll = new JScrollPane(message);
        message.setFont(new Font(Font.MONOSPACED, Font.PLAIN, 12));
        // numéro;titre;auteur;année;pages;catégorie
        message.append("\nNo.\tTitre\t\t\t\t\t\t\t\tAuteur\tAnnée\tPages\tCatégorie\t\t\n\n");

        ArrayList<Integer> listeClesTriees = new ArrayList<Integer>();
        listeClesTriees = trierClesHashMap(biblioMap);

        for(Integer cle : listeClesTriees){
            message.append(biblioMap.get(cle).toString());
            message.append("\n");
        }
            
        message.append("Nombre de livres = " + biblioMap.size());
        JOptionPane.showMessageDialog(null, scroll, null, JOptionPane.PLAIN_MESSAGE);
    }

    public static ArrayList<Integer> trierClesHashMap(Map<Integer, Livre> leMapAtrier){
		ArrayList<Integer> listeCles = new ArrayList<Integer>();
		for(Integer cle : leMapAtrier.keySet()) {
			listeCles.add(cle);
		}
		Collections.sort(listeCles);
		return listeCles;
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

    public static void supprimerUnLivre() {
        int num = Integer.parseInt(JOptionPane.showInputDialog(null, "Entrez le numéro", "ENLEVER UN LIVRE", JOptionPane.PLAIN_MESSAGE));
        listeLivres.removeIf((unLivre) -> unLivre.getNumero() == num);
    }

    public static void sauvegarderFichierObjets(Object obj, String fichier) throws IOException {
        try {
            tmpLivresWriteObj = new ObjectOutputStream(new FileOutputStream(fichier));
            tmpLivresWriteObj.writeObject(obj);
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable. Vérifiez le chemin et nom du fichier.");
        } catch (IOException e) {
            System.out.println("Un probléme est arrivé lors de la manipulation du fichier. Vérifiez vos données.");
        } catch (Exception e) {
            System.out.println("Un probléme est arrivé lors du chargement du fichier. Contactez l'administrateur.");
        } finally {// Exécuté si erreur ou pas
            tmpLivresWriteObj.close();
        }
    }

	public static void main(String[] args) throws Exception {
		int choix;
		File f = new File(FICHIER_LIVRES_OBJ);
		if (f.exists()) { chargerLivresObj(); }
        else { chargerLivresTexte(); }
		do {
			choix = menuGeneral();
            if (choix == 0) {
                JOptionPane.showMessageDialog(null, "Au revoir!", "FIN", JOptionPane.PLAIN_MESSAGE);
                System.exit(0);
            }
			switch (choix) {
				case 1:
					afficherLivres();
					break;
				case 2:
                    afficherLivresTries();
					break;
				case 3:
					afficherUnNumero();
					break;
				case 4:
					afficherParAuteur();
					break;
				case 5:
					supprimerUnLivre();
					break;
				case 6:
                    sauvegarderFichierObjets(biblioMap, FICHIER_LIVRES_OBJ);
					JOptionPane.showMessageDialog(null, "Au revoir!", "FIN", JOptionPane.PLAIN_MESSAGE);
					System.exit(0);
				default:
                    JOptionPane.showMessageDialog(null, "Choix invalide. Les option sont de 1 à 6...", "Erreur", JOptionPane.PLAIN_MESSAGE);
					break;
			}
		} while (choix != 6);
	}
}
