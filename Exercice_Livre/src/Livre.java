public class Livre {

    // Attributs d'instances, appartiennent aux objets.
    private int num;
    private String titre;
    private int pages;

    // Attributs de classe.
    public static int nbLivres = 0;

    // Les constructeurs.
    // Le constructeur par défaut.
    Livre() {
        nbLivres++;
    }
    // Le constructeur paramétré.
    Livre(int num, String titre, int pages) {
        this.num = num;
        this.titre = titre;
        this.pages = pages;
        nbLivres++;
    }
    // Le constructeur de copie.
    Livre(Livre unLivre) {
        this.num = unLivre.num;
        this.titre = unLivre.titre;
        this.pages = unLivre.pages;
        nbLivres++;
    }
    // Les "get"s. (Les getters : les accesseurs).
    public int getNum() {
        return this.num;
    }
    public String getTitre() {
        return this.titre;
    }
    public int getPages() {
        return this.pages;
    }
    // Les "set"s. (Les setters : les mutateurs).
    public void setNum(int num) {
        if (num > 0) { this.num = num; }
        else { System.out.println("Numéro de livre invalide."); }
    }
    public void setTitre(String titre) {
        this.titre = titre;
    }
    public void setPages(int pages) {
        if (pages > 0) { this.pages = pages; }
        else { System.out.println("Nombre de pages invalide."); }
    }
    // Retourner le contenu d'un objet selon un format voulu.
    public String toString() {
        return this.num + "\t" + this.titre + "\t" + this.pages;
    }

}
