package pkgabstract;

public abstract class SudokuBase {
    protected Celda[][] tablero;

    public SudokuBase() {
        tablero = new Celda[9][9];
        for (int fila = 0; fila < 9; fila++) {
            for (int col = 0; col < 9; col++) {
                tablero[fila][col] = new Celda(0);
            }
        }
    }

    public abstract boolean esMovimientoValido(int fila, int col, int numero);

    public int getValor(int fila, int col) {
        return tablero[fila][col].getValor();
    }

    public void setValor(int fila, int col, int valor) {
        tablero[fila][col].setValor(valor);
    }

    public boolean esEditable(int fila, int col) {
        return tablero[fila][col].getValor() == 0;
    }

    public Celda[][] getTablero() {
        return tablero;
    }
}

