public class L3_B12_Arc {

    private final int suivant;
    private final int poid;

    public L3_B12_Arc(int suivant, int poid) {
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
