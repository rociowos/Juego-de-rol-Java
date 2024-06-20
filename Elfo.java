import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Elfo extends Personaje {
    private static List<String> nombres = new ArrayList<>();
    private static List<String> apodos = new ArrayList<>();

    static {
        nombres.add("Legolas");
        nombres.add("Thranduil");
        nombres.add("Galadriel");
        nombres.add("Arja");
        nombres.add("Dephinus");
        nombres.add("Tolistorry");

        apodos.add("El Arquero");
        apodos.add("El Sabio");
        apodos.add("La Reina");
        apodos.add("El Inquebrantable");
        apodos.add("El Vengador");
        apodos.add("El Temerario");
    }

    public Elfo(String nombre, String apodo, LocalDate fechaNacimiento, int edad, int salud, int velocidad, int destreza, int fuerza, int nivel, int armadura) {
        super("ELFO", nombre, apodo, fechaNacimiento, edad, salud, velocidad, destreza, fuerza, nivel, armadura);
    }

    public static Elfo crearElfoAleatorio() {
        Random random = new Random();
        String nombre = nombres.get(random.nextInt(nombres.size()));
        String apodo = apodos.get(random.nextInt(apodos.size()));
        LocalDate fechaNacimiento = LocalDate.of(random.nextInt(2022 - 1800) + 1800, random.nextInt(12) + 1, random.nextInt(28) + 1);
        int edad = LocalDate.now().getYear() - fechaNacimiento.getYear();
        int salud = 100;
        int velocidad = random.nextInt(10) + 1;
        int destreza = random.nextInt(5) + 1;
        int fuerza = random.nextInt(10) + 1;
        int nivel = random.nextInt(10) + 1;
        int armadura = random.nextInt(10) + 1;

        return new Elfo(nombre, apodo, fechaNacimiento, edad, salud, velocidad, destreza, fuerza, nivel, armadura);
    }

    @Override
    public double calcularDaño(Personaje enemigo) {
        int PD = getDestreza() * getFuerza() * getNivel();
        int ED = new Random().nextInt() * (100 - 1) + 1;
        int VA = PD * (ED / 100);
        int PDEF = enemigo.getArmadura() * enemigo.getVelocidad();
        double damage = (((VA * ED) - PDEF) / 500) * 1.05;

        // Ajustar daño según la raza del enemigo
        if (enemigo.getRaza().equals("HUMANO")) {
            damage *= 1;
        } else if (enemigo.getRaza().equals("ORCO")) {
            damage *= 1.1;
        }

        return damage > 0 ? damage : 0; // Asegura que el daño no sea negativo
    }
}
