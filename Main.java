import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String g = "";

        //demande a l'utilisateur quel graphe il veut analyser
        try(Scanner scanner = new Scanner(System.in)){
            while (!g.equals("fin")){
                System.out.println("\n\n\nVeillez entrer le nom complet du fichier a lire (ecrire fin pour finir), \nexemple graph.txt:");
                g = scanner.nextLine();
                if (!g.equals("fin")) {
                    Analyse(g);
                }
            }
        }

    }

    //methode pour analyser le graphe
    private static void Analyse(String g){
        try{

            //lecture et stockage des sommets du graphe dans arraylist
            Scanner scanner = new Scanner(new File(g));

            ArrayList<Sommet> allsommet = new ArrayList<Sommet>();

            int nbsommets = Integer.parseInt(scanner.nextLine()); //la premiere ligne du fichier est  le nombre de sommet
            int nbarcs = Integer.parseInt(scanner.nextLine()); //la deuxieme ligne du fichier est le nombre d'arretes

            //initialisation de chaque sommet du graphe
            //pour chaque sommet on recupere tous ses arcs et on les stock dans une arraylist
            for (int i=0; i<nbsommets; i++){

                ArrayList<Arc> arcs = new ArrayList<>(); //creation de l'arraylist qui stocker les arcs du sommet en cours
                Scanner scanner1 = new Scanner(new File(g)); //creation du scanner pour parcourir le ficher pour le sommet en cours

                //les 2 premieres ligne ont deja ete recupere
                scanner1.nextLine();
                scanner1.nextLine();

                while(scanner1.hasNext()){
                    String line = scanner1.nextLine();
                    String[] lineSplit = line.split("\\s+"); //split de la ligne selon les espaces

                    //le [0] du split correspond a premier chiffre de la ligne, le nom du sommet
                    if (Integer.parseInt(lineSplit[0])==i) {

                        //le [1] du est le nom du sommet suivant, le [2] su split est le poid de l'arc
                        Arc a = new Arc(Integer.parseInt(lineSplit[1]), Integer.parseInt(lineSplit[2]));

                        arcs.add(a);
                    }
                }

                Sommet s = new Sommet(i, arcs); //creation d'un objet sommeta avec un nom et une liste d'arcs
                allsommet.add(s);
            }

            //creation d'un objet graphe qui a un nombre de sommets et d'arcs et une liste de sommets
            Graphe graphe = new Graphe(nbsommets, nbarcs, allsommet);

            graphe.Lecture(); //lecture du graphe depuis l'objet
            graphe.Adjacence(); //affichage de la matrice d'ajacence
            graphe.Valeur(); //affichage de la matrice des valeurs
            if (graphe.DetectionCircuit()) { //detection de circuit au sein du graphe
                graphe.Rang(); //affichage des rangs
                if (graphe.Ordonancement()){ //detection d'ordonancement
                    graphe.Dates(); //afficahge des dates
                }else{
                    System.out.println("\n\nle graph n'est pas un graphe d'ordonancement");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
