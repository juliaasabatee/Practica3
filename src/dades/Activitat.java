package dades;
import java.time.LocalDate;

public abstract class Activitat {
    protected String nom;
    protected String[] collectius;
    protected LocalDate dataIniciInscripcio;
    protected LocalDate dataFiInscripcio;

    
    public Activitat() {
    }

   
    public Activitat(String nom, String[] collectius, LocalDate dataIniciInscripcio, LocalDate dataFiInscripcio) {
        this.nom = nom;
        this.collectius = collectius;
        this.dataIniciInscripcio = dataIniciInscripcio;
        this.dataFiInscripcio = dataFiInscripcio;
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
}