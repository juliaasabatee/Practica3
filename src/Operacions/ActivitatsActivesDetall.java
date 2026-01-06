package Operacions;

import Dades.ActivitatDetall;
import Llistes.LlistaActivitats;
import java.time.LocalDate;

public class ActivitatsActivesDetall {
    public static ActivitatDetall[] obtenir(LlistaActivitats llista, LocalDate data) {
        if (llista == null || data == null) return new ActivitatDetall[0];
        return llista.activitatsActivesEnDataDetall(data);
    }
}