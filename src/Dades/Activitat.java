package Dades;

import java.time.LocalDate;

public abstract class Activitat {
    protected String nom;
    protected String[] collectius;
    protected LocalDate dataIniciInscripcio;
    protected LocalDate dataFiInscripcio;
    protected String responsable; // responsable de l'activitat (p. ex. "Pau")
    protected int capacitat;
    protected int inscrits;

    public Activitat(String nom, String[] collectius, LocalDate dataIniciInscripcio, LocalDate dataFiInscripcio) {
        this.nom = nom;
        this.collectius = collectius;
        this.dataIniciInscripcio = dataIniciInscripcio;
        this.dataFiInscripcio = dataFiInscripcio;
    }

    public boolean estaEnPeriode(LocalDate data) {
        return (data.isEqual(dataIniciInscripcio) || data.isAfter(dataIniciInscripcio)) &&
               (data.isEqual(dataFiInscripcio) || data.isBefore(dataFiInscripcio));
    }

    public boolean haAcabat(LocalDate data) {
        return data.isAfter(dataFiInscripcio);
    }

    public boolean tePlacesDisponibles() {
        return inscrits < capacitat;
    }

    public boolean esEnTerminiInscripcio(LocalDate data) {
        return !haAcabat(data);
    }

    public void inscriure() {
        inscrits++;
    }

    public void desinscriure() {
        if (inscrits > 0) inscrits--;
    }

    public String getNom() {
        return nom;
    }

    @Override
    public String toString() {
        return nom + " (" + inscrits + "/" + capacitat + ")";
    }

    public abstract boolean hiHaClasseAvui(LocalDate data);
    }

    public Activitat() {
    }



    public Activitat(String nom) {
        this.nom = nom;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String[] getCollectius() {
        return collectius;
    }

    public void setCollectius(String[] collectius) {
        this.collectius = collectius;
    }

    public LocalDate getDataIniciInscripcio() {
        return dataIniciInscripcio;
    }

    public void setDataIniciInscripcio(LocalDate dataIniciInscripcio) {
        this.dataIniciInscripcio = dataIniciInscripcio;
    }

    public LocalDate getDataFiInscripcio() {
        return dataFiInscripcio;
    }

    public void setDataFiInscripcio(LocalDate dataFiInscripcio) {
        this.dataFiInscripcio = dataFiInscripcio;
    }

    public String getResponsable() {
        return responsable;
    }

    public void setResponsable(String responsable) {
        this.responsable = responsable;
    }
}
