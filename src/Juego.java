package src;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Juego {
    private List<Personaje> Jugador1;
    private List<Personaje> Jugador2;
    private Set<String> nombresUsados;

    public Juego() {
        Jugador1 = new ArrayList<>();
        Jugador2 = new ArrayList<>();
        nombresUsados = new HashSet<>();
    }

    public void generarEquiposAleatorios() {
        while (Jugador1.size() < 3) {
            Personaje personaje = EstadoPersonajes.crearPersonajeAleatorio();
            if (!nombresUsados.contains(personaje.getNombre())) {
                Jugador1.add(personaje);
                nombresUsados.add(personaje.getNombre());
            }
        }
        while (Jugador2.size() < 3) {
            Personaje personaje = EstadoPersonajes.crearPersonajeAleatorio();
            if (!nombresUsados.contains(personaje.getNombre())) {
                Jugador2.add(personaje);
                nombresUsados.add(personaje.getNombre());
            }
        }
    }

    public boolean ingresarPersonajesManualmente() {
        Scanner scanner = new Scanner(System.in);
        for (int i = 0; i < 6; i++) {
            System.out.println("Ingrese los detalles del " + (i + 1) + "° personaje:");

            String nombre;
            do {
                nombre = ingresarTexto(scanner, "Nombre: ");
                if (nombresUsados.contains(nombre)) {
                    System.out.println("Error: El nombre del personaje ya está en uso. Ingrese un nombre diferente.");
                }
            } while (nombresUsados.contains(nombre));

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

            nombresUsados.add(nombre);

            if (i < 3) {
                Jugador1.add(personaje);
            } else {
                Jugador2.add(personaje);
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
            System.out.print("Raza: Humano (H), Orco (O), Elfo (E): ");
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

        System.out.println("--- Comienza el juego --- \n ");

        int indexEquipo1 = 0;
        int indexEquipo2 = 0;
        StringBuilder log = new StringBuilder();

        while (indexEquipo1 < Jugador1.size() && indexEquipo2 < Jugador2.size()) {
            Personaje atacante;
            Personaje defensor;

            if (turnoEquipo1) {
                atacante = Jugador1.get(indexEquipo1);
                defensor = Jugador2.get(indexEquipo2);
            } else {
                atacante = Jugador2.get(indexEquipo2);
                defensor = Jugador1.get(indexEquipo1);
            }

            if (defensor.getSalud() > 0) { // Verifica si el defensor tiene salud mayor a 0
                Combate combate = new Combate(atacante, defensor);
                log.append(combate.realizarAtaques());

                if (defensor.getSalud() == 0) {
                    if (turnoEquipo1) {
                        indexEquipo2++;
                    } else {
                        indexEquipo1++;
                    }
                }
            } else {
                // Si el defensor no tiene salud, avanza al siguiente personaje en el equipo contrario
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

        if (indexEquipo1 == Jugador1.size()) {
            System.out.println("Felicitaciones Jugador 2 , las fuerzas mágicas del universo luz te abrazan!\n");
            log.append("Felicitaciones Jugador 2 , las fuerzas mágicas del universo luz te abrazan!\n.\n");
        } else if (indexEquipo2 == Jugador2.size()) {
            System.out.println("Felicitaciones Jugador 1 , las fuerzas mágicas del universo luz te abrazan!\n");
            log.append("Felicitaciones Jugador 1 , las fuerzas mágicas del universo luz te abrazan!\n\n");
        }

        log.append(generarLog());
        EstadoPersonajes.guardarLog(log.toString());
    }
    private String generarLog() {
        StringBuilder log = new StringBuilder();
        log.append("*** Estado Final del Juego ***\n");

        log.append("    JUGADOR 1\n     ");
        for (Personaje personaje : Jugador1) {
            log.append(personaje.toStringFormatted(0, "Jugador 1")).append("\n");
        }

        log.append("    JUGADOR 2\n     ");
        for (Personaje personaje : Jugador2) {
            log.append(personaje.toStringFormatted(0, "Jugador 2")).append("\n");
        }

        return log.toString();
    }

    public void mostrarEstadoFinal() {
        System.out.println("\n*** Estado Final del Juego *** \n ");

        System.out.println("         JUGADOR 1   ");
        for (Personaje personaje : Jugador1) {
            System.out.println(personaje.toStringFormatted(0, "Jugador 1"));
        }

        System.out.println("        JUGADOR 2   ");
        for (Personaje personaje : Jugador2) {
            System.out.println(personaje.toStringFormatted(0, "Jugador 2"));
        }
    }

    public void jugar() {
        if (Jugador1.isEmpty() && Jugador2.isEmpty()) {
            generarEquiposAleatorios();
        }
        iniciarJuego();
        mostrarEstadoFinal();
    }
}
