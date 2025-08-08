package pkgabstract;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuGui extends JFrame {
    private JTextField[][] campos = new JTextField[9][9];
    private TableroSudoku tablero;
    private int fallos = 0;
    private static final int MAX_FALLOS = 3;
    private JLabel lblErrores;
    private TableroSudoku.Dificultad dificultad;

    public SudokuGui() {
        setTitle("Sudoku");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        dificultad = pedirDificultad();
        if (dificultad == null) {
            dificultad = TableroSudoku.Dificultad.MEDIO;
        }

        tablero = new TableroSudoku(dificultad);

        lblErrores = new JLabel("", SwingConstants.CENTER);
        lblErrores.setFont(new Font("SansSerif", Font.PLAIN, 14));
        lblErrores.setForeground(Color.DARK_GRAY);
        add(lblErrores, BorderLayout.NORTH);

        JPanel tableroPanel = new JPanel(new GridLayout(9, 9));

        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                JTextField campo = new JTextField();
                campo.setHorizontalAlignment(JTextField.CENTER);

                int top = (fila % 3 == 0) ? 2 : 1;
                int left = (col % 3 == 0) ? 2 : 1;
                int bottom = (fila == 8) ? 2 : 1;
                int right = (col == 8) ? 2 : 1;
                campo.setBorder(BorderFactory.createMatteBorder(top, left, bottom, right, Color.BLACK));

                int valor = tablero.getValor(fila, col);
                if (valor != 0) {
                    campo.setText(String.valueOf(valor));
                    campo.setEditable(false);
                    campo.setBackground(Color.LIGHT_GRAY);
                }

                final int f = fila, c = col;
                campo.addKeyListener(new KeyAdapter() {
                    @Override
                    public void keyReleased(KeyEvent e) {
                        if (!campo.isEditable()) {
                            e.consume();
                            return;
                        }
                        String texto = campo.getText().trim();
                        if (!texto.matches("[1-9]?")) {
                            campo.setText("");
                            campo.setForeground(Color.BLACK);
                            return;
                        }
                        if (texto.isEmpty()) {
                            tablero.setValor(f, c, 0);
                            campo.setForeground(Color.BLACK);
                            return;
                        }
                        int num = Integer.parseInt(texto);
                        if (tablero.esMovimientoValido(f, c, num)) {
                            tablero.setValor(f, c, num);
                            campo.setForeground(Color.BLACK);
                            campo.setEditable(false);
                            campo.setBackground(Color.WHITE);
                        } else {
                            campo.setForeground(Color.RED);
                            registrarFallo();
                        }
                    }
                });

                campos[fila][col] = campo;
                tableroPanel.add(campo);
            }
        }

        JButton reiniciarBtn = new JButton("Reiniciar");
        reiniciarBtn.setToolTipText("Reinicia la partida con un nuevo tablero");
        reiniciarBtn.addActionListener(e -> reiniciarPartida());

        add(tableroPanel, BorderLayout.CENTER);
        add(reiniciarBtn, BorderLayout.SOUTH);

        actualizarIndicadores();
    }

    private TableroSudoku.Dificultad pedirDificultad() {
        String[] opciones = {"Fácil", "Medio", "Difícil"};
        int sel = JOptionPane.showOptionDialog(
                this,
                "Elige la dificultad:",
                "Dificultad",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[1]
        );
        if (sel == 0) return TableroSudoku.Dificultad.FACIL;
        if (sel == 1) return TableroSudoku.Dificultad.MEDIO;
        if (sel == 2) return TableroSudoku.Dificultad.DIFICIL;
        return null;
    }

    private void registrarFallo() {
        fallos++;
        actualizarIndicadores();
        if (fallos >= MAX_FALLOS) {
            JOptionPane.showMessageDialog(this,
                    "Has alcanzado " + MAX_FALLOS + " intentos fallidos.\nLa partida se reiniciará.",
                    "Fin de partida", JOptionPane.WARNING_MESSAGE);
            reiniciarPartida();
        }
    }

    private void actualizarIndicadores() {
        setTitle("Sudoku - " + dificultad + "  |  Fallos: " + fallos + "/" + MAX_FALLOS);
        if (lblErrores != null) {
            lblErrores.setText("Dificultad: " + textoDificultad(dificultad)
                    + "   |   Errores: " + fallos + " / " + MAX_FALLOS);
        }
    }

    private String textoDificultad(TableroSudoku.Dificultad d) {
        switch (d) {
            case FACIL: return "Fácil";
            case MEDIO: return "Medio";
            case DIFICIL: return "Difícil";
            default: return d.toString();
        }
    }

    private void reiniciarPartida() {
        fallos = 0;
        tablero = new TableroSudoku(dificultad);
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                int v = tablero.getValor(fila, col);
                JTextField campo = campos[fila][col];
                if (v != 0) {
                    campo.setText(String.valueOf(v));
                    campo.setEditable(false);
                    campo.setForeground(Color.BLACK);
                    campo.setBackground(Color.LIGHT_GRAY);
                } else {
                    campo.setText("");
                    campo.setEditable(true);
                    campo.setForeground(Color.BLACK);
                    campo.setBackground(Color.WHITE);
                }
            }
        }
        actualizarIndicadores();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SudokuGui().setVisible(true));
    }
}
