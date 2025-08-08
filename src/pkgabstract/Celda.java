package pkgabstract;


public class Celda {
    private int valor;               
    private boolean[] candidatos;     

    public Celda(int valor) {
        this.valor = valor;
        this.candidatos = new boolean[10]; 

        if (valor == 0) {
            
            for (int i = 1; i <= 9; i++) {
                candidatos[i] = true;
            }
        } else {
           
            for (int i = 1; i <= 9; i++) {
                candidatos[i] = false;
            }
        }
    }

   
    public int getValor() {
        return valor;
    }

    public void setValor(int valor) {
        this.valor = valor;
        for (int i = 1; i <= 9; i++) {
            candidatos[i] = false;
        }
    }

   
    public boolean esCandidato(int numero) {
        return candidatos[numero];
    }

    
    public void eliminarCandidato(int numero) {
        if (numero >= 1 && numero <= 9) {
            candidatos[numero] = false;
        }
    }

    
    public void agregarCandidato(int numero) {
        if (valor == 0 && numero >= 1 && numero <= 9) {
            candidatos[numero] = true;
        }
    }

   
    public boolean tieneUnicoCandidato() {
        int contador = 0;
        for (int i = 1; i <= 9; i++) {
            if (candidatos[i]) contador++;
        }
        return contador == 1;
    }

    
    public int getUnicoCandidato() {
        if (!tieneUnicoCandidato()) return 0;
        for (int i = 1; i <= 9; i++) {
            if (candidatos[i]) return i;
        }
        return 0;
    }

    
    public void resetearCandidatos() {
        if (valor == 0) {
            for (int i = 1; i <= 9; i++) {
                candidatos[i] = true;
            }
        }
    }
}
