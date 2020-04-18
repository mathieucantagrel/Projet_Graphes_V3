import java.util.ArrayList;
import java.util.Iterator;

public class Graphe {

    private int nbsommets;
    private int nbarcs;
    private ArrayList<Sommet> sommets;
    private int rang_max;

    public Graphe(int nbsommets, int nbarcs, ArrayList<Sommet> sommets) {
        this.nbsommets = nbsommets;
        this.nbarcs = nbarcs;
        this.sommets = sommets;
    }

    public Graphe(Graphe another){
        this.nbsommets = another.nbsommets;
        ArrayList<Sommet> s = new ArrayList<Sommet>();
        s.addAll(another.sommets);
        this.sommets = s;
    }

    public int getNbsommets() {
        return nbsommets;
    }

    public void setNbsommets(int nbsommets) {
        this.nbsommets = nbsommets;
    }

    public int getNbarcs() {
        return nbarcs;
    }

    public void setNbarcs(int nbarcs) {
        this.nbarcs = nbarcs;
    }

    public ArrayList<Sommet> getSommets() {
        return sommets;
    }

    public void setSommets(ArrayList<Sommet> sommets) {
        this.sommets = sommets;
    }

    public int getRang_max() {
        return rang_max;
    }

    public void setRang_max(int rang_max) {
        this.rang_max = rang_max;
    }

    public void Lecture(){

        System.out.println("\n\n\t\t---Lecture du graphe:---\n");

        for (Sommet sommet : sommets){
            for (Arc arc : sommet.getArcs()){
                System.out.printf("%s -> %s = %s\n", sommet.getNom(), arc.getSuivant(), arc.getPoid());
            }
        }
    }

    private String[][] creationMatrice(){
        String[][] Matrice = new String [nbsommets][nbsommets];

        //remplissage de la matrice avec des * pour montrer que c'est un espace vide
        for (int i=0; i<nbsommets; i++){
            for (int j=0; j<nbsommets; j++){
                Matrice[i][j]= String.valueOf('*');
            }
        }
        return Matrice;
    }

    private void affichageMatrice(String[][] Matrix){
        for (int i=-1; i<nbsommets; i++){
            if (i==-1){
                System.out.print("\t");
            }else{
                System.out.printf("\t %s", i);
            }
        }
        System.out.println("");
        for (int i=0; i<nbsommets; i++){
            System.out.printf("\t %s", i);
            for (int j=0; j<nbsommets; j++){
                System.out.printf("\t %s", Matrix[i][j]);
            }
            System.out.println("  ");
        }
    }

    public void Adjacence(){

        System.out.println("\n\n\t\t---Matrice d'adjacence:---\n");

        String[][] Matrix = creationMatrice();

        for (Sommet sommet : sommets){
            for (Arc arc : sommet.getArcs()){
                Matrix[sommet.getNom()][arc.getSuivant()] = String.valueOf(1);
            }
        }
        affichageMatrice(Matrix);
    }

    public void Valeur(){

        System.out.println("\n\n\t\t---Matrice des valeurs:---\n");

        String[][] Matrix = creationMatrice();

        for (Sommet sommet : sommets){
            for (Arc arc : sommet.getArcs()){
                Matrix[sommet.getNom()][arc.getSuivant()] = String.valueOf(arc.getPoid());
            }
        }
        affichageMatrice(Matrix);
    }

    public boolean DetectionCircuit(){

        System.out.println("\n\n\t\t---Detection de circuit:---\n");

        Graphe graphe2 = new Graphe(this);

        for (int i=0; i<=nbsommets; i++){
            ArrayList<Sommet> hasNoPrec = new ArrayList<Sommet>();
            boolean changement = false;

            for (Sommet sommet : graphe2.sommets){
                hasNoPrec.add(sommet);
            }

            for (Sommet sommet1 : graphe2.sommets){
                for (Sommet sommet2 : graphe2.sommets){
                    for (Arc arc : sommet2.getArcs()){
                        if (arc.getSuivant()==sommet1.getNom()){
                            hasNoPrec.remove(sommet1);
                            changement = true;
                        }
                    }
                }
            }

            System.out.println("\n\nPoints d'entrees :");
            for (Sommet sommet : hasNoPrec){
                System.out.print(sommet.getNom()+"  ");
            }

            System.out.println("\nSuppression des points d'entrees.");
            for (Sommet sommet : hasNoPrec){
                graphe2.sommets.remove(sommet);
                for (Sommet sommet1 : this.sommets){
                    if (sommet1.getNom()==sommet.getNom()){
                        sommet1.setRang(i);
                    }
                }
            }
            System.out.println("\nSommets restants :");
            for (Sommet sommet : graphe2.sommets){
                System.out.print(sommet.getNom()+"  ");
            }
            if (!changement){
                this.setRang_max(i);
                break;
            }
        }


        if (graphe2.getSommets().size()==0){
            System.out.print("\n\n---le graphe ne contient pas de circuit---\n");
            return true;
        }else{
            System.out.print("\n\n---le graphe contient un circuit---\n");
            return false;
        }
    }

    public void Rang(){

        System.out.println("\n\n\t\t---Calcul des rangs:---\n");

        for (int i=0; i<=rang_max; i++){
            System.out.println("\n\nRang courant: "+i+"\nPoints d'entree");

            for (Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    System.out.print(sommet.getNom()+"  ");
                }
            }
            System.out.println("\nSuppression des points d'entrees\nSommets restants: ");
            for (Sommet sommet : sommets){
                if (sommet.getRang()>i){
                    System.out.print(sommet.getNom()+"  ");
                }
            }
        }

        System.out.println("\n\n---Rangs calculees---");
        for (Sommet sommet : sommets){
            System.out.print(sommet.getNom()+"  ");
        }
        System.out.print("\n");
        for (Sommet sommet : sommets){
            System.out.print(sommet.getRang()+"  ");
        }
    }

    public boolean Ordonancement(){

        System.out.println("\n\n\t\t---Le graphe est-il un graphe d'ordonancement:---\n");

        int nbPoint = 0;

        for (Sommet sommet : sommets){
            if (sommet.getRang()==0){
                nbPoint++;
                if (nbPoint>1){
                    System.out.println("Le graphe contient plusieurs entrees");
                    return false;
                }
            }
        }
        System.out.println("Le graphe contient une seule entrees");

        nbPoint = 0;
        for (Sommet sommet : sommets){
            if (sommet.getRang()==rang_max){
                nbPoint++;
                if (nbPoint>1){
                    System.out.println("le graphe contient plusieurs sorties");
                    return false;
                }
            }
        }
        System.out.println("le graphe contient une seule sortie");

        for (Sommet sommet : sommets){
            int val = -1;
            for (Arc arc : sommet.getArcs()){
                if (val==-1){
                    val = arc.getPoid();
                }else if (arc.getPoid()!=val){
                    System.out.println("tous les arcs sortants d'un meme sommet n'ont pas la meme valeur");
                    return false;
                }
            }
        }
        System.out.println("tous les arcs sortants d'un meme sommet ont la meme valeur");

        for (Sommet sommet : sommets){
            if (sommet.getRang()==0){
                for (Arc arc : sommet.getArcs()){
                    if (arc.getPoid()!=0){
                        System.out.println("les arcs sortants du point d'entree n'ont pas tous une valeure nulle");
                        return false;
                    }
                }
            }
        }
        System.out.println("les arcs sortants du point d'entree ont tous une valeure nulle");

        for (Sommet sommet : sommets){
            for (Arc arc : sommet.getArcs()){
                if (arc.getPoid()<0){
                    System.out.println("Tous les arcs n'ont pas une valeure positive");
                    return false;
                }
            }
        }
        System.out.println("Tous les arcs ont une valeure positive");

        System.out.println("\n\nle graphe est un graphe d'ordonancement");

        return true;
    }

    public void Dates(){

        System.out.println("\n\n\t\t---Le graphe est-il un graphe d'ordonancement:---\n");

        for (Sommet sommet : sommets){
            if (sommet.getRang()==0){
                sommet.setDate_tot(0);
            }
        }

        for (int i=1; i<=rang_max; i++){
            for (Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    for (Sommet prec : sommets){
                        for (Arc arc : prec.getArcs()){
                            if (arc.getSuivant()==sommet.getNom()) {
                                if (sommet.getDate_tot() == -1) {
                                    sommet.setDate_tot(prec.getDate_tot() + arc.getPoid());
                                } else if (sommet.getDate_tot() < prec.getDate_tot() + arc.getPoid()) {
                                    sommet.setDate_tot(prec.getDate_tot() + arc.getPoid());
                                }
                            }
                        }
                    }
                }
            }
        }

        for (Sommet sommet : sommets){
            if (sommet.getRang()==rang_max){
                sommet.setDate_tard(sommet.getDate_tot());
            }
        }

        for (int i=rang_max-1; i>=0; i--){
            for (Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    for (Arc arc : sommet.getArcs()){
                        for (Sommet suivant : sommets){
                            if (arc.getSuivant()==suivant.getNom()){
                                if (sommet.getDate_tard()==-1){
                                    sommet.setDate_tard(suivant.getDate_tard()-arc.getPoid());
                                }else if (sommet.getDate_tard()>suivant.getDate_tard()-arc.getPoid()){
                                    sommet.setDate_tard(suivant.getDate_tard()-arc.getPoid());
                                }
                            }
                        }
                    }
                }
            }
        }

        for (int i=0; i<=rang_max; i++){
            for (Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    int marge = sommet.getDate_tard()-sommet.getDate_tot();
                    sommet.setMarge_totale(marge);
                }
            }
        }

        for (Sommet sommet : sommets){
            for (Arc arc : sommet.getArcs()){
                for (Sommet sommet1 : sommets){
                    if (arc.getSuivant()==sommet1.getNom()){
                        int margeLibre = sommet1.getDate_tot()-sommet.getDate_tot()-arc.getPoid();
                        if (sommet.getMarge_libre()==-1){
                            sommet.setMarge_libre(margeLibre);
                        }else if(sommet.getMarge_libre()>margeLibre){
                            sommet.setMarge_libre(margeLibre);
                        }
                    }
                }
            }
        }

        System.out.print("\nrang\t\t\t\t");
        for (int i=0; i<=rang_max; i++){
            for (Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    System.out.print(sommet.getRang()+"\t");
                }
            }
        }

        System.out.print("\nsommet\t\t\t\t");
        for (int i=0; i<=rang_max; i++){
            for (Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    System.out.print(sommet.getNom()+"\t");
                }
            }
        }

        System.out.print("\ndate au plus tot\t");
        for (int i=0; i<=rang_max; i++){
            for (Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    System.out.print(sommet.getDate_tot()+"\t");
                }
            }
        }

        System.out.print("\ndate au plus tard\t");
        for (int i=0; i<=rang_max; i++){
            for (Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    System.out.print(sommet.getDate_tard()+"\t");
                }
            }
        }

        System.out.print("\nmarge totale\t\t");
        for (int i=0; i<=rang_max; i++){
            for (Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    System.out.print(sommet.getMarge_totale()+"\t");
                }
            }
        }

        System.out.print("\nmarge libre\t\t\t");
        for (int i=0; i<=rang_max; i++){
            for (Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    if (sommet.getMarge_libre()==-1){
                        continue;
                    }
                    System.out.print(sommet.getMarge_libre()+"\t");
                }
            }
        }
    }
}
