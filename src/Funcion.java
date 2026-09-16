
public class Funcion {

    public static final int TOTAL_PUESTOS = 20;
    private static final char DISPONIBLE = 'D';
    private static final char OCUPADO = 'O';

    private String codigoFuncion;
    private String nombrePelicula;
    private String horaInicio;
    private char[] puestos;

    public Funcion(String codigo, String nombrePelicula, String horaInicio) {
        this.setCodigoFuncion(codigo);
        this.setNombrePelicula(nombrePelicula);
        this.setHoraInicio(horaInicio);
        this.setPuestos(new char[TOTAL_PUESTOS]);
        for (int i = 0; i < TOTAL_PUESTOS; i++) {
            puestos[i] = DISPONIBLE;
        }
    }

    public String getCodigo() {
        return codigoFuncion;
    }

    public String getNombrePelicula() {
        return nombrePelicula;
    }

    public String getHoraInicio() {
        return horaInicio;
    }

    public void setCodigoFuncion(String codigoFuncion) {
        this.codigoFuncion = codigoFuncion;
    }

    public void setNombrePelicula(String nombrePelicula) {
        this.nombrePelicula = nombrePelicula;
    }

    public void setHoraInicio(String horaInicio) {
        this.horaInicio = horaInicio;
    }

    public void setPuestos(char[] puestos) {
        this.puestos = puestos;
    }

    public boolean puestoValido(int numero) {
        return numero >= 1 && numero <= TOTAL_PUESTOS;
    }

    public boolean estaDisponible(int numero) {
        return puestos[numero - 1] == DISPONIBLE; // Restar uno para que sea acorde con los indices del arreglo
    }

    public boolean venderPuesto(int numero) {
        if (!puestoValido(numero) || !estaDisponible(numero)) { //Invierte le resultado de los metodos, si son verdad no entran
            return false;
        }
        puestos[numero - 1] = OCUPADO;
        return true;
    }

    public int contarVendidos() {
        int contador = 0;
        for (char p : puestos) {
            if (p == OCUPADO) {
                contador++;
            }
        }
        return contador;
    }

    public int contarDisponibles() {
        return TOTAL_PUESTOS - contarVendidos();
    }

    public void mostrarPuestos() {
        System.out.println("Puestos:");
        for (int i = 0; i < TOTAL_PUESTOS; i++) {
            int numeroPuesto = i + 1;
            System.out.printf("%-3d[%c]  ", numeroPuesto, puestos[i]);
            /*
            Printf es un metodo que permite dar formato
            % marca el inicio de patron del formato
            - alinea el texto a al izquierda
            3 deja un espacio de tres caracteres (incluyendo numero puesto)
            d espera un numero decimal integer que es numero Puesto
            %c espera una varible char que es puestos[i]
             */
            if ((i + 1) % 5 == 0) {
                System.out.println();
            }
        }
        System.out.println();
        System.out.println("D = Disponible   O = Ocupado");
    }

    public void mostrarInformacion() {
        System.out.println("=============================================");
        System.out.println("Película: " + nombrePelicula);
        System.out.println("Hora: " + horaInicio);
        mostrarPuestos();
        System.out.println("=============================================");
    }

}