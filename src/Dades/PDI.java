package Dades;

public class PDI extends Usuari {
    
    private String departament;
    private String campus;

    public PDI(String alies, String correu) {
        super(alies, correu);
        this.departament = departament;
        this.campus = campus;
    }
    public String getCollectiu() {
        return "PDI"; 
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
