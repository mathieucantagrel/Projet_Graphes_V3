public class Sommet {
    private int nom;
    private int arc;
    private int valeur;

    public Sommet(int nom, int arc, int valeur) {
        this.nom = nom;
        this.arc = arc;
        this.valeur = valeur;
    }
//
    public int getNom() {
        return nom;
    }

    public void setNom(int nom) {
        this.nom = nom;
    }

    public int getArc() {
        return arc;
    }

    public void setArc(int arc) {
        this.arc = arc;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }
}
