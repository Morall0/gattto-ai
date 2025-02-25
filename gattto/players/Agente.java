package gattto.players;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import gattto.Tablero;

public class Agente extends Jugador {

    private char fichaContraria;   
    private Random random;

    public Agente(char ficha) {
        super(ficha);
        this.fichaContraria = (ficha == 'x') ? 'o' : 'x';
        this.random = new Random();
    }   
   
    

    

    public void jugar(Tablero tablero) {
        char[][] board = tablero.getTablero();
        int size = board.length;

        // 1. Intentar bloquear: revisar si el oponente tiene 2 en línea y se puede bloquear el tercero

        // Bloqueo horizontal
        for (int i = 0; i < size; i++) {
            for (int j = 0; j <= size - 3; j++) {
                int contadorOponente = 0;
                int contadorVacio = 0;
                int columnaVacio = -1;
                for (int k = 0; k < 3; k++) {
                    if (board[i][j + k] == fichaContraria) {
                        contadorOponente++;
                    } else if (board[i][j + k] == ' ') {
                        contadorVacio++;
                        columnaVacio = j + k;
                    }
                }
                if (contadorOponente == 2 && contadorVacio == 1) {
                    tablero.colocarFicha(i, columnaVacio, ficha);
                    System.out.println("Agente bloquea en (" + i + ", " + columnaVacio + ") horizontal");
                    return;
                }
            }
        }

        // Bloqueo vertical
        for (int j = 0; j < size; j++) {
            for (int i = 0; i <= size - 3; i++) {
                int contadorOponente = 0;
                int contadorVacio = 0;
                int filaVacia = -1;
                for (int k = 0; k < 3; k++) {
                    if (board[i + k][j] == fichaContraria) {
                        contadorOponente++;
                    } else if (board[i + k][j] == ' ') {
                        contadorVacio++;
                        filaVacia = i + k;
                    }
                }
                if (contadorOponente == 2 && contadorVacio == 1) {
                    tablero.colocarFicha(filaVacia, j, ficha);
                    System.out.println("Agente bloquea en (" + filaVacia + ", " + j + ") vertical");
                    return;
                }
            }
        }

        // Bloqueo diagonal (de izquierda a derecha)
        for (int i = 0; i <= size - 3; i++) {
            for (int j = 0; j <= size - 3; j++) {
                int contadorOponente = 0;
                int contadorVacio = 0;
                int filaVacia = -1;
                int columnaVacia = -1;
                for (int k = 0; k < 3; k++) {
                    if (board[i + k][j + k] == fichaContraria) {
                        contadorOponente++;
                    } else if (board[i + k][j + k] == ' ') {
                        contadorVacio++;
                        filaVacia = i + k;
                        columnaVacia = j + k;
                    }
                }
                if (contadorOponente == 2 && contadorVacio == 1) {
                    tablero.colocarFicha(filaVacia, columnaVacia, ficha);
                    System.out.println("Agente bloquea en (" + filaVacia + ", " + columnaVacia + ") diagonal");
                    return;
                }
            }
        }

        // Bloqueo diagonal inversa (de derecha a izquierda)
        for (int i = 0; i <= size - 3; i++) {
            for (int j = 2; j < size; j++) {
                int contadorOponente = 0;
                int contadorVacio = 0;
                int filaVacia = -1;
                int columnaVacia = -1;
                for (int k = 0; k < 3; k++) {
                    if (board[i + k][j - k] == fichaContraria) {
                        contadorOponente++;
                    } else if (board[i + k][j - k] == ' ') {
                        contadorVacio++;
                        filaVacia = i + k;
                        columnaVacia = j - k;
                    }
                }
                if (contadorOponente == 2 && contadorVacio == 1) {
                    tablero.colocarFicha(filaVacia, columnaVacia, ficha);
                    System.out.println("Agente bloquea en (" + filaVacia + ", " + columnaVacia + ") diagonal inversa");
                    return;
                }
            }
        }

        // 2. Si no hay jugada peligrosa, el agente juega de forma aleatoria
        List<int[]> casillasVacias = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == ' ') {
                    casillasVacias.add(new int[]{i, j});
                }
            }
        }

        if (!casillasVacias.isEmpty()) {
            int[] jugada = casillasVacias.remove(random.nextInt(casillasVacias.size()));
            tablero.colocarFicha(jugada[1], jugada[0], ficha);
            System.out.println("Agente juega aleatoriamente en (" + jugada[0] + ", " + jugada[1] + ")");
        }
    }

}
