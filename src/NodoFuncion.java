public class NodoFuncion {

    private Funcion dato;
    private NodoFuncion siguiente;

    public NodoFuncion(Funcion dato) {
        this.setDato(dato);
        this.setSiguiente(null);
    }

    public Funcion getDato() {return dato;}

    public NodoFuncion getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(NodoFuncion siguiente) {
        this.siguiente = siguiente;
    }

    public void setDato(Funcion dato) {this.dato = dato;}
}
