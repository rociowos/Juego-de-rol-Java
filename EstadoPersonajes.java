import java.io.*;
import java.util.List;
import java.util.Random;

public class EstadoPersonajes {

    private static final String LOG_FILE = "logs.txt";

    static {
        verificarLogFile();
    }

    public static Personaje crearPersonajeAleatorio() {
        int razaAleatoria = new Random().nextInt(3);
        switch (razaAleatoria) {
            case 0:
                return Humano.crearHumanoAleatorio();
            case 1:
                return Orco.crearOrcoAleatorio();
            case 2:
                return Elfo.crearElfoAleatorio();
            default:
                throw new IllegalArgumentException("Raza no válida");
        }
    }

    public static void mostrarEstadoFinal(List<Personaje> personajes, String jugador) {
        System.out.println("\n*** Estado Final de " + jugador + " ***");
        for (int i = 0; i < personajes.size(); i++) {
            System.out.println(personajes.get(i).toStringFormatted(i + 1, jugador));
        }
    }

    public static void pausar() {
        try {
            Thread.sleep(1000); // Pausa de 1 segundo
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public static void leerLogs() {
        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                System.out.println(linea);
            }
        } catch (IOException e) {
            System.out.println("Error al leer el archivo de logs: " + e.getMessage());
        }
    }

    public static void borrarLogs() {
        File file = new File(LOG_FILE);
        if (file.exists()) {
            if (file.delete()) {
                System.out.println("Archivo de logs borrado exitosamente.");
            } else {
                System.out.println("Error al borrar el archivo de logs.");
            }
        } else {
            System.out.println("El archivo de logs no existe.");
        }
    }

    public static void guardarLog(String log) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE, true))) {
            writer.write(log);
            writer.newLine();
        } catch (IOException e) {
            System.out.println("Error al guardar el log: " + e.getMessage());
        }
    }

    private static void verificarLogFile() {
        File file = new File(LOG_FILE);
        try {
            if (file.createNewFile()) {
                System.out.println("Archivo de logs creado: " + LOG_FILE);
            }
        } catch (IOException e) {
            System.out.println("Error al verificar o crear el archivo de logs: " + e.getMessage());
        }
    }
}
