package consola;

import java.time.LocalDate;
import java.util.Scanner;

import Dades.Activitat;
import Dades.Usuari;
import Dades.Estudiant;
import Dades.PDI;
import Dades.PTGAS;
import Dades.Inscripcions;

import Llistes.LlistaActivitats;
import Llistes.LlistaInscripcions;
import Llistes.LlistaUsuaris;

public class Consola {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);

        LlistaUsuaris llistaUsuaris = new LlistaUsuaris();
        LlistaActivitats llistaActivitats = new LlistaActivitats();
        LlistaInscripcions llistaInscripcions = new LlistaInscripcions();

        int opcio;

        do {
            System.out.println("\n--- MENÚ ---");
            System.out.println("1. Indicar data d'avui");
            System.out.println("2. Mostrar usuaris");
            System.out.println("7. Detall activitat");
            System.out.println("8. Detall usuari");
            System.out.println("9. Activitats d'un usuari");
            System.out.println("18. Resum valoracions usuari");
            System.out.println("19. Mitjana valoracions per col·lectiu");
            System.out.println("20. Usuari més actiu d'un col·lectiu");
            System.out.println("0. Sortir");

            opcio = Integer.parseInt(sc.nextLine());

            switch (opcio) {

                case 1:
                    System.out.print("Introdueix la data (YYYY-MM-DD): ");
                    LocalDate dataAvui = LocalDate.parse(sc.nextLine());
                    System.out.println("Data actual: " + dataAvui);
                    break;

                case 2:
                    for (int i = 0; i < llistaUsuaris.getNumUsuaris(); i++) {
                        System.out.println(llistaUsuaris.getUsuari(i));
                    }
                    break;

                case 7:
                    mostrarDetallActivitat(llistaActivitats, sc);
                    break;

                case 8:
                    mostrarDetallUsuari(llistaUsuaris, sc);
                    break;

                case 9:
                    mostrarActivitatsUsuari(llistaInscripcions, sc);
                    break;

                case 18:
                    resumValoracionsUsuari(llistaInscripcions, sc);
                    break;

                case 19:
                    mitjanaValoracionsPerCollectiu(llistaInscripcions);
                    break;

                case 20:
                    usuariMesActiu(llistaInscripcions, llistaUsuaris, sc);
                    break;
            }

        } while (opcio != 0);

        sc.close();
    }

    private static void mostrarDetallUsuari(LlistaUsuaris llista, Scanner sc) {
        System.out.print("Àlies usuari: ");
        String alias = sc.nextLine();
        Usuari u = llista.cercarUsuari(alias);
        System.out.println(u != null ? u : "Usuari no trobat");
    }

    private static void mostrarDetallActivitat(LlistaActivitats llista, Scanner sc) {
        System.out.print("Nom activitat: ");
        String nom = sc.nextLine();
        Activitat a = llista.cercarPerNom(nom);
        System.out.println(a != null ? a : "Activitat no trobada");
    }

    private static void mostrarActivitatsUsuari(LlistaInscripcions llista, Scanner sc) {
        System.out.print("Àlies usuari: ");
        String alias = sc.nextLine();

        for (int i = 0; i < llista.getNumInscripcions(); i++) {
            Inscripcions in = llista.getInscripcio(i);
            if (in != null && in.getUsuari() != null &&
                    in.getUsuari().getAlias().equalsIgnoreCase(alias)) {
                System.out.println(in.getNomActivitat());
            }
        }
    }

    private static void resumValoracionsUsuari(LlistaInscripcions llista, Scanner sc) {
        System.out.print("Àlies usuari: ");
        String alias = sc.nextLine();

        for (int i = 0; i < llista.getNumInscripcions(); i++) {
            Inscripcions in = llista.getInscripcio(i);
            if (in != null && in.getUsuari() != null &&
                    in.getUsuari().getAlias().equalsIgnoreCase(alias) &&
                    in.getValoracio() != null) {

                System.out.println(in.getNomActivitat() +
                        " → " + in.getValoracio());
            }
        }
    }

    private static void mitjanaValoracionsPerCollectiu(LlistaInscripcions llista) {

        double sumaEst = 0, sumaPDI = 0, sumaPT = 0;
        int cEst = 0, cPDI = 0, cPT = 0;

        for (int i = 0; i < llista.getNumInscripcions(); i++) {
            Inscripcions in = llista.getInscripcio(i);
            if (in == null || in.getValoracio() == null) continue;

            Usuari u = in.getUsuari();

            if (u instanceof Estudiant) {
                sumaEst += in.getValoracio();
                cEst++;
            } else if (u instanceof PDI) {
                sumaPDI += in.getValoracio();
                cPDI++;
            } else if (u instanceof PTGAS) {
                sumaPT += in.getValoracio();
                cPT++;
            }
        }

        if (cEst > 0) System.out.println("Mitjana Estudiants: " + (sumaEst / cEst));
        if (cPDI > 0) System.out.println("Mitjana PDI: " + (sumaPDI / cPDI));
        if (cPT > 0) System.out.println("Mitjana PTGAS: " + (sumaPT / cPT));
    }

    private static void usuariMesActiu(LlistaInscripcions llistaIns,
                                       LlistaUsuaris llistaUs,
                                       Scanner sc) {

        System.out.print("Col·lectiu (Estudiant/PDI/PTGAS): ");
        String tipus = sc.nextLine();

        Usuari millor = null;
        int max = 0;

        for (int i = 0; i < llistaUs.getNumUsuaris(); i++) {
            Usuari u = llistaUs.getUsuari(i);

            if (!u.getClass().getSimpleName().equalsIgnoreCase(tipus)) continue;

            int compt = 0;
            for (int j = 0; j < llistaIns.getNumInscripcions(); j++) {
                Inscripcions in = llistaIns.getInscripcio(j);
                if (in != null && in.getUsuari() != null &&
                        in.getUsuari().equals(u)) {
                    compt++;
                }
            }

            if (compt > max) {
                max = compt;
                millor = u;
            }
        }

        System.out.println(millor != null ? millor : "Cap usuari trobat");
    }
}
/* @author Júlia, Mario & Pau */
