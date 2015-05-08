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
 *
 * @author John
 */
public class GameViewBoard extends JPanel{
    private int row, col;
    private MyJButton[][] square;
    private GameViewController gcon;
    private int boardSize = 20;
    private Color boardColor = new Color(204,204,255);
    private final int MAX_COUNT = 1; //maximum number of moves
    private int count = 0; //current number of moves
    
    public GameViewBoard(GameViewController con){
        gcon = con;
        row = boardSize;
        col = boardSize;
    
        //set up grid in for the center panel
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
     * changes cell color indicating the cell has a gomoku piece
     * @param i
     * @param j 
     */ 
    private void updateCell(int i, int j){
         
     }
    
    /**
     * sends grid to opponent vice versa
     */
    public void updateGrid() {
        
    }
    
    /**
     * The actionPerformed method is called each time a button
     * in the center panel is selected. It allows the user to
     * select MAX_COUNT	WHITE cells to inoculate (set to GREEN)
     *
     */
    private class SquareListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e) {
            MyJButton button = (MyJButton) e.getSource();
            
            //deselect (check needed to deselect previous legal moves)
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
     * The actionPerformed method is called each time the step
     * button in the north panel is selected. The user must have
     * select MAX_COUNT	WHITE cells to inoculate (set to GREEN)
     *
     */
    private class ButtonListener implements ActionListener{
        
        public void actionPerformed(ActionEvent e) {
            
        }    
    }
    
    /**
     * This is a private helper class the extends
     * JButton to include its (i,j) location in the
     * center panel.
     */
    private static class MyJButton extends JButton{
        private int i;
        private int j;
    }
}
