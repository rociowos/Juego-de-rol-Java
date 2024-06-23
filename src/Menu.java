package src;

import java.util.Scanner;

public class Menu {
    public void mostrarMenu() {
        Scanner scanner = new Scanner(System.in);
        int opcion;

        do {
            System.out.println("\n--- Menú de Juego ---");
            System.out.println("1. Iniciar partida (personajes aleatorios)");
            System.out.println("2. Iniciar partida (ingresar personajes manualmente)");
            System.out.println("3. Leer desde el archivo logs de todas las partidas jugadas");
            System.out.println("4. Borrar archivo de logs");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");

            opcion = scanner.nextInt();
            scanner.nextLine();

            switch (opcion) {
                case 1:
                    Juego juego1 = new Juego();
                    juego1.jugar();
                    break;
                case 2:
                    Juego juego2 = new Juego();
                    if (juego2.ingresarPersonajesManualmente()) {
                        juego2.jugar();
                    }
                    break;
                case 3:
                    EstadoPersonajes.leerLogs();
                    break;
                case 4:
                    EstadoPersonajes.borrarLogs();
                    break;
                case 5:
                    System.out.println("¡Gracias por jugar!");
                    break; // Salir del método mostrarMenu()
                default:
                    System.out.println("Opción no válida. Inténtelo de nuevo.");
            }
        } while (opcion != 5);


    }
}
