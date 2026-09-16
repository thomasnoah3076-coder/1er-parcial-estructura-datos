import java.util.Scanner;

public class Cine {

    private ListaFunciones Funciones;

    public Cine() {
        this.Funciones = new ListaFunciones();
    }

    public void registrarFuncion(Scanner sc) {
        System.out.println("---- REGISTRAR FUNCIÓN ----");

        String codigo;
        do {
            System.out.print("Código de función: ");
            codigo = sc.nextLine().trim();
            if (Funciones.existeCodigo(codigo)) {
                System.out.println("Ese código ya existe. Ingrese otro.");
            }
        } while (Funciones.existeCodigo(codigo));

        System.out.print("Nombre de la película: ");
        String pelicula = sc.nextLine();

        System.out.print("Hora de inicio (ej. 7:00 PM): ");
        String hora = sc.nextLine();

        Funcion nuevaFuncion = new Funcion(codigo, pelicula, hora);
        Funciones.insertar(nuevaFuncion);

        System.out.println("Función registrada con éxito.\n");
    }

    private int validarEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }

    public void comprarEntrada(Scanner sc) {
        if (Funciones.estaVacia()) {
            System.out.println("No hay funciones registradas todavía.\n");
            return;
        }

        System.out.println("========== FUNCIONES DISPONIBLES ==========");
        NodoFuncion actual = Funciones.getHead();
        int contador = 1;
        while (actual != null) {
            System.out.println(contador + ". " + actual.getDato().getNombrePelicula());
            actual = actual.getSiguiente();
            contador++;
        }
        System.out.println("===========================================");

        System.out.print("Seleccione una función: ");
        int opcion = validarEntero(sc);

        Funcion seleccionada = Funciones.obtenerPorPosicion(opcion - 1);
        if (seleccionada == null) {
            System.out.println("Opción inválida.\n");
            return;
        }

        seleccionada.mostrarInformacion();

        boolean puestoVendido = false;
        while (!puestoVendido) {
            System.out.print("Seleccione el número del puesto que desea comprar: ");
            int puesto = validarEntero(sc);

            if (!seleccionada.puestoValido(puesto)) {
                System.out.println("El puesto debe estar entre 1 y 20.");
                continue;
            }
            if (!seleccionada.estaDisponible(puesto)) {
                System.out.println("Ese puesto no está disponible. Elija otro.");
                continue;
            }
            seleccionada.venderPuesto(puesto);
            System.out.println("¡Compra realizada con éxito!\n");
            puestoVendido = true;
        }
    }


    public void iniciarLabores(Scanner sc) {
        if (Funciones.estaVacia()) {
            System.out.println("No hay funciones registradas todavía.\n");
            return;
        }
        NodoFuncion actual = Funciones.getHead();
        while (actual != null) {
            Funcion f = actual.getDato();
            System.out.println("====================================");
            System.out.println("       REPRODUCIENDO FUNCIÓN        ");
            System.out.println("====================================");
            System.out.println("Película: " + f.getNombrePelicula());
            System.out.println("Hora: " + f.getHoraInicio());
            System.out.println("Puestos vendidos: " + f.contarVendidos());
            System.out.println("Puestos disponibles: " + f.contarDisponibles());
            System.out.println("====================================");

            actual = actual.getSiguiente();

            if (actual != null) {
                System.out.println("Presione una tecla para reproducir la siguiente función...");
                sc.nextLine();
            }
        }

        System.out.println("\nTodas las funciones han sido reproducidas. Fin del programa.");
    }


}