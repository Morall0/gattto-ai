import gattto.Tablero;
import gattto.players.*;

public class Juego {

    public int partida() {
        Tablero tablero = new Tablero();
        Jugador[] jugadores = new Jugador[2];
        int turno = 0;

        determinaTurnos(jugadores);

        do {
            if (jugadores[turno] instanceof Agente) {
                ((Agente) jugadores[turno]).jugar(tablero, turno);
            } else {
                jugadores[turno].jugar(tablero);
            }

            turno = (turno + 1) % 2;
        } while (continuaJuego(tablero, jugadores));

        if (tablero.hayGanador(jugadores[0].getFicha())) {
            System.out.println("Gana el jugador 1");
            return 0; // Agente wins
        } else if (tablero.hayGanador(jugadores[1].getFicha())) {
            System.out.println("Gana el jugador 2");
            return 1; // Jugador wins
        } else {
            System.out.println("Empate");
            return -1; // Draw
        }
    }

    private static void determinaTurnos(Jugador[] jugadores) {
        jugadores[0] = new Agente('o');
        jugadores[1] = new Jugador('x');
    }

    private static boolean continuaJuego(Tablero tablero, Jugador[] jugadores) {
        return !tablero.tableroLleno() && !( 
               tablero.hayGanador(jugadores[0].getFicha()) || 
               tablero.hayGanador(jugadores[1].getFicha()));
    }
}
