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
 * with reference to the row and column and changes color depending on the 
 * player's input. The boardSize is passed in from the GameView.
 */
public class GameViewBoard extends JPanel{
    private int row, col;
    public MyJButton[][] square;
    private GameViewModel current;
    private GameViewController gcon;
    public int boardSize = 20;
    public Color boardColor = new Color(204,204,255);
    private final int MAX_COUNT = 1; //maximum number of moves
    public int count = 0; //current number of moves
    
    /**
     * Constructor, initializes the GameViewBoard and sets up the Gomoku board.
     * @param con, the GameViewController
     */
    public GameViewBoard(GameViewController con, int size){
        gcon = con;
        boardSize = size;
        row = boardSize;
        col = boardSize;
        
        current = new GameViewModel(boardSize, boardSize);
        
        //Sets up grid and assigns each button an actionListener
        this.setLayout(new GridLayout(row,col));
        square = new MyJButton[row][col];
        SquareListener listener = new SquareListener();
        for(int i =0; i<row; i++) {
            for (int j=0; j<col; j++){
                square[i][j]= new MyJButton(i,j);
                square[i][j].setSize(boardSize,boardSize);
                square[i][j].setBackground(boardColor);
                square[i][j].addActionListener(listener);
                this.add(square[i][j]);
            }
        }
    }
    
    /**
     * Updates the location of a given cell to white
     * @param i, the row
     * @param j, the column
     */
    public void updateMyGrid(int i, int j){
        square[i][j].setBackground(Color.WHITE);
    }
    
    /**
     * Updates the location of the yellow cell to black
     * @param i, the row
     * @param j, the column
     */
    public void updateYellowSquare(int i, int j){
        square[i][j].setBackground(Color.BLACK);
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
                gcon.moveDeselected();
            }
            
            //if the user makes a move that is not an open space
            else if(button.getBackground().equals(Color.WHITE) || button.getBackground().equals(Color.BLACK)){
                gcon.invalidMoveMsg();
            }
            //the move is successful (no cell is not marked by a player)
            else if(MAX_COUNT != count){
                button.setBackground(Color.YELLOW);
                gcon.validMoveMsg();
                count++; 
            }
        }
    }
    
    /**
     * This is a private helper class the extends JButton to include
     * its (i,j) location in the center panel.
     */
    public static class MyJButton extends JButton{
        public int i; //the row
        public int j; //the column
        
        /**
         * JButton with a reference to the row and column.
         * @param row, the row
         * @param column, the column
         */
        public MyJButton(int row, int column){
            i = row;
            j = column;
        }
        
        /**
         * Public helper method to find the background color in String form.
         * @return the String representation of the background color
         */
        public String getColor(){
            return getBackground().toString();
        }
    }
}