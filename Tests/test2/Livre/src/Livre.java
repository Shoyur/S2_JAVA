public class Livre { 
    private static int nbLivres = 0; 
    private String titre; 
    private int nbPages; 
    private int annee; 
    // constructeur d’initialisation 
    public Livre(String t, int nb, int a) { 
        titre = t; 
        nbPages = nb; 
        annee = a; 
        nbLivres++; 
    } 

    private int getNbPages() { 
        return nbPages; 
    } 
    public static String getTitre() { 
        return titre; 
    } 
    public int getAnnee() { 
        return annee; 
    } 
    public String affiche() { 
        return titre + ", paru en " + annee + ", a " + nbPages + " pages"; 
    } 
} // fin Livre 
