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
            llista.afegirUsuari(e1);
            System.out.println("Usuari maria afegit correctament");
        } catch (UsuariDuplicatException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        try {
            llista.afegirUsuari(e2);
            System.out.println("Usuari joan afegit correctament");
        } catch (UsuariDuplicatException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        try {
            llista.afegirUsuari(e3);
            System.out.println("Usuari anna afegit correctament");
        } catch (UsuariDuplicatException e) {
            System.out.println("ERROR: " + e.getMessage());
        }

        System.out.println("Nombre d'usuaris: " + llista.getNumUsuaris());

        System.out.println("Cerca usuari joan: " + llista.cercarUsuari("joan"));

        System.out.println("Usuaris ordenats:");
        for (int i = 0; i < llista.getNumUsuaris(); i++) {
            System.out.println(" - " + llista.getUsuari(i).getAlias());
        }

        System.out.println("Prova usuari duplicat:");
        try {
            llista.afegirUsuari(e1);
        } catch (UsuariDuplicatException e) {
            System.out.println("ERROR esperat: " + e.getMessage());
        }
    }
}
/* @author Júlia Sabaté */
