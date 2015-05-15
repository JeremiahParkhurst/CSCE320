package gomoku;

/**
 * The AI player mimics a real player and prioritizes cells depending on
 * the assigned number. The AI will implement a hybrid level of player, 
 * utilizing both offensive and defensive moves.
 */
public class AIPlayer {
    private int[][] board, priorityBoard;
    private int dif;
    
    /**
     * Instantiates the AI based on the difficulty level passed in from the
     * DifficultyViewController.
     * @param difficulty, the difficulty Level
     */
    public AIPlayer(int difficulty){
        this.dif = difficulty;
    }
    
    /**
     * Returns a move that is best fitted to the current board
     * @param currentBoard a 2-d array representing the current board
     * @return an array of size 2 containing the row and column of the best move available
     */
    public int[] getMove(int[][] currentBoard){
        this.board = currentBoard;
        this.priorityBoard = new int[board.length][board.length];
        
        for(int i = 0; i < priorityBoard.length; i++){
            for(int j = 0; j < priorityBoard.length; j++){
                priorityBoard[i][j] = 0;
            }
        }
        setValues();
        return findMove();
    }
    
    /**
     * checks the priority board and finds the highest priority move
     * @return an array containing the [row,col]
     */
    private int[] findMove(){
        int maxValue = 0;
        int maxRow = 0;
        int maxCol = 0;
        
        for(int i = 0; i < priorityBoard.length; i++){
            for(int j = 0; j < priorityBoard.length; j++){
                if(maxValue < priorityBoard[i][j]){
                    maxValue = priorityBoard[i][j];
                    maxRow = i;
                    maxCol = j;
                }
            }
        }
        
        int[] returnValue = new int[2];
        returnValue[0] = maxRow;
        returnValue[1] = maxCol;
        System.out.println("Row: " + maxRow + " Column: " + maxCol);
        return returnValue;
    }
    
    /**
     * Sets the values on the priority board to select the best move
     */
    private void setValues(){
        int numberConnected1 = 0;
        int numberConnected2 = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                numberConnected1 = 0;
                numberConnected2 = 0;
                if(board[i][j] != 0){
                    numberConnected1 = 0;
                    numberConnected2 = 0;
                }
                else{
                    if(numberConnected1 < northSouth(i,j,2)){
                        numberConnected1 = northSouth(i,j,2);
                    }
                    else if(numberConnected1 < westEast(i,j,2)){
                        numberConnected1 = westEast(i,j,2);
                    }
                    else if(numberConnected1 < northWestSouthEast(i,j,2)){
                        numberConnected1 = northWestSouthEast(i,j,2);
                    }
                    else if(numberConnected1 < northEastSouthWest(i,j,2)){
                        numberConnected1 = northEastSouthWest(i,j,2);
                    }
                    
                    if(numberConnected2 < northSouth(i,j,3)){
                        numberConnected2 = northSouth(i,j,3);
                    }
                    else if(numberConnected2 < westEast(i,j,3)){
                        numberConnected2 = westEast(i,j,3);
                    }
                    else if(numberConnected2 < northWestSouthEast(i,j,3)){
                        numberConnected2 = northWestSouthEast(i,j,3);
                    }
                    else if(numberConnected2 < northEastSouthWest(i,j,3)){
                        numberConnected2 = northEastSouthWest(i,j,3);
                    }

                }
                if(numberConnected1 >= numberConnected2){
                    priorityBoard[i][j] = numberConnected1;
                }
                else{
                    priorityBoard[i][j] = numberConnected2;
                }         
            }
        }
    }
    
    /**
     * Counts the number of a color in a vertical line going up
     * @param row the starting row to look at
     * @param col the starting col to look at
     * @param lookingFor the color to look for
     * @return the number of spaces occupied by the color you are looking for
     */
    private int toNorth(int row, int col, int lookingFor){
        int count = 0;
        while(row > 0){
            if(board[row-1][col] == lookingFor){
                count++;
                row--;
            }
            else{
                break;
            }
        }
        return count;
    }
    
    /**
     * Counts the number of a color in a vertical line going down
     * @param row the starting row to look at
     * @param col the starting col to look at
     * @param lookingFor the color to look for
     * @return the number of spaces occupied by the color you are looking for
     */
    private int toSouth(int row, int col, int lookingFor){        
        int count = 0;
        while(row < board.length-1){
            if(board[row+1][col] == lookingFor){
                count++;
                row++;
            }
            else{
                break;
            }
        }
        return count;
    }
    
    /**
     * Counts the number of a color in a horizontal line going left
     * @param row the starting row to look at
     * @param col the starting col to look at
     * @param lookingFor the color to look for
     * @return the number of spaces occupied by the color you are looking for
     */
    private int toWest(int row, int col, int lookingFor){
        int count = 0;
        while(col > 0){
            if(board[row][col-1] == lookingFor){
                count++;
                col--;
            }
            else{
                break;
            }
        }
        return count;
    }
    
    /**
     * Counts the number of a color in a horizontal line going right
     * @param row the starting row to look at
     * @param col the starting col to look at
     * @param lookingFor the color to look for
     * @return the number of spaces occupied by the color you are looking for
     */
    private int toEast(int row, int col, int lookingFor){
        int count = 0;
        while(col < board.length-1){
            if(board[row][col+1] == lookingFor){
                count++;
                col++;
            }
            else{
                break;
            }
        }
        return count;
    }
    
    /**
     * Counts the number of a color in a diagonal line going up and to the left
     * @param row the starting row to look at
     * @param col the starting col to look at
     * @param lookingFor the color to look for
     * @return the number of spaces occupied by the color you are looking for
     */
    private int toNorthWest(int row, int col, int lookingFor){
        int count = 0;
        while(col > 0 && row > 0){
            if(board[row-1][col-1] == lookingFor){
                count++;
                col--;
                row--;
            }
            else{
                break;
            }
        }
        return count;
    }
    
    /**
     * Counts the number of a color in a diagonal line going down and to the right
     * @param row the starting row to look at
     * @param col the starting col to look at
     * @param lookingFor the color to look for
     * @return the number of spaces occupied by the color you are looking for
     */
    private int toSouthEast(int row, int col, int lookingFor){
        int count = 0;
        while(col < board.length -1 && row < board.length-1){
            if(board[row+1][col+1] == lookingFor){
                count++;
                col++;
                row++;
            }
            else{
                break;
            }
        }
        return count;
    }

    /**
     * Counts the number of a color in a diagonal line going up and to the right
     * @param row the starting row to look at
     * @param col the starting col to look at
     * @param lookingFor the color to look for
     * @return the number of spaces occupied by the color you are looking for
     */
    private int toNorthEast(int row, int col, int lookingFor){
        int count = 0;
        while(col < board.length-1 && row > 0){
            if(board[row-1][col+1] == lookingFor){
                count++;
                col++;
                row--;
            }
            else{
                break;
            }
        }
        return count;
    }
    
    /**
     * Counts the number of a color in a diagonal line going down and to the left
     * @param row the starting row to look at
     * @param col the starting col to look at
     * @param lookingFor the color to look for
     * @return the number of spaces occupied by the color you are looking for
     */
    private int toSouthWest(int row, int col, int lookingFor){
        int count = 0;
        while(col > 0 && row < board.length){
            if(board[row][col] == lookingFor){
                count++;
                col--;
                row++;
            }
            else{
                break;
            }
        }
        return count;
    }
    
    /**
     * add the number of spaces occupied above and below the starting space
     * @param rowStart  the starting row
     * @param colStart  the stating col 
     * @param lookingFor    the color you are looking for
     * @return the sum of the two called methods
     */
    private int northSouth(int rowStart, int colStart, int lookingFor){
        return toNorth(rowStart,colStart,lookingFor) + toSouth(rowStart,colStart,lookingFor);
    }
    
    /**
     * add the number of spaces occupied left and right of the starting space
     * @param rowStart  the starting row
     * @param colStart  the stating col 
     * @param lookingFor    the color you are looking for
     * @return the sum of the two called methods
     */
    private int westEast(int rowStart, int colStart, int lookingFor){
        return toWest(rowStart,colStart,lookingFor) + toEast(rowStart,colStart,lookingFor);
    }
    
    /**
     * add the number of spaces occupied above and to the left to the right and below the starting space
     * @param rowStart  the starting row
     * @param colStart  the stating col 
     * @param lookingFor    the color you are looking for
     * @return the sum of the two called methods
     */
    private int northWestSouthEast(int rowStart, int colStart, int lookingFor){
        return toNorthWest(rowStart,colStart,lookingFor) + toSouthEast(rowStart,colStart,lookingFor);
    }
    
    /**
     * add the number of spaces occupied above and to the right to the left and below the starting space
     * @param rowStart  the starting row
     * @param colStart  the stating col 
     * @param lookingFor    the color you are looking for
     * @return the sum of the two called methods
     */
    private int northEastSouthWest(int rowStart, int colStart, int lookingFor){
        return toNorthEast(rowStart,colStart,lookingFor) + toSouthWest(rowStart,colStart,lookingFor);
    }
}
