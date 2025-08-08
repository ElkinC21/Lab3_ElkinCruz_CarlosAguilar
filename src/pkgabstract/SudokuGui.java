package pkgabstract;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class SudokuGui extends JFrame {
    private JTextField[][] campos = new JTextField[9][9];
    private TableroSudoku tablero;

    public SudokuGui() {
        setTitle("Sudoku - Jugar Aleatorio");
        setSize(500, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        tablero = new TableroSudoku();
        JPanel tableroPanel = new JPanel(new GridLayout(9, 9));

        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                JTextField campo = new JTextField();
                campo.setHorizontalAlignment(JTextField.CENTER);

                // Aplicar bordes gruesos para marcar regiones 3x3
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
                    public void keyReleased(KeyEvent e) {
                        String texto = campo.getText();
                        if (!texto.matches("[1-9]?")) {
                            campo.setText("");
                            return;
                        }

                        if (texto.isEmpty()) {
                            tablero.setValor(f, c, 0);
                            return;
                        }

                        int num = Integer.parseInt(texto);
                        if (tablero.esMovimientoValido(f, c, num)) {
                            tablero.setValor(f, c, num);
                            campo.setForeground(Color.BLACK);
                        } else {
                            campo.setForeground(Color.RED);
                        }
                    }
                });

                campos[fila][col] = campo;
                tableroPanel.add(campo);
            }
        }

        JButton limpiarBtn = new JButton("Limpiar");
        limpiarBtn.addActionListener(e -> {
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    if (campos[i][j].isEditable()) {
                        campos[i][j].setText("");
                        campos[i][j].setForeground(Color.BLACK);
                        tablero.setValor(i, j, 0);
                    }
                }
            }
        });

        add(tableroPanel, BorderLayout.CENTER);
        add(limpiarBtn, BorderLayout.SOUTH);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new SudokuGui().setVisible(true));
    }
}
