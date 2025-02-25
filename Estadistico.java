public class Estadistico {
    public static void main(String[] args) {
        System.out.println("Jugando 500 partidas...");
        int ganadasJugador = 0;
        int ganadasAgente = 0;
        int empates = 0;

        for (int i = 0; i < 500; i++) {
            Juego juego = new Juego();
            int ganador = juego.partida();
            if (ganador == 0) {
                ganadasAgente++;
            } else if (ganador == 1) {
                ganadasJugador++;
            } else {
                empates++;
            }
        }

        System.out.println("Estadísticas:");
        System.out.println("Jugador: " + ganadasJugador + " victorias");
        System.out.println("Agente: " + ganadasAgente + " victorias");
        System.out.println("Empates: " + empates);
    }
}
