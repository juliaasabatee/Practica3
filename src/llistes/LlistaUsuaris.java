package llistes;

import dades.Usuari;
import excepcions.UsuariDuplicatException;

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
        for (int i = 0; i < this.numUsuaris; i++) {
            novaLlista[i] = this.llista[i];
        }
        this.llista = novaLlista;
    }

    public void afegirUsuari(Usuari u) throws UsuariDuplicatException {
        if (u == null) {
            throw new IllegalArgumentException("L'usuari no pot ser null");
        }

        if (existeixUsuari(u.getAlias())) {
            throw new UsuariDuplicatException(
                    "L'usuari amb àlies '" + u.getAlias() + "' ja existeix."
            );
        }

        if (numUsuaris == llista.length) {
            duplicarCapacitat();
        }

        int pos = 0;
        while (pos < numUsuaris &&
                llista[pos].getAlias().compareToIgnoreCase(u.getAlias()) < 0) {
            pos++;
        }

        for (int i = numUsuaris; i > pos; i--) {
            llista[i] = llista[i - 1];
        }

        llista[pos] = u;
        numUsuaris++;
    }

    public boolean existeixUsuari(String alias) {
        return cercarUsuari(alias) != null;
    }

    public Usuari cercarUsuari(String alias) {
        for (int i = 0; i < numUsuaris; i++) {
            if (llista[i].getAlias().equalsIgnoreCase(alias)) {
                return llista[i];
            }
        }
        return null;
    }

    public int getNumUsuaris() {
        return numUsuaris;
    }

    public Usuari getUsuari(int pos) {
        if (pos < 0 || pos >= numUsuaris) {
            return null;
        }
        return llista[pos];
    }

    public Usuari[] getUsuaris() {
        Usuari[] resultat = new Usuari[numUsuaris];
        for (int i = 0; i < numUsuaris; i++) {
            resultat[i] = llista[i];
        }
        return resultat;
    }
}
/* @author Júlia Sabaté */
