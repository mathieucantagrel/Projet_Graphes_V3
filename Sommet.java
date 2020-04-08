public class Sommet {
    private int nom;
    private int suivant;
    private int valeur;
    

    public Sommet(int nom, int suivant, int valeur) {
        this.nom = nom;
        this.suivant = suivant;
        this.valeur = valeur;
    }
//
    public int getNom() {
        return nom;
    }

    public void setNom(int nom) {
        this.nom = nom;
    }

    public int getSuivant() {
        return suivant;
    }

    public void setArc(int suivant) {
        this.suivant = suivant;
    }

    public int getValeur() {
        return valeur;
    }

    public void setValeur(int valeur) {
        this.valeur = valeur;
    }

}
