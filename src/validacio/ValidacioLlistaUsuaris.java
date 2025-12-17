package validacio;

import llistes.LlistaUsuaris;
import dades.Estudiant;
import excepcions.UsuariDuplicatException;

public class ValidacioLlistaUsuaris {

    public static void main(String[] args) {

        LlistaUsuaris llista = new LlistaUsuaris();

        Estudiant e1 = new Estudiant("maria", "maria.lopez", "GEI", 2022);
        Estudiant e2 = new Estudiant("joan", "joan.perez", "GEB", 2021);
        Estudiant e3 = new Estudiant("anna", "anna.garcia", "GESST", 2023);

        try {
            System.out.println("Afegir maria: " + llista.afegirUsuari(e1));
        } catch (UsuariDuplicatException ex) {
            System.out.println("Error afegint maria: " + ex.getMessage());
        }

        try {
            System.out.println("Afegir joan: " + llista.afegirUsuari(e2));
        } catch (UsuariDuplicatException ex) {
            System.out.println("Error afegint joan: " + ex.getMessage());
        }

        try {
            System.out.println("Afegir anna: " + llista.afegirUsuari(e3));
        } catch (UsuariDuplicatException ex) {
            System.out.println("Error afegint anna: " + ex.getMessage());
        }

        System.out.println("\nNombre d'usuaris: " + llista.getNumUsuaris());

        System.out.println("Cerca usuari 'joan': " + llista.cercarUsuari("joan"));

        System.out.println("\nUsuaris ordenats:");
        for (int i = 0; i < llista.getNumUsuaris(); i++) {
            System.out.println(" - " + llista.getUsuari(i).getAlias());
        }
    }
}
/* @author Júlia Sabaté */
