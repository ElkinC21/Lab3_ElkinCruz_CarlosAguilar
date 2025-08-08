package pkgabstract;


public class SudokuJuego extends Sudokuresolver{

    public SudokuJuego(int[][] tablero) {
        super(tablero);
    }

    @Override
    public boolean resolver() {
        int[] vacia = buscarCasilla();
        if (vacia == null) return true; 

        int fila = vacia[0];
        int col = vacia[1];

        for (int num = 1; num <= 9; num++) {
            if (esvalido(fila, col, num)) {
                tablero[fila][col] = num;

                if (resolver()) return true;

                tablero[fila][col] = 0; 
            }
        }

        return false;
    }

    @Override
    protected boolean esvalido(int fila, int col, int num) {
      
        for (int i = 0; i < 9; i++) {
            if (tablero[fila][i] == num) return false;
        }

        
        for (int i = 0; i < 9; i++) {
            if (tablero[i][col] == num) return false;
        }

      
        int inicioFila = (fila / 3) * 3;
        int inicioCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[inicioFila + i][inicioCol + j] == num) return false;
            }
        }

        return true;
    }

    @Override
    protected int[] buscarCasilla() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                if (tablero[fila][col] == 0) {
                    return new int[]{fila, col};
                }
            }
        }
        return null;
    }
}


