public class Radio {
 
    public String marque;
    private String modele;
    private boolean cd;
    private boolean cassette;
    private boolean mp3;
    private double prix;

    public static int nombreRadios = 0;

    Radio() {
        nombreRadios++;
    }

    Radio(String marque, String modele, boolean cd, boolean cassette, boolean mp3, double prix)  {
        this.marque = marque;
        this.modele = modele;
        this.cd = cd;
        this.cassette = cassette;
        this.mp3 = mp3;
        this.prix = prix;
        nombreRadios++; // Mais n'est pas utilisé car nous utilisons plutôt listeRadios.size()
    }

    // Les getters
    public String getMarque() {
        return this.marque;
    }
    public String getModele() {
        return this.modele;
    }
    public boolean getCd() {
        return this.cd;
    }
    public boolean getCassette() {
        return this.cassette;
    }
    public boolean getMp3() {
        return this.mp3;
    }
    public double getPrix() {
        return this.prix;
    }

    // Les getters
    public void setMarque(String marque) {
        this.marque = marque;
    }
    public void setModele(String modele) {
        this.modele = modele;
    }
    public void setCd(boolean cd) {
        this.cd = cd;
    }
    public void setCassette(boolean cassette) {
        this.cassette = cassette;
    }
    public void setMp3(boolean mp3) {
        this.mp3 = mp3;
    }
    public void setPrix(double prix) {
        this.prix = prix;
    }

    public String toString() {
        return this.marque + "\t\t" + this.modele + "\t\t" + (this.cd ? "Oui" : "") + "\t" + (this.cassette? "Oui" : "") + "\t" + (this.mp3 ? "Oui" : "") + "\t\t" + String.format("%.2f", this.prix) + " $";
    }

    public boolean sontEgales(Object radio) {
		Radio radio2 = (Radio) radio;
		if ((this.marque == radio2.marque) && (this.modele == radio2.modele)) { return true; } 
        else { return false; }
	}

}
