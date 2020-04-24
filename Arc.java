public class Arc {

    private final int suivant;
    private final int poid;

    public Arc(int suivant, int poid) {
        this.suivant = suivant;
        this.poid = poid;
    }

    public int getSuivant() {
        return suivant;
    }

    public int getPoid() {
        return poid;
    }


}
