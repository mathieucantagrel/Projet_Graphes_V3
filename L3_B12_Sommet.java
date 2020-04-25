import java.util.ArrayList;

public class L3_B12_Sommet {

    //un arc a un nom, une liste d'arcs, un rang, un ensemble de dates et de marges
    private final int nom;
    private ArrayList<L3_B12_Arc> arcs = null;
    private int rang;
    private int date_tot=-1;
    private int date_tard=-1;
    private int marge_totale=-1;
    private int marge_libre= -1;

    public L3_B12_Sommet(int nom, ArrayList<L3_B12_Arc> arcs) {
        this.nom = nom;
        this.arcs = arcs;
    }

    public int getNom() {
        return nom;
    }

    public ArrayList<L3_B12_Arc> getArcs() {
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

    public int getMarge_totale() {
        return marge_totale;
    }

    public void setMarge_totale(int marge_totale) {
        this.marge_totale = marge_totale;
    }

    public int getMarge_libre() {
        return marge_libre;
    }

    public void setMarge_libre(int marge_libre) {
        this.marge_libre = marge_libre;
    }
}
