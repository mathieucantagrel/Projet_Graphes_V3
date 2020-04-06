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
            Scanner scanner = new Scanner(new File("graph_ordo.txt")); //declaration de lecture sur fichier .txt
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

        DetectionCircuit(MatriceAdjacence, allSommet);
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
        for (int i=-1; i<nbSommet; i++){
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
    public static void DetectionCircuit(String[][] Matrix, ArrayList<Sommet> allSommet) {
        boolean hasNoPrec;
        ArrayList<Integer> noPrecList = new ArrayList<Integer>();
        ArrayList<Integer> sommet = new ArrayList<Integer>();
        ArrayList<Integer> rank = new ArrayList<Integer>();
        ArrayList<Integer> affichage = new ArrayList<Integer>();

        String[][] newMatrix = new String[nbSommet][nbSommet];
        for (int i=0; i<nbSommet; i++){
            for (int j=0; j<nbSommet; j++){
                newMatrix[i][j] = Matrix[i][j];
            }
        }

        System.out.println("\n\nDetection de circuit\nmethode de suppression des points d'entres");

        for (int k=0; k<nbSommet+1; k++) { //au maximum on effectuera autant d'iteration qu'il y a de points +1
            noPrecList.clear();
            affichage.clear();
            for (int i = 0; i < nbSommet; i++) { //on regarde chaque point du graphe

                hasNoPrec = true;
                for (int j = 0; j < nbSommet; j++) {
                    if (!Matrix[j][i].equals("*")) { //on regarde si un point dans la matrice a au moins un predesseur
                        hasNoPrec = false;
                    }
                }
                if (hasNoPrec) { //si le point n'a pas de predecesseur alors on supprime le point, on met la ligne qui lui correspond a zero
                    if (!sommet.contains(i)){
                        sommet.add(i);
                        rank.add(k);
                        affichage.add(i);
                    }
                    noPrecList.add(i);
                }
            }
            for (Integer i : noPrecList){
                for (int j=0; j<nbSommet; j++){
                    Matrix[i][j]="*";
                }

            }

            if (affichage.size()!=0) {
                System.out.println("Point(s) d'entree :");
                for (Integer i : affichage) {
                    System.out.printf("%s \t", i);
                }
                System.out.println("\nSuppression des points d'entree ");
            }

        }

        boolean isCircuit = false;
        //on regarde la matrice, si elle a un point different de zero, alors il y a un circuit
        for (int i=0; i<nbSommet; i++){
            for (int j=0; j<nbSommet; j++){
                if (!Matrix[i][j].equals("*")){
                    isCircuit = true;
                    break;
                }
            }
        }

        if (!isCircuit){
            CalculRang(rank, sommet);
            Ordonancement(rank, sommet, allSommet, newMatrix);
        }else {
            System.out.print("\n\nle graphe contient un circuit\n");
        }
    }

    public static void CalculRang(ArrayList<Integer> rank, ArrayList<Integer> sommet){
        System.out.print("\nLe graph ne contient pas de circuit\n\n");
        System.out.println("Calcul des rangs:");
        System.out.println("Methode de suppression des points d'entree");

        for (int i=0; i<rank.size()-1; i++){
            System.out.printf("\nrang courant: %s", i);
            System.out.println("\nPoints d'entree: ");

            for (int j=0; j<rank.size(); j++){
                if (rank.get(j)==i){
                    System.out.printf("%s\t", sommet.get(j));
                }
            }
        }

        System.out.print("\n\nrangs calculees:\nsommet : ");
        for (Integer integer : sommet) {
            System.out.printf("\t%s", integer);
        }

        System.out.print("\nrang : \t");
        for (Integer integer : rank) {
            System.out.printf("\t%s", integer);
        }
    }

    public static void Ordonancement(ArrayList<Integer> rank, ArrayList<Integer> sommet, ArrayList<Sommet> allsommet, String[][] Matrix){
        int nbEntree=0;
        int entree = 0;
        for (Integer i : rank){
            if (i==0){
                nbEntree++;
                if (nbEntree>1){

                    System.out.println("\n\nle graph n'est pas un graphe d'ordonancement 1");
//                    return;
                }
                entree = i;
            }
        }

        boolean isSortie;
        int nbSortie = 0;
        for (int i=0; i<nbSommet; i++){
            isSortie = true;
            for (int j=0; j<nbSommet; j++){
                if (!Matrix[i][j].equals("*")){
                    isSortie = false;
                }
            }
            if (isSortie) {
                nbSortie++;
                if (nbSortie>1){
                    System.out.println("\n\nle graph n'est pas un graphe d'ordonancement 2");
//                    return;
                }
            }
        }

        int arcValue = 0;
        for (int i=0; i<allsommet.size(); i++){
            arcValue = 0;
            for (Sommet s : allsommet){
                if (s.getNom()==i){
                    if (arcValue==0){
                        arcValue = s.getValeur();
                    }else if (s.getValeur()!=arcValue){
                        System.out.println("\n\nle graph n'est pas un graphe d'ordonancement 3");
//                        return;
                    }
                }
            }
        }

        for (Sommet s : allsommet){
            if (s.getNom()==entree){
                if (s.getValeur()!=0){
                    System.out.println("\n\nle graph n'est pas un graphe d'ordonancement 4");
//                        return;
                }
            }
        }

        for (Sommet s : allsommet){
            if (s.getValeur()<0){
                System.out.println("\n\nle graph n'est pas un graphe d'ordonancement 5");
//                        return;
            }
        }

    }
}
