package Validacio;

import Dades.Estudiant;

public class ValidacioEstudiant {

    public static void main(String[] args) {

        Estudiant e = new Estudiant(
                "julia",
                "julia.sabate"
        );

        System.out.println("Alias: " + e.getAlias());
        System.out.println("Correu inicial: " + e.getCorreuInici());
        System.out.println("Ensenyament: " + e.getEnsenyament());
        System.out.println("Any inici: " + e.getAnyInici());

        e.setAnyInici(2023);
        System.out.println("Any inici després del setter: " + e.getAnyInici());

        System.out.println(e.toString());
    }
}
/* @author Júlia Sabaté */

