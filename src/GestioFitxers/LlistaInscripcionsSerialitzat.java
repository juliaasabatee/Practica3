package GestioFitxers;

import Llistes.LlistaInscripcions;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class LlistaInscripcionsSerialitzat {

    private static final String FILE_PATH = "inscripcions.ser";

    public static LlistaInscripcions carregar() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(FILE_PATH))) {
            Object obj = ois.readObject();
            return (LlistaInscripcions) obj;
        } catch (IOException e) {
            return new LlistaInscripcions();
        } catch (ClassNotFoundException | ClassCastException e) {
            System.out.println("Error carregant inscripcions: " + e.getMessage());
            return new LlistaInscripcions();
        }
    }

    public static void guardar(LlistaInscripcions llista) {
        if (llista == null) {
            throw new IllegalArgumentException("La llista no pot ser null");
        }
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
            oos.writeObject(llista);
        } catch (IOException e) {
            System.out.println("Error guardant inscripcions: " + e.getMessage());
        }
    }
}
