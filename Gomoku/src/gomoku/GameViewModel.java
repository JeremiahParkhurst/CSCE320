package gomoku;

import gomoku.GameViewBoard.MyJButton;
import javafx.scene.paint.Color;
import javax.swing.JButton;

/**
 * The GameViewModel has a 2-dimensional primitive array which is a
 * representation of the GameViewBoard. Each cell has a number which represents
 * the cell's color. For example, the boardColor is an open space on the board,
 * the black cells are your Gomoku pieces, and the white pieces belong to the
 * opposing player. This class also sends, receives, and merges grids between
 * two players.
 */
public class GameViewModel {
    protected int row; // number of rows
    protected int column; // number of columns
    protected int[][] grid; // represents the game board
    public static final int boardColor = 0; // open board space
    public static final int yellow = 1; // tentative move piece
    public static final int black = 2; // your board piece
    public static final int white = 3;  // opponent board piece
    GameViewController gcon;
    
    /**
     * Constructor
     * Creates a GameViewModel with r rows and c columns and all values in
     * the grid are boardColor (that means its 0)
     * @param r, the number of rows
     * @param c, the number of columns
     */
    public GameViewModel(int r, int c){
        row = r;
        column = c;
        
        grid = new int[row][column];
        for (int i=0; i<row; i++){
            for (int j=0; j<column; j++){
                grid[i][j]=0;
            }
        }
    }
    
    /**
     * Copy Constructor
     * Preconditions: the copy is not null
     * This constructor will make a deep copy of the GameViewModel
     * @param copy, the GameViewModel that is to be copied
     */
    public GameViewModel(GameViewModel copy){
        this.row = copy.row;
        this.column = copy.column;
        this.grid = new int[this.row][this.column];
        for (int i=0; i<this.row; i++){
            for (int j=0; j<this.column; j++){
                this.grid[i][j]=copy.grid[i][j];
            }
        }
    }
    
    /**
     * returns the number of rows
     * @return the rows
     */
    public int getRows() {
        return row;
    }
    
    /**
     * returns the number of columns
     * @return the columns
     */
    public int getColumns() {
        return column;
    }
    
    /**
     * Helper method that changes all black cells to white cells and is used
     * in the sendGrid method.
     * @param blackCell, the black cells to be updated
     */
    public void updateBlackCells(GameViewModel blackCell){
        for (int i=0; i<this.row; i++){
            for (int j=0; j<this.column; j++){
                if (blackCell.grid[i][j] == black)
                    this.grid[i][j] = white;
            }
        }
    }
    
    /**
     * Copies an existing GameViewModel
     * @param copy, the GameViewModel to be copied
     */
    public void copyGrid(GameViewModel copy) {
        for (int i=0; i<this.row; i++){
            for (int j=0; j<this.column; j++){
                this.grid[i][j]=copy.grid[i][j];
                
            }
        }
    }
    
    /**
     * this.grid must contain an element in position [i][j] and status is a
     * legal value (i.e., between [0,3]).
     * setCell assigns grid element in row i and column j to status
     * @param i, the row
     * @param j, the column
     * @param value, the value grid[i][j] is set to
     */
    public void setCell(int i, int j, int value) {
        grid[i][j] = value;
    }
    
    /**
     * this.grid must contain an element in position [i][j] and the method
     * returns the value of the element in row i and column j
     * @param i, the row
     * @param j, the column
     * @return the value in grid[i][j]
     */
    public int getCell(int i, int j) {
        return grid[i][j];
    }
    
    /**
     * This method counts the number of black cells surrounding a given cell.
     * @param i, the row
     * @param j, the column
     * @return count, the number of surrounding black cells
     */
    public int blackNeighbors(int i, int j){
        int count = 0;
        
        if(grid[(i-1+row)%row][(j-1+column)%column] == 2)
            count++;
        if(grid[(i-1+row)%row][j] == 2)
            count++;
        if(grid[(i-1+row)%row][(j+1)%column] == 2)
            count++;
        if(grid[i][(j-1+column)%column] == 2)
            count++;
        if(grid[i][(j+1)%column] == 2)
            count++;
        if(grid[(i+1)%row][(j-1+column)%column] == 2)
            count++;
        if(grid[(i+1)%row][j] == 2)
            count++;
        if(grid[(i+1)%row][(j+1)%column] == 2)
            count++;
        
        return count;
    }
    
    /**
     * Locates and returns the position of the yellow cell
     * @param grid, the 2-dim array representation of the grid
     * @return cell, the coordinates of the yellow cell
     */
    public Cell findYellowCell(MyJButton[][] grid){
        Cell cell = null;
        for (int i=0; i<this.row; i++){
            for (int j=0; j<this.column; j++){
                if(grid[i][j].getBackground().equals(java.awt.Color.YELLOW)){
                    cell = new Cell(i,j);
                    return cell;
                }    
            }
        }
        return null;
    }
    
    /**
     * Helper method that has the coordinates of a cell
     */
    public static class Cell {
        private int row;
        private int col;
        
        /**
         * The position of the cell
         * @param r, the rows
         * @param c, the columns
         */
        public Cell(int r, int c){
            row = r;
            col = c;
        }
        
        public String toString(){
            String str = "(" + row + ", " + col + ")";
            return str;
        }
    }
}