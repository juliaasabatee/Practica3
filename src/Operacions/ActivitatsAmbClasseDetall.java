package Operacions;

import Dades.ActivitatDetall;
import Llistes.LlistaActivitats;
import java.time.LocalDate;

public class ActivitatsAmbClasseDetall {
    public static ActivitatDetall[] obtenir(LlistaActivitats llista, LocalDate data) {
        if (llista == null || data == null) return new ActivitatDetall[0];
        return llista.activitatsAmbClasseEnDataDetall(data);
    }
}