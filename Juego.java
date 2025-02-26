import gattto.Tablero;
import gattto.players.Agente;
import gattto.players.Jugador;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class Juego {
    private JFrame frame;
    private JButton[][] buttons;
    private Tablero tablero;
    private Jugador[] jugadores;
    private int turno;
    private int size;

    public Juego() {
        tablero = new Tablero();
        jugadores = new Jugador[2];
        size = tablero.getSize();

        frame = new JFrame("Tic-tac-toe");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new GridLayout(size, size));

        buttons = new JButton[size][size];

        determinaTurnos(jugadores);

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                buttons[i][j] = new JButton(" ");
                buttons[i][j].setFont(new Font("Arial", Font.BOLD, 40));
                final int row = i, col = j;
                buttons[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        realizarJugada(row, col);
                    }
                });
                frame.add(buttons[i][j]);
            }
        }
        frame.setVisible(true);

        if (jugadores[turno] instanceof Agente) {
            ((Agente) jugadores[turno]).jugar(tablero);
            actualizarTablero();
            turno = (turno + 1) % 2;
        }
    }

    private void realizarJugada(int fila, int columna) {
        if (tablero.colocarFicha(columna, fila, jugadores[turno].getFicha())) {
            buttons[fila][columna].setText(String.valueOf(jugadores[turno].getFicha()));

            if (verificarFinDelJuego()) return; // Si el juego terminó, salimos

            turno = (turno + 1) % 2;

            if (jugadores[turno] instanceof Agente) {
                ((Agente) jugadores[turno]).jugar(tablero);
                actualizarTablero();

                if (verificarFinDelJuego()) return; // Verificamos si la IA terminó el juego

                turno = (turno + 1) % 2;
            }
        }
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

    private boolean verificarFinDelJuego() {
        if (tablero.hayGanador(jugadores[0].getFicha())) {
            mostrarMensaje("¡Gana el jugador 1!");
            return true;
        } else if (tablero.hayGanador(jugadores[1].getFicha())) {
            mostrarMensaje("¡Gana el jugador 2!");
            return true;
        } else if (tablero.tableroLleno()) {
            mostrarMensaje("¡Empate!");
            return true;
        }
        return false;
    }
    
    private void actualizarTablero() {
        char[][] board = tablero.getTablero();
        for (int i = 0; i < tablero.getSize(); i++) {
            for (int j = 0; j < tablero.getSize(); j++) {
                buttons[i][j].setText(String.valueOf(board[i][j]));
            }
        }
    }

    private void mostrarMensaje(String mensaje) {
        JOptionPane.showMessageDialog(frame, mensaje);
        frame.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Juego::new);
    }
}
