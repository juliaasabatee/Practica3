package Llistes;

import Dades.Activitat;
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

    public boolean afegirInscripcio(Inscripcions inscripcio) {
        if (inscripcio == null) return false;
        if (numInscripcions < inscripcions.length) {
            inscripcions[numInscripcions] = inscripcio;
            numInscripcions++;
            return true;
        }
        return false;
    }

    public Inscripcions getInscripcio(int index) {
        if (index >= 0 && index < numInscripcions) {
            return inscripcions[index];
        }
        return null;
    }

    public boolean eliminarInscripcio(Inscripcions ins) {
        if (ins == null) return false;
        for (int i = 0; i < numInscripcions; i++) {
            if (inscripcions[i] == ins) {
                // desplazar a la izquierda
                for (int j = i; j < numInscripcions - 1; j++) {
                    inscripcions[j] = inscripcions[j + 1];
                }
                inscripcions[numInscripcions - 1] = null;
                numInscripcions--;
                return true;
            }
        }
        return false;
    }

    public Inscripcions[] getInscripcionsPerActivitat(Activitat a) {
        if (a == null) return new Inscripcions[0];
        Inscripcions[] temp = new Inscripcions[numInscripcions];
        int c = 0;
        for (int i = 0; i < numInscripcions; i++) {
            if (inscripcions[i] != null && inscripcions[i].getActivitat() != null &&
                inscripcions[i].getActivitat().getNom().equalsIgnoreCase(a.getNom())) {
                temp[c++] = inscripcions[i];
            }
        }
        Inscripcions[] res = new Inscripcions[c];
        System.arraycopy(temp, 0, res, 0, c);
        return res;
    }

    public int contarInscritsActius(Activitat a) {
        if (a == null) return 0;
        int compt = 0;
        for (int i = 0; i < numInscripcions; i++) {
            Inscripcions in = inscripcions[i];
            if (in != null && !in.isEnLlistaEspera() && in.getActivitat() != null &&
                in.getActivitat().getNom().equalsIgnoreCase(a.getNom())) {
                compt++;
            }
        }
        return compt;
    }
}