package llistes;
import dades.Activitat;
public class LlistaActivitats {
    private Activitat[] llista;
    private int nElem;

    public LlistaActivitats(int tamanyMaxim) {
        this.llista = new Activitat[tamanyMaxim];
        this.nElem = 0;
    }

    public void afegirActivitat(Activitat a) {
        if (nElem < llista.length) {
            llista[nElem] = a;
            nElem++;
        }
    }

    public Activitat getActivitat(int index) {
        if (index >= 0 && index < nElem) {
            return llista[index];
        }
        return null;
    }

    public int getNElem() {
        return nElem;
    }
}