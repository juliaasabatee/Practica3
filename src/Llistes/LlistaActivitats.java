package Llistes;

import Dades.*;
import Excepcions.ActivitatDuplicadaException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.Arrays;

public class LlistaActivitats {
    private Activitat[] llista;
    private int nElem;

    public LlistaActivitats() {
        this(50);
    }

    public LlistaActivitats(int tamanyMaxim) {
        this.llista = new Activitat[tamanyMaxim];
        this.nElem = 0;
    }

    /**
     * Inserta la actividad manteniendo orden por nombre.
     * Lanza ActivitatDuplicadaException si ya existe una con el mismo nombre.
     */
    public void afegirActivitat(Activitat a) throws ActivitatDuplicadaException {
        if (a == null) return;
        if (nElem >= llista.length) {
            throw new IllegalStateException("Capacitat màxima assolida");
        }
        // Comprobar duplicado por nombre
        if (cercarPerNom(a.getNom()) != null) {
            throw new ActivitatDuplicadaException(a.getNom());
        }
        // encontrar posición de inserción (orden lexicográfico por nombre)
        int pos = 0;
        while (pos < nElem && llista[pos].getNom().compareToIgnoreCase(a.getNom()) < 0) {
            pos++;
        }
        // desplazar a la derecha
        for (int i = nElem; i > pos; i--) {
            llista[i] = llista[i - 1];
        }
        llista[pos] = a;
        nElem++;
    }

    public Activitat getActivitat(int index) {
        if (index >= 0 && index < nElem) {
            return llista[index];
        }
        return null;
    }

    public int getNElem() {
        return nElem;
    }

    public int getNumActivitats() {
        return nElem;
    }

    public Activitat cercarPerNom(String nom) {
        if (nom == null) return null;
        for (int i = 0; i < nElem; i++) {
            if (nom.equalsIgnoreCase(llista[i].getNom())) {
                return llista[i];
            }
        }
        return null;
    }

    public boolean eliminarPerNom(String nom) {
        if (nom == null) return false;
        for (int i = 0; i < nElem; i++) {
            if (nom.equalsIgnoreCase(llista[i].getNom())) {
                // desplazar a la izquierda
                for (int j = i; j < nElem - 1; j++) {
                    llista[j] = llista[j + 1];
                }
                llista[nElem - 1] = null;
                nElem--;
                return true;
            }
        }
        return false;
    }

    public Activitat[] getAllActivitats() {
        return Arrays.copyOf(llista, nElem);
    }

    /**
     * Devuelve actividades cuyo periodo de inscripción contiene la fecha indicada.
     */
    public Activitat[] activitatsEnPeriodInscripcio(LocalDate data) {
        if (data == null) return new Activitat[0];
        Activitat[] temp = new Activitat[nElem];
        int c = 0;
        for (int i = 0; i < nElem; i++) {
            Activitat a = llista[i];
            if (a.getDataIniciInscripcio() != null && a.getDataFiInscripcio() != null) {
                if ((!data.isBefore(a.getDataIniciInscripcio())) && (!data.isAfter(a.getDataFiInscripcio()))) {
                    temp[c++] = a;
                }
            }
        }
        return Arrays.copyOf(temp, c);
    }

    /**
     * Devuelve actividades que tienen clase en la fecha dada.
     * - ActivitatPuntual: si data == data de la activitat
     * - ActivitatPeriodica: si la fecha coincide con un día de clase dentro de su periodo
     * - ActivitatOnline: no tiene "classe" en un día concreto (se ignora)
     */
    public Activitat[] activitatsAmbClasseEnData(LocalDate data) {
        if (data == null) return new Activitat[0];
        Activitat[] temp = new Activitat[nElem];
        int c = 0;
        for (int i = 0; i < nElem; i++) {
            Activitat a = llista[i];
            if (a instanceof ActivitatPuntual) {
                ActivitatPuntual ap = (ActivitatPuntual) a;
                if (ap.getData() != null && ap.getData().isEqual(data)) {
                    temp[c++] = a;
                }
            } else if (a instanceof ActivitatPeriodica) {
                ActivitatPeriodica ap = (ActivitatPeriodica) a;
                String dataInicialStr = ap.getDataInicial();
                try {
                    LocalDate dataInicial = LocalDate.parse(dataInicialStr);
                    LocalDate ultima = dataInicial.plusWeeks(ap.getNumSetmanes() - 1);
                    if ((!data.isBefore(dataInicial)) && (!data.isAfter(ultima))) {
                        DayOfWeek diaEsperat = diaSetmanaToDayOfWeek(ap.getDiaSetmana());
                        if (diaEsperat != null && data.getDayOfWeek() == diaEsperat) {
                            temp[c++] = a;
                        }
                    }
                } catch (DateTimeParseException e) {
                    // formato desconocido; se ignora esa actividad para este método
                }
            }
        }
        return Arrays.copyOf(temp, c);
    }

    /**
     * Devuelve lista detallada (nom, duracio hores, responsable) de actividades que tienen clase en la fecha dada.
     */
    public ActivitatDetall[] activitatsAmbClasseEnDataDetall(LocalDate data) {
        if (data == null) return new ActivitatDetall[0];
        ActivitatDetall[] temp = new ActivitatDetall[nElem];
        int c = 0;
        for (int i = 0; i < nElem; i++) {
            Activitat a = llista[i];
            if (a instanceof ActivitatPuntual) {
                ActivitatPuntual ap = (ActivitatPuntual) a;
                if (ap.getData() != null && ap.getData().isEqual(data)) {
                    double dur = ap.getDuracioHores();
                    String resp = a.getResponsable() == null ? "" : a.getResponsable();
                    temp[c++] = new ActivitatDetall(a.getNom(), dur, resp);
                }
            } else if (a instanceof ActivitatPeriodica) {
                ActivitatPeriodica ap = (ActivitatPeriodica) a;
                String dataInicialStr = ap.getDataInicial();
                try {
                    LocalDate dataInicial = LocalDate.parse(dataInicialStr);
                    LocalDate ultima = dataInicial.plusWeeks(ap.getNumSetmanes() - 1);
                    if ((!data.isBefore(dataInicial)) && (!data.isAfter(ultima))) {
                        DayOfWeek diaEsperat = diaSetmanaToDayOfWeek(ap.getDiaSetmana());
                        if (diaEsperat != null && data.getDayOfWeek() == diaEsperat) {
                            double dur = parseDuracioFromHorari(ap.getHorari());
                            String resp = a.getResponsable() == null ? "" : a.getResponsable();
                            temp[c++] = new ActivitatDetall(a.getNom(), dur, resp);
                        }
                    }
                } catch (DateTimeParseException e) {
                    // formato desconocido; se ignora esa actividad para este método
                }
            }
        }
        return Arrays.copyOf(temp, c);
    }

    private double parseDuracioFromHorari(String horari) {
        if (horari == null) return 1.0;
        try {
            String[] parts = horari.split("-");
            if (parts.length != 2) return 1.0;
            LocalTime start = LocalTime.parse(parts[0].trim());
            LocalTime end = LocalTime.parse(parts[1].trim());
            long minutes = java.time.Duration.between(start, end).toMinutes();
            return minutes / 60.0;
        } catch (Exception e) {
            return 1.0;
        }
    }

    /**
     * Devuelve actividades activas en una fecha (la actividad está vigente):
     * - Puntual: si data == data
     * - Periòdica: si data está entre dataInicial y última clase
     * - Online: si data entre dataIniciActivitat y dataIniciActivitat + diesVisualitzacio - 1
     */
    public Activitat[] activitatsActivesEnData(LocalDate data) {
        if (data == null) return new Activitat[0];
        Activitat[] temp = new Activitat[nElem];
        int c = 0;
        for (int i = 0; i < nElem; i++) {
            Activitat a = llista[i];
            if (a instanceof ActivitatPuntual) {
                ActivitatPuntual ap = (ActivitatPuntual) a;
                if (ap.getData() != null && ap.getData().isEqual(data)) {
                    temp[c++] = a;
                }
            } else if (a instanceof ActivitatPeriodica) {
                ActivitatPeriodica ap = (ActivitatPeriodica) a;
                try {
                    LocalDate dataInicial = LocalDate.parse(ap.getDataInicial());
                    LocalDate ultima = dataInicial.plusWeeks(ap.getNumSetmanes() - 1);
                    if ((!data.isBefore(dataInicial)) && (!data.isAfter(ultima))) {
                        temp[c++] = a;
                    }
                } catch (DateTimeParseException e) {
                    // ignorar
                }
            } else if (a instanceof ActivitatOnline) {
                ActivitatOnline ao = (ActivitatOnline) a;
                if (ao.getDataIniciActivitat() != null && ao.getDiesVisualitzacio() > 0) {
                    LocalDate fi = ao.getDataIniciActivitat().plusDays(ao.getDiesVisualitzacio() - 1);
                    if ((!data.isBefore(ao.getDataIniciActivitat())) && (!data.isAfter(fi))) {
                        temp[c++] = a;
                    }
                }
            }
        }
        return Arrays.copyOf(temp, c);
    }

    /**
     * Devuelve lista detallada (nom, duracio hores, responsable) de actividades que están activas en la fecha dada.
     */
    public ActivitatDetall[] activitatsActivesEnDataDetall(LocalDate data) {
        if (data == null) return new ActivitatDetall[0];
        ActivitatDetall[] temp = new ActivitatDetall[nElem];
        int c = 0;
        for (int i = 0; i < nElem; i++) {
            Activitat a = llista[i];
            if (a instanceof ActivitatPuntual) {
                ActivitatPuntual ap = (ActivitatPuntual) a;
                if (ap.getData() != null && ap.getData().isEqual(data)) {
                    double dur = ap.getDuracioHores();
                    String resp = a.getResponsable() == null ? "" : a.getResponsable();
                    temp[c++] = new ActivitatDetall(a.getNom(), dur, resp);
                }
            } else if (a instanceof ActivitatPeriodica) {
                ActivitatPeriodica ap = (ActivitatPeriodica) a;
                try {
                    LocalDate dataInicial = LocalDate.parse(ap.getDataInicial());
                    LocalDate ultima = dataInicial.plusWeeks(ap.getNumSetmanes() - 1);
                    if ((!data.isBefore(dataInicial)) && (!data.isAfter(ultima))) {
                        double dur = parseDuracioFromHorari(ap.getHorari());
                        String resp = a.getResponsable() == null ? "" : a.getResponsable();
                        temp[c++] = new ActivitatDetall(a.getNom(), dur, resp);
                    }
                } catch (DateTimeParseException e) {
                    // ignorar
                }
            } else if (a instanceof ActivitatOnline) {
                ActivitatOnline ao = (ActivitatOnline) a;
                if (ao.getDataIniciActivitat() != null && ao.getDiesVisualitzacio() > 0) {
                    LocalDate fi = ao.getDataIniciActivitat().plusDays(ao.getDiesVisualitzacio() - 1);
                    if ((!data.isBefore(ao.getDataIniciActivitat())) && (!data.isAfter(fi))) {
                        double dur = ao.getDiesVisualitzacio() * 24.0; // duración en horas (dias * 24)
                        String resp = a.getResponsable() == null ? "" : a.getResponsable();
                        temp[c++] = new ActivitatDetall(a.getNom(), dur, resp);
                    }
                }
            }
        }
        return Arrays.copyOf(temp, c);
    }

    /**
     * Devuelve actividades con plazas disponibles.
     * Para contar inscritos usa la lista de inscripciones pasada como argumento.
     * - Online: siempre tiene plazas disponibles (no límite).
     * - Puntual / Periodica: compara limitPlaces con número de inscritos (no en lista de espera).
     */
    public Activitat[] activitatsAmbPlacesDisponibles(LlistaInscripcions llistaInscripcions) {
        Activitat[] temp = new Activitat[nElem];
        int c = 0;
        for (int i = 0; i < nElem; i++) {
            Activitat a = llista[i];
            if (a instanceof ActivitatOnline) {
                temp[c++] = a; // ilimitada
                continue;
            }
            int limit = -1;
            if (a instanceof ActivitatPuntual) {
                limit = ((ActivitatPuntual) a).getLimitPlaces();
            } else if (a instanceof ActivitatPeriodica) {
                limit = ((ActivitatPeriodica) a).getLimitPlaces();
            }
            if (limit <= 0) continue;
            int compt = 0;
            if (llistaInscripcions != null) {
                Inscripcions[] ins = llistaInscripcions.getInscripcions();
                int nIns = llistaInscripcions.getNumInscripcions();
                for (int j = 0; j < nIns; j++) {
                    if (ins[j] != null && !ins[j].isEnLlistaEspera()) {
                        Activitat act = ins[j].getActivitat();
                        if (act != null && act.getNom().equalsIgnoreCase(a.getNom())) {
                            compt++;
                        }
                    }
                }
            }
            if (compt < limit) {
                temp[c++] = a;
            }
        }
        return Arrays.copyOf(temp, c);
    }

    /**
     * Devuelve lista detallada (nom, duracio hores, responsable) de actividades que tienen plazas disponibles.
     */
    public ActivitatDetall[] activitatsAmbPlacesDisponiblesDetall(LlistaInscripcions llistaInscripcions) {
        Activitat[] disponibles = activitatsAmbPlacesDisponibles(llistaInscripcions);
        ActivitatDetall[] temp = new ActivitatDetall[disponibles.length];
        int c = 0;
        for (Activitat a : disponibles) {
            if (a == null) continue;
            double dur = 1.0;
            if (a instanceof ActivitatPuntual) {
                dur = ((ActivitatPuntual) a).getDuracioHores();
            } else if (a instanceof ActivitatPeriodica) {
                dur = parseDuracioFromHorari(((ActivitatPeriodica) a).getHorari());
            } else if (a instanceof ActivitatOnline) {
                dur = ((ActivitatOnline) a).getDiesVisualitzacio() * 24.0;
            }
            String resp = a.getResponsable() == null ? "" : a.getResponsable();
            temp[c++] = new ActivitatDetall(a.getNom(), dur, resp);
        }
        return Arrays.copyOf(temp, c);
    }

    /**
     * Devuelve el detalle (nom, duracio, responsable) a partir del nombre de la actividad.
     */
    public ActivitatDetall detallPerNomDetall(String nom) {
        if (nom == null) return null;
        Activitat a = cercarPerNom(nom);
        if (a == null) return null;
        double dur = 1.0;
        if (a instanceof ActivitatPuntual) {
            dur = ((ActivitatPuntual) a).getDuracioHores();
        } else if (a instanceof ActivitatPeriodica) {
            dur = parseDuracioFromHorari(((ActivitatPeriodica) a).getHorari());
        } else if (a instanceof ActivitatOnline) {
            dur = ((ActivitatOnline) a).getDiesVisualitzacio() * 24.0;
        }
        String resp = a.getResponsable() == null ? "" : a.getResponsable();
        return new ActivitatDetall(a.getNom(), dur, resp);
    }

    private DayOfWeek diaSetmanaToDayOfWeek(String dia) {
        if (dia == null) return null;
        String d = dia.trim().toLowerCase();
        switch (d) {
            case "dilluns": return DayOfWeek.MONDAY;
            case "dimarts": return DayOfWeek.TUESDAY;
            case "dimecres": return DayOfWeek.WEDNESDAY;
            case "dijous": return DayOfWeek.THURSDAY;
            case "divendres": return DayOfWeek.FRIDAY;
            case "dissabte": return DayOfWeek.SATURDAY;
            case "diumenge": return DayOfWeek.SUNDAY;
            default: return null;
        }
    }
}