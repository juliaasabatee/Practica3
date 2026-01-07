
package InterficieGrafica;

import javax.swing.*;
import java.awt.*;

import Llistes.LlistaUsuaris;
import Dades.Usuari;

public class AppInterficieGrafica extends JFrame {

    private LlistaUsuaris llistaUsuaris;

    private JTextField campAlias;
    private JTextArea areaResultats;

    public AppInterficieGrafica(LlistaUsuaris llistaUsuaris) {
        this.llistaUsuaris = llistaUsuaris;

        configurarFinestra();
        crearComponents();
        afegirAccions();
    }

    private void configurarFinestra() {
        setTitle("Gestió d'Usuaris");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());
    }

    private void crearComponents() {

        JPanel panelSuperior = new JPanel();
        panelSuperior.add(new JLabel("Àlies usuari:"));

        campAlias = new JTextField(12);
        panelSuperior.add(campAlias);

        JButton botoCerca = new JButton("Cercar usuari");
        panelSuperior.add(botoCerca);

        add(panelSuperior, BorderLayout.NORTH);

        areaResultats = new JTextArea();
        areaResultats.setEditable(false);
        areaResultats.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scroll = new JScrollPane(areaResultats);
        add(scroll, BorderLayout.CENTER);

        JPanel panelInferior = new JPanel();

        JButton botoLlistar = new JButton("Mostrar tots els usuaris");
        JButton botoMesActiu = new JButton("Usuari més actiu");

        panelInferior.add(botoLlistar);
        panelInferior.add(botoMesActiu);

        add(panelInferior, BorderLayout.SOUTH);

        botoCerca.addActionListener(e -> cercarUsuari());
        botoLlistar.addActionListener(e -> mostrarTots());
        botoMesActiu.addActionListener(e -> mostrarUsuariMesActiu());
    }

    private void afegirAccions() {
    }

    private void cercarUsuari() {
        String alias = campAlias.getText().trim();

        if (alias.isEmpty()) {
            areaResultats.setText("Introdueix un àlies per fer la cerca.");
            return;
        }

        Usuari u = llistaUsuaris.cercarUsuari(alias);

        if (u != null) {
            areaResultats.setText(u.toString());
        } else {
            areaResultats.setText("No existeix cap usuari amb aquest àlies.");
        }
    }

    private void mostrarTots() {
        areaResultats.setText("");

        if (llistaUsuaris.getNumUsuaris() == 0) {
            areaResultats.setText("No hi ha usuaris registrats.");
            return;
        }

        for (int i = 0; i < llistaUsuaris.getNumUsuaris(); i++) {
            areaResultats.append(llistaUsuaris.getUsuari(i).toString());
            areaResultats.append("\n------------------------\n");
        }
    }

    private void mostrarUsuariMesActiu() {
        Usuari u = llistaUsuaris.usuariMesActiu();

        if (u != null) {
            areaResultats.setText("Usuari més actiu:\n\n" + u.toString());
        } else {
            areaResultats.setText("No es pot determinar l'usuari més actiu.");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LlistaUsuaris llista = new LlistaUsuaris();
            new AppInterficieGrafica(llista).setVisible(true);
        });
    }
}
