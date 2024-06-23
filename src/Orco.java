package src;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Orco extends Personaje {
    private static List<String> nombres = new ArrayList<>();
    private static List<String> apodos = new ArrayList<>();

    static {
        nombres.add("Gorbag");
        nombres.add("Shagrat");
        nombres.add("Ugluk");
        nombres.add("Mansilyn");
        nombres.add("Robim Yunt");
        nombres.add("Lizzardon");

        apodos.add("El Destructor");
        apodos.add("El Brutal");
        apodos.add("El Sanguinario");
        apodos.add("El Aniquilador");
        apodos.add("El Implacable");
        apodos.add("El Feroz");
    }

    public Orco(String nombre, String apodo, LocalDate fechaNacimiento, int edad, int salud, int velocidad, int destreza, int fuerza, int nivel, int armadura) {
        super("ORCO", nombre, apodo, fechaNacimiento, edad, salud, velocidad, destreza, fuerza, nivel, armadura);
    }

    public static Orco crearOrcoAleatorio() {
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

        return new Orco(nombre, apodo, fechaNacimiento, edad, salud, velocidad, destreza, fuerza, nivel, armadura);
    }

    @Override
    public double calcularDaño(Personaje enemigo) {
        int PD = getDestreza() * getFuerza() * getNivel();
        int ED = new Random().nextInt() * (100 - 1) + 1;
        int VA = PD * (ED / 100);
        int PDEF = enemigo.getArmadura() * enemigo.getVelocidad();
        double damage = (((VA * ED) - PDEF) / 500) * 1.1;

        // Ajustar daño según la raza del enemigo
        if (enemigo.getRaza().equals("HUMANO")) {
            damage *= 1;
        } else if (enemigo.getRaza().equals("ELFO")) {
            damage *= 1.05;
        }

        return damage > 0 ? damage : 0; // Asegura que el daño no sea negativo
    }
}
