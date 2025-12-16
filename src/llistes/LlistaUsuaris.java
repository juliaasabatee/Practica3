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
        for (int i = 0; i < this.numUsuaris; i++) {
            novaLlista[i] = this.llista[i];
        }
        this.llista = novaLlista;
    }

    public boolean afegirUsuari(Usuari u) {
        if (u == null || existeixUsuari(u.getAlies())) {
            return false;
        }

        if (numUsuaris == llista.length) {
            duplicarCapacitat();
        }

        int pos = 0;
        while (pos < numUsuaris && 
               llista[pos].getAlies().compareToIgnoreCase(u.getAlies()) < 0) {
            pos++;
        }

        for (int i = numUsuaris; i > pos; i--) {
            llista[i] = llista[i - 1];
        }

        llista[pos] = u;
        numUsuaris++;
        return true;
    }

    public boolean existeixUsuari(String alies) {
        return cercarUsuari(alies) != null;
    }


    public Usuari cercarUsuari(String alies) {
        for (int i = 0; i < numUsuaris; i++) {
            if (llista[i].getAlies().equalsIgnoreCase(alies)) {
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
