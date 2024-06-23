package src;

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


        int ataquesAtacante = 0;
        int ataquesDefensor = 0;

        while ((atacante.getSalud() > 0 && defensor.getSalud() > 0) && (ataquesAtacante < 7 || ataquesDefensor < 7)) {
            if (ataquesAtacante < 7) {
                int danioAtacante = ataque(atacante, defensor);
                String ataqueAtacante = atacante.getNombre() + " ataca a " + defensor.getNombre() + " y le quita " + danioAtacante + " de salud. " + defensor.getNombre() + " queda con " + defensor.getSalud() + " de salud.\n";
                logCombate.append(ataqueAtacante);
                System.out.print(ataqueAtacante);
                ataquesAtacante++;

                if (defensor.getSalud() <= 0) {
                    String muerteDefensor = "*** Muere " + defensor.getNombre() + " el destino ha decidido, y las fuerzas del universo oscuro te abrazan.. ***\n";
                    System.out.print("\n");

                    logCombate.append(muerteDefensor);
                    System.out.print(muerteDefensor);

                    atacante.setSalud(atacante.getSalud() + 10); // Recompensa
                    String recompensaAtacante = " \uD83D\uDC51 " + atacante.getNombre() + " gana 10 de salud como premio, quedando con " + atacante.getSalud() + " de salud.\n";
                    System.out.print("\n");
                    logCombate.append(recompensaAtacante);
                    System.out.print(recompensaAtacante);
                    break;
                }
            }

            if (ataquesDefensor < 7) {
                int danioDefensor = ataque(defensor, atacante);
                String ataqueDefensor = defensor.getNombre() + " ataca a " + atacante.getNombre() + " y le quita " + danioDefensor + " de salud. " + atacante.getNombre() + " queda con " + atacante.getSalud() + " de salud.\n";
                logCombate.append(ataqueDefensor);
                System.out.print(ataqueDefensor);
                ataquesDefensor++;

                if (atacante.getSalud() <= 0) {
                    String muerteAtacante = "*** Muere " + atacante.getNombre() + " el destino ha decidido, y las fuerzas del universo oscuro te abrazan.. ***\n";
                    System.out.print("\n");
                    logCombate.append(muerteAtacante);
                    System.out.print(muerteAtacante);

                    defensor.setSalud(defensor.getSalud() + 10); // Recompensa
                    String recompensaDefensor = " \uD83D\uDC51 "+defensor.getNombre() + " gana 10 de salud como premio, quedando con " + defensor.getSalud() + " de salud.\n";
                    System.out.print("\n");
                    logCombate.append(recompensaDefensor);
                    System.out.print(recompensaDefensor);
                    break;
                }
            }
        }

        if (ataquesAtacante >= 7 && ataquesDefensor >= 7) {
            logCombate.append("Se alcanzó el límite de 7 ataques por personaje.\n");
            System.out.print("Se alcanzó el límite de 7 ataques por personaje.\n");

            if (atacante.getSalud() > defensor.getSalud()) {
                logCombate.append(atacante.getNombre()).append(" gana el combate con ").append(atacante.getSalud()).append(" de salud restante.\n");
                System.out.print(atacante.getNombre() + " gana el combate con " + atacante.getSalud() + " de salud restante.\n");
            } else if (defensor.getSalud() > atacante.getSalud()) {
                logCombate.append(defensor.getNombre()).append(" gana el combate con ").append(defensor.getSalud()).append(" de salud restante.\n");
                System.out.print(defensor.getNombre() + " gana el combate con " + defensor.getSalud() + " de salud restante.\n");
            } else {
                logCombate.append("El combate termina en empate con ambos personajes teniendo ").append(atacante.getSalud()).append(" de salud restante.\n");
                System.out.print("El combate termina en empate con ambos personajes teniendo " + atacante.getSalud() + " de salud restante.\n");
            }
        }

        logCombate.append("---------------------------------------------------\n\n");
        System.out.print("---------------------------------------------------\n\n");
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

        if (danio < 1) {
            danio = 1;
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