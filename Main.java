import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    private static int nbSommet;
    private static int nbArc;

    public static void main(String[] args) {

        //declaration du tableau dans lequel on va stocker nos sommets
        ArrayList<Sommet> allSommet = new ArrayList<Sommet>();

        try {
            Scanner scanner = new Scanner(new File("graph2.txt")); //declaration de lecture sur fichier .txt
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
        MatriceAdjacence(nbArc, nbSommet, allSommet);
        MatriceValeurs(nbArc, nbSommet, allSommet);
    }


    public static void Lecture(ArrayList<Sommet> sommet){
        System.out.println("\n\nLecture du graphe:\n");
        for (Sommet s : sommet){
            System.out.printf("%s -> %s = %s \n", s.getNom(), s.getArc(), s.getValeur());
        }
    }


    public static void MatriceAdjacence(int nbArc, int nbSommet, ArrayList<Sommet> sommet){
        System.out.println("\n\nMatrice d'adjacence:\n");
        //initialisation de la matrice
        String[][] Matrix = CreationMatrice(nbSommet);

        for (int i=0; i<nbArc; i++){
            for (int j=0; j<nbArc; j++){
                if (sommet.get(i).getArc()==j){
                    Matrix[sommet.get(i).getNom()][j]= String.valueOf(1);
                }
            }
        }

        AffichageMatrice(nbArc, nbSommet, Matrix);
    }


    public static void MatriceValeurs(int nbArc, int nbSommet, ArrayList<Sommet> sommet){
        System.out.println("\n\nMatrice des valeurs:\n");
        String[][] Matrix = CreationMatrice(nbSommet);

        for (int i=0; i<nbArc; i++){
            for (int j=0; j<nbArc; j++){
                if (sommet.get(i).getArc()==j){
                    Matrix[sommet.get(i).getNom()][j]= String.valueOf(sommet.get(i).getValeur());
                }
            }
        }

        AffichageMatrice(nbArc, nbSommet, Matrix);
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
        for (int i=0; i<nbArc; i++){
            System.out.printf("\t %s", i);
        }
        System.out.println("");
        for (int i=0; i<nbSommet; i++){
            System.out.printf("\t %s", i+1);
            for (int j=0; j<nbSommet; j++){
                System.out.printf("\t %s", Matrix[i][j]);
            }
            System.out.println("  ");
        }

    }
}
