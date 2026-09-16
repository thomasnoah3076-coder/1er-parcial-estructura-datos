import java.util.Scanner;
public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Cine cine = new Cine();
        int opcion;

        do {
            System.out.println("========== CINE ==========");
            System.out.println("1. Registrar función");
            System.out.println("2. Comprar entrada");
            System.out.println("3. Iniciar labores");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = leerEntero(sc);
            System.out.println();

            switch (opcion) {
                case 1:
                    cine.registrarFuncion(sc);
                    break;
                case 2:
                    cine.comprarEntrada(sc);
                    break;
                case 3:
                    cine.iniciarLabores(sc);
                    break;
                case 0:
                    System.out.println("Saliendo del sistema...");
                    break;
                default:
                    System.out.println("Opción inválida.\n");
            }
        } while (opcion != 0);

        sc.close();
    }

    private static int leerEntero(Scanner sc) {
        while (!sc.hasNextInt()) {
            System.out.print("Ingrese un número válido: ");
            sc.next();
        }
        int valor = sc.nextInt();
        sc.nextLine();
        return valor;
    }
}