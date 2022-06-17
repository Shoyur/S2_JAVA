
import javax.swing.*;

public class Test { 
    public static void main(String args[]) { 
        Incremente unObjet = new Incremente(10, 1); 
        System.out.println("" + unObjet.additionne(5));
        String test = JOptionPane.showInputDialog(null, "Jour :", "AJOUT D'UN VOL", JOptionPane.PLAIN_MESSAGE);
        if (test == null) { return; }
        else { System.out.println("Le String test est : " + test); }

    }
}
