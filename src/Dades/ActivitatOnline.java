package Dades;

import java.time.LocalDate;

public class ActivitatOnline extends Activitat {

    private String enllac;
    private LocalDate dataIniciActivitat;
    private int diesVisualitzacio;

    public ActivitatOnline(String nom, String[] collectius,
                           LocalDate dataIniciInscripcio,
                           LocalDate dataFiInscripcio,
                           int capacitat,
                           String enllac,
                           LocalDate dataIniciActivitat,
                           int diesVisualitzacio) {

        super(nom, collectius, dataIniciInscripcio, dataFiInscripcio, capacitat);
        this.enllac = enllac;
        this.dataIniciActivitat = dataIniciActivitat;
        this.diesVisualitzacio = diesVisualitzacio;
    }
public LocalDate getDataIniciActivitat() {
    return dataIniciActivitat;
}

public int getDiesVisualitzacio() {
    return diesVisualitzacio;
}

public String getResponsable() {
    return responsable;
}

    @Override
    public boolean hiHaClasseAvui(LocalDate data) {
        LocalDate fi = dataIniciActivitat.plusDays(diesVisualitzacio);
        return (data.isEqual(dataIniciActivitat) || data.isAfter(dataIniciActivitat)) &&
               (data.isBefore(fi) || data.isEqual(fi));
    }
}
