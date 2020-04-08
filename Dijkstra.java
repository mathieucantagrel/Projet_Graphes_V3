public class Dijkstra {
	private int nom;
    private int suivant;
    private int valeur;
    private int date;
    
    public Dijkstra(int nom, int suivant, int valeur,int date) {
            this.nom = nom;
            this.suivant = suivant;
            this.valeur = valeur;
            this.date = date;
    }
    
    public Dijkstra(int nom,int date) {
    	this.nom = nom;
    	this.date = date;
    }  
    
	public int getNom() {
		return nom;
	}
	public void setNom(int nom) {
		this.nom = nom;
	}
	public int getSuivant() {
		return suivant;
	}
	public void setSuivant(int suivant) {
		this.suivant = suivant;
	}
	public int getValeur() {
		return valeur;
	}
	public void setValeur(int valeur) {
		this.valeur = valeur;
	}
	public int getDate() {
		return date;
	}
	public void setDate(int date) {
		this.date = date;
	}
}
