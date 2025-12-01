package dades;

public class PDI extends Usuari {
    
    private String departament;
    private String campus;

    public PDI(String alies, String correu, String departament, String campus) {
        super(alies, correu);
        this.departament = departament;
        this.campus = campus;
    }

    public String getDepartament() {
        return departament;
    }

    public void setDepartament(String departament) {
        this.departament = departament;
    }

    public String getCampus() {
        return campus;
    }

    public void setCampus(String campus) {
        this.campus = campus;
    }
}