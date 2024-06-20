import java.text.DecimalFormat;
import java.util.Random;

public class Combate {
    private Personaje atacante;
    private Personaje defensor;

    public Combate(Personaje atacante, Personaje defensor) {
        this.atacante = atacante;
        this.defensor = defensor;
    }

    public void realizarAtaques() {
        System.out.println("---------------------------------------------------");
        System.out.println("                  Ronda de Combate");
        System.out.println("---------------------------------------------------");
        System.out.println("Atacante: " + atacante.getNombre());
        System.out.println("Defensor: " + defensor.getNombre());

        while (atacante.getSalud() > 0 && defensor.getSalud() > 0) {
            int danioAtacante = ataque(atacante, defensor);
            if (defensor.getSalud() <= 0) {
                System.out.println("Muere " + defensor.getNombre() + ".");
                atacante.setSalud(atacante.getSalud() + 10); // Recompensa
                System.out.println(atacante.getNombre() + " gana 10 de salud como premio, quedando con " + atacante.getSalud() + " de salud.");
                break;
            }

            int danioDefensor = ataque(defensor, atacante);
            if (atacante.getSalud() <= 0) {
                System.out.println("Muere " + atacante.getNombre() + ".");
                defensor.setSalud(defensor.getSalud() + 10); // Recompensa
                System.out.println(defensor.getNombre() + " gana 10 de salud como premio, quedando con " + defensor.getSalud() + " de salud.");
                break;
            }
        }
    }

    public int ataque(Personaje atacante, Personaje defensor) {
        int PD = atacante.getDestreza() * atacante.getFuerza() * atacante.getNivel();
        int ED = numAleatorio(1, 100);
        DecimalFormat df = new DecimalFormat("#");
        int VA = Integer.parseInt(df.format((PD * ED) / 100));
        int PDEF = defensor.getVelocidad() * defensor.getArmadura();

        // Lógica calculo del ataque truncando decimales
        int danio = 0;
        switch (atacante.getRaza().toUpperCase()) {
            case "HUMANO":
                danio = Integer.parseInt(df.format((((VA * ED) - PDEF) / 500) * 5));
                break;
            case "ORCO":
                danio = Integer.parseInt(df.format(((((VA * ED) - PDEF) / 500) * 5) * 1.1));
                break;
            case "ELFO":
                danio = Integer.parseInt(df.format(((((VA * ED) - PDEF) / 500) * 5) * 1.05));
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + atacante.getRaza());
        }

        // Resta el daño en la salud
        int salud = defensor.getSalud();
        if (salud > danio) {
            salud = salud - danio;
        } else {
            salud = 0;
        }
        defensor.setSalud(salud);

        System.out.println(atacante.getNombre() + " ataca a " + defensor.getNombre() + " y le quita " + danio + " de salud. " + defensor.getNombre() + " queda con " + salud + " de salud.");
        return danio;
    }

    private int numAleatorio(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
