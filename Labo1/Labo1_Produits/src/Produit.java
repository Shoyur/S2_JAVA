public class Produit {

    private int numero;
    private double prix;

    public static int nbTotalProduits = 0;
    public static double prixTotalProduits = 0;

    // Constructeur paramétré par défaut.
    Produit(int numero, double prix) {
        this.numero = numero;
        this.prix = prix;
        nbTotalProduits++;
        prixTotalProduits += prix;
    }

    // String.
    public String toString() {
        return this.numero + "\t" + this.prix;
        //return "Le produit no." + this.numero + " a un prix de " + String.format("%.2f", this.prix) + " $.";
    }

    // Les getters.
    public int getNumero() {
        return this.numero;
    }
    public double getPrix() {
        return this.prix;
    }

    // Les setters.
    public void setNumero(int numero) {
        this.numero = numero; 
    }     
    public void setPrix(double prix) {
        prixTotalProduits -= this.prix;
        prixTotalProduits += prix;
        this.prix = prix;
    }

}
