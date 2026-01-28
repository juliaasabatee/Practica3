package GestioFitxers;

import Dades.*;
import Llistes.LlistaActivitats;
import java.io.*;
import java.nio.file.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Formato (campos separados por ';'):
 *
 * PUNTUAL;nom;collectius(coma);inscripcioInici;inscripcioFi;limitPlaces;preu;data(yyyy-MM-dd);hora(HH:mm);ciutat
 * PERIODICA;nom;collectius;inscripcioInici;inscripcioFi;limitPlaces;preu;diaSetmana;horari;dataInicial;numSetmanes;nomCentre;ciutat
 * ONLINE;nom;collectius;inscripcioInici;inscripcioFi;enllac;dataIniciActivitat;diesVisualitzacio
 */
public class LlistaActivitatsText {

    public static void carregar(LlistaActivitats llista, Path fitxer) throws IOException {
        if (llista == null || fitxer == null) return;
        try (BufferedReader r = Files.newBufferedReader(fitxer)) {
            String linia;
            int lin = 0;
            while ((linia = r.readLine()) != null) {
                lin++;
                linia = linia.trim();
                if (linia.isEmpty() || linia.startsWith("#")) continue;
                String[] parts = linia.split(";");
                String tipus = parts[0].trim().toUpperCase();
                try {
                    switch (tipus) {
                        case "PUNTUAL":
                            parseAndAddPuntual(parts, llista);
                            break;
                        case "PERIODICA":
                        case "PERIÒDICA": // admitir variantes
                            parseAndAddPeriodica(parts, llista);
                            break;
                        case "ONLINE":
                            parseAndAddOnline(parts, llista);
                            break;
                        default:
                            // línea desconocida -> ignorar
                    }
                } catch (Exception e) {
                    // ignorar línea malformada, pero no detener la carga
                    System.err.println("Warning: linea " + lin + " malformada (" + e.getMessage() + ")");
                }
            }
        }
    }

    public static void desar(LlistaActivitats llista, Path fitxer) throws IOException {
        if (llista == null || fitxer == null) return;
        List<String> lineas = new ArrayList<>();
        Activitat[] arr = llista.getAllActivitats();
        for (Activitat a : arr) {
            if (a instanceof ActivitatPuntual) {
                ActivitatPuntual ap = new ActivitatPuntual(
    nom,
    collectius,
    insIni,
    insFi,
    limit,
    data,
    hora,
    ciutat,
    limit,
    preu
);

                String collectius = joinCollectius(ap.getCollectius());
                String linea = String.join(";",
                        "PUNTUAL",
                        safe(ap.getNom()),
                        safe(collectius),
                        safeDate(ap.getDataIniciInscripcio()),
                        safeDate(ap.getDataFiInscripcio()),
                        String.valueOf(ap.getLimitPlaces()),
                        String.valueOf(ap.getPreu()),
                        safeDate(ap.getData()),
                        safeTime(ap.getHoraInici()),
                        safe(ap.getCiutat())
                );
                lineas.add(linea);
            } else if (a instanceof ActivitatPeriodica) {
                ActivitatPeriodica ap = new ActivitatPeriodica(
    nom,
    collectius,
    insIni,
    insFi,
    limit,
    diaSetmana,
    horari,
    dataInicial,
    numSetmanes,
    limit,
    preu,
    nomCentre,
    ciutat,
    Set.of(DayOfWeek.MONDAY) // o el que toqui
);

                String collectius = joinCollectius(ap.getCollectius());
                String linea = String.join(";",
                        "PERIODICA",
                        safe(ap.getNom()),
                        safe(collectius),
                        safeDate(ap.getDataIniciInscripcio()),
                        safeDate(ap.getDataFiInscripcio()),
                        String.valueOf(ap.getLimitPlaces()),
                        String.valueOf(ap.getPreu()),
                        safe(ap.getDiaSetmana()),
                        safe(ap.getHorari()),
                        safe(ap.getDataInicial()),
                        String.valueOf(ap.getNumSetmanes()),
                        safe(ap.getNomCentre()),
                        safe(ap.getCiutat())
                );
                lineas.add(linea);
            } else if (a instanceof ActivitatOnline) {
                ActivitatOnline ao = (ActivitatOnline) a;
                String collectius = joinCollectius(ao.getCollectius());
                String linea = String.join(";",
                        "ONLINE",
                        safe(ao.getNom()),
                        safe(collectius),
                        safeDate(ao.getDataIniciInscripcio()),
                        safeDate(ao.getDataFiInscripcio()),
                        safe(ao.getEnllac()),
                        safeDate(ao.getDataIniciActivitat()),
                        String.valueOf(ao.getDiesVisualitzacio())
                );
                lineas.add(linea);
            } else {
                // tipo desconocido -> ignorar
            }
        }
        Files.write(fitxer, lineas, StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
    }

    /* Helpers de parsing */

    private static void parseAndAddPuntual(String[] p, LlistaActivitats llista) {
        // indices mínimos ver formato
        if (p.length < 10) throw new IllegalArgumentException("Campos insuficientes PUNTUAL");
        String nom = p[1].trim();
        String[] collectius = splitCollectius(p[2]);
        LocalDate insIni = parseDateOrNull(p[3]);
        LocalDate insFi = parseDateOrNull(p[4]);
        int limit = parseIntOrDefault(p[5], 0);
        double preu = parseDoubleOrDefault(p[6], 0.0);
        LocalDate data = parseDateOrNull(p[7]);
        LocalTime hora = parseTimeOrNull(p[8]);
        String ciutat = p[9].trim();

        ActivitatPuntual ap = new ActivitatPuntual(nom, data, hora, ciutat, limit, preu);
        ap.setDataFiInscripcio(insFi);
        try {
            llista.afegirActivitat(ap);
        } catch (RuntimeException e) {
            // duplicado u otro -> ignorar
        }
    }

    private static void parseAndAddPeriodica(String[] p, LlistaActivitats llista) {
        if (p.length < 13) throw new IllegalArgumentException("Campos insuficientes PERIODICA");
        String nom = p[1].trim();
        String[] collectius = splitCollectius(p[2]);
        LocalDate insIni = parseDateOrNull(p[3]);
        LocalDate insFi = parseDateOrNull(p[4]);
        int limit = parseIntOrDefault(p[5], 0);
        double preu = parseDoubleOrDefault(p[6], 0.0);
        String diaSetmana = p[7].trim();
        String horari = p[8].trim();
        String dataInicial = p[9].trim();
        int numSetmanes = parseIntOrDefault(p[10], 1);
        String nomCentre = p[11].trim();
        String ciutat = p[12].trim();

        ActivitatPeriodica ap = new ActivitatPeriodica(nom, diaSetmana, horari, dataInicial, numSetmanes, limit, preu, nomCentre, ciutat);
        ap.setCollectius(collectius);
        ap.setDataIniciInscripcio(insIni);
        ap.setDataFiInscripcio(insFi);
        try {
            llista.afegirActivitat(ap);
        } catch (RuntimeException e) {
            // ignorar duplicados
        }
    }

    private static void parseAndAddOnline(String[] p, LlistaActivitats llista) {
        if (p.length < 8) throw new IllegalArgumentException("Campos insuficientes ONLINE");
        String nom = p[1].trim();
        String[] collectius = splitCollectius(p[2]);
        LocalDate insIni = parseDateOrNull(p[3]);
        LocalDate insFi = parseDateOrNull(p[4]);
        String enllac = p[5].trim();
        LocalDate dataIniciActivitat = parseDateOrNull(p[6]);
        int dies = parseIntOrDefault(p[7], 0);

        ActivitatOnline ao = new ActivitatOnline(nom, collectius, insIni, insFi, enllac, dataIniciActivitat, dies);
        try {
            llista.afegirActivitat(ao);
        } catch (RuntimeException e) {
            // ignorar duplicados
        }
    }

    /* utilitarios */

    private static String[] splitCollectius(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        String[] parts = s.split(",");
        for (int i = 0; i < parts.length; i++) parts[i] = parts[i].trim();
        return parts;
    }

    private static String joinCollectius(String[] arr) {
        if (arr == null || arr.length == 0) return "";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length; i++) {
            if (i > 0) sb.append(",");
            sb.append(arr[i]);
        }
        return sb.toString();
    }

    private static LocalDate parseDateOrNull(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        return LocalDate.parse(s);
    }

    private static LocalTime parseTimeOrNull(String s) {
        if (s == null) return null;
        s = s.trim();
        if (s.isEmpty()) return null;
        return LocalTime.parse(s);
    }

    private static int parseIntOrDefault(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (Exception e) { return def; }
    }

    private static double parseDoubleOrDefault(String s, double def) {
        try { return Double.parseDouble(s.trim()); } catch (Exception e) { return def; }
    }

    private static String safe(String s) { return s == null ? "" : s; }
    private static String safeDate(LocalDate d) { return d == null ? "" : d.toString(); }
    private static String safeTime(LocalTime t) { return t == null ? "" : t.toString(); }
}
