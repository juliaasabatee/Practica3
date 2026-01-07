package GestioFitxers;

import Dades.Estudiant;
import Dades.Usuari;
import Excepcions.UsuariDuplicatException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

    public class LlistaUsuarisText {

        private static final String FILE_PATH = "usuaris.txt";
        private Usuari[] llista;
        private int nElem;
        private static final int CAPACITAT_INICIAL = 100;

        public LlistaUsuarisText() {
            llista = new Usuari[CAPACITAT_INICIAL];
            nElem = 0;
        }

        public void afegirUsuari(Usuari u) throws UsuariDuplicatException {
            if (existeixUsuari(u.getAlias())) {
                throw new UsuariDuplicatException("Usuari duplicat: " + u.getAlias());
            }
            llista[nElem] = u;
            nElem++;
        }

        private boolean existeixUsuari(String alias) {
            for (int i = 0; i < nElem; i++) {
                if (llista[i].getAlias().equalsIgnoreCase(alias)) {
                    return true;
                }
            }
            return false;
        }

        public Usuari getUsuari(int pos) {
            if (pos >= 0 && pos < nElem) {
                return llista[pos];
            }
            return null;
        }

        public int getNElem() {
            return nElem;
        }

        public void carregarUsuaris() {
            BufferedReader br = null;
            try {
                br = new BufferedReader(new FileReader(FILE_PATH));
                String linia;

                while ((linia = br.readLine()) != null) {
                    String[] camps = linia.split(";");

                    if (camps[0].equals("ESTUDIANT")) {
                        String alias = camps[1];
                        String correu = camps[2];
                        String ensenyament = camps[3];
                        int anyInici = Integer.parseInt(camps[4]);

                        Usuari e = new Estudiant(alias, correu);
                        afegirUsuari(e);
                    }
                }

            } catch (IOException | NumberFormatException | UsuariDuplicatException e) {
                System.out.println("Error carregant usuaris: " + e.getMessage());
            } finally {
                if (br != null) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        System.out.println("Error tancant fitxer");
                    }
                }
            }
        }

        public void guardarUsuaris() {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                for (int i = 0; i < nElem; i++) {
                    if (llista[i] instanceof Estudiant) {
                        Estudiant e = (Estudiant) llista[i];
                        bw.write("ESTUDIANT;" +
                                e.getAlias() + ";" +
                                e.getCorreuInici() + ";" +
                                e.getEnsenyament() + ";" +
                                e.getAnyInici());
                        bw.newLine();
                    }
                }
            } catch (IOException e) {
                System.out.println("Error guardant usuaris: " + e.getMessage());
            }
        }
    }
/* @author Júlia Sabaté */


