package pkgabstract;

public class Celda {
    private int valor;

    public Celda(int valor) {
        this.valor = valor;
    }

    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }

    public boolean estaVacia() {
        return valor == 0;
    }
}

