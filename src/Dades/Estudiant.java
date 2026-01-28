package Dades;

public class Estudiant extends Usuari {

    private String ensenyament;
    private int anyInici;

    public Estudiant(String alias, String correuInici, String ensenyament, int anyInici) {
        super(alias, correuInici);
        this.ensenyament = ensenyament;
        this.anyInici = anyInici;
    }

    @Override
    public String getCollectiu() {
        return "Estudiant";
    }

    public String getEnsenyament() {
        return ensenyament;
    }

    public int getAnyInici() {
        return anyInici;
    }

    public void setAnyInici(int i) {
        this.anyInici= anyInici;
    }
}
