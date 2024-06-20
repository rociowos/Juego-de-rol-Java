import java.util.Scanner;

public class Menu {
    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú del Juego ---");
            System.out.println("1. Iniciar partida (personajes aleatorios)");
            System.out.println("2. Iniciar partida (ingresar personajes manualmente)");
            System.out.println("3. Leer desde el archivo logs de todas las partidas jugadas");
            System.out.println("4. Borrar archivo de logs");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            Juego juego = new Juego();
            switch (opcion) {
                case 1:
                    juego.jugar();
                    break;
                case 2:
                    juego.ingresarPersonajesManualmente();
                    juego.jugar();
                    break;
                case 3:
                    //EstadoPersonajes.leerLogs();
                    break;
                case 4:
                    //EstadoPersonajes.borrarLogs();
                    break;
                case 5:
                    System.out.println("¡Gracias por jugar!");
                    break;
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (opcion != 5);


    }
}
