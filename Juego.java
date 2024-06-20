import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Juego {
    private List<Personaje> equipo1;
    private List<Personaje> equipo2;

    public Juego() {
        equipo1 = new ArrayList<>();
        equipo2 = new ArrayList<>();
    }

    public void generarEquiposAleatorios() {
        for (int i = 0; i < 3; i++) {
            equipo1.add(EstadoPersonajes.crearPersonajeAleatorio());
            equipo2.add(EstadoPersonajes.crearPersonajeAleatorio());
        }
    }

    public boolean ingresarPersonajesManualmente() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 6; i++) {
            System.out.println("Ingrese los detalles del " + (i + 1) + "° personaje:");

            String nombre = ingresarTexto(scanner, "Nombre: ");
            String apodo = ingresarTexto(scanner, "Apodo: ");
            String raza = ingresarRaza(scanner);
            LocalDate fechaNacimiento = ingresarFecha(scanner);
            int velocidad = ingresarEntero(scanner, "Velocidad (1-10): ", 1, 10);
            int destreza = ingresarEntero(scanner, "Destreza (1-5): ", 1, 5);
            int fuerza = ingresarEntero(scanner, "Fuerza (1-10): ", 1, 10);
            int nivel = ingresarEntero(scanner, "Nivel (1-10): ", 1, 10);
            int armadura = ingresarEntero(scanner, "Armadura (1-10): ", 1, 10);
            int edad = LocalDate.now().getYear() - fechaNacimiento.getYear();

            Personaje personaje;
            switch (raza) {
                case "H":
                    personaje = new Humano(nombre, apodo, fechaNacimiento, edad, 100, velocidad, destreza, fuerza, nivel, armadura);
                    break;
                case "O":
                    personaje = new Orco(nombre, apodo, fechaNacimiento, edad, 100, velocidad, destreza, fuerza, nivel, armadura);
                    break;
                case "E":
                    personaje = new Elfo(nombre, apodo, fechaNacimiento, edad, 100, velocidad, destreza, fuerza, nivel, armadura);
                    break;
                default:
                    throw new IllegalArgumentException("Raza no válida");
            }

            if (i < 3) {
                equipo1.add(personaje);
            } else {
                equipo2.add(personaje);
            }
        }
        return true;
    }

    private String ingresarTexto(Scanner scanner, String mensaje) {
        String input;
        do {
            System.out.print(mensaje);
            input = scanner.nextLine();
            if (input.trim().isEmpty()) {
                System.out.println("Error: El campo no puede estar vacío.");
            }
        } while (input.trim().isEmpty());
        return input;
    }

    private String ingresarRaza(Scanner scanner) {
        String raza;
        do {
            System.out.print("Raza (Humano (H), Orco (O), Elfo (E)): ");
            raza = scanner.nextLine().trim().toUpperCase();
            if (!raza.equals("H") && !raza.equals("O") && !raza.equals("E")) {
                System.out.println("Error: Raza no válida. Ingrese H, O o E.");
            }
        } while (!raza.equals("H") && !raza.equals("O") && !raza.equals("E"));
        return raza;
    }

    private LocalDate ingresarFecha(Scanner scanner) {
        LocalDate fechaNacimiento = null;
        do {
            System.out.print("Fecha de nacimiento (AAAA-MM-DD): ");
            try {
                fechaNacimiento = LocalDate.parse(scanner.nextLine().trim());
            } catch (DateTimeParseException e) {
                System.out.println("Error: Fecha no válida. Use el formato AAAA-MM-DD.");
            }
        } while (fechaNacimiento == null);
        return fechaNacimiento;
    }

    private int ingresarEntero(Scanner scanner, String mensaje, int min, int max) {
        int valor = 0;
        boolean valido = false;
        do {
            System.out.print(mensaje);
            try {
                valor = Integer.parseInt(scanner.nextLine().trim());
                if (valor < min || valor > max) {
                    System.out.println("Error: El valor debe estar entre " + min + " y " + max + ".");
                } else {
                    valido = true;
                }
            } catch (NumberFormatException e) {
                System.out.println("Error: Entrada no válida. Debe ser un número entero.");
            }
        } while (!valido);
        return valor;
    }

    public void iniciarJuego() {
        Random random = new Random();
        boolean turnoEquipo1 = random.nextBoolean();

        System.out.println("--- Comienza el juego ---");

        int indexEquipo1 = 0;
        int indexEquipo2 = 0;

        while (indexEquipo1 < equipo1.size() && indexEquipo2 < equipo2.size()) {
            Personaje atacante;
            Personaje defensor;

            if (turnoEquipo1) {
                atacante = equipo1.get(indexEquipo1);
                defensor = equipo2.get(indexEquipo2);
            } else {
                atacante = equipo2.get(indexEquipo2);
                defensor = equipo1.get(indexEquipo1);
            }

            Combate combate = new Combate(atacante, defensor);
            combate.realizarAtaques();

            if (defensor.getSalud() == 0) {
                if (turnoEquipo1) {
                    indexEquipo2++;
                } else {
                    indexEquipo1++;
                }
            }

            turnoEquipo1 = !turnoEquipo1;

            // Pausa entre combates
            EstadoPersonajes.pausar();
        }

        if (indexEquipo1 == equipo1.size()) {
            System.out.println("Equipo 2 ha ganado el juego.");
        } else if (indexEquipo2 == equipo2.size()) {
            System.out.println("Equipo 1 ha ganado el juego.");
        }
    }

    public void mostrarEstadoFinal() {
        System.out.println("\n*** Estado Final del Juego ***");

        System.out.println("JUGADOR 1");
        for (Personaje personaje : equipo1) {
            System.out.println(personaje.toStringFormatted(0, "Jugador 1"));
        }

        System.out.println("JUGADOR 2");
        for (Personaje personaje : equipo2) {
            System.out.println(personaje.toStringFormatted(0, "Jugador 2"));
        }
    }

    public void jugar() {
        if (equipo1.isEmpty() && equipo2.isEmpty()) {
            generarEquiposAleatorios();
        }
        iniciarJuego();
        mostrarEstadoFinal();
    }


}
