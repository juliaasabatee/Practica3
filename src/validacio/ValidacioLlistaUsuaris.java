package validacio;

import llistes.LlistaUsuaris;
import dades.Estudiant;

public class ValidacioLlistaUsuaris {

    public static void main(String[] args) {

        LlistaUsuaris llista = new LlistaUsuaris();

        Estudiant e1 = new Estudiant("maria", "maria.lopez", "GEI", 2022);
        Estudiant e2 = new Estudiant("joan", "joan.perez", "GEB", 2021);
        Estudiant e3 = new Estudiant("anna", "anna.garcia", "GESST", 2023);

        System.out.println("Afegir maria: " + llista.afegirUsuari(e1));
        System.out.println("Afegir joan: " + llista.afegirUsuari(e2));
        System.out.println("Afegir anna: " + llista.afegirUsuari(e3));

        System.out.println("Nombre usuaris: " + llista.getNumUsuaris());
        System.out.println("Cerca usuari joan: " + llista.cercarUsuari("joan"));

        System.out.println("Usuaris ordenats:");
        for (int i = 0; i < llista.getNumUsuaris(); i++) {
            System.out.println(" - " + llista.getUsuari(i).getAlias());
        }
    }
}
/* @author Júlia Sabaté */
