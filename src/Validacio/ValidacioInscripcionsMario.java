package Validacio;

import Dades.*;
import Excepcions.InscripcioNoPermesaException;
import GestioFitxers.LlistaInscripcionsSerialitzat;
import Llistes.LlistaActivitats;
import Llistes.LlistaInscripcions;
import Llistes.LlistaUsuaris;

import java.time.LocalDate;
import java.time.LocalTime;

public class ValidacioInscripcionsMario {

    public static void main(String[] args) throws Exception {
        LocalDate avui = LocalDate.of(2025, 10, 1);

        LlistaUsuaris usuaris = new LlistaUsuaris();
        usuaris.afegirUsuari(new Estudiant("mario", "mario"));
        usuaris.afegirUsuari(new Estudiant("pau", "pau"));
        usuaris.afegirUsuari(new Estudiant("julia", "julia"));

        LlistaActivitats activitats = new LlistaActivitats(20);
        ActivitatPuntual a1 = new ActivitatPuntual(
                "TallerJava",
                LocalDate.of(2025, 10, 10),
                LocalTime.of(10, 0),
                "Tarragona",
                2,
                10.0
        );
        a1.setCollectius(new String[]{"Estudiant"});
        a1.setDataIniciInscripcio(LocalDate.of(2025, 9, 1));
        a1.setDataFiInscripcio(LocalDate.of(2025, 10, 15));
        activitats.afegirActivitat(a1);

        LlistaInscripcions llista = new LlistaInscripcions();

        try {
            llista.inscriure(usuaris.cercarUsuari("mario"), a1, avui);
            llista.inscriure(usuaris.cercarUsuari("pau"), a1, avui);
            llista.inscriure(usuaris.cercarUsuari("julia"), a1, avui); // espera
        } catch (InscripcioNoPermesaException e) {
            System.out.println("Error inscripcio: " + e.getMessage());
        }

        System.out.println("Inscrits:");
        for (Usuari u : llista.getUsuarisInscrits(a1)) {
            System.out.println(" - " + u.getAlias());
        }
        System.out.println("Espera:");
        for (Usuari u : llista.getUsuarisEnEspera(a1)) {
            System.out.println(" - " + u.getAlias());
        }

        System.out.println("Eliminar 'pau' i promoure espera...");
        llista.eliminarUsuariDeActivitat("pau", "TallerJava");

        System.out.println("Inscrits:");
        for (Usuari u : llista.getUsuarisInscrits(a1)) {
            System.out.println(" - " + u.getAlias());
        }

        System.out.println("Valorar (després de la data)...");
        try {
            llista.valorar("mario", "TallerJava", 9, LocalDate.of(2025, 10, 10));
        } catch (Exception e) {
            System.out.println("Error valorar: " + e.getMessage());
        }

        System.out.println("Serialitzar...");
        LlistaInscripcionsSerialitzat.guardar(llista);
        LlistaInscripcions carregada = LlistaInscripcionsSerialitzat.carregar();
        carregada.resoldreReferencies(usuaris, activitats);
        System.out.println("Inscripcions carregades: " + carregada.getNumInscripcions());
    }
}
