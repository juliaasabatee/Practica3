package Dades;
import java.time.LocalDate;

public class ActivitatOnline extends Activitat {
    private String enllac;
    private LocalDate dataIniciActivitat;
    private int diesVisualitzacio;

    public ActivitatOnline(String nom, String[] collectius, LocalDate dataIniciInscripcio, LocalDate dataFiInscripcio,
                           String enllac, LocalDate dataIniciActivitat, int diesVisualitzacio) {
      
        this.nom = nom;
        this.collectius = collectius;
        this.dataIniciInscripcio = dataIniciInscripcio;
        this.dataFiInscripcio = dataFiInscripcio;
        
       
        this.enllac = enllac;
        this.dataIniciActivitat = dataIniciActivitat;
        this.diesVisualitzacio = diesVisualitzacio;
    }

    public String getEnllac() {
        return enllac;
    }

    public void setEnllac(String enllac) {
        this.enllac = enllac;
    }

    public LocalDate getDataIniciActivitat() {
        return dataIniciActivitat;
    }

    public void setDataIniciActivitat(LocalDate dataIniciActivitat) {
        this.dataIniciActivitat = dataIniciActivitat;
    }

    public int getDiesVisualitzacio() {
        return diesVisualitzacio;
    }

    public void setDiesVisualitzacio(int diesVisualitzacio) {
        this.diesVisualitzacio = diesVisualitzacio;
    }
}