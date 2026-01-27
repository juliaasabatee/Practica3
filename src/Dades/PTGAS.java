package Dades;

public class PTGAS extends Usuari {

    private String campusTreball;

    public PTGAS(String alies, String correu, String campusTreball) {
        super(alies, correu);
        this.campusTreball = campusTreball;
    }

    @Override
    public String getCollectiu() {
        return "PTGAS";
    }
}
