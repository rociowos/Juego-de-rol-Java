# Mini Juego de rol con JAVA

Este es un juego de combate basado en personajes de fantasía, desarrollado en Java. Los jugadores pueden seleccionar personajes de diferentes razas y luchar en batallas por turnos. El juego incluye características como la generación aleatoria de personajes, la entrada manual de personajes y la gestión de logs de combates.

## Características del Juego
* Generación de Personajes Aleatorios: Los jugadores pueden iniciar una partida con personajes generados aleatoriamente.
* Entrada Manual de Personajes: Los jugadores pueden ingresar manualmente los detalles de sus personajes.
* Combate por Turnos: Los personajes de los jugadores luchan en rondas de combate, atacándose mutuamente hasta que uno de ellos queda sin salud.
* Recompensas por Victoria: Los personajes que ganan un combate reciben una bonificación de salud.
* Logs de Combate: Se guarda un registro detallado de todos los combates en un archivo de logs.
* Gestión de Logs: Los jugadores pueden leer y borrar el archivo de logs a través del menú del juego.

### Estructura del Proyecto
* Main.java: Punto de entrada del juego.
* Menu.java: Maneja la interacción con el usuario y el menú principal.
* Juego.java: Contiene la lógica principal del juego, incluida la generación de personajes y la secuencia de combate.
* Combate.java: Gestiona la lógica de los combates entre personajes.
* EstadoPersonajes.java: Gestiona la creación de personajes.
* ManejoLogs: Manipulación del archivo de logs.
* Personaje.java: Clase abstracta que define las propiedades y métodos básicos de un personaje.
* Humano.java, Orco.java, Elfo.java: Clases que heredan de Personaje y definen los detalles específicos de cada raza.
