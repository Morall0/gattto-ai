package gattto.players;



import gattto.Tablero;

//Esta es la version que busca jugar la mejor jugada
//Implementa el algoritmo minimax


import java.util.HashMap;
import java.util.Map;


public class Agente extends Jugador { // Suponiendo que extiendes de alguna clase base que maneja la ficha
    private char fichaContraria;
    
    private Map<String, Integer> cache; // Para memoización

    public Agente(char ficha) {
        super(ficha);
        this.fichaContraria = (ficha == 'x') ? 'o' : 'x';
    }

    public void jugar(Tablero tablero, int turno) {
        // Obtener el estado actual del tablero y su tamaño
        char[][] board = tablero.getTablero();
        int size = board.length;
        boolean vacio = true;

        // Verificar si el tablero está vacío (primera jugada)
        for (int i = 0; i < size && vacio; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] != ' ') {
                    vacio = false;
                    break;
                }
            }
        }
        if (vacio) {
            // Jugar en el centro si está libre
            tablero.colocarFicha(1, 1, ficha);
            return;
        }

        // Inicializar la cache para memoización
        cache = new HashMap<>();

        int mejorJugada = Integer.MIN_VALUE;
        int mejorFila = -1;
        int mejorColumna = -1;

        // Recorrer todas las celdas vacías y aplicar minimax con poda alfa-beta
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = ficha;
                    int valor = minimax(board, 0, false, size, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    board[i][j] = ' '; // Deshacer el movimiento

                    if (valor > mejorJugada) {
                        mejorJugada = valor;
                        mejorFila = i;
                        mejorColumna = j;
                    }
                }
            }
        }

        tablero.colocarFicha(mejorFila, mejorColumna, ficha);
        System.out.println("Agente juega en (" + mejorFila + ", " + mejorColumna + ")");
    }

    /**
     * Función minimax optimizada con poda alfa-beta y memoización.
     *
     * @param board      Estado actual del tablero.
     * @param profundidad Profundidad actual de la recursión.
     * @param esMax      Indica si se busca maximizar o minimizar.
     * @param size       Tamaño del tablero.
     * @param alpha      Valor alfa para poda.
     * @param beta       Valor beta para poda.
     * @return Valor evaluado para el estado actual.
     */
    private int minimax(char[][] board, int profundidad, boolean esMax, int size, int alpha, int beta) {
        // Condiciones terminales: victoria, derrota o empate
        if (hayGanador(board, ficha, size)) {
            return 10 - profundidad;
        }
        if (hayGanador(board, fichaContraria, size)) {
            return profundidad - 10;
        }
        if (tableroLleno(board, size)) {
            return 0;
        }

        // Crear una clave única para el estado actual (incluye quién juega y la profundidad)
        String key = boardToString(board) + "_" + (esMax ? "max" : "min") + "_" + profundidad;
        if (cache.containsKey(key)) {
            return cache.get(key);
        }

        int mejorValor;
        if (esMax) {
            mejorValor = Integer.MIN_VALUE;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = ficha;
                        int valor = minimax(board, profundidad + 1, false, size, alpha, beta);
                        board[i][j] = ' ';
                        mejorValor = Math.max(mejorValor, valor);
                        alpha = Math.max(alpha, mejorValor);
                        if (beta <= alpha) {
                            break; // Poda alfa-beta
                        }
                    }
                }
            }
        } else {
            mejorValor = Integer.MAX_VALUE;
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = fichaContraria;
                        int valor = minimax(board, profundidad + 1, true, size, alpha, beta);
                        board[i][j] = ' ';
                        mejorValor = Math.min(mejorValor, valor);
                        beta = Math.min(beta, mejorValor);
                        if (beta <= alpha) {
                            break; // Poda alfa-beta
                        }
                    }
                }
            }
        }

        cache.put(key, mejorValor); // Guardar el resultado en la cache
        return mejorValor;
    }

    /**
     * Convierte el estado del tablero a una cadena única.
     *
     * @param board Estado actual del tablero.
     * @return Representación en cadena del tablero.
     */
    private String boardToString(char[][] board) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                sb.append(board[i][j]);
            }
        }
        return sb.toString();
    }

    /**
     * Verifica si hay un ganador en el tablero para la ficha dada.
     *
     * @param board Estado actual del tablero.
     * @param ficha Ficha a verificar.
     * @param size  Tamaño del tablero.
     * @return true si se encuentra una secuencia ganadora; false de lo contrario.
     */
    private boolean hayGanador(char[][] board, char ficha, int size) {
        // Secuencias horizontales
        for (int i = 0; i < size; i++) {
            for (int j = 0; j <= size - 3; j++) {
                if (board[i][j] == ficha &&
                    board[i][j + 1] == ficha &&
                    board[i][j + 2] == ficha) {
                    return true;
                }
            }
        }
        // Secuencias verticales
        for (int j = 0; j < size; j++) {
            for (int i = 0; i <= size - 3; i++) {
                if (board[i][j] == ficha &&
                    board[i + 1][j] == ficha &&
                    board[i + 2][j] == ficha) {
                    return true;
                }
            }
        }
        // Secuencias diagonales (de izquierda a derecha)
        for (int i = 0; i <= size - 3; i++) {
            for (int j = 0; j <= size - 3; j++) {
                if (board[i][j] == ficha &&
                    board[i + 1][j + 1] == ficha &&
                    board[i + 2][j + 2] == ficha) {
                    return true;
                }
            }
        }
        // Secuencias diagonales (de derecha a izquierda)
        for (int i = 0; i <= size - 3; i++) {
            for (int j = 2; j < size; j++) {
                if (board[i][j] == ficha &&
                    board[i + 1][j - 1] == ficha &&
                    board[i + 2][j - 2] == ficha) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Verifica si el tablero está lleno.
     *
     * @param board Estado actual del tablero.
     * @param size  Tamaño del tablero.
     * @return true si no hay celdas vacías; false de lo contrario.
     */
    private boolean tableroLleno(char[][] board, int size) {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }
}