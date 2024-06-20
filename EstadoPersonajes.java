import java.util.List;
import java.util.Random;


public class EstadoPersonajes {
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
}

