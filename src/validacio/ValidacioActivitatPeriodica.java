package validacio;

import dades.ActivitatPeriodica;

public class ValidacioActivitatPeriodica {

    public static void main(String[] args) {

        ActivitatPeriodica ap = new ActivitatPeriodica(
                "Ioga",
                "Dilluns",
                "18:00",
                "01/10/2025",
                8,
                20,
                35.0,
                "Centre Esportiu",
                "Tarragona"
        );

        System.out.println(ap.toString());
        System.out.println("Nom: " + ap.getNom());
        System.out.println("Dia setmana: " + ap.getDiaSetmana());
        System.out.println("Horari: " + ap.getHorari());
        System.out.println("Data inicial: " + ap.getDataInicial());
        System.out.println("Nombre setmanes: " + ap.getNumSetmanes());
        System.out.println("Límit places: " + ap.getLimitPlaces());
        System.out.println("Preu: " + ap.getPreu());
        System.out.println("Centre: " + ap.getNomCentre());
        System.out.println("Ciutat: " + ap.getCiutat());
    }
}
/* @author Júlia Sabaté */
