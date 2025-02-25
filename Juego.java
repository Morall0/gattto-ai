import java.util.Random;
import java.util.Scanner;

import gattto.Tablero;
import gattto.players.*;

public class Juego {


    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Tablero tablero = new Tablero();
        Jugador[] jugadores = new Jugador[2];
        int turno=0;
        int[] coords = new int[2];

        determinaTurnos(jugadores);

        do { //Permite jugar hasta que se llene o haya un ganador
            tablero.mostrarTablero();
            System.out.println("Turno de jugador "+ (turno+1));

            boolean casilla_valida;

            if (jugadores[turno] instanceof Agente) { //Si es agente se tira con base en sus algoritmos
                ((Agente) jugadores[turno]).jugar(tablero, turno);
            } else { //Si es usuario se le pide la casilla
                do {
                    System.out.print("\nTirar en (fila,columna): ");
                    coords = scanCoords(scanner);
                    casilla_valida = jugadores[turno].tirar(tablero, coords[0], coords[1]);

                    if (casilla_valida == false)
                        System.out.println("Casilla inválida, elige otra casilla.");

                } while (casilla_valida == false); 
            }

            turno = (turno + 1) % 2; //Va cambiando el turno (indice) de forma ciclica.
        } while (continuaJuego(tablero, jugadores));

        tablero.mostrarTablero();
        if (tablero.hayGanador(jugadores[0].getFicha()))
            System.out.println("Gana el jugador 1");
        else if (tablero.hayGanador(jugadores[1].getFicha()))
            System.out.println("Gana el jugador 2");
        else
            System.out.println("Empate");


        scanner.close();
    }

    private static void determinaTurnos(Jugador[] jugadores) {
        Random random = new Random(); 
        int turno = random.nextInt(2);

        if (turno == 1) {
            jugadores[0] = new Jugador('o');
            jugadores[1] = new Agente('x');
            System.out.println("Comienza JUGADOR (o)");
        } else {
            jugadores[0] = new Agente('o');
            jugadores[1] = new Jugador('x');
            System.out.println("Comienza AGENTE (o)");
        }
    }

    private static boolean continuaJuego(Tablero tablero, Jugador[] jugadores) {
        return !tablero.tableroLleno() && !( 
               tablero.hayGanador(jugadores[0].getFicha()) || 
               tablero.hayGanador(jugadores[1].getFicha()));
    }

    private static int[] scanCoords(Scanner scanner) {
        String string_coords = scanner.nextLine();
        String[] splitted = string_coords.split(",");

        return new int[]{Integer.parseInt(splitted[0]), Integer.parseInt(splitted[1])}; //Retorna un int[2] con {fila,columna}
    }

}
