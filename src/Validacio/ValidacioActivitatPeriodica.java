package Validacio;

import Dades.ActivitatPeriodica;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

public class ValidacioActivitatPeriodica {

    public static void main(String[] args) {

        ActivitatPeriodica ap = new ActivitatPeriodica(
                "Ioga",
                new String[]{"Estudiant"},
                LocalDate.of(2025, 9, 1),
                LocalDate.of(2025, 9, 30),
                20,
                "dilluns",
                "18:00-19:30",
                "2025-10-01",
                8,
                7,
                35.0,
                "Centre Esportiu",
                "Tarragona",
                Set.of(DayOfWeek.MONDAY)

        );

        System.out.println(ap);
    }
}
