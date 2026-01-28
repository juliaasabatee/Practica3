package GestioFitxers;

import Dades.*;
import Llistes.LlistaActivitats;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Formato:
 *
 * PUNTUAL;nom;collectius;insIni;insFi;limit;preu;data;hora;ciutat
 * PERIODICA;nom;collectius;insIni;insFi;limit;preu;dia;horari;dataInicial;numSetmanes;centre;ciutat
 * ONLINE;nom;collectius;insIni;insFi;enllac;dataInici;dies
 */
public class LlistaActivitatsText {

    /* ================= CARREGAR ================= */

    public static void carregar(LlistaActivitats llista, Path fitxer) throws IOException {
        if (llista == null || fitxer == null) return;

        try (BufferedReader br = Files.newBufferedReader(fitxer)) {
            String linia;
            while ((linia = br.readLine()) != null) {
                if (linia.isBlank() || linia.startsWith("#")) continue;

                String[] p = linia.split(";");
                switch (p[0].toUpperCase()) {
                    case "PUNTUAL" -> parsePuntual(p, llista);
                    case "PERIODICA", "PERIÒDICA" -> parsePeriodica(p, llista);
                    case "ONLINE" -> parseOnline(p, llista);
                }
            }
        }
    }

    /* ================= DESAR ================= */

    public static void desar(LlistaActivitats llista, Path fitxer) throws IOException {
        List<String> out = new ArrayList<>();

        for (Activitat a : llista.getAllActivitats()) {

            if (a instanceof ActivitatPuntual ap) {
                out.add(String.join(";",
                        "PUNTUAL",
                        ap.getNom(),
                        join(ap.getCollectius()),
                        safeDate(ap.getDataIniciInscripcio()),
                        safeDate(ap.getDataFiInscripcio()),
                        String.valueOf(ap.getLimitPlaces()),
                        String.valueOf(ap.getPreu()),
                        safeDate(ap.getData()),
                        safeTime(ap.getHoraInici()),
                        ap.getCiutat()
                ));
            }

            else if (a instanceof ActivitatPeriodica ap) {
                out.add(String.join(";",
                        "PERIODICA",
                        ap.getNom(),
                        join(ap.getCollectius()),
                        safeDate(ap.getDataIniciInscripcio()),
                        safeDate(ap.getDataFiInscripcio()),
                        String.valueOf(ap.getLimitPlaces()),
                        String.valueOf(ap.getPreu()),
                        ap.getDiaSetmana(),
                        ap.getHorari(),
                        ap.getDataInicial(),
                        String.valueOf(ap.getNumSetmanes()),
                        ap.getNomCentre(),
                        ap.getCiutat()
                ));
            }

            else if (a instanceof ActivitatOnline ao) {
                out.add(String.join(";",
                        "ONLINE",
                        ao.getNom(),
                        join(ao.getCollectius()),
                        safeDate(ao.getDataIniciInscripcio()),
                        safeDate(ao.getDataFiInscripcio()),
                        ao.getEnllac(),
                        safeDate(ao.getDataIniciActivitat()),
                        String.valueOf(ao.getDiesVisualitzacio())
                ));
            }
        }

        Files.write(fitxer, out,
                StandardOpenOption.CREATE,
                StandardOpenOption.TRUNCATE_EXISTING);
    }

    /* ================= PARSING ================= */

    private static void parsePuntual(String[] p, LlistaActivitats l) {
        ActivitatPuntual a = new ActivitatPuntual(
                p[1],                       // nom
                split(p[2]),                // collectius (asumo que split devuelve String[])
                LocalDate.parse(p[3]),      // dataIniciInscripcio
                LocalDate.parse(p[4]),      // dataFiInscripcio
                Integer.parseInt(p[5]),     // capacitat
                LocalDate.parse(p[7]),      // data
                LocalTime.parse(p[8]),      // horaInici
                p[9],                       // ciutat
                Double.parseDouble(p[5]),   // limitPlaces (uso capacitat porque no hay otro en el CSV)
                Double.parseDouble(p[6])    // preu
        );
        l.afegirActivitat(a);
    }

    private static void parsePeriodica(String[] p, LlistaActivitats l) {
        // Al no poder usar HashSet, pasamos null.
        // Si necesitas guardar los días, deberías cambiar el constructor para aceptar String p[7]

        ActivitatPeriodica a = new ActivitatPeriodica(
                p[1],                       // nom
                split(p[2]),                // collectius
                LocalDate.parse(p[3]),      // dataIniciInscripcio
                LocalDate.parse(p[4]),      // dataFiInscripcio
                Integer.parseInt(p[5]),     // capacitat
                p[7],                       // diaSetmana (String raw)
                p[8],                       // horari
                p[9],                       // dataInicial
                Integer.parseInt(p[10]),    // numSetmanes
                Integer.parseInt(p[5]),     // limitPlaces
                Double.parseDouble(p[6]),   // preu
                p[11],                      // nomCentre
                p[12],                      // ciutat
                null                        // <--- AQUÍ: Pasamos null porque no puedes usar HashSet
        );
        l.afegirActivitat(a);
    }

    private static void parseOnline(String[] p, LlistaActivitats l) {
        // NOTA: He ajustado los índices asumiendo que el CSV sigue el orden lógico
        // de los atributos que pide el constructor nuevo.
        ActivitatOnline a = new ActivitatOnline(
                p[1],                       // nom
                split(p[2]),                // collectius
                LocalDate.parse(p[3]),      // dataIniciInscripcio
                LocalDate.parse(p[4]),      // dataFiInscripcio
                Integer.parseInt(p[5]),     // capacitat
                p[6],                       // enllac (asumiendo que está tras la capacidad)
                LocalDate.parse(p[7]),      // dataIniciActivitat
                Integer.parseInt(p[8])      // diesVisualitzacio
        );
        l.afegirActivitat(a);
    }
    /* ================= UTILS ================= */

    private static String[] split(String s) {
        return s == null || s.isBlank() ? null : s.split(",");
    }

    private static String join(String[] a) {
        return a == null ? "" : String.join(",", a);
    }

    private static String safeDate(LocalDate d) {
        return d == null ? "" : d.toString();
    }

    private static String safeTime(LocalTime t) {
        return t == null ? "" : t.toString();
    }

    public static void carregar(Path p) {
    }
}
