public class TestLivre { 
    public static void main(String args[]) { 
        Livre dictionnaire; 
        Livre roman = new Livre("Da Vinci code", 514, 2003); 
        Livre essai = new Livre(); 
        System.out.println("Le roman a " + roman.getNbPages()); 
        System.out.println("Le titre du roman est " + roman.getTitre()); 
        System.out.println("Le dictionnaire est paru en " +  dictionnaire.getAnnee()); 
        System.out.println(roman.affiche()); 
        System.out.println(roman); 
        System.out.println("Le nombre de livres est " + Livre.nbLivres); 
    } 
} // fin TestLivre 
