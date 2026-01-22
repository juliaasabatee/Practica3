package Dades;

import java.time.LocalDate;

public abstract class Activitat {

    protected String nom;
    protected String[] collectius;
    protected LocalDate dataIniciInscripcio;
    protected LocalDate dataFiInscripcio;
    protected String responsable;
    protected int capacitat;
    protected int inscrits;

    public Activitat(String nom, String[] collectius,
                     LocalDate dataIniciInscripcio,
                     LocalDate dataFiInscripcio,
                     int capacitat) {
        this.nom = nom;
        this.collectius = collectius;
        this.dataIniciInscripcio = dataIniciInscripcio;
        this.dataFiInscripcio = dataFiInscripcio;
        this.capacitat = capacitat;
        this.inscrits = 0;
    }

    public String getNom() {
        return nom;
    }

    public int getCapacitat() {
        return capacitat;
    }

    public int getInscrits() {
        return inscrits;
    }

    public boolean estaEnPeriode(LocalDate data) {
        return (data.isEqual(dataIniciInscripcio) || data.isAfter(dataIniciInscripcio)) &&
               (data.isEqual(dataFiInscripcio) || data.isBefore(dataFiInscripcio));
    }

    public boolean esEnTerminiInscripcio(LocalDate data) {
        return estaEnPeriode(data);
    }

    public boolean haAcabat(LocalDate data) {
        return data.isAfter(dataFiInscripcio);
    }

    public boolean tePlacesDisponibles() {
        return inscrits < capacitat;
    }

    public void inscriure() {
        inscrits++;
    }

    public void desinscriure() {
        if (inscrits > 0) inscrits--;
    }

    public abstract boolean hiHaClasseAvui(LocalDate data);

    @Override
    public String toString() {
        return nom + " (" + inscrits + "/" + capacitat + ")";
    }
}
