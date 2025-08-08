


public class SudokuJuego extends Sudokuresolver {

    public SudokuJuego(int[][] tablero) {
        super(tablero); // se hereda desde la clase abstracta
    }

    @Override
    public boolean resolver() {
        int[] vacia = buscarCasillaVacia();
        if (vacia == null) return true; // ya está resuelto

        int fila = vacia[0];
        int col = vacia[1];

        for (int num = 1; num <= 9; num++) {
            if (esValido(fila, col, num)) {
                tablero[fila][col] = num;

                if (resolver()) return true;

                tablero[fila][col] = 0; // backtrack
            }
        }

        return false; // no hay solución válida con este camino
    }

    @Override
    protected boolean esValido(int fila, int col, int num) {
        // Verificar fila
        for (int i = 0; i < 9; i++) {
            if (tablero[fila][i] == num) return false;
        }

        // Verificar columna
        for (int i = 0; i < 9; i++) {
            if (tablero[i][col] == num) return false;
        }

        // Verificar caja 3x3
        int inicioFila = (fila / 3) * 3;
        int inicioCol = (col / 3) * 3;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[inicioFila + i][inicioCol + j] == num) return false;
            }
        }

        return true; // Si pasó todas las pruebas, el número es válido
    }

    @Override
    protected int[] buscarCasillaVacia() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                if (tablero[fila][col] == 0) {
                    return new int[]{fila, col};
                }
            }
        }
        return null; // No hay celdas vacías
    }

    public void imprimirTablero() {
        for (int fila = 0; fila < 9; fila++) {
            if (fila % 3 == 0 && fila != 0) {
                System.out.println("-----------+-----------+-----------");
            }

            for (int col = 0; col < 9; col++) {
                if (col % 3 == 0 && col != 0) {
                    System.out.print(" | ");
                }

                int valor = tablero[fila][col];
                if (valor == 0) {
                    System.out.print(" . ");
                } else {
                    System.out.print(" " + valor + " ");
                }
            }
            System.out.println();
        }
    }
}


