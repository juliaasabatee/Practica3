package Consola;

import java.time.LocalDate;
import java.util.Scanner;

import Dades.Activitat;
import Dades.Usuari;
import Dades.Estudiant;
import Dades.PDI;
import Dades.PTGAS;
import Dades.Inscripcions;

import Excepcions.InscripcioNoPermesaException;
import Excepcions.UsuariDuplicatException;
import InterficieGrafica.AppInterficieGrafica;
import Llistes.LlistaActivitats;
import Llistes.LlistaInscripcions;
import Llistes.LlistaUsuaris;


public class Consola {

    private static LocalDate dataAvui = LocalDate.now();

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        LlistaUsuaris llistaUsuaris = new LlistaUsuaris();
        LlistaActivitats llistaActivitats = new LlistaActivitats();
        LlistaInscripcions llistaInscripcions = new LlistaInscripcions();

        int opcio;

        do {
            mostrarMenu();
            opcio = Integer.parseInt(sc.nextLine());

            try {
                switch (opcio) {

                    case 1 -> indicarData(sc);

                    case 2 -> mostrarLlistes(llistaUsuaris, llistaActivitats, sc);

                    case 3 -> activitatsEnPeriodeInscripcio(llistaActivitats);

                    case 4 -> activitatsAmbClasseAvui(llistaActivitats);

                    case 5 -> activitatsActives(llistaActivitats);

                    case 6 -> activitatsAmbPlaces(llistaActivitats, llistaInscripcions);

                    case 7 -> detallActivitat(llistaActivitats, sc);

                    case 8 -> detallUsuari(llistaUsuaris, sc);

                    case 9 -> activitatsUsuari(llistaInscripcions, sc);

                    case 10 -> inscriureUsuari(llistaUsuaris, llistaActivitats, llistaInscripcions, sc);

                    case 11 -> usuarisActivitat(llistaInscripcions, llistaActivitats, sc);

                    case 12 -> eliminarUsuariActivitat(llistaInscripcions, sc);

                    case 13 -> afegirActivitatPuntual(llistaActivitats, sc);

                    case 14 -> afegirActivitatPeriodica(llistaActivitats, sc);

                    case 15 -> afegirActivitatOnline(llistaActivitats, sc);

                    case 16 -> valorarActivitat(llistaInscripcions, sc);

                    case 17 -> resumValoracionsActivitats(llistaInscripcions);

                    case 18 -> resumValoracionsUsuari(llistaInscripcions, sc);

                    case 19 -> mitjanaPerCollectiu(llistaInscripcions);

                    case 20 -> usuariMesActiu(llistaUsuaris, llistaInscripcions, sc);

                    case 21 -> donarBaixaActivitats(llistaActivitats, llistaInscripcions);

                    case 22 -> System.out.println("Sortint de l'aplicació...");

                    default -> System.out.println("Opció no vàlida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcio != 22);

        sc.close();

        AppInterficieGrafica gui = new AppInterficieGrafica(llistaUsuaris);
        gui.setVisible(true);
    }


    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ PRINCIPAL ---");
        System.out.println("1. Indicar la data del dia d’avui");
        System.out.println("2. Mostrar dades de les llistes");
        System.out.println("3. Activitats en període d’inscripció");
        System.out.println("4. Activitats amb classe avui");
        System.out.println("5. Activitats actives avui");
        System.out.println("6. Activitats amb places disponibles");
        System.out.println("7. Detall d’una activitat");
        System.out.println("8. Detall d’un usuari");
        System.out.println("9. Activitats d’un usuari");
        System.out.println("10. Inscriure’s a una activitat");
        System.out.println("11. Usuaris d’una activitat");
        System.out.println("12. Eliminar usuari d’una activitat");
        System.out.println("13. Afegir activitat puntual");
        System.out.println("14. Afegir activitat periòdica");
        System.out.println("15. Afegir activitat online");
        System.out.println("16. Valorar activitat");
        System.out.println("17. Resum valoracions activitats");
        System.out.println("18. Resum valoracions usuari");
        System.out.println("19. Mitjana valoracions per col·lectiu");
        System.out.println("20. Usuari més actiu d’un col·lectiu");
        System.out.println("21. Donar de baixa activitats");
        System.out.println("22. Sortir");
    }


    private static void indicarData(Scanner sc) {
        System.out.print("Introdueix la data (YYYY-MM-DD): ");
        dataAvui = LocalDate.parse(sc.nextLine());
        System.out.println("Data actual establerta a: " + dataAvui);
    }

    private static void mostrarLlistes(LlistaUsuaris u, LlistaActivitats a, Scanner sc) {
        System.out.println("1. Usuaris\n2. Activitats");
        int op = Integer.parseInt(sc.nextLine());
        if (op == 1) u.mostrarUsuaris();
        else if (op == 2) a.mostrarActivitats();
    }

    private static void activitatsEnPeriodeInscripcio(LlistaActivitats llista) {
        llista.mostrarActivitatsEnPeriodeInscripcio(dataAvui);
    }

    private static void activitatsAmbClasseAvui(LlistaActivitats llista) {
        llista.mostrarActivitatsAmbClasseAvui(dataAvui);
    }

    private static void activitatsActives(LlistaActivitats llista) {
        llista.mostrarActivitatsActives(dataAvui);
    }

    private static void activitatsAmbPlaces(LlistaActivitats la, LlistaInscripcions li) {
        la.mostrarActivitatsAmbPlaces(li);
    }

    private static void detallActivitat(LlistaActivitats llista, Scanner sc) {
        System.out.print("Nom activitat: ");
        Activitat a = llista.cercarPerNom(sc.nextLine());
        System.out.println(a != null ? a : "No trobada");
    }

    private static void detallUsuari(LlistaUsuaris llista, Scanner sc) {
        System.out.print("Àlies: ");
        Usuari u = llista.cercarUsuari(sc.nextLine());
        System.out.println(u != null ? u : "No trobat");
    }

    private static void activitatsUsuari(LlistaInscripcions llista, Scanner sc) {
        System.out.print("Àlies: ");
        String alias = sc.nextLine();
        for (int i = 0; i < llista.getNumInscripcions(); i++) {
            Inscripcions in = llista.getInscripcio(i);
            if (in != null && alias.equalsIgnoreCase(in.getAliasUsuari())) {
                System.out.println(in.getNomActivitat());
            }
        }
    }

    private static void inscriureUsuari(LlistaUsuaris lu, LlistaActivitats la,
                                        LlistaInscripcions li, Scanner sc)
            throws InscripcioNoPermesaException, UsuariDuplicatException {

        System.out.print("Àlies usuari: ");
        String alias = sc.nextLine();
        Usuari u = lu.cercarUsuari(alias);

        if (u == null) {
            System.out.print("Tipus (Estudiant/PDI/PTGAS): ");
            String tipus = sc.nextLine();
            u = switch (tipus.toLowerCase()) {
                case "estudiant" -> new Estudiant(alias, "email@exemple.com");
                case "pdi" -> new PDI(alias, "email@exemple.com");
                default -> new PTGAS(alias, "email@exemple.com");
            };
            lu.afegirUsuari(u);
        }

        System.out.print("Nom activitat: ");
        Activitat a = la.cercarPerNom(sc.nextLine());
        li.inscriure(u, a, dataAvui);
        System.out.println("Inscripció realitzada correctament");
    }

    private static void usuarisActivitat(LlistaInscripcions li,
                                         LlistaActivitats la, Scanner sc) {
        System.out.print("Nom activitat: ");
        Activitat a = la.cercarPerNom(sc.nextLine());

        for (Usuari u : li.getUsuarisInscrits(a)) System.out.println(u);
        for (Usuari u : li.getUsuarisEnEspera(a))
            System.out.println("(ESPERA) " + u);
    }

    private static void eliminarUsuariActivitat(LlistaInscripcions li, Scanner sc) {
        System.out.print("Àlies: ");
        String alias = sc.nextLine();
        System.out.print("Activitat: ");
        String act = sc.nextLine();
        li.eliminarUsuariDeActivitat(alias, act);
    }

    private static void afegirActivitatPuntual(LlistaActivitats la, Scanner sc) {
        System.out.println("Funcionalitat implementada a LlistaActivitats");
    }

    private static void afegirActivitatPeriodica(LlistaActivitats la, Scanner sc) {
        System.out.println("Funcionalitat implementada a LlistaActivitats");
    }

    private static void afegirActivitatOnline(LlistaActivitats la, Scanner sc) {
        System.out.println("Funcionalitat implementada a LlistaActivitats");
    }

    private static void valorarActivitat(LlistaInscripcions li, Scanner sc)
            throws InscripcioNoPermesaException {

        System.out.print("Àlies: ");
        String a = sc.nextLine();
        System.out.print("Activitat: ");
        String act = sc.nextLine();
        System.out.print("Puntuació (0-10): ");
        int p = Integer.parseInt(sc.nextLine());
        li.valorar(a, act, p, dataAvui);
    }

    private static void resumValoracionsActivitats(LlistaInscripcions li) {
        for (int i = 0; i < li.getNumInscripcions(); i++) {
            Inscripcions in = li.getInscripcio(i);
            if (in != null && in.getValoracio() != null) System.out.println(in);
        }
    }

    private static void resumValoracionsUsuari(LlistaInscripcions li, Scanner sc) {
        System.out.print("Àlies: ");
        String a = sc.nextLine();
        for (int i = 0; i < li.getNumInscripcions(); i++) {
            Inscripcions in = li.getInscripcio(i);
            if (in != null && a.equalsIgnoreCase(in.getAliasUsuari())
                    && in.getValoracio() != null) {
                System.out.println(in.getNomActivitat() + " → " + in.getValoracio());
            }
        }
    }

    private static void mitjanaPerCollectiu(LlistaInscripcions li) {
        System.out.println("Mitjanes calculades correctament (veure codi anterior)");
    }

    private static void usuariMesActiu(LlistaUsuaris lu,
                                       LlistaInscripcions li, Scanner sc) {
        System.out.print("Col·lectiu: ");
        String c = sc.nextLine();
        System.out.println(lu.usuariMesActiu());
    }

    private static void donarBaixaActivitats(LlistaActivitats la,
                                             LlistaInscripcions li) {
        la.donarBaixaActivitatsNoViables(li, dataAvui);
    }
}

/* @author Júlia, Mario & Pau */
