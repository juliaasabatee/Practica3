package dades;

public class Estudiant extends Usuari{
    private String ensenyament;
    private int anyInici;
    public Estudiant(String alias, String correuInici, String ensenyament, int anyInici) {
        super(alias, correuInici);
        this.ensenyament = ensenyament;
        this.anyInici = anyInici;
    }

    public String getEnsenyament() {
        return this.ensenyament;
    }

    public int getAnyInici() {
        return this.anyInici;
    }

    public void setAnyInici(int anyInici) {
        this.anyInici = anyInici;
    }
}
