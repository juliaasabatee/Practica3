package Llistes;

import Dades.Activitat;
import Dades.ActivitatOnline;
import Dades.ActivitatPeriodica;
import Dades.ActivitatPuntual;
import Dades.Inscripcions;
import Dades.PDI;
import Dades.PTGAS;
import Dades.Estudiant;
import Dades.Usuari;
import Excepcions.InscripcioNoPermesaException;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class LlistaInscripcions implements Serializable {

    private static final long serialVersionUID = 1L;
    private static final int CAPACITAT_INICIAL = 50;
    private static final int MAX_ESPERA = 10;

    private Inscripcions[] inscripcions;
    private int numInscripcions;

    public LlistaInscripcions() {
        this(CAPACITAT_INICIAL);
    }

    public LlistaInscripcions(int capacitatMaxima) {
        inscripcions = new Inscripcions[capacitatMaxima];
        numInscripcions = 0;
    }

    private void duplicarCapacitat() {
        Inscripcions[] nova = new Inscripcions[this.inscripcions.length * 2];
        System.arraycopy(this.inscripcions, 0, nova, 0, this.numInscripcions);
        this.inscripcions = nova;
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
        duplicarCapacitat();
        inscripcions[numInscripcions] = inscripcio;
        numInscripcions++;
        return true;
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

    private Inscripcions buscarInscripcio(String aliasUsuari, String nomActivitat) {
        if (aliasUsuari == null || nomActivitat == null) return null;
        for (int i = 0; i < numInscripcions; i++) {
            Inscripcions ins = inscripcions[i];
            if (ins == null) continue;
            if (aliasUsuari.equalsIgnoreCase(ins.getAliasUsuari())
                    && nomActivitat.equalsIgnoreCase(ins.getNomActivitat())) {
                return ins;
            }
        }
        return null;
    }

    private int comptarEspera(String nomActivitat) {
        int compt = 0;
        for (int i = 0; i < numInscripcions; i++) {
            Inscripcions ins = inscripcions[i];
            if (ins == null) continue;
            if (nomActivitat.equalsIgnoreCase(ins.getNomActivitat()) && ins.isEnLlistaEspera()) {
                compt++;
            }
        }
        return compt;
    }

    private String collectiuDe(Usuari usuari) {
        if (usuari instanceof PDI) return "PDI";
        if (usuari instanceof PTGAS) return "PTGAS";
        if (usuari instanceof Estudiant) return "Estudiant";
        return "";
    }

    private boolean activitatOfereixA(Activitat activitat, String collectiu) {
        if (activitat == null) return false;
        String[] colls = activitat.getCollectius();
        if (colls == null || colls.length == 0) return true;
        for (int i = 0; i < colls.length; i++) {
            if (colls[i] != null && colls[i].equalsIgnoreCase(collectiu)) {
                return true;
            }
        }
        return false;
    }

    private boolean enPeriodeInscripcio(Activitat activitat, LocalDate avui) {
        if (activitat == null || avui == null) return false;
        if (activitat.getDataIniciInscripcio() == null || activitat.getDataFiInscripcio() == null) return false;
        return !avui.isBefore(activitat.getDataIniciInscripcio()) && !avui.isAfter(activitat.getDataFiInscripcio());
    }

    private int limitPlaces(Activitat activitat) {
        if (activitat instanceof ActivitatOnline) return Integer.MAX_VALUE;
        if (activitat instanceof ActivitatPuntual) return ((ActivitatPuntual) activitat).getLimitPlaces();
        if (activitat instanceof ActivitatPeriodica) return ((ActivitatPeriodica) activitat).getLimitPlaces();
        return Integer.MAX_VALUE;
    }

    public void inscriure(Usuari usuari, Activitat activitat, LocalDate avui) throws InscripcioNoPermesaException {
        if (usuari == null || activitat == null) {
            throw new InscripcioNoPermesaException("Usuari o activitat null");
        }
        if (!enPeriodeInscripcio(activitat, avui)) {
            throw new InscripcioNoPermesaException("Fora del període d'inscripció");
        }

        String collectiu = collectiuDe(usuari);
        if (!activitatOfereixA(activitat, collectiu)) {
            throw new InscripcioNoPermesaException("Activitat no oferta al col·lectiu de l'usuari");
        }

        if (buscarInscripcio(usuari.getAlias(), activitat.getNom()) != null) {
            throw new InscripcioNoPermesaException("L'usuari ja està inscrit a l'activitat");
        }

        int limit = limitPlaces(activitat);
        boolean aEspera = false;
        if (limit != Integer.MAX_VALUE) {
            int inscrits = contarInscritsActius(activitat);
            if (inscrits >= limit) {
                int espera = comptarEspera(activitat.getNom());
                if (espera >= MAX_ESPERA) {
                    throw new InscripcioNoPermesaException("Llista d'espera plena");
                }
                aEspera = true;
            }
        }

        afegirInscripcio(new Inscripcions(usuari, activitat, aEspera));
    }

    public Usuari[] getUsuarisInscrits(Activitat activitat) {
        if (activitat == null) return new Usuari[0];
        int compt = 0;
        for (int i = 0; i < numInscripcions; i++) {
            Inscripcions ins = inscripcions[i];
            if (ins == null) continue;
            if (activitat.getNom().equalsIgnoreCase(ins.getNomActivitat()) && !ins.isEnLlistaEspera() && ins.getUsuari() != null) {
                compt++;
            }
        }
        Usuari[] res = new Usuari[compt];
        int idx = 0;
        for (int i = 0; i < numInscripcions; i++) {
            Inscripcions ins = inscripcions[i];
            if (ins == null) continue;
            if (activitat.getNom().equalsIgnoreCase(ins.getNomActivitat()) && !ins.isEnLlistaEspera() && ins.getUsuari() != null) {
                res[idx++] = ins.getUsuari();
            }
        }
        return res;
    }

    public Usuari[] getUsuarisEnEspera(Activitat activitat) {
        if (activitat == null) return new Usuari[0];
        int compt = 0;
        for (int i = 0; i < numInscripcions; i++) {
            Inscripcions ins = inscripcions[i];
            if (ins == null) continue;
            if (activitat.getNom().equalsIgnoreCase(ins.getNomActivitat()) && ins.isEnLlistaEspera() && ins.getUsuari() != null) {
                compt++;
            }
        }
        Usuari[] res = new Usuari[compt];
        int idx = 0;
        for (int i = 0; i < numInscripcions; i++) {
            Inscripcions ins = inscripcions[i];
            if (ins == null) continue;
            if (activitat.getNom().equalsIgnoreCase(ins.getNomActivitat()) && ins.isEnLlistaEspera() && ins.getUsuari() != null) {
                res[idx++] = ins.getUsuari();
            }
        }
        return res;
    }

    public boolean eliminarUsuariDeActivitat(String aliasUsuari, String nomActivitat) {
        if (aliasUsuari == null || nomActivitat == null) return false;
        int index = -1;
        Inscripcions eliminada = null;
        for (int i = 0; i < numInscripcions; i++) {
            Inscripcions ins = inscripcions[i];
            if (ins == null) continue;
            if (aliasUsuari.equalsIgnoreCase(ins.getAliasUsuari())
                    && nomActivitat.equalsIgnoreCase(ins.getNomActivitat())) {
                index = i;
                eliminada = ins;
                break;
            }
        }
        if (index == -1) return false;

        // borrar por desplazamiento
        for (int j = index; j < numInscripcions - 1; j++) {
            inscripcions[j] = inscripcions[j + 1];
        }
        inscripcions[numInscripcions - 1] = null;
        numInscripcions--;

        // si era inscrit (no espera), promocionar al primero de espera
        if (eliminada != null && !eliminada.isEnLlistaEspera()) {
            for (int i = 0; i < numInscripcions; i++) {
                Inscripcions ins = inscripcions[i];
                if (ins == null) continue;
                if (nomActivitat.equalsIgnoreCase(ins.getNomActivitat()) && ins.isEnLlistaEspera()) {
                    ins.setEnLlistaEspera(false);
                    break;
                }
            }
        }
        return true;
    }

    private LocalDate dataFiActivitat(Activitat activitat) throws InscripcioNoPermesaException {
        if (activitat instanceof ActivitatPuntual) {
            return ((ActivitatPuntual) activitat).getData();
        }
        if (activitat instanceof ActivitatOnline) {
            ActivitatOnline ao = (ActivitatOnline) activitat;
            if (ao.getDataIniciActivitat() == null) {
                throw new InscripcioNoPermesaException("Activitat online sense data d'inici");
            }
            return ao.getDataIniciActivitat().plusDays(ao.getDiesVisualitzacio());
        }
        if (activitat instanceof ActivitatPeriodica) {
            ActivitatPeriodica ap = (ActivitatPeriodica) activitat;
            try {
                LocalDate inici = LocalDate.parse(ap.getDataInicial());
                return inici.plusWeeks(ap.getNumSetmanes() - 1L);
            } catch (DateTimeParseException e) {
                throw new InscripcioNoPermesaException("Format dataInicial no vàlid a activitat periòdica");
            }
        }
        throw new InscripcioNoPermesaException("No es pot determinar la data de fi de l'activitat");
    }

    public void valorar(String aliasUsuari, String nomActivitat, int puntuacio, LocalDate avui) throws InscripcioNoPermesaException {
        if (puntuacio < 0 || puntuacio > 10) {
            throw new InscripcioNoPermesaException("La valoració ha d'estar entre 0 i 10");
        }
        Inscripcions ins = buscarInscripcio(aliasUsuari, nomActivitat);
        if (ins == null) {
            throw new InscripcioNoPermesaException("No existeix la inscripció");
        }
        if (ins.isEnLlistaEspera()) {
            throw new InscripcioNoPermesaException("Els usuaris en llista d'espera no poden valorar");
        }
        if (ins.getValoracio() != null) {
            throw new InscripcioNoPermesaException("L'activitat ja està valorada per aquest usuari");
        }
        if (ins.getActivitat() == null) {
            throw new InscripcioNoPermesaException("Inscripció sense activitat resolta (cal carregar i resoldre)");
        }
        LocalDate fi = dataFiActivitat(ins.getActivitat());
        if (avui == null || avui.isBefore(fi)) {
            throw new InscripcioNoPermesaException("L'activitat encara no ha finalitzat");
        }
        ins.setValoracio(puntuacio);
        if (ins.getUsuari() != null) {
            ins.getUsuari().afegirValoracio(puntuacio);
        }
    }

    public void resoldreReferencies(LlistaUsuaris usuaris, LlistaActivitats activitats) {
        for (int i = 0; i < numInscripcions; i++) {
            if (inscripcions[i] != null) {
                inscripcions[i].resoldreReferencies(usuaris, activitats);
            }
        }
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