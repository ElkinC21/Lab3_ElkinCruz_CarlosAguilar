package pkgabstract;


public class TableroSudokulogica {
    private Celda[][] tablero;

    public TableroSudokulogica(int[][] tableroInicial) {
        tablero = new Celda[9][9];
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                tablero[fila][col] = new Celda(tableroInicial[fila][col]);
            }
        }
    }

   
    public int getValor(int fila, int col) {
        return tablero[fila][col].getValor();
    }

   
    public void setValor(int fila, int col, int valor) {
        tablero[fila][col].setValor(valor);
    }

   
    public boolean esMovimientoValido(int fila, int col, int numero) {
       
        for (int i = 0; i < 9; i++) {
            if (tablero[fila][i].getValor() == numero) return false;
        }

        
        for (int i = 0; i < 9; i++) {
            if (tablero[i][col].getValor() == numero) return false;
        }

        
        int inicioFila = (fila / 3) * 3;
        int inicioCol = (col / 3) * 3;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (tablero[inicioFila + i][inicioCol + j].getValor() == numero) {
                    return false;
                }
            }
        }

        return true;
    }

   
    public int[] buscarCasillaVacia() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                if (tablero[fila][col].getValor() == 0) {
                    return new int[]{fila, col};
                }
            }
        }
        return null; 
    }

    
    public int[][] obtenerMatrizSimple() {
        int[][] resultado = new int[9][9];
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                resultado[fila][col] = tablero[fila][col].getValor();
            }
        }
        return resultado;
    }

    
    public void resetearCandidatos() {
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                tablero[fila][col].resetearCandidatos();
            }
        }
    }

   
    public Celda getCelda(int fila, int col) {
        return tablero[fila][col];
    }
}

