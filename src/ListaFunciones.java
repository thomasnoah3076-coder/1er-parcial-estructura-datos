public class ListaFunciones {

    private NodoFuncion head;
    private int tamano = 0;

    public ListaFunciones() {
        this.head = null;
    }

    public boolean estaVacia() {return head == null;}

    public boolean existeCodigo(String codigo) {
        NodoFuncion actual = head;
        while (actual != null) {
            if (actual.getDato().getCodigo().equalsIgnoreCase(codigo)) {
                return true;
            }
            actual = actual.getSiguiente();
        }
        return false;
    }

    public boolean insertar(Funcion funcion) {
        if (existeCodigo(funcion.getCodigo())) {
            System.out.println("Ya hay una funcion con el codigo: " + funcion.getCodigo());
            return false;
        }
        NodoFuncion nuevo = new NodoFuncion(funcion);
        if (estaVacia()) {
            head = nuevo;
        } else {
            NodoFuncion actual = head;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevo);
        }
        setTamano(getTamano() +1);
        return true;
    }

    public Funcion obtenerPorPosicion(int posicion) {
        if (posicion < 0 || posicion >= getTamano()) {
            return null;
        }
        NodoFuncion actual = head;
        for (int i = 0; i < posicion; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }



    public NodoFuncion getHead() {
        return head;
    }

    public int getTamano() {
        return tamano;
    }

    public void setTamano(int tamano) {
        this.tamano = tamano;
    }
}
