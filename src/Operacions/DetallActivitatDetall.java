package Operacions;

import Dades.ActivitatDetall;
import Llistes.LlistaActivitats;

public class DetallActivitatDetall {
    public static ActivitatDetall obtenir(LlistaActivitats llista, String nom) {
        if (llista == null || nom == null) return null;
        return llista.detallPerNomDetall(nom);
    }
}