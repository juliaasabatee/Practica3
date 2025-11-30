package llistes;
import dades.Usuari;
public class LlistaUsuaris {
    private Usuari[] llista;
    private int numUsuaris;
    private static final int MAX_INICIAL = 10;
    public LlistaUsuaris() {
        this.llista = new Usuari[MAX_INICIAL];
        this.numUsuaris = 0;
    }
    private void duplicarCapacitat() {
        Usuari[] novaLlista = new Usuari[this.llista.length * 2];
        for (int i = 0; i < this.llista.length; i++) {
            novaLlista[i] = this.llista[i];
        }
        this.llista = novaLlista;
    }
}