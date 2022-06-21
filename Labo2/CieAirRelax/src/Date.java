public class Date {

    private int jour;
    private int mois;
    private int an;

    Date() {
        jour = mois = 1;
        an = 2000;
    }

    Date(int jour, int mois, int an) {
        this.jour = jour;
        this. mois = mois;
        this.an = an;
    }

    public int getJour() {
        return this.jour;
    }
    public int getMois() {
        return this.mois;
    }
    public int getAn() {
        return this.an;
    }

    public void setJour(int jour) {
        this.jour = jour;
    }
    public void setMois(int mois) {
        this. mois = mois;
    }
    public void setAn(int an) {
        this.an = an;
    }

    public String toString() {
        return this.jour + "/" + this.mois + "/" + this.an;
    }


}
