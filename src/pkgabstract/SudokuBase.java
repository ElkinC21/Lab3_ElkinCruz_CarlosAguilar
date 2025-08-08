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

   
    public boolean resolver() {
        return resolverDesde(0, 0);
    }

    private boolean resolverDesde(int fila, int col) {
        if (fila == 9) return true;
        int sigFila = (col == 8) ? fila + 1 : fila;
        int sigCol = (col + 1) % 9;

        if (!tablero[fila][col].estaVacia()) {
            return resolverDesde(sigFila, sigCol);
        }
        for (int num = 1; num <= 9; num++) {
            if (esMovimientoValido(fila, col, num)) {
                tablero[fila][col].setValor(num);
                if (resolverDesde(sigFila, sigCol)) return true;
                tablero[fila][col].setValor(0);
            }
        }
        return false;
    }

    
    public boolean tieneUnicaSolucion() {
        return contarSoluciones(0, 0, 0) == 1;
    }

    private int contarSoluciones(int fila, int col, int conteo) {
        if (conteo > 1) return conteo; 
        if (fila == 9) return conteo + 1;

        int sigFila = (col == 8) ? fila + 1 : fila;
        int sigCol = (col + 1) % 9;

        if (!tablero[fila][col].estaVacia()) {
            return contarSoluciones(sigFila, sigCol, conteo);
        }
        for (int num = 1; num <= 9; num++) {
            if (esMovimientoValido(fila, col, num)) {
                tablero[fila][col].setValor(num);
                conteo = contarSoluciones(sigFila, sigCol, conteo);
                if (conteo > 1) { 
                    tablero[fila][col].setValor(0);
                    return conteo;
                }
                tablero[fila][col].setValor(0);
            }
        }
        return conteo;
    }
}



