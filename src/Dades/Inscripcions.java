package Dades;

import Llistes.LlistaActivitats;
import Llistes.LlistaUsuaris;

import java.io.Serializable;

public class Inscripcions implements Serializable {

    private static final long serialVersionUID = 1L;

    private transient Usuari usuari;
    private transient Activitat activitat;
    private String aliasUsuari;
    private String nomActivitat;
    private boolean enLlistaEspera;
    private Integer valoracio;

    public Inscripcions() {
    }

    public Inscripcions(Usuari usuari,
                      Activitat activitat,
                      boolean enLlistaEspera) {
        this.usuari = usuari;
        this.activitat = activitat;
        this.aliasUsuari = (usuari == null) ? null : usuari.getAlias();
        this.nomActivitat = (activitat == null) ? null : activitat.getNom();
        this.enLlistaEspera = enLlistaEspera;
        this.valoracio = null;
    }

    public String getAliasUsuari() {
        return aliasUsuari;
    }

    public String getNomActivitat() {
        return nomActivitat;
    }

    public Usuari getUsuari() {
        return usuari;
    }

    public void setUsuari(Usuari usuari) {
        this.usuari = usuari;
        this.aliasUsuari = (usuari == null) ? null : usuari.getAlias();
    }

    public Activitat getActivitat() {
         return activitat;
    }

    public void setActivitat(Activitat activitat) {
        this.activitat = activitat;
        this.nomActivitat = (activitat == null) ? null : activitat.getNom();
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

    public void resoldreReferencies(LlistaUsuaris usuaris, LlistaActivitats activitats) {
        if (usuaris != null && aliasUsuari != null) {
            this.usuari = usuaris.cercarUsuari(aliasUsuari);
        }
        if (activitats != null && nomActivitat != null) {
            this.activitat = activitats.cercarPerNom(nomActivitat);
        }
    }

    @Override
    public String toString() {
        String estat = enLlistaEspera ? "ESPERA" : "INSCRIT";
        String v = (valoracio == null) ? "-" : valoracio.toString();
        return "Inscripcio(" + aliasUsuari + " -> " + nomActivitat + ", " + estat + ", valoracio=" + v + ")";
    }
}