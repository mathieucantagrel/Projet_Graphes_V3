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
        //je suis special 
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
//        Calendrier(MatriceValeurs,allSommet);
    }


    //lecture du graphe
    public static void Lecture(ArrayList<Sommet> sommet){
        System.out.println("\n\nLecture du graphe:\n");
        for (Sommet s : sommet){
            System.out.printf("%s -> %s = %s \n", s.getNom(), s.getSuivant(), s.getValeur());
        }
    }


    //creation de la matrice d'adjacence
    public static String[][] MatriceAdjacence(int nbArc, int nbSommet, ArrayList<Sommet> sommet){
        //initialisation de la matrice
        String[][] Matrix = CreationMatrice(nbSommet);

        //pour chaque case de la matrice on regarde si le cas est verifie, si oui on place un 1 dans cette case
        for (int i=0; i<nbArc; i++){
            for (int j=0; j<nbArc; j++){
                if (sommet.get(i).getSuivant()==j){
                    Matrix[sommet.get(i).getNom()][j]= String.valueOf(1);
                }
            }
        }

        return Matrix;
    }


    //creation de la matrice des valeurs
    public static String[][] MatriceValeurs(int nbArc, int nbSommet, ArrayList<Sommet> sommet){

        //initialisation de la matrice
        String[][] Matrix = CreationMatrice(nbSommet);

        //pour chaque case de la matrice on regarde si le cas est verifie, si oui on place la valeur de l'arc dans cette case
        for (int i=0; i<nbArc; i++){
            for (int j=0; j<nbArc; j++){
                if (sommet.get(i).getSuivant()==j){
                    Matrix[sommet.get(i).getNom()][j]= String.valueOf(sommet.get(i).getValeur());
                }
            }
        }

        return Matrix;
    }


    //creation d'une matrice vide
    public static String[][] CreationMatrice(int nbSommet){
        //initialisatin d'une matrice dont la taille est egale au nombre de sommet dans le graphe
        String[][] Matrix = new String [nbSommet][nbSommet];

        //remplissage de la matrice avec des * pour montrer que c'est un espace vide
        for (int i=0; i<nbSommet; i++){
            for (int j=0; j<nbSommet; j++){
                Matrix[i][j]= String.valueOf('*');
            }
        }
        return Matrix;
    }


    //affichage d'une matrice
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

        //copie de la matrice dans un autre pour y effectuer des modifications sans rien casser
        String[][] newMatrix = new String[nbSommet][nbSommet];
        for (int i=0; i<nbSommet; i++){
            for (int j=0; j<nbSommet; j++){
                newMatrix[i][j] = Matrix[i][j];
            }
        }

        //debut utilisation de la methode suppression des points d'entree

        System.out.println("\n\nDetection de circuit\nmethode de suppression des points d'entres");

        for (int k=0; k<nbSommet+1; k++) { //au maximum on effectuera autant d'iteration qu'il y a de points +1
            noPrecList.clear();
            affichage.clear();
            for (int i = 0; i < nbSommet; i++) { //on regarde chaque point du graphe

                hasNoPrec = true;
                for (int j = 0; j < nbSommet; j++) {
                    if (!Matrix[j][i].equals("*")) { //on regarde si un point dans la matrice a au moins un predesseur
                        hasNoPrec = false;
                        break;
                    }
                }
                if (hasNoPrec) { //si le point n'a pas de predecesseur alors on retient son rang et on le met dans un liste temporaire des points a supprimmer
                    if (!sommet.contains(i)){
                        sommet.add(i);
                        rank.add(k);
                        affichage.add(i);
                    }
                    noPrecList.add(i);
                }
            }
            //quand on a fini de parcourir la matrice on retire tous les points qu on avait mis comme n'ayant pas de predecesseur
            for (Integer i : noPrecList){
                for (int j=0; j<nbSommet; j++){
                    Matrix[i][j]="*";
                }

            }

            //affichage dans le terminal
            if (affichage.size()!=0) {
                System.out.println("Point(s) d'entree :");
                for (Integer i : affichage) {
                    System.out.printf("%s \t", i);
                }
                System.out.println("\nSuppression des points d'entree ");
            }

        }

        boolean isCircuit = false;
        //on regarde la matrice, si elle a un point different de zero alors il y a un circuit
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

    //calcul de rang
    //pour "calculer" le rang de chaque point on utilise les arrayLists qu on a creee dans la detectvion de circuit
    //ici en verite on ne calcul pas de rang mais on se contente de les afficher
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

    //detection de graphe d'ordonancement
    public static void Ordonancement(ArrayList<Integer> rank, ArrayList<Integer> sommet, ArrayList<Sommet> allsommet, String[][] Matrix){

        //detection du nombre de points d'entree
        int nbEntree=0;
        int entree = 0;
        for (int i=0; i<rank.size(); i++){
            if (rank.get(i)==0){
                nbEntree++;
                if (nbEntree>1){
                    System.out.println("\n\nle graph n'est pas un graphe d'ordonancement");
                    System.out.println("Le graphe contient plusieurs entrees");
                    return;
                }
                entree = sommet.get(i);
            }
        }

        //detection du nombre de points de sorties
        boolean isSortie;
        int nbSortie = 0;
        for (int i=0; i<nbSommet; i++){
            isSortie = true;
            for (int j=0; j<nbSommet; j++){
                if (!Matrix[i][j].equals("*")) {
                    isSortie = false;
                    break;
                }
            }
            if (isSortie) {
                nbSortie++;
                if (nbSortie>1){
                    System.out.println("\n\nle graph n'est pas un graphe d'ordonancement");
                    System.out.println("le graphe contient plusieurs sorties");
                    return;
                }
            }
        }


        //verification des valeurs des arcs sortants de chaque sommets
        int arcValue = 0;
        for (int i=0; i<allsommet.size(); i++){
            arcValue = 0;
            for (Sommet s : allsommet){
                if (s.getNom()==i){
                    if (arcValue==0){
                        arcValue = s.getValeur();
                    }else if (s.getValeur()!=arcValue){
                        System.out.println("\n\nle graph n'est pas un graphe d'ordonancement");
                        System.out.println("tous les arcs sortants d'un meme sommet n'ont pas la meme valeur");
                        return;
                    }
                }
            }
        }

        //verification des valeurs des arcs sortants du point d'entree
        for (Sommet s : allsommet){
            if (s.getNom()==entree){
                if (s.getValeur()!=0){
                    System.out.println("\n\nle graph n'est pas un graphe d'ordonancement");
                    System.out.println("les arcs sortants du point d'entree n'ont pas tous une valeure nulle");
                        return;
                }
            }
        }

        //verification de la presence d'arcs a valeur negative
        for (Sommet s : allsommet){
            if (s.getValeur()<0){
                System.out.println("\n\nle graph n'est pas un graphe d'ordonancement");
                System.out.println("Tous les arcs n'ont pas une valeure positive");
                        return;
            }
        }

        //si le graphe n'a qu'un seul point d'entree, un seul point de sortie, tous les arcs sortant d'un meme sommet ont la meme valeur,
        //tous les arcs sortant du point d'entree ont une valeur nulle et tous les arcs ont une valeur positive,
        //alors, c'est un graphe d'ordonancement.
        System.out.println("\n\nle graphe est un graphe d'ordonancement");

    }
    public static void Calendrier(String[][] Matrix, ArrayList<Sommet> allsommet) {
    	
    	ArrayList<Integer> Calendar_nom = new ArrayList<Integer>();
    	ArrayList<Integer> Calendar_date_tot = new ArrayList<Integer>();
    	ArrayList<Integer> Sommet_autorise = new ArrayList<Integer>();
    	ArrayList<Integer> val_temp = new ArrayList<Integer>();
    	ArrayList<Integer> Sommet_temp = new ArrayList<Integer>();
    	ArrayList<Dijkstra> point_entree = new ArrayList<Dijkstra>();
    	ArrayList<Dijkstra> determine_sommet = new ArrayList<Dijkstra>();
    	ArrayList<Integer> doublon_temp = new ArrayList<Integer>();
    	
    	int suivant_temp = 0;
    	int val = 0;
    	int Sommetdi = 0;
    	
    	int cc[][] = new int[nbSommet][nbSommet];
    	int temp = 0;
    	 
		for (Sommet s : allsommet){
			if(s.getValeur()==0) {
				temp  = s.getNom();
		    }
		}
		Calendar_nom.add(temp);
		Calendar_date_tot.add(0);
		
		 
		while(nbSommet != Calendar_nom.size()) {
			
			Sommet_autorise.clear();
			val_temp.clear();
			
			for (Integer nom : Calendar_nom){
				for (Sommet s : allsommet){//on détermine les successeur que l'on peut atteindre
					if(s.getNom() == nom) {
						Sommet_autorise.add(s.getSuivant());
					}
				}
			}
				
			for (Integer nom_fait : Calendar_nom){//on enleve les successeur avec deja des dates au plus tot
				for(int i=0;i<Sommet_autorise.size();i++) {
					if(nom_fait == Sommet_autorise.get(i)) {
						doublon_temp.add(i);
					}
				}
			}
			
			for(int i=0;i<doublon_temp.size();i++) {
				Sommet_autorise.remove(doublon_temp.get(i));
			}
			
			for(Integer auto : Sommet_autorise ) {//on determine les predecesseur du sommet pour calculer le plus cour chemin
				for (Sommet s : allsommet){
					point_entree.clear();//on clear la liste avant chaque calcul de predecesseur sur le sommet concerne
					if(auto == s.getSuivant() ) {
						Dijkstra S = new Dijkstra(s.getNom(),s.getSuivant(),s.getValeur(),1);
						point_entree.add(S);
					}
				}
					
				for(Dijkstra date_temp : point_entree) {
					while(date_temp.getNom()!= temp) {
						int predecesseur = 0;
						for (Sommet s : allsommet){
							if(s.getSuivant()==date_temp.getNom()) {
								if(predecesseur>0) {
									val = date_temp.getDate()+ s.getValeur();
									Dijkstra S = new Dijkstra(s.getNom(),s.getSuivant(),s.getValeur(),val);
									point_entree.add(S);
									predecesseur = predecesseur+1;
								}
								else {
									val = date_temp.getDate() + s.getValeur();
									date_temp.setDate(val);
									date_temp.setNom(s.getNom());
									date_temp.setValeur(s.getValeur());
									date_temp.setSuivant(s.getSuivant());
								}
							}
						}
					}	
				}
				
//				val = point_entree.get(0).getDate();

				for(Dijkstra date_temp : point_entree) {
					if(date_temp.getDate()<val) {
						val = date_temp.getDate();
					}				
				}

				Dijkstra D = new Dijkstra(auto,val);
				determine_sommet.add(D);
			}
			
			if (determine_sommet.size()!=0) {
				val =  determine_sommet.get(0).getDate();
				Sommetdi = determine_sommet.get(0).getNom();
			}
			
			for(Dijkstra di : determine_sommet) {
				if(val>di.getDate()) {
					val = di.getDate();
					Sommetdi = di.getNom();
				}
			}
			Calendar_nom.add(Sommetdi);
			Calendar_date_tot.add(val);
			
		}
		
		for(Integer testnom : Calendar_nom ) {
			System.out.println(testnom);
		}
		
		
	}
    
			
	  			
    			
    	
    	
    	
    
}
