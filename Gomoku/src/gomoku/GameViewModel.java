package gomoku;

/**
 *
 * @author John
 */
public class GameViewModel {
    protected int row; // number of rows
    protected int column; // number of columns
    protected int[][] grid; // represents the game board
    public static final int boardColor = 0; // open board space
    public static final int yellow = 1; // tentative move piece
    public static final int black = 2; // your board piece
    public static final int white = 3;  // opponent board piece
    
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
     * Updates all yellow cells to black cells.
     * @param yellowCell, the yellow cells to be updated
     */
    public void updateYellowCells(GameViewModel yellowCell){
        for (int i=0; i<this.row; i++){
            for (int j=0; j<this.column; j++){
                if (yellowCell.grid[i][j] == yellow)
                    this.grid[i][j] = black;
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
}