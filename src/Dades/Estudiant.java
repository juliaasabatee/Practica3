package Dades;

public class Estudiant extends Usuari {

    private String ensenyament;
    private int anyInici;

    public Estudiant(String alias, String correu,
                     String ensenyament, int anyInici) {
        super(alias, correu);
        this.ensenyament = ensenyament;
        this.anyInici = anyInici;
    }

    @Override
    public String getCollectiu() {
        return "Estudiant";
    }
}
