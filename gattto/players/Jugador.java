package gattto.players;

import gattto.Tablero;

public class Jugador {
    private char ficha;
    
    public Jugador (char ficha) {
        this.ficha = ficha;
    }

    public boolean tirar(Tablero tablero, int fila, int columna) {
        return tablero.colocarFicha(columna, fila, ficha); 
    }
    
    public char getFicha() {
        return this.ficha;
    }
}
