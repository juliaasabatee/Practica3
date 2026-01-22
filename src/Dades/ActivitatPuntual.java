package Dades;

import java.time.LocalDate;
import java.time.LocalTime;

public class ActivitatPuntual extends Activitat {

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
                            double preu,
                            double duracioHores) {

        super(nom, collectius, dataIniciInscripcio, dataFiInscripcio, capacitat);
        this.data = data;
        this.horaInici = horaInici;
        this.ciutat = ciutat;
        this.preu = preu;
        this.duracioHores = duracioHores;
    }

    @Override
    public boolean hiHaClasseAvui(LocalDate dataAvui) {
        return this.data.equals(dataAvui);
    }
}
