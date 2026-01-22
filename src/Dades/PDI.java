package Dades;

public class PDI extends Usuari {

    private String departament;
    private String campus;

    public PDI(String alias, String correu,
               String departament, String campus) {
        super(alias, correu);
        this.departament = departament;
        this.campus = campus;
    }

    @Override
    public String getCollectiu() {
        return "PDI";
    }
}
