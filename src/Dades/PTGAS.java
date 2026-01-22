package Dades;

public class PTGAS extends Usuari {

    private String campusTreball;

    public PTGAS(String alias, String correu,
                 String campusTreball) {
        super(alias, correu);
        this.campusTreball = campusTreball;
    }

    @Override
    public String getCollectiu() {
        return "PTGAS";
    }
}
