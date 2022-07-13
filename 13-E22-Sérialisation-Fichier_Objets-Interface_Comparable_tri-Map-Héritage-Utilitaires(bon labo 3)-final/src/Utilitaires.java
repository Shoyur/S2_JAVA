import java.util.ArrayList;
import java.util.StringTokenizer;
import java.io.*;

public class Utilitaires {
    

    static BufferedReader tmpReadTexte;
    static BufferedWriter tmpWriteTexte;
    static ObjectInputStream tmpReadObj;
    static ObjectOutputStream tmpWriteObj;

    static Object biblio;
    static Object obj;


    public static void sauvegarderFichierObjets(Object obj, String fichier) throws IOException {
        try {
            tmpWriteObj = new ObjectOutputStream(new FileOutputStream(fichier));
            tmpWriteObj.writeObject(obj);
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable. Vérifiez le chemin et nom du fichier.");
        } catch (IOException e) {
            System.out.println("Un probléme est arrivé lors de la manipulation du fichier. Vérifiez vos données.");
        } catch (Exception e) {
            System.out.println("Un probléme est arrivé lors du chargement du fichier. Contactez l'administrateur.");
        } finally {// Exécuté si erreur ou pas
            tmpWriteObj.close();
        }
    }

    public static Object chargerFichierObjets(String fichier) throws Exception {
        try {
            tmpReadObj = new ObjectInputStream(new FileInputStream(fichier));
            obj = tmpReadObj.readObject();
            tmpReadObj.close();
            return obj;
        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable. Vérifiez le chemin et nom du fichier.");
        } catch (IOException e) {
            System.out.println("Un probléme est arrivé lors de la manipulation du fichier. Vérifiez vos données.");
        } catch (Exception e) {
            System.out.println("Un probléme est arrivé lors du chargement du fichier. Contactez l'administrateur.");
        } finally {// Exécuté si erreur ou pas
            tmpReadObj.close();
        }
        return null;
    }

    public static ArrayList<ArrayList<String>> chargerFichierTexte(String fichier, String delimiteurs) throws Exception {
        String ligne;
        // String elems[] = new String[];
        ArrayList<ArrayList<String>> listeAttributs = new ArrayList<ArrayList<String>>();
        ArrayList<String> attributs;
        try {
            tmpReadTexte = new BufferedReader(new FileReader(fichier));
            StringTokenizer st;
            ligne = tmpReadTexte.readLine();// Lire la premiére ligne du fichier
            while (ligne != null) {// Si ligne == null alors ont a atteint la fin du fichier
                st= new StringTokenizer(ligne, delimiteurs);
                attributs = new ArrayList<String>();
                while(st.hasMoreTokens()){
                     attributs.add(st.nextToken());
                }
                listeAttributs.add(attributs);// elems[0] contient le num�ro du livre;elems[1] le titre et elems[2] les pages
                // biblio.add(new Object(Integer.parseInt(elems[0]), elems[1], Integer.parseInt(elems[2]),
                //         Integer.parseInt(elems[3])));
                ligne = tmpReadTexte.readLine();
            } // fin while

        } catch (FileNotFoundException e) {
            System.out.println("Fichier introuvable. Vérifiez le chemin et nom du fichier.");
        } catch (IOException e) {
            System.out.println("Un probléme est arrivé lors de la manipulation du fichier. V�rifiez vos donn�es.");
        } catch (Exception e) {
            System.out.println("Un probléme est arrivé lors du chargement du fichier. Contactez l'administrateur.");
        } finally {// Exécuté si erreur ou pas
            tmpReadTexte.close();
           
        }
        return listeAttributs;
    }
}
