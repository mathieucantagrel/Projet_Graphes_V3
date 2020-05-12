import java.util.ArrayList;

public class L3_B12_Graphe {

    //un graphe contient toujours un nombre de sommet, un nombre d'arcs, a une liste de sommets et un rang max
    private int nbsommets;
    private int nbarcs;
    private ArrayList<L3_B12_Sommet> sommets;
    private int rang_max;

    public L3_B12_Graphe(int nbsommets, int nbarcs, ArrayList<L3_B12_Sommet> sommets) {
        this.nbsommets = nbsommets;
        this.nbarcs = nbarcs;
        this.sommets = sommets;
    }

    //copie du graphe dans un autre
    public L3_B12_Graphe(L3_B12_Graphe another){
        this.nbsommets = another.nbsommets;
        ArrayList<L3_B12_Sommet> s = new ArrayList<L3_B12_Sommet>();
        s.addAll(another.sommets);
        this.sommets = s;
    }

    public ArrayList<L3_B12_Sommet> getSommets() {
        return sommets;
    }

    public void setRang_max(int rang_max) {
        this.rang_max = rang_max;
    }

    //lecture du graphe
    public void Lecture(){

        System.out.println("\n\n\t\t---Lecture du graphe:---\n");

        for (L3_B12_Sommet sommet : sommets){
            for (L3_B12_Arc arc : sommet.getArcs()){
                System.out.printf("%s -> %s = %s\n", sommet.getNom(), arc.getSuivant(), arc.getPoid());
            }
        }
    }

    //creation d'une matrice carree de dimension nbsommets
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

    //affichage console d'une matrice
    private void affichageMatrice(String[][] Matrix){
        //"header" qui affiche les numeros des sommets
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

    //creation de la matrice d'adjacence
    public void Adjacence(){

        System.out.println("\n\n\t\t---Matrice d'adjacence:---\n");

        String[][] Matrix = creationMatrice(); //creation d'une matrice vide

        //remplissage de la matrice en fonction des sommets et de leurs successeurs
        for (L3_B12_Sommet sommet : sommets){
            for (L3_B12_Arc arc : sommet.getArcs()){
                Matrix[sommet.getNom()][arc.getSuivant()] = String.valueOf(1);
            }
        }

        affichageMatrice(Matrix);
    }

    //creation de la matrice des valeurs
    public void Valeur(){

        System.out.println("\n\n\t\t---Matrice des valeurs:---\n");

        String[][] Matrix = creationMatrice(); //creatio d'une matrice vide

        //remplissage de la matrice en fonction des sommets et de leurs successeurs
        for (L3_B12_Sommet sommet : sommets){
            for (L3_B12_Arc arc : sommet.getArcs()){
                Matrix[sommet.getNom()][arc.getSuivant()] = String.valueOf(arc.getPoid());
            }
        }
        affichageMatrice(Matrix);
    }

    //detection de circuit dans le graphe et calcul des rangs
    public boolean DetectionCircuit(){

        System.out.println("\n\n\t\t---Detection de circuit:---\n");

        //copie du graphe pour pouvoir effectuer les suppressions des points d'entree sans modifier le graphe original
        L3_B12_Graphe graphe2 = new L3_B12_Graphe(this);

        for (int i=0; i<=nbsommets; i++){
            boolean changement = false;

            //creation d'une liste contenant les sommets restant du graphe
            ArrayList<L3_B12_Sommet> hasNoPrec = new ArrayList<L3_B12_Sommet>(graphe2.sommets);

            //detection des sommets ayant des predecesseurs et les supprime de la liste
            for (L3_B12_Sommet sommet1 : graphe2.sommets){
                for (L3_B12_Sommet sommet2 : graphe2.sommets){
                    for (L3_B12_Arc arc : sommet2.getArcs()){
                        if (arc.getSuivant()==sommet1.getNom()){
                            hasNoPrec.remove(sommet1);
                        }
                    }
                }
            }

            //les sommets restant dans la liste sont les sommets qui n'ont pas de predecesseurs
            System.out.println("\n\nPoints d'entrees :");
            for (L3_B12_Sommet sommet : hasNoPrec){
                System.out.print(sommet.getNom()+"  ");
            }

            //suppression des points d'entree du graphe copie
            System.out.println("\nSuppression des points d'entrees.");
            for (L3_B12_Sommet sommet : hasNoPrec){
                graphe2.sommets.remove(sommet);
                changement = true;

                //set du rang du sommet qui est supprime
                for (L3_B12_Sommet sommet1 : this.sommets){
                    if (sommet1.getNom()==sommet.getNom()){
                        sommet1.setRang(i);
                    }
                }

            }

            System.out.println("\nSommets restants :");
            for (L3_B12_Sommet sommet : graphe2.sommets){
                System.out.print(sommet.getNom()+"  ");
            }

            //si on n a pas effectue de changement on sort de la suppression des points d'entres
            if (!changement){
                this.setRang_max(i-1);
                break;
            }
        }

        //s'il ne reste pas de sommets, le graphe est vide, il n'y a pas de circuit
        if (graphe2.getSommets().size()==0){
            System.out.print("\n\n---le graphe ne contient pas de circuit---\n");
            return true;
        }else{
            System.out.print("\n\n---le graphe contient un circuit---\n");
            return false;
        }
    }

    //affichage des rangs
    public void Rang(){

        System.out.println("\n\n\t\t---Calcul des rangs:---\n");

        for (int i=0; i<=rang_max; i++){
            System.out.println("\n\nRang courant: "+i+"\nPoints d'entree");

            //parcours des sommets en fonction de leur rang
            for (L3_B12_Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    System.out.print(sommet.getNom()+"  ");
                }
            }
            System.out.println("\nSuppression des points d'entrees\nSommets restants: ");
            for (L3_B12_Sommet sommet : sommets){
                if (sommet.getRang()>i){
                    System.out.print(sommet.getNom()+"  ");
                }
            }
        }

        System.out.println("\n\n---Rangs calculees---");
        for (L3_B12_Sommet sommet : sommets){
            System.out.print(sommet.getNom()+"  ");
        }
        System.out.print("\n");
        for (L3_B12_Sommet sommet : sommets){
            System.out.print(sommet.getRang()+"  ");
        }
    }

    //test pour voir si graphe d'ordonancement
    public boolean Ordonancement(){

        System.out.println("\n\n\t\t---Le graphe est-il un graphe d'ordonancement:---\n");

        int nbPoint = 0;

        //test un seul point d entree -> un seul sommet de rang 0
        for (L3_B12_Sommet sommet : sommets){
            if (sommet.getRang()==0){
                nbPoint++;
                if (nbPoint>1){
                    System.out.println("Le graphe contient plusieurs entrees");
                    return false;
                }
            }
        }
        System.out.println("Le graphe contient une seule entrees");

        //test un seul point de sortie -> un seul sommet de rang max
        nbPoint = 0;
        for (L3_B12_Sommet sommet : sommets){
            if (sommet.getRang()==rang_max){
                nbPoint++;
                if (nbPoint>1){
                    System.out.println("le graphe contient plusieurs sorties");
                    return false;
                }
            }
        }
        System.out.println("le graphe contient une seule sortie");

        //tes tous les arcs sortant d'un sommet on la meme valeur
        for (L3_B12_Sommet sommet : sommets){
            int val = -1;
            for (L3_B12_Arc arc : sommet.getArcs()){
                if (val==-1){
                    val = arc.getPoid();
                }else if (arc.getPoid()!=val){
                    System.out.println("tous les arcs sortants d'un meme sommet n'ont pas la meme valeur");
                    return false;
                }
            }
        }
        System.out.println("tous les arcs sortants d'un meme sommet ont la meme valeur");

        //test tous les arcs sortant du point d'entree ont un poid de 0
        for (L3_B12_Sommet sommet : sommets){
            if (sommet.getRang()==0){
                for (L3_B12_Arc arc : sommet.getArcs()){
                    if (arc.getPoid()!=0){
                        System.out.println("les arcs sortants du point d'entree n'ont pas tous une valeure nulle");
                        return false;
                    }
                }
            }
        }
        System.out.println("les arcs sortants du point d'entree ont tous une valeure nulle");

        //test tous arcs ont un poid positif
        for (L3_B12_Sommet sommet : sommets){
            for (L3_B12_Arc arc : sommet.getArcs()){
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

    //calcul des dates et des marges
    public void Dates(){

        System.out.println("\n\n\t\t---calcul des dates:---\n");

        //intialisaton de la date du point d'entree a 0
        for (L3_B12_Sommet sommet : sommets){
            if (sommet.getRang()==0){
                sommet.setDate_tot(0);
            }
        }

        //calcul des dates au plus tot
        for (int i=1; i<=rang_max; i++){ //parcours des sommets rang par rang croissant
            for (L3_B12_Sommet sommet : sommets){
                if (sommet.getRang()==i){

                    //lorsqu on a sommet de notre rang courant on recupere sa liste de predecesseurs et leurs arcs
                    for (L3_B12_Sommet prec : sommets){
                        for (L3_B12_Arc arc : prec.getArcs()){
                            if (arc.getSuivant()==sommet.getNom()) {
                                if (sommet.getDate_tot() == -1) { // si le sommet courant n a pas de date on lui applique
                                    //calcul de la date : date du predecesseur + poid de l arc
                                    sommet.setDate_tot(prec.getDate_tot() + arc.getPoid());
                                    //si le sommet a deja une date, on regarde si la nouvelle date est plus grande pour la remplacer ou non
                                } else if (sommet.getDate_tot() < prec.getDate_tot() + arc.getPoid()) {
                                    sommet.setDate_tot(prec.getDate_tot() + arc.getPoid());
                                }
                            }
                        }
                    }
                }
            }
        }

        //initialisation de la date au plus tard du point de sortie a sa date au plus tot
        for (L3_B12_Sommet sommet : sommets){
            if (sommet.getRang()==rang_max){
                sommet.setDate_tard(sommet.getDate_tot());
            }
        }

        //calcul des dates au plus tard
        for (int i=rang_max-1; i>=0; i--){ //parcours des sommets rang par rang decroissant
            for (L3_B12_Sommet sommet : sommets){
                if (sommet.getRang()==i){

                    //lorsqu on a sommet de notre rang courant on recupere sa liste de successeurs et les arcs qui y vont
                    for (L3_B12_Arc arc : sommet.getArcs()){
                        for (L3_B12_Sommet suivant : sommets){
                            if (arc.getSuivant()==suivant.getNom()){
                                if (sommet.getDate_tard()==-1){ // si le sommet courant n a pas de date on lui applique
                                    //calcul de la date : date au du successeur - poid de l'arc
                                    sommet.setDate_tard(suivant.getDate_tard()-arc.getPoid());
                                    //si le sommet a deja une date, on regarde si la nouvelle date est plus petite pour la remplacer ou non
                                }else if (sommet.getDate_tard()>suivant.getDate_tard()-arc.getPoid()){
                                    sommet.setDate_tard(suivant.getDate_tard()-arc.getPoid());
                                }
                            }
                        }
                    }
                }
            }
        }

        //calcul des marges totales
        for (L3_B12_Sommet sommet : sommets){
            //calcul : date au plus tards - date au plus tot
            int marge = sommet.getDate_tard()-sommet.getDate_tot();
            sommet.setMarge_totale(marge);
        }

        // calcul des marges libres
        //pour chaque sommet on recupere ses successeurs et ses arcs
        for (L3_B12_Sommet sommet : sommets){
            for (L3_B12_Arc arc : sommet.getArcs()){
                for (L3_B12_Sommet sommet1 : sommets){
                    if (arc.getSuivant()==sommet1.getNom()){

                        //calcul : date tot du sommet suivant - date tot du sommet courant - poid de l'arc
                        int margeLibre = sommet1.getDate_tot()-sommet.getDate_tot()-arc.getPoid();

                        if (sommet.getMarge_libre()==-1){ // si le sommet courant n a pas de date on lui applique
                            sommet.setMarge_libre(margeLibre);
                            //si le sommet a deja une marge, on regarde si la nouvelle marge est plus petite pour la remplacer ou non
                        }else if(sommet.getMarge_libre()>margeLibre){
                            sommet.setMarge_libre(margeLibre);
                        }
                    }
                }
            }
        }

        //affichage des rangs de tous les sommets
        System.out.print("\nrang\t\t\t\t");
        for (int i=0; i<=rang_max; i++){
            for (L3_B12_Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    System.out.print(sommet.getRang()+"\t");
                }
            }
        }

        //afficahge des sommets en fonction de leurs rang
        System.out.print("\nsommet\t\t\t\t");
        for (int i=0; i<=rang_max; i++){
            for (L3_B12_Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    System.out.print(sommet.getNom()+"\t");
                }
            }
        }

        //affichage des dates au plus tot
        System.out.print("\ndate au plus tot\t");
        for (int i=0; i<=rang_max; i++){
            for (L3_B12_Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    System.out.print(sommet.getDate_tot()+"\t");
                }
            }
        }

        //affichage des dates au plus tard
        System.out.print("\ndate au plus tard\t");
        for (int i=0; i<=rang_max; i++){
            for (L3_B12_Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    System.out.print(sommet.getDate_tard()+"\t");
                }
            }
        }

        //affichage des marges totales
        System.out.print("\nmarge totale\t\t");
        for (int i=0; i<=rang_max; i++){
            for (L3_B12_Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    System.out.print(sommet.getMarge_totale()+"\t");
                }
            }
        }

        //affichage des marges libres
        System.out.print("\nmarge libre\t\t\t");
        for (int i=0; i<=rang_max; i++){
            for (L3_B12_Sommet sommet : sommets){
                if (sommet.getRang()==i){
                    if (sommet.getMarge_libre()==-1){ //skip la marge libre du dernier sommet
                        continue;
                    }
                    System.out.print(sommet.getMarge_libre()+"\t");
                }
            }
        }
    }
}
