import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public abstract class Personaje {
    private String raza;
    private String nombre;
    private String apodo;
    private LocalDate fechaNacimiento;
    private int edad;
    private int salud;
    private int velocidad;
    private int destreza;
    private int fuerza;
    private int nivel;
    private int armadura;

    public Personaje(String raza, String nombre, String apodo, LocalDate fechaNacimiento, int edad, int salud, int velocidad, int destreza, int fuerza, int nivel, int armadura) {
        this.raza = raza;
        this.nombre = nombre;
        this.apodo = apodo;
        this.fechaNacimiento = fechaNacimiento;
        this.edad = edad;
        this.salud = salud;
        this.velocidad = velocidad;
        this.destreza = destreza;
        this.fuerza = fuerza;
        this.nivel = nivel;
        this.armadura = armadura;
    }

    public String getRaza() { return raza; }
    public String getNombre() { return nombre; }
    public String getApodo() { return apodo; }
    public LocalDate getFechaNacimiento() { return fechaNacimiento; }
    public int getEdad() { return edad; }
    public int getSalud() { return salud; }
    public int getVelocidad() { return velocidad; }
    public int getDestreza() { return destreza; }
    public int getFuerza() { return fuerza; }
    public int getNivel() { return nivel; }
    public int getArmadura() { return armadura; }

    public void setSalud(int salud) { this.salud = salud; }
    public void setNivel(int nivel) { this.nivel = nivel; }

    public abstract double calcularDaño(Personaje enemigo);

    public String toStringFormatted(int index, String jugador) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return String.format(
                "\n-------------------------------\n" +
                        "   Personaje: %s\n" +
                        "   Raza: %s\n" +
                        "   Apodo: %s\n" +
                        "   Nacimiento: %s\n" +
                        "   Edad: %d\n" +
                        "   velocidad: %d\n" +
                        "   destreza: %d\n" +
                        "   fuerza: %d\n" +
                        "   nivel: %d\n" +
                        "   armadura: %d\n" +
                        "   Salud: %d\n" +
                        "-------------------------------",
                nombre, raza, apodo, fechaNacimiento.format(formatter), edad, velocidad, destreza, fuerza, nivel, armadura, salud
        );
    }
}
