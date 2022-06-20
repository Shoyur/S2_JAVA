
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Utilitaires {
	static BufferedReader tmpFic;
	static ObjectOutputStream ficObjEcrire;
	static ObjectInputStream ficObjLire;
	
	public static final String MSG1 = " ne peut pas être inférieure à l'année actuelle, soit ";
	public static final String MSG2 = "Impossible de valider le jour puisque votre mois est invalide";
	public static final String FICHIER_OBJETS_FILM = "src/donnees/films.obj";
	public static final String FICHIER_DONNEES = "src/donnees/livres.txt";
	
	public static void chargerFichierText(String fichier, String delimiteur, List<Object> listeLivres, char typeObjet) throws Exception {
			try {
				
				String elems[] = new String[20];
				tmpFic = new BufferedReader(new FileReader(fichier));
				String ligne = tmpFic.readLine();//Lire la première ligne du fichier
				while (ligne != null) {//Si ligne == null alors ont a atteint la fin du fichier
					elems = ligne.split(delimiteur);//elems[0] contient le numéro du livre;elems[1] le titre et elems[2] les pages
					if(typeObjet == 'L') {
						listeLivres.add(new Livre(Integer.parseInt(elems[0]), elems[1], Integer.parseInt(elems[2])));
					}
					ligne = tmpFic.readLine();
				}//fin while
				
			} catch (FileNotFoundException e) {
				System.out.println("Fichier introuvable. Vérifiez le chemin et nom du fichier.");
			}
			catch (IOException e) {
				System.out.println("Un problème est arrivé lors de la manipulation du fichier. Vérifiez vos données.");
			}catch (Exception e) { 
				System.out.println("Un problème est arrivé lors du chargement du fichier. Contactez l'administrateur.");
			}finally {//Exécuté si erreur ou pas
				tmpFic.close();
			}
	}
		
	/*public static void enregistrerObjetsFilms(String fichierObj, Map<Integer, Film> listeFilms) throws IOException {
		ficObjEcrire = new ObjectOutputStream (new FileOutputStream (fichierObj));
		for(Integer cle : listeFilms.keySet()) {
			ficObjEcrire.writeObject(listeFilms.get(cle));
		}
		ficObjEcrire.close();
	}
	
	
	public static void chargerFichierObjet(String fichierObj, Map<Integer, Film> listeFilms) throws IOException {
		
			ficObjLire = new ObjectInputStream (new FileInputStream (fichierObj));
			listeFilms.clear();
			Film unFilm;
			try {
				while(true) {//Pour determiner la fin du ficher. On sert par une IOException
					unFilm = (Film) ficObjLire.readObject();
					listeFilms.put(unFilm.getNumFilm(), unFilm);
				}
			}catch (IOException e) {
				//Rien à faire. Tout le fichier a été lu
			}catch (Exception e){
				System.out.println("Erreur d'entrée/sortie du fichier "+fichierObj);
			}finally{
				ficObjLire.close();
			}
	}*/
	
	public static String ajouterEspacesFin(int tailleColMax, String chaine) {
		int nbEspaces=tailleColMax-chaine.length();
		String espaces="";
		for(int i=0;i < nbEspaces; i++) {
			espaces+=" ";
		}
		System.out.println(espaces.length()+"==>"+chaine.length()+"==>"+(espaces.length()+chaine.length()));
		return espaces;
	}
	
	public static String ajouterEspacesDebut(int tailleColMax, String chaine) {
		int nbEspaces=(tailleColMax-chaine.length())/2;
		String espaces="";
		for(int i=0;i < nbEspaces; i++) {
			espaces+=" ";
		}
		return espaces;
	}
	
	public static String ajouterCaractereGauche(char car, int longueur, String ch) {
		String rep="";
		int nbCar = longueur-ch.length();
		for (int i=1; i<=nbCar; i++) {
			rep+=car;
		}
		return rep+ch;
	}
}

