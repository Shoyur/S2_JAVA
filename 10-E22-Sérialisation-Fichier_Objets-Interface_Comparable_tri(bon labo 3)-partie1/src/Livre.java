import java.io.*;
public class Livre implements Serializable, Comparable<Livre>{

	private static final long serialVersionUID = 2040890116313738088L;

	String tabCategs[] = {"Horreur","Drame","Comédie","Jeunesse","Poésie","Biographie","Cuisine"};
	
	//Attributs d'instances
	private int num;
	private String titre;
	private int pages;
	private int categorie;//0, 1, 2 ou 3
	
	public static int nbLivres = 0; //Attribut de classe
	
	//Les constructeurs
	Livre(){ //Par défaut
		++nbLivres;
	}
	
	Livre(int num, String titre, int pages, int categorie){ //Paramétré
		this.setNum(num);
		this.titre = titre;
		this.setPages(pages);
		this.setCategorie(categorie);
		++nbLivres;
	}
	
	//Les accesseurs (getter)
	public int getNum() {
		return this.num;
	}
	
	public String getTitre() {
		return this.titre;
	}
	
	public int getPages() {
		return this.pages;
	}
	
	public int getCategorie() {
		return this.categorie;
	}
	//Les mutateurs (setter)
	
	public void setNum(int num) {
		if(num > 0) {
			this.num = num;
		} else {
			System.out.println("Numéro de livre invalide !");
		}
	}
	
	public void setTitre(String titre) {
		this.titre = titre;
	}
	
	public void setPages(int pages) {
		if(pages > 0) {
			this.pages = pages;
		} else {
			System.out.println("Nombre de pages invalide !");
		}
	}

	public void setCategorie(int categorie){
		int nbCategs = tabCategs.length;
		if(categorie >= 0 && categorie < nbCategs){
			this.categorie = categorie; 
		} else {
			System.out.println("Catégorie invalide !");
		}
	}

	public String getCategorieString(){
		return tabCategs[this.categorie];
	}

	public boolean equals(Object obj) {
		Livre autreLivre = (Livre) obj;
		if (this.num == autreLivre.num) {
			return true;
		} else {
			return false;
		}
	}
	public int compareTo(Livre unLivre){
		//Par num livre
		//return (int) (this.num - unLivre.num);//En ordre croissant
		//return (int) (unLivre.num - this.num);//En ordre décroissant
		//Par titre
		return this.titre.compareTo(unLivre.titre);
	}
	
	//Retourner le contenu d'un objet selon un format voulu
	public String toString() {
		return (this.titre.length() > 17) ? this.num+"\t"+this.titre+"\t"+this.pages+"\t"+getCategorieString()+"\n" : this.num+"\t"+this.titre+"\t\t"+this.pages+"\t"+getCategorieString()+"\n";
	}
}

