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
        this.priorityBoard = currentBoard;
        
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
        
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                if(maxValue < board[i][j]){
                    maxValue = board[i][j];
                    maxRow = i;
                    maxCol = j;
                }
            }
        }
        
        int[] returnValue = new int[2];
        returnValue[0] = maxRow;
        returnValue[1] = maxCol;
        return returnValue;
    }
    
    private void setValues(){
        int numberConnected = 0;
        for(int i = 0; i < board.length; i++){
            for(int j = 0; j < board.length; j++){
                numberConnected = 0;
                if(board[i][j] != 0){
                    numberConnected = 0;
                }
                else{
                    numberConnected += northSouth(i,j,2);
                    numberConnected += westEast(i,j,2);
                    numberConnected += northWestSouthEast(i,j,2);
                    numberConnected += northEastSouthWest(i,j,2);
                    
                    numberConnected += northSouth(i,j,3);
                    numberConnected += westEast(i,j,3);
                    numberConnected += northWestSouthEast(i,j,3);
                    numberConnected += northEastSouthWest(i,j,3);
                }
                priorityBoard[i][j] = numberConnected;
            }
        }
    }
    
    private int toNorth(int row, int col, int lookingFor){
        int count = 0;
        while(row >= 0 && board[row][col] == lookingFor){
            count++;
            row--;
        }
        return count;
    }
    
    private int toSouth(int row, int col, int lookingFor){
        int count = 0;
        while(row < board.length && board[row][col] == lookingFor){
            count++;
            row++;
        }
        return count;
    }
    
    private int toWest(int row, int col, int lookingFor){
        int count = 0;
        while(col >= 0 && board[row][col] == lookingFor){
            count++;
            col--;
        }
        return count;
    }
    
    private int toEast(int row, int col, int lookingFor){
        int count = 0;
        while(col < board.length && board[row][col] == lookingFor){
            count++;
            col++;
        }
        return count;
    }
    
    private int toNorthWest(int row, int col, int lookingFor){
        int count = 0;
        while(col >= 0 && row >= 0 && board[row][col] == lookingFor){
            count++;
            col--;
            row--;
        }
        return count;
    }
    
    private int toSouthEast(int row, int col, int lookingFor){
        int count = 0;
        while(col < board.length && row < board.length && board[row][col] == lookingFor){
            count++;
            col++;
            row++;
        }
        return count;
    }
    
    private int toNorthEast(int row, int col, int lookingFor){
        int count = 0;
        while(col < board.length && row >= 0 && board[row][col] == lookingFor){
            count++;
            col++;
            row--;
        }
        return count;
    }
    
    private int toSouthWest(int row, int col, int lookingFor){
        int count = 0;
        while(col >= 0 && row < board.length && board[row][col] == lookingFor){
            count++;
            col--;
            row++;
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
