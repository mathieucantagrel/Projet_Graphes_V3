import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static int nbSommet;
    private static int nbArc;

    public static void main(String[] args) {

        //declaration du tableau dans lequel on va stocker nos sommets
        ArrayList<Sommet> allSommet = new ArrayList<Sommet>();

        try {
            Scanner scanner = new Scanner(new File("graph1.txt")); //declaration de lecture sur fichier .txt
            //on commence par recuperer le nombre de sommets et d'arcs
            nbSommet = Integer.parseInt(scanner.nextLine());
            nbArc = Integer.parseInt(scanner.nextLine());
            //on recupere et on stocke les sommets un a un dans le tableau
            while (scanner.hasNext()) {
                String Sommet = scanner.nextLine();
                String[] splited = Sommet.split("\\s+");
                Sommet s = new Sommet(Integer.parseInt(splited[0]), Integer.parseInt(splited[1]), Integer.parseInt(splited[2]));
                allSommet.add(s);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        System.out.println("Projet V1");
        Lecture(allSommet);

        //matrice d'adjacence
        String[][] MatriceAdjacence = MatriceAdjacence(nbArc, nbSommet, allSommet);
        System.out.println("\n\nMatrice d'adjacence:\n");
        AffichageMatrice(nbArc, nbSommet, MatriceAdjacence);

        //matrice des valeurs
        String[][] MatriceValeurs = MatriceValeurs(nbArc, nbSommet, allSommet);
        System.out.println("\n\nMatrice des valeurs:\n");
        AffichageMatrice(nbArc, nbSommet, MatriceValeurs);

        boolean circuit = DetectionCircuit(MatriceAdjacence);
        if (circuit){
            System.out.print("\n\nce graphe contient un circuit\n");
        }else {
            System.out.print("\n\nce graphe ne contient pas de circuit\n");
        }
    }


    public static void Lecture(ArrayList<Sommet> sommet){
        System.out.println("\n\nLecture du graphe:\n");
        for (Sommet s : sommet){
            System.out.printf("%s -> %s = %s \n", s.getNom(), s.getSuivant(), s.getValeur());
        }
    }


    public static String[][] MatriceAdjacence(int nbArc, int nbSommet, ArrayList<Sommet> sommet){
        //initialisation de la matrice
        String[][] Matrix = CreationMatrice(nbSommet);

        for (int i=0; i<nbArc; i++){
            for (int j=0; j<nbArc; j++){
                if (sommet.get(i).getSuivant()==j){
                    Matrix[sommet.get(i).getNom()][j]= String.valueOf(1);
                }
            }
        }

        return Matrix;
    }


    public static String[][] MatriceValeurs(int nbArc, int nbSommet, ArrayList<Sommet> sommet){

        String[][] Matrix = CreationMatrice(nbSommet);

        for (int i=0; i<nbArc; i++){
            for (int j=0; j<nbArc; j++){
                if (sommet.get(i).getSuivant()==j){
                    Matrix[sommet.get(i).getNom()][j]= String.valueOf(sommet.get(i).getValeur());
                }
            }
        }

        return Matrix;
    }


    public static String[][] CreationMatrice(int nbSommet){
        String[][] Matrix = new String [nbSommet][nbSommet];

        for (int i=0; i<nbSommet; i++){
            for (int j=0; j<nbSommet; j++){
                Matrix[i][j]= String.valueOf('*');
            }
        }
        return Matrix;
    }


    public static void AffichageMatrice(int nbArc, int nbSommet, String[][] Matrix){
        for (int i=-1; i<nbArc-1; i++){
            if (i==-1){
                System.out.print("\t");
            }else{
            System.out.printf("\t %s", i);
            }
        }
        System.out.println("");
        for (int i=0; i<nbSommet; i++){
            System.out.printf("\t %s", i);
            for (int j=0; j<nbSommet; j++){
                System.out.printf("\t %s", Matrix[i][j]);
            }
            System.out.println("  ");
        }
    }

    //pour detecter un circuit, on enleve successivement les points qui n'ont pas de predecesseurs
    //pour faire cela, on regarde la matrice d'adjacence, les points n'ayants pas de predecesseurs sont les points
    //qui n'ont rien dans leur colonne. Enlever un point siginifie mettre a zero (ou *) la ligne qui lui correspond
    //lorsqu'on a fini de retirer tous les points qui n'ont pas de predecesseur, on regarde la matrice d'adjacence : s'il reste
    //des points alors il y a un circuit, sinon il n'y a pas de circuit.
    public static boolean DetectionCircuit(String[][] Matrix) {
        boolean hasNoPrec;
        ArrayList<Integer> noPrecList = new ArrayList<Integer>();
        ArrayList<Integer> sommet = new ArrayList<Integer>();
        ArrayList<Integer> rank = new ArrayList<Integer>();

        for (int k=0; k<nbSommet+1; k++) { //au maximum on effectuera autant d'iteration qu'il y a de points +1
            noPrecList.clear();
            for (int i = 0; i < nbSommet; i++) { //on regarde chaque point du graphe

                hasNoPrec = true;
                for (int j = 0; j < nbSommet; j++) {
                    if (!Matrix[j][i].equals("*")) { //on regarde si un point dans la matrice a au moins un predesseur
                        hasNoPrec = false;
                    }
                }
                if (hasNoPrec) { //si le point n'a pas de predecesseur alors on supprime point, on met la ligne qui lui correspond a zero
                    if (!sommet.contains(i)){
                        sommet.add(i);
                        rank.add(k);
                    }
                    noPrecList.add(i);
                }
            }
            for (Integer i : noPrecList){
                for (int j=0; j<nbSommet; j++){
                    Matrix[i][j]="*";
                }

            }
        }

        for (int i=0; i<sommet.size(); i++){
            System.out.printf("%s -> %s\n", sommet.get(i), rank.get(i));
        }

        //on regarde la matrice, si elle a un point different de zero, alors il y a un circuit
        for (int i=0; i<nbSommet; i++){
            for (int j=0; j<nbSommet; j++){
                if (!Matrix[i][j].equals("*")){
                    return true;
                }
            }
        }
        return false;
    }
}
