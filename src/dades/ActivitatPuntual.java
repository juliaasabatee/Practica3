package dades;

import java.time.LocalDate;
import java.time.LocalTime;

public class ActivitatPuntual extends Activitat {

    private LocalDate data;
    private LocalTime horaInici;
    private String ciutat;
    private int limitPlaces;
    private double preu;

    public ActivitatPuntual() {
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
}
