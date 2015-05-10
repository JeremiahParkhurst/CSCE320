package gomoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * The GameViewBoard displays the Gomoku game board that consists of JButtons
 * that will change color depending on the player's input.
 */
public class GameViewBoard extends JPanel{
    private int row, col;
    private MyJButton[][] square;
    private GameViewController gcon;
    private int boardSize = 20;
    private Color boardColor = new Color(204,204,255);
    private final int MAX_COUNT = 1; //maximum number of moves
    private int count = 0; //current number of moves
    
    /**
     * Constructor, initializes the GameViewBoard
     * @param con, the GameViewController
     */
    public GameViewBoard(GameViewController con){
        gcon = con;
        row = boardSize;
        col = boardSize;
        
        //Sets up grid
        this.setLayout(new GridLayout(row,col));
        square = new MyJButton[row][col];
        SquareListener listener = new SquareListener();
        for(int i =0; i<row; i++) {
            for (int j=0; j<col; j++){
                square[i][j]= new MyJButton();
                square[i][j].i=i;
                square[i][j].j=j;
                square[i][j].setSize(boardSize,boardSize);
                square[i][j].setBackground(boardColor);
                square[i][j].addActionListener(listener);
                this.add(square[i][j]);
            }
        }
    }
    
    /**
     * Changes cell color indicating the cell has a gomoku piece
     * @param i
     * @param j
     */
    private void updateCell(int i, int j){
        // TODO add your handling code here:
    }
    
    /**
     * Updates the board, sends the grid to opponent, vice versa
     */
    public void updateGrid() {
        // TODO add your handling code here:
    }
    
    /**
     * The actionPerformed method is called each time a button
     * on the GameViewBoard selected. It allows the user to select 
     * MAX_COUNT greater or equal to 1 boardColor cells to send to 
     * the opposing player.
     * 
     * Cell Color Definitions:
     * boardColor = empty cell (valid location regardless)
     * black = taken cell (by this player)
     * white = taken cell (by the opposing player)
     * yellow = tentative cell (indicates the player has chosen this cell
     * this turn)
     */
    private class SquareListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e) {
            MyJButton button = (MyJButton) e.getSource();
            
            //deselects (check needed to deselect previous legal moves)
            if(button.getBackground().equals(Color.YELLOW)){
                button.setBackground(boardColor);
                count = 0;
                gcon.clearStatus();
                gcon.moveDeselected();
            }
            
            //if the user makes a move that is not an open space
            else if(button.getBackground().equals(Color.WHITE) || button.getBackground().equals(Color.BLACK)){
                gcon.clearStatus();
                gcon.invalidMoveMsg();
            }
            //the move is successful (no cell is not marked by a player)
            else if(MAX_COUNT != count){
                button.setBackground(Color.YELLOW);
                gcon.clearStatus();
                gcon.validMoveMsg();
                count++; //reset count on your next turn
            }
        }
    }
    
    /**
     * The actionPerformed method is called each time the sendMove
     * button is selected. The user must have select MAX_COUNT = 1
     * in order to send the move. (MAX_COUNT indicates the number of moves
     * they have made this turn.
     */
    private class ButtonListener implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            // TODO add your handling code here:
        }
    }
    
    /**
     * This is a private helper class the extends JButton to include
     * its (i,j) location in the center panel.
     */
    private static class MyJButton extends JButton{
        private int i; //the row
        private int j; //the column
    }
}
