package pkgabstract;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TableroSudoku extends SudokuBase {

    public enum Dificultad { FACIL, MEDIO, DIFICIL }

    private Dificultad dificultad = Dificultad.MEDIO;

    
    private int[][] solucion = new int[9][9];

    public TableroSudoku() {
        super();
        generarTableroAleatorio(dificultad);
    }

    public TableroSudoku(Dificultad dificultad) {
        super();
        this.dificultad = dificultad;
        generarTableroAleatorio(dificultad);
    }

    @Override
    public boolean esMovimientoValido(int fila, int col, int numero) {
        for (int i = 0; i < 9; i++) {
            if (i != col && tablero[fila][i].getValor() == numero) return false;
            if (i != fila && tablero[i][col].getValor() == numero) return false;
        }
        int inicioFila = (fila / 3) * 3;
        int inicioCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int f = inicioFila + i;
                int c = inicioCol + j;
                if ((f != fila || c != col) && tablero[f][c].getValor() == numero) return false;
            }
        }
        return true;
    }

    
    private boolean resolverSudoku(int fila, int col) {
        if (fila == 9) return true;
        int siguienteFila = (col == 8) ? fila + 1 : fila;
        int siguienteCol = (col + 1) % 9;

        if (!tablero[fila][col].estaVacia()) {
            return resolverSudoku(siguienteFila, siguienteCol);
        }

        List<Integer> numeros = new ArrayList<>();
        for (int i = 1; i <= 9; i++) numeros.add(i);
        Collections.shuffle(numeros);

        for (int num : numeros) {
            if (esMovimientoValido(fila, col, num)) {
                tablero[fila][col].setValor(num);
                if (resolverSudoku(siguienteFila, siguienteCol)) return true;
                tablero[fila][col].setValor(0);
            }
        }
        return false;
    }

    
    private void generarTableroAleatorio(Dificultad dificultad) {
        
        resolverSudoku(0, 0);

    
        for (int f = 0; f < 9; f++)
            for (int c = 0; c < 9; c++)
                solucion[f][c] = tablero[f][c].getValor();

        
        Random rand = new Random();
        int celdasPorBorrar;
        switch (dificultad) {
            case FACIL:   celdasPorBorrar = 30 + rand.nextInt(6); break; 
            case DIFICIL: celdasPorBorrar = 50 + rand.nextInt(6); break; 
            case MEDIO:
            default:      celdasPorBorrar = 40 + rand.nextInt(6); break; 
        }

       
        List<int[]> posiciones = new ArrayList<>();
        for (int f = 0; f < 9; f++)
            for (int c = 0; c < 9; c++)
                posiciones.add(new int[]{f, c});
        Collections.shuffle(posiciones, rand);

        int borrados = 0;
        for (int[] pos : posiciones) {
            if (borrados >= celdasPorBorrar) break;
            int f = pos[0], c = pos[1];
            if (tablero[f][c].estaVacia()) continue;

            int backup = tablero[f][c].getValor();
            tablero[f][c].setValor(0);

            
            SudokuBase chequeo = new SudokuBase() {
                @Override
                public boolean esMovimientoValido(int fila, int col, int n) {
                    return TableroSudoku.this.esMovimientoValido(fila, col, n);
                }
            };
            for (int i = 0; i < 9; i++)
                for (int j = 0; j < 9; j++)
                    chequeo.setValor(i, j, tablero[i][j].getValor());

            if (chequeo.tieneUnicaSolucion()) {
                borrados++; 
            } else {
                tablero[f][c].setValor(backup); 
            }
        }
        
    }

    
    public int getSolucionValor(int fila, int col) {
        return solucion[fila][col];
    }
}
