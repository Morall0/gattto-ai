package gattto.players;



import gattto.Tablero;

//Esta es la version que busca jugar la mejor jugada
//Implementa el algoritmo minimax


public class Agente extends Jugador {

      
    private char fichaContraria;   
  

    public Agente(char ficha) {
        
        super(ficha);
        this.fichaContraria = (ficha == 'x') ? 'o' : 'x';
  
    }   

    
    

    

    

    public void jugar(Tablero tablero, int turno) {
        char[][] board = tablero.getTablero();
        int size = board.length;
        boolean vacio = true;

        int mejorJugada = Integer.MIN_VALUE;
        int mejorFila = -1;
        int mejorColumna = -1;

        //Revisar si es la primera jugada
        //Tirar en el centro
        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(board[i][j] != ' '){
                    vacio = false;
                    break;
                }
            }
        }

        if(vacio){
            tablero.colocarFicha(1, 1, ficha);
            return;
        }

        //Recorrer el tablero
        //Buscamos movimientos validos
        

        for(int i = 0; i < size; i++){
            for(int j = 0; j < size; j++){
                if(board[i][j] == ' '){
                    board[i][j] = ficha;
                    int valor = minimax(board, 0, false, size);
                    board[i][j] = ' ';

                    if(valor > mejorJugada){
                        mejorJugada = valor;
                        mejorFila = i;
                        mejorColumna = j;
                    }
                }
            }
        }

        tablero.colocarFicha(mejorColumna, mejorFila, ficha);


    
        

        
        
    }

    private int minimax( char[][] board, int profundidad, boolean esMejor, int size) {
        //Gana agente
        if(hayGanador(board, ficha, size)){
            return 10 - profundidad;
        }

        //Gana jugador
        if(hayGanador(board, fichaContraria, size)){
            return profundidad - 10;
        }

        if(tableroLleno(board, size)){
            return 0;
        }

        int mejorJugada;

        if(esMejor){
            mejorJugada = Integer.MIN_VALUE;
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    if(board[i][j] == ' '){
                        board[i][j] = ficha;
                        mejorJugada = Math.max(mejorJugada, minimax(board, profundidad + 1, !esMejor, size));
                        board[i][j] = ' ';
                    }
                }
            }
        } else {
            mejorJugada = Integer.MAX_VALUE;
            for(int i = 0; i < size; i++){
                for(int j = 0; j < size; j++){
                    if(board[i][j] == ' '){
                        board[i][j] = fichaContraria;
                        mejorJugada = Math.min(mejorJugada, minimax(board, profundidad + 1, !esMejor, size));
                        board[i][j] = ' ';
                    }
                }
            }
        }

        return mejorJugada;
    }

    private boolean hayGanador(char[][] tablero, char ficha, int size){
         // Secuencias horizontales
         for (int i = 0; i < size; i++) {
            for (int j = 0; j <= size - 3; j++) {
                if (tablero[i][j] == ficha &&
                    tablero[i][j+1] == ficha &&
                    tablero[i][j+2] == ficha) {
                    return true;
                }
            }
        }

        // Secuencias verticales
        for (int j = 0; j < size; j++) {
            for (int i = 0; i <= size - 3; i++) {
                if (tablero[i][j] == ficha &&
                    tablero[i+1][j] == ficha &&
                    tablero[i+2][j] == ficha) {
                    return true;
                }
            }
        }

        // Secuencias diagonales (de izquierda a derecha)
        for (int i = 0; i <= size - 3; i++) {
            for (int j = 0; j <= size - 3; j++) {
                if (tablero[i][j] == ficha &&
                    tablero[i+1][j+1] == ficha &&
                    tablero[i+2][j+2] == ficha) {
                    return true;
                }
            }
        }

        // Secuencias diagonales (de derecha a izquierda)
        for (int i = 0; i <= size - 3; i++) {
            for (int j = 2; j < size; j++) {
                if (tablero[i][j] == ficha &&
                    tablero[i+1][j-1] == ficha &&
                    tablero[i+2][j-2] == ficha) {
                    return true;
                }
            }
        }

        return false;
    }

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
