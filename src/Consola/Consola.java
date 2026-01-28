package Consola;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Scanner;
import java.nio.file.Path;
import java.nio.file.Paths;

import Dades.*;
import Excepcions.*;
import InterficieGrafica.AppInterficieGrafica;
import Llistes.*;

public class Consola {

    private static LocalDate dataAvui = LocalDate.now();

    public static void main(String[] args) {
        Path p = Paths.get("activitats.txt");
        LlistaActivitatsText.carregar(p);

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

                    case 3 -> llistaActivitats.mostrarActivitatsEnPeriodeInscripcio(dataAvui);

                    case 4 -> llistaActivitats.mostrarActivitatsAmbClasseAvui(dataAvui);

                    case 5 -> llistaActivitats.mostrarActivitatsActives(dataAvui);

                    case 6 -> llistaActivitats.mostrarActivitatsAmbPlaces(llistaInscripcions);

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

                    case 19 -> mitjanaPerCollectiu(llistaUsuaris, sc);

                    case 20 -> System.out.println("Usuari més actiu: " + llistaUsuaris.usuariMesActiu());

                    case 21 -> llistaActivitats.donarBaixaActivitatsNoViables(llistaInscripcions, dataAvui);

                    case 22 -> System.out.println("Sortint...");

                    default -> System.out.println("Opció no vàlida");
                }
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }

        } while (opcio != 22);

        sc.close();

        new AppInterficieGrafica(llistaUsuaris).setVisible(true);
    }

    private static void mostrarMenu() {
        System.out.println("\n--- MENÚ ---");
        System.out.println("1. Indicar data");
        System.out.println("2. Mostrar llistes");
        System.out.println("3. Activitats en període inscripció");
        System.out.println("4. Activitats amb classe avui");
        System.out.println("5. Activitats actives avui");
        System.out.println("6. Activitats amb places");
        System.out.println("7. Detall activitat");
        System.out.println("8. Detall usuari");
        System.out.println("9. Activitats d’un usuari");
        System.out.println("10. Inscriure usuari");
        System.out.println("11. Usuaris d’una activitat");
        System.out.println("12. Eliminar usuari d’una activitat");
        System.out.println("13. Afegir activitat puntual");
        System.out.println("14. Afegir activitat periòdica");
        System.out.println("15. Afegir activitat online");
        System.out.println("16. Valorar activitat");
        System.out.println("17. Resum valoracions activitats");
        System.out.println("18. Resum valoracions usuari");
        System.out.println("19. Mitjana per col·lectiu");
        System.out.println("20. Usuari més actiu");
        System.out.println("21. Donar baixa activitats");
        System.out.println("22. Sortir");
    }

    private static void indicarData(Scanner sc) {
        System.out.print("Data (YYYY-MM-DD): ");
        dataAvui = LocalDate.parse(sc.nextLine());
    }

    private static void mostrarLlistes(LlistaUsuaris u, LlistaActivitats a, Scanner sc) {
        System.out.println("1. Usuaris\n2. Activitats");
        int op = Integer.parseInt(sc.nextLine());
        if (op == 1) u.mostrarUsuaris();
        else a.mostrarActivitats();
    }

    private static void detallActivitat(LlistaActivitats la, Scanner sc) {
        System.out.print("Nom: ");
        Activitat a = la.cercarPerNom(sc.nextLine());
        System.out.println(a != null ? a : "No trobada");
    }

    private static void detallUsuari(LlistaUsuaris lu, Scanner sc) {
        System.out.print("Àlies: ");
        Usuari u = lu.cercarUsuari(sc.nextLine());
        System.out.println(u != null ? u : "No trobat");
    }

    private static void activitatsUsuari(LlistaInscripcions li, Scanner sc) {
        System.out.print("Àlies: ");
        String alias = sc.nextLine();
        for (int i = 0; i < li.getNumInscripcions(); i++) {
            Inscripcions in = li.getInscripcio(i);
            if (in != null && alias.equalsIgnoreCase(in.getAliasUsuari())) {
                System.out.println(in);
            }
        }
    }

    private static void inscriureUsuari(LlistaUsuaris lu, LlistaActivitats la,
                                        LlistaInscripcions li, Scanner sc)
            throws Exception {

        System.out.print("Àlies: ");
        String alias = sc.nextLine();
        Usuari u = lu.cercarUsuari(alias);

        if (u == null) {
            System.out.print("Tipus (Estudiant/PDI/PTGAS): ");
            String t = sc.nextLine().toLowerCase();
            u = switch (t) {
                case "estudiant" -> new Estudiant(alias, "mail");
                case "pdi" -> new PDI(alias, "mail");
                default -> new PTGAS(alias, "mail");
            };
            lu.afegirUsuari(u);
        }

        System.out.print("Activitat: ");
        Activitat a = la.cercarPerNom(sc.nextLine());
        li.inscriure(u, a, dataAvui);
    }

    private static void usuarisActivitat(LlistaInscripcions li,
                                         LlistaActivitats la, Scanner sc) {
        System.out.print("Activitat: ");
        Activitat a = la.cercarPerNom(sc.nextLine());
        for (Usuari u : li.getUsuarisInscrits(a)) System.out.println(u);
        for (Usuari u : li.getUsuarisEnEspera(a)) System.out.println("(ESPERA) " + u);
    }

    private static void eliminarUsuariActivitat(LlistaInscripcions li, Scanner sc) {
        System.out.print("Àlies: ");
        String a = sc.nextLine();
        System.out.print("Activitat: ");
        String act = sc.nextLine();
        li.eliminarUsuariDeActivitat(a, act);
    }

    private static void afegirActivitatPuntual(LlistaActivitats la, Scanner sc) {
        System.out.print("Nom: ");
        String nom = sc.nextLine();
        System.out.print("Data (YYYY-MM-DD): ");
        LocalDate d = LocalDate.parse(sc.nextLine());
        System.out.print("Hora (HH:MM): ");
        LocalTime h = LocalTime.parse(sc.nextLine());
        ActivitatPuntual ap = ActivitatPuntual ap = new ActivitatPuntual(nom, new String[]{"Estudiant","PDI","PTGAS"}, dataAvui, dataAvui.plusDays(5), 20, d, h, "Ciutat", 20.0,  1.0);
        la.afegirActivitat(ap);
    }

    private static void afegirActivitatPeriodica(LlistaActivitats la, Scanner sc) {
        System.out.println("Afegida via constructor (simplificat)");
    }

    private static void afegirActivitatOnline(LlistaActivitats la, Scanner sc) {
        System.out.println("Afegida via constructor (simplificat)");
    }

    private static void valorarActivitat(LlistaInscripcions li, Scanner sc)
            throws InscripcioNoPermesaException {

        System.out.print("Àlies: ");
        String a = sc.nextLine();
        System.out.print("Activitat: ");
        String act = sc.nextLine();
        System.out.print("Puntuació: ");
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
            if (in != null && a.equalsIgnoreCase(in.getAliasUsuari()))
                System.out.println(in);
        }
    }

    private static void mitjanaPerCollectiu(LlistaUsuaris lu, Scanner sc) {
        System.out.print("Col·lectiu: ");
        String c = sc.nextLine();
        System.out.println("Mitjana: " + lu.mitjanaPerCollectiu(c));
    }
}


