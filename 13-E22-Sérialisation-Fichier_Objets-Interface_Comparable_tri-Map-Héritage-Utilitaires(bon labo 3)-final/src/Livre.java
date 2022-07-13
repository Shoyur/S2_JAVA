import java.io.*;

public class Livre implements Serializable {

	private static final long serialVersionUID = 2040890116313738088L;

    // Attributs d'instances, appartiennent aux objets.
    private int numero;
    private String titre;
    private String auteur;
    private int annee;
    private int pages;
    private String categorie;

    // Attributs de classe.
    public static int nbLivres = 0;

    // Les constructeurs.
    // Le constructeur par défaut.
    Livre() {
        nbLivres++;
    }
    // Le constructeur paramétré.
    // numéro;titre;auteur;année;pages;catégorie
    Livre(int numero, String titre, String auteur, int annee, int pages, String categorie) {
        this.numero = numero;
        this.titre = titre;
        this.auteur = auteur;
        this.annee = annee;
        this.pages = pages;
        this.categorie = categorie;
        nbLivres++;
    }
    // Le constructeur de copie.
    Livre(Livre unLivre) {
        this.numero = unLivre.numero;
        this.titre = unLivre.titre;
        this.pages = unLivre.pages;
        nbLivres++;
    }

    // Les "get"s. (Les getters : les accesseurs).
    public int getNumero() {
        return this.numero;
    }
    public String getTitre() {
        return this.titre;
    }
    public String getAuteur() {
        return this.auteur;
    }
    public int getAnnee() {
        return this.annee;
    }
    public int getPages() {
        return this.pages;
    }
    public String getCategorie() {
        return this.categorie;
    }

    // Les "set"s. (Les setters : les mutateurs).
    public void setNumero(int numero) {
        if (numero > 0) { this.numero = numero; }
        else { System.out.println("Numéro de livre invalide."); }
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public void setAuteur(String auteur) {
        this.auteur = auteur;
    }
    public void setAnnee(int annee) {
        if (annee > 0) { this.annee = annee; }
        else { System.out.println("L'année doit être plus grande que 0."); }
    }
    public void setPages(int pages) {
        if (pages > 0) { this.pages = pages; }
        else { System.out.println("Nombre de pages invalide."); }
    }
    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    // Retourner le contenu d'un objet selon un format voulu.
    // numéro;titre;auteur;année;pages;catégorie
    public String toString() {
        return this.numero + "\t" + String.format("%-60s", this.titre) + "\t" + this.auteur + "\t" + this.annee + "\t" + this.pages + "\t" + this.categorie;
    }
}
