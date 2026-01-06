package Excepcions;

public class ActivitatDuplicadaException extends RuntimeException {
    public ActivitatDuplicadaException(String nom) {
        super("Ja existeix una activitat amb el nom: " + nom);
    }
}