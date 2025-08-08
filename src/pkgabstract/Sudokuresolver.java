package pkgabstract;

public abstract class Sudokuresolver {
    protected int[][] tablero;

    public Sudokuresolver(int[][] tablero) {
        this.tablero = tablero;
    }

    public abstract boolean resolver();  

    protected abstract boolean esvalido(int fila, int columna, int numero);

    protected abstract int[] buscarCasilla();
}
