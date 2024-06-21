import java.text.DecimalFormat;
import java.util.Random;

public class Combate {
    private Personaje atacante;
    private Personaje defensor;

    public Combate(Personaje atacante, Personaje defensor) {
        this.atacante = atacante;
        this.defensor = defensor;
    }

    public String realizarAtaques() {
        StringBuilder logCombate = new StringBuilder();
        logCombate.append("---------------------------------------------------\n")
                .append("                  Ronda de Combate\n")
                .append("---------------------------------------------------\n")
                .append("Atacante: ").append(atacante.getNombre()).append("\n")
                .append("Defensor: ").append(defensor.getNombre()).append("\n");
        System.out.print(logCombate.toString());

        while (atacante.getSalud() > 0 && defensor.getSalud() > 0) {
            int danioAtacante = ataque(atacante, defensor);
            String ataqueAtacante = atacante.getNombre() + " ataca a " + defensor.getNombre() + " y le quita " + danioAtacante + " de salud. " + defensor.getNombre() + " queda con " + defensor.getSalud() + " de salud.\n";
            logCombate.append(ataqueAtacante);
            System.out.print(ataqueAtacante);

            if (defensor.getSalud() <= 0) {
                String muerteDefensor = "Muere " + defensor.getNombre() + ".\n";
                logCombate.append(muerteDefensor);
                System.out.print(muerteDefensor);

                atacante.setSalud(atacante.getSalud() + 10); // Recompensa
                String recompensaAtacante = atacante.getNombre() + " gana 10 de salud como premio, quedando con " + atacante.getSalud() + " de salud.\n";
                logCombate.append(recompensaAtacante);
                System.out.print(recompensaAtacante);
                break;
            }

            int danioDefensor = ataque(defensor, atacante);
            String ataqueDefensor = defensor.getNombre() + " ataca a " + atacante.getNombre() + " y le quita " + danioDefensor + " de salud. " + atacante.getNombre() + " queda con " + atacante.getSalud() + " de salud.\n";
            logCombate.append(ataqueDefensor);
            System.out.print(ataqueDefensor);

            if (atacante.getSalud() <= 0) {
                String muerteAtacante = "Muere " + atacante.getNombre() + ".\n";
                logCombate.append(muerteAtacante);
                System.out.print(muerteAtacante);

                defensor.setSalud(defensor.getSalud() + 10); // Recompensa
                String recompensaDefensor = defensor.getNombre() + " gana 10 de salud como premio, quedando con " + defensor.getSalud() + " de salud.\n";
                logCombate.append(recompensaDefensor);
                System.out.print(recompensaDefensor);
                break;
            }
        }

        return logCombate.toString();
    }

    public int ataque(Personaje atacante, Personaje defensor) {
        int PD = atacante.getDestreza() * atacante.getFuerza() * atacante.getNivel();
        int ED = numAleatorio(1, 100);
        DecimalFormat df = new DecimalFormat("#");
        int VA = Integer.parseInt(df.format((PD * ED) / 100));
        int PDEF = defensor.getVelocidad() * defensor.getArmadura();

        // Lógica cálculo del ataque truncando decimales
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

        return danio;
    }

    private int numAleatorio(int min, int max) {
        return new Random().nextInt((max - min) + 1) + min;
    }
}
