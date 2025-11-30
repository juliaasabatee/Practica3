package dades;

public class ActivitatPeriodica extends Activitat{
    private String diaSetmana;
    private String horari;
    private String dataInicial;
    private int numSetmanes;
    private int limitPlaces;
    private double preu;
    private String nomCentre;
    private String ciutat;

    public ActivitatPeriodica(String nom, String diaSetmana, String horari, String dataInicial, int numSetmanes, int limitPlaces, double preu, String nomCentre, String ciutat) {
        super(nom);
        this.diaSetmana = diaSetmana;
        this.horari = horari;
        this.dataInicial = dataInicial;
        this.numSetmanes = numSetmanes;
        this.limitPlaces = limitPlaces;
        this.preu = preu;
        this.nomCentre = nomCentre;
        this.ciutat = ciutat;
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

    @Override
    public String toString() {
        return "ActivitatPeriodica (Nom: " + super.getNom() + ", Dia: " + diaSetmana + ", Horari: " + horari + ", Inici: " + dataInicial + ", Durada: " + numSetmanes + " setmanes, Places: " + limitPlaces + ", Preu: " + preu + "€, Centre: " + nomCentre + ", Ciutat: " + ciutat + ")";
    }
}
}
