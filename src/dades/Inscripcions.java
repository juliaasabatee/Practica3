package dades;

public class Inscripcions {

    private Usuari usuari;
    private Activitat activitat;
    private boolean enLlistaEspera;
    private Integer valoracio;

    public Inscripcions() {
    }

    public Inscripcions(Usuari usuari,
                      Activitat activitat,
                      boolean enLlistaEspera) {
        this.usuari = usuari;
        this.activitat = activitat;
        this.enLlistaEspera = enLlistaEspera;
        this.valoracio = null;
    }

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
    }

    public Activitat getActivitat() {
         return activitat;
    }

    public void setActivitat(Activitat activitat) {
        this.activitat = activitat;
    }

    public boolean isEnLlistaEspera() {
        return enLlistaEspera;
    }

    public void setEnLlistaEspera(boolean enLlistaEspera) {
        this.enLlistaEspera = enLlistaEspera;
    }

    public Integer getValoracio() {
        return valoracio;
    }

    public void setValoracio(Integer valoracio) {
        this.valoracio = valoracio;
    }
}