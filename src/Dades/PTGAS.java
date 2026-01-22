package Dades;

public class PTGAS extends Usuari {

    private String campusTreball;

    public PTGAS() {
        super("", "");
    }

    public PTGAS(String alies,
                       String correuSenseDomini) {
        super(alies, correuSenseDomini);
        this.campusTreball = campusTreball;
    }

    public String getCampusTreball() {
        return campusTreball;
    }

    public void setCampusTreball(String campusTreball) {
        this.campusTreball = campusTreball;
    }
    public String getCollectiu() { 
        return "PTGAS"; 
    }

}
