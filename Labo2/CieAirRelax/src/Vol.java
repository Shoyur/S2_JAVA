public class Vol {
 
    private int numeroVol;
    private String destination;
    private Date date;
    private int nbReservations;

    public static int nombreVols = 0;

    Vol(int numeroVol, String destination, Date date, int nbReservations)  {
        this.numeroVol = numeroVol;
        this.destination = destination;
        this.date = date;
        this.nbReservations = nbReservations;
        nombreVols++; // Mais n'est pas utilisé car nous utilisons plutôt tabVols.size()
    }

    Vol() {}

    // Les getters
    public int getNumeroVol() {
        return this.numeroVol;
    }
    public String getDestination() {
        return this.destination;
    }
    public Date getDate() {
        return this.date;
    }
    public int getNbReservations() {
        return this.nbReservations;
    }
    // Les setters
    public void setNumeroVol(int numeroVol) {
        this.numeroVol = numeroVol;
    }
    public void setDestination(String destination) {
        this.destination = destination;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public void setNbReservations(int nbReservations) {
        this.nbReservations = nbReservations;
    }

    public String toString() {
        return this.numeroVol + "\t" + this.destination + "\t" + this.date + "\t" + this.nbReservations;
    }

}
