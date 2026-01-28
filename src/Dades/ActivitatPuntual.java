package Dades;

import java.time.LocalDate;
import java.time.LocalTime;

public class ActivitatPuntual extends Activitat {

    private final int limitPlaces;
    private LocalDate data;
    private LocalTime horaInici;
    private String ciutat;
    private double preu;
    private double duracioHores;

    public ActivitatPuntual(String nom,
                            String[] collectius,
                            LocalDate dataIniciInscripcio,
                            LocalDate dataFiInscripcio,
                            int capacitat,
                            LocalDate data,
                            LocalTime horaInici,
                            String ciutat,
                            double limitPlaces,
                            double preu) {
        super(nom, collectius, dataIniciInscripcio, dataFiInscripcio, capacitat);
        this.data = data;
        this.horaInici = horaInici;
        this.ciutat = ciutat;
        this.limitPlaces = (int) limitPlaces;
        this.preu = preu;
        this.capacitat = (int) limitPlaces;
        this.inscrits = 0;
    }

    public LocalDate getData() {
        return data;
    }

    public LocalTime getHora() {
        return horaInici;
    }

    public String getCiutat() {
        return ciutat;
    }

    public double getPreu() {
        return preu;
    }

public String getResponsable() {
    return responsable;
}

    @Override
    public boolean hiHaClasseAvui(LocalDate dataAvui) {
        return this.data.equals(dataAvui);
    }

    public int getLimitPlaces() {
        return limitPlaces;
    }

    public double getDuracioHores() {
        return duracioHores;
    }

    public LocalTime getHoraInici() {
        return horaInici;
    }


}
