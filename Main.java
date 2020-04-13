import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        String g = "";

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

    private static void Analyse(String g){
        try{
            Scanner scanner = new Scanner(new File(g));

            ArrayList<Sommet> allsommet = new ArrayList<Sommet>();

            int nbsommets = Integer.parseInt(scanner.nextLine());
            int nbarcs = Integer.parseInt(scanner.nextLine());


            for (int i=0; i<nbsommets; i++){
                ArrayList<Arc> arcs = new ArrayList<Arc>();
                Scanner scanner1 = new Scanner(new File(g));
                scanner1.nextLine();
                scanner1.nextLine();
                while(scanner1.hasNext()){
                    String line = scanner1.nextLine();
                    String[] lineSplit = line.split("\\s+");
                    if (Integer.parseInt(lineSplit[0])==i) {
                        Arc a = new Arc(Integer.parseInt(lineSplit[1]), Integer.parseInt(lineSplit[2]));
                        arcs.add(a);
                    }
                }

                Sommet s = new Sommet(i, arcs);
                allsommet.add(s);
            }

            Graphe graphe = new Graphe(nbsommets, nbarcs, allsommet);

            graphe.Lecture();
            graphe.Adjacence();
            graphe.Valeur();
            if (graphe.DetectionCircuit()) {
                graphe.Rang();
                if (graphe.Ordonancement()){
                    graphe.Dates();
                }else{
                    System.out.println("\n\nle graph n'est pas un graphe d'ordonancement");
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
