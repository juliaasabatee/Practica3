package Dades;

import java.time.LocalDate;
import java.time.LocalTime;

public class ActivitatPuntual extends Activitat {

    private LocalDate data;
    private LocalTime horaInici;
    private String ciutat;
    private int limitPlaces;
    private double preu;
    private double duracioHores = 1.0; // duración en horas por defecto

    public ActivitatPuntual() {
        super();
    }

    public ActivitatPuntual(String nom,
                            LocalDate data,
                            LocalTime horaInici,
                            String ciutat,
                            int limitPlaces,
                            double preu) {
        super(nom);
        this.data = data;
        this.horaInici = horaInici;
        this.ciutat = ciutat;
        this.limitPlaces = limitPlaces;
        this.preu = preu;
    }

    public ActivitatPuntual(String nom,
                            LocalDate data,
                            LocalTime horaInici,
                            String ciutat,
                            int limitPlaces,
                            double preu,
                            double duracioHores) {
        this(nom, data, horaInici, ciutat, limitPlaces, preu);
        this.duracioHores = duracioHores;
    }

    public LocalDate getData() {
        return data;
    }

    public void setData(LocalDate data) {
        this.data = data;
    }

    public LocalTime getHoraInici() {
        return horaInici;
    }

    public void setHoraInici(LocalTime horaInici) {
        this.horaInici = horaInici;
    }

    public String getCiutat() {
        return ciutat;
    }

    public void setCiutat(String ciutat) {
        this.ciutat = ciutat;
    }

    public int getLimitPlaces() {
        return limitPlaces;
    }

    public void setLimitPlaces(int limitPlaces) {
        this.limitPlaces = limitPlaces;
    }

    public double getPreu() {
        return preu;
    }

    public void setPreu(double preu) {
        this.preu = preu;
    }

    public double getDuracioHores() {
        return duracioHores;
    }

    public void setDuracioHores(double duracioHores) {
        this.duracioHores = duracioHores;
    }
}