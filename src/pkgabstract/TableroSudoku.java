package pkgabstract;

import java.util.Collections;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class TableroSudoku extends SudokuBase {

    public enum Dificultad { FACIL, MEDIO, DIFICIL }

    private Dificultad dificultad = Dificultad.MEDIO;

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

    
    private void generarTableroAleatorio(Dificultad dif) {
        
        resolverSudoku(0, 0);

       
        Random rand = new Random();
        int celdasPorBorrar;

        switch (dif) {
            case FACIL:
                
                celdasPorBorrar = 30 + rand.nextInt(6); 
                break;
            case DIFICIL:
                
                celdasPorBorrar = 50 + rand.nextInt(6); 
                break;
            case MEDIO:
            default:
                celdasPorBorrar = 40 + rand.nextInt(6); 
                break;
        }

        while (celdasPorBorrar > 0) {
            int fila = rand.nextInt(9);
            int col = rand.nextInt(9);
            if (!tablero[fila][col].estaVacia()) {
                tablero[fila][col].setValor(0);
                celdasPorBorrar--;
            }
        }
    }
}
