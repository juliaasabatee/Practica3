package Dades;

import Llistes.LlistaActivitats;

public abstract class Usuari {

    protected String alias;
    protected String correuInici;
    protected LlistaActivitats activitats;
    protected String[] collectius;
    protected int sumaValoracions;
    protected int numValoracions;

    public Usuari(String alias, String correuInici) {
        this.alias = alias;
        this.correuInici = correuInici;
        this.activitats = new LlistaActivitats();
        this.collectius = new String[0];
        this.sumaValoracions = 0;
        this.numValoracions = 0;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getCorreuInici() {
        return this.correuInici;
    }

    public int getNumActivitats() {
        return activitats.getNumActivitats();
    }

    public int getNumValoracions() {
        return numValoracions;
    }

    public int getSumaValoracions() {
        return sumaValoracions;
    }

    public void afegirValoracio(int puntuacio) {
        sumaValoracions += puntuacio;
        numValoracions++;
    }

    public boolean teCollectiu(String collectiu) {
        for (int i = 0; i < collectius.length; i++) {
            if (collectius[i].equalsIgnoreCase(collectiu)) {
                return true;
            }
        }
        return false;
    }

    public void mostrarActivitats() {
        if (activitats.getNumActivitats() == 0) {
            System.out.println("Aquest usuari no té activitats.");
            return;
        }

        for (int i = 0; i < activitats.getNumActivitats(); i++) {
            System.out.println(activitats.getActivitat(i));
        }
    }

    public void resumValoracions() {
        if (numValoracions == 0) {
            System.out.println("Sense valoracions");
            return;
        }

        double mitjana = sumaValoracions / (double) numValoracions;
        System.out.println("Mitjana: " + mitjana);
    }

    
    public abstract String getCollectiu();

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Usuari)) return false;
        return alias.equalsIgnoreCase(((Usuari) o).alias);
    }

    @Override
    public String toString() {
        return alias + " (" + getCollectiu() + ")";
    }
}

/* @author Júlia Sabaté */
