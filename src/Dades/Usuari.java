package Dades;

public abstract class Usuari {

    protected String alias;
    protected String correuInici;

    public Usuari(String alias, String correuInici) {
        this.alias = alias;
        this.correuInici = correuInici;
    }

    public String getAlias() {
        return this.alias;
    }

    public String getCorreuInici() {
        return this.correuInici;
    }

    @Override
    public String toString() {
        return "Usuari Base (Alias: " + alias + ", Correu Inicial: " + correuInici + ")";
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Usuari altreUsuari = (Usuari) obj;
        return this.alias.equals(altreUsuari.alias);
    }
}
/* @author Júlia Sabaté */