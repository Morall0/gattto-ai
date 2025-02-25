package gattto.players;

import java.util.Random;

import gattto.Tablero;

public class Agente extends Jugador {

      
    private Random random;

    public Agente(char ficha) {
        super(ficha);
        this.random = new Random();
    }   
   
    

    

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

}
