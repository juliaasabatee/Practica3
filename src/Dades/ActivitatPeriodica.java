package Dades;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public abstract class ActivitatPeriodica extends Activitat{
    private Set<DayOfWeek> dies;
    private String diaSetmana;
    private String horari;
    private String dataInicial;
    private int numSetmanes;
    private int limitPlaces;
    private double preu;
    private String nomCentre;
    private String ciutat;
    private double duracioHores;

    public  ActivitatPeriodica(String nom, String[] collectius,
                                       LocalDate dataIniciInscripcio,
                                       LocalDate dataFiInscripcio,
                                       int capacitat, String diaSetmana, String horari, String dataInicial, int numSetmanes, int limitPlaces, double preu, String nomCentre, String ciutat, Set<DayOfWeek> dies, double duracioHores) {

        super(nom, collectius, dataIniciInscripcio, dataFiInscripcio, capacitat);
        this.diaSetmana = diaSetmana;
        this.horari = horari;
        this.dataInicial = dataInicial;
        this.numSetmanes = numSetmanes;
        this.limitPlaces = limitPlaces;
        this.preu = preu;
        this.nomCentre = nomCentre;
        this.ciutat = ciutat;
        this.dies = dies;
    }
    public String getDiaSetmana() {
        return this.diaSetmana;
    }
    public String getHorari() {
        return this.horari;
    }
    public String getDataInicial() {
        return this.dataInicial;
    }
    public int getNumSetmanes() {
        return this.numSetmanes;
    }
    public int getLimitPlaces() {
        return this.limitPlaces;
    }
    public double getPreu() {
        return this.preu;
    }
    public String getNomCentre() {
        return this.nomCentre;
    }
    public String getCiutat() {
        return this.ciutat;
    }
    public String getResponsable() {
        return responsable;
    }
    public double getDuracioHores() {
        return duracioHores;
    }

    @Override
    public String toString() {
        return "ActivitatPeriodica (Nom: " + super.getNom() + ", Dia: " + diaSetmana + ", Horari: " + horari + ", Inici: " + dataInicial + ", Durada: " + numSetmanes + " setmanes, Places: " + limitPlaces + ", Preu: " + preu + "€, Centre: " + nomCentre + ", Ciutat: " + ciutat + ")";
    }
    @Override
    public boolean hiHaClasseAvui(LocalDate data) {
        return estaEnPeriode(data) &&
               dies.contains(data.getDayOfWeek());
    }
}
/* @author Júlia Sabaté */

