package src;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Humano extends Personaje {
    private static List<String> nombres = new ArrayList<>();
    private static List<String> apodos = new ArrayList<>();

    static {
        nombres.add("Aragorn");
        nombres.add("Boromir");
        nombres.add("Eowyn");
        nombres.add("Garos");
        nombres.add("Bryert");
        nombres.add("Tremblus");

        apodos.add("El Rey");
        apodos.add("El Valiente");
        apodos.add("La Doncella");
        apodos.add("El Detective");
        apodos.add("El Misterioso");
        apodos.add("El Temible");
    }

    public Humano(String nombre, String apodo, LocalDate fechaNacimiento, int edad, int salud, int velocidad, int destreza, int fuerza, int nivel, int armadura) {
        super("HUMANO", nombre, apodo, fechaNacimiento, edad, salud, velocidad, destreza, fuerza, nivel, armadura);
    }

    public static Humano crearHumanoAleatorio() {
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

        return new Humano(nombre, apodo, fechaNacimiento, edad, salud, velocidad, destreza, fuerza, nivel, armadura);
    }

    @Override
    public double calcularDaño(Personaje enemigo) {
        int PD = getDestreza() * getFuerza() * getNivel();
        int ED = new Random().nextInt() * (100 - 1) + 1;
        int VA = PD * (ED / 100);
        int PDEF = enemigo.getArmadura() * enemigo.getVelocidad();
        int damage = (((VA * ED) - PDEF) / 500) ;

        // Ajustar daño según la raza del enemigo
        if (enemigo.getRaza().equals("ELFO")) {
            damage *= 1.05;
        } else if (enemigo.getRaza().equals("ORCO")) {
            damage *= 1.1;
        }

        return damage > 0 ? damage : 0; // Asegura que el daño no sea negativo
    }
}
