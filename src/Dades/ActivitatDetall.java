package Dades;

public class ActivitatDetall {
    private String nom;
    private double duracioHores;
    private String responsable;

    public ActivitatDetall(String nom, double duracioHores, String responsable) {
        this.nom = nom;
        this.duracioHores = duracioHores;
        this.responsable = responsable;
    }

    public String getNom() {
        return nom;
    }

    public double getDuracioHores() {
        return duracioHores;
    }

    public String getResponsable() {
        return responsable;
    }
}