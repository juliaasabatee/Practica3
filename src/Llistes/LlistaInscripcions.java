package Llistes;

import Dades.Inscripcions;

public class LlistaInscripcions {

    private Inscripcions[] inscripcions;
    private int numInscripcions;

    public LlistaInscripcions(int capacitatMaxima) {
        inscripcions = new Inscripcions[capacitatMaxima];
        numInscripcions = 0;
    }

    public Inscripcions[] getInscripcions() {
        return inscripcions;
    }

    public void setInscripcions(Inscripcions[] inscripcions) {
        this.inscripcions = inscripcions;
    }

    public int getNumInscripcions() {
        return numInscripcions;
    }

    public void setNumInscripcions(int numInscripcions) {
        this.numInscripcions = numInscripcions;
    }

    public void afegirInscripcio(Inscripcions inscripcio) {
        if (numInscripcions < inscripcions.length) {
            inscripcions[numInscripcions] = inscripcio;
            numInscripcions++;
        }
    }

    public Inscripcions[] getInscripcio(int j) {
        return inscripcions;
    }
}