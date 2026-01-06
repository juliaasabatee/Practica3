package Operacions;

import Dades.ActivitatDetall;
import Llistes.LlistaActivitats;
import Llistes.LlistaInscripcions;

public class ActivitatsAmbPlacesDisponiblesDetall {
    public static ActivitatDetall[] obtenir(LlistaActivitats llista, LlistaInscripcions ins) {
        if (llista == null) return new ActivitatDetall[0];
        return llista.activitatsAmbPlacesDisponiblesDetall(ins);
    }
}