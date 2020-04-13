import java.util.ArrayList;

public class Sommet {

    private int nom;
    private ArrayList<Arc> arcs = null;
    private int rang;
    private int date_tot=-1;
    private int date_tard=-1;

    public Sommet(int nom, ArrayList<Arc> arcs) {
        this.nom = nom;
        this.arcs = arcs;
    }

    public int getNom() {
        return nom;
    }

    public ArrayList<Arc> getArcs() {
        return arcs;
    }

    public int getRang() {
        return rang;
    }

    public void setRang(int rang) {
        this.rang = rang;
    }

    public int getDate_tot() {
        return date_tot;
    }

    public void setDate_tot(int date_tot) {
        this.date_tot = date_tot;
    }

    public int getDate_tard() {
        return date_tard;
    }

    public void setDate_tard(int date_tard) {
        this.date_tard = date_tard;
    }
}
