import javax.swing.*;

public class TestVin {
    
    public static void main(String[] args) throws Exception {
        // Créer tableau des vins.
        Vin listeVins[] = new Vin[10];
        // Créer un texte.
        JTextArea texte = new JTextArea();
        // Créer 3 vins.
        listeVins[0] = new Vin("MiamMiam", 2, "Espagne", 8.95);
        listeVins[1] = new Vin("Délicieux", 1, "France", 14.50);
        listeVins[2] = new Vin("Mystère", 3, "Californie", 10.00);
        // Voici les 3 vins.
        texte.append("Voici les " + Vin.nbTotalVins + " vins :\n");
        texte.append("Le prix total des vins est de " + Vin.prixTotalVins + " $\n");
        for (int i = 0; i < Vin.nbTotalVins; i++) {
            texte.append("\t" + listeVins[i].toString() + "\n");
        }
        texte.append("\n");
        // Modifier un peu.
        listeVins[0].setPrix(listeVins[0].getPrix() + 2.00);
        listeVins[1].setPrix(23.00);
        listeVins[1].setOrigine("Italie");
        listeVins[2].setNom("Vino verde");
        listeVins[2].setType(2);
        listeVins[2].setOrigine(listeVins[0].getOrigine());
        listeVins[3] = new Vin("L'érablière", 1, "Québec", 15.00);
        // Voici les 4 vins.
        texte.append("Voici les " + Vin.nbTotalVins + " vins :\n");
        texte.append("Le prix total des vins est de " + Vin.prixTotalVins + " $\n");
        for (int i = 0; i < Vin.nbTotalVins; i++) {
            texte.append("\t" + listeVins[i].toString() + "\n");
        }
        // Afficher ce texte.
        JOptionPane.showMessageDialog(null, texte,
    "Résultats obtenus", JOptionPane.PLAIN_MESSAGE);

    }
}
