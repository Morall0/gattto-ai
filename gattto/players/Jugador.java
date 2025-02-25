package gattto.players;

import gattto.Tablero;
import java.util.Random;

public class Jugador {
    protected char ficha;
    private Random random = new Random();
    
    public Jugador (char ficha) {
        this.ficha = ficha;

    }

    /*public boolean tirar(Tablero tablero, int fila, int columna) {
        return tablero.colocarFicha(columna, fila, ficha); 
    }*/

    public void jugar(Tablero tablero) {
        char[][] board = tablero.getTablero();
        int size = board.length;
        boolean condition = true;

        do {
            int fila = random.nextInt(size);
            int columna = random.nextInt(size);
            condition = tablero.colocarFicha(columna, fila, ficha);
        } while (!condition);


    }
    
    public char getFicha() {
        return this.ficha;
    }
}
