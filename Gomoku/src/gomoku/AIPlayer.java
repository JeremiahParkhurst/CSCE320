/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;
/**
 *
 * @author Brendan
 */
public class AIPlayer {
    private int[][] board, priorityBoard;
    private int dif;
    
    public AIPlayer(int difficulty){
        this.dif = difficulty;
    }
    
    /**
     * returns a move that is best fitted to the current board
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
    
    private int northSouth(int rowStart, int colStart, int lookingFor){
        return toNorth(rowStart,colStart,lookingFor) + toSouth(rowStart,colStart,lookingFor);
    }
    
    private int westEast(int rowStart, int colStart, int lookingFor){
        return toWest(rowStart,colStart,lookingFor) + toEast(rowStart,colStart,lookingFor);
    }
    
    private int northWestSouthEast(int rowStart, int colStart, int lookingFor){
        return toNorthWest(rowStart,colStart,lookingFor) + toSouthEast(rowStart,colStart,lookingFor);
    }
    
    private int northEastSouthWest(int rowStart, int colStart, int lookingFor){
        return toNorthEast(rowStart,colStart,lookingFor) + toSouthWest(rowStart,colStart,lookingFor);
    }
}
