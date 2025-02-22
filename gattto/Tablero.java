package gattto;



public class Tablero {

    //Colocamos 'X' y 'O'
    private char[][] tablero;

    //Aqui se modifica el tamaño del tablero
    private final int size = 3;

    public Tablero() {
        tablero = new char[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tablero[i][j] = ' ';
            }
        }
    }

    public boolean colocarFicha(int columna, int fila, char ficha) {
        if(columna < 0 || columna >= size || fila < 0 || fila >= size) { //Valida que este dentro del tamaño del tablero
            return false;
        }

        if (tablero[fila][columna] == ' ') { //Si hay espacio para colocar
            tablero[fila][columna] = ficha;
            return true;
        }
        return false;
    }

    public void mostrarTablero() {
        System.out.println("-------------");
        for (int i = 0; i < size; i++) {
            System.out.print("| ");
            for (int j = 0; j < size; j++) {
                System.out.print(tablero[i][j] + " | ");
            }
            System.out.println("\n-------------");
        }
    }


    //Esta es una opción para determinar si ya se llenó el tablero
    //La otra idea sería llevar un contador del número de jugadas según el número de casillas
    //Controlarlo en una clase de Juego por ejemplo


    public boolean tableroLleno() {
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (tablero[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }


    public boolean hayGanador(char ficha) {
        //Filas
        for (int i = 0; i < size; i++) {
            for (int j = 0; j <= size - 3; j++) {
                if (tablero[i][j] == ficha && tablero[i][j+1] == ficha && tablero[i][j+2] == ficha) {
                    return true;
                }
            }
        }

        //Columnas
        for (int j = 0; j < size; j++) {
            for (int i = 0; i <= size - 3; i++) {
                if (tablero[i][j] == ficha && tablero[i+1][j] == ficha && tablero[i+2][j] == ficha) {
                    return true;
                }
            }
        }

        //Diagonales
        for (int i = 0; i <= size - 3; i++) {
            for (int j = 0; j <= size - 3; j++) {
                if (tablero[i][j] == ficha && tablero[i+1][j+1] == ficha && tablero[i+2][j+2] == ficha) {
                    return true;
                }
            }
        }

        for (int i = 0; i <= size - 3; i++) {
            for (int j = 2; j < size; j++) {
                if (tablero[i][j] == ficha && tablero[i+1][j-1] == ficha && tablero[i+2][j-2] == ficha) {
                    return true;
                }
            }
        }
        
        
        return false;
    }

    public char[][] getTablero() {
        return tablero;
    }
}
