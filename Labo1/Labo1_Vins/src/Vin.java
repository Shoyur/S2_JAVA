public class Vin {

    private String nom;
    private int type;
    private String origine;
    private double prix;

    final public static String type1 = "rouge";
    final public static String type2 = "blanc";
    final public static String type3 = "rosé";

    public static int nbTotalVins = 0;
    public static double prixTotalVins = 0;

    // Constructeur paramétré par défaut.
    Vin(String nom, int type, String origine, double prix) {
        this.nom = nom;
        this.type = type;
        this.origine = origine;
        this.prix = prix;
        nbTotalVins++;
        prixTotalVins += prix;
    }
    // Constructeur (surcharge) sans le type (donc vin rouge par défaut).
    Vin(String nom, String origine, double prix) {
        this.nom = nom;
        this.type = 1;
        this.origine = origine;
        this.prix = prix;
        nbTotalVins++;
        prixTotalVins += prix;
    }
    // String.
    public String toString() {
        return this.nom + " est un vin " + typeIntToString(this.type) + 
            " de " + this.origine + " et son prix est de " + 
            String.format("%.2f", this.prix) + " $";
    }
    // Retourne le type de vin (les constantes en haut).
    private String typeIntToString(int type) {
        String typeS;
        switch (type) {
            case 1: typeS = type1; break;
            case 2: typeS = type2; break;
            case 3: typeS = type3; break;
            default: typeS = type1;
        }
        return typeS;
    }
    // Les getters.
    public String getNom() {
        return this.nom;
    }
    public int getType() {
        return this.type;
    }
    public String getOrigine() {
        return this.origine;
    }
    public double getPrix() {
        return this.prix;
    }
    // Les setters.
    public void setNom(String nom) { 
        this.nom = nom; 
    }    
    public void setType(int type) { 
        this.type = type; 
    }    
    public void setOrigine(String origine) { 
        this.origine = origine; 
    }    
    public void setPrix(double prix) {
        prixTotalVins -= this.prix;
        prixTotalVins += prix;
        this.prix = prix;
    }

}
