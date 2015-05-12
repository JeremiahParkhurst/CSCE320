/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Brendan
 */
public class GameEngine {
    private int[][] boardModel; //0 = blank, 1 = player1, 2 = player2;
    private final int SIZE;
    private Player player1,player2;
    private int turn = 0;
    
    
    public GameEngine(Player p1, Player p2, int boardSize){
        SIZE = boardSize;
        boardModel = new int[SIZE][SIZE];
        player1 = p1;
        player2 = p2;
        for(int i = 0; i < SIZE; i++){
            for(int j = 0; j < SIZE; j++){
                boardModel[i][j] = 0;
            }
        }
    }
    
    private void playGame(){
        boolean over = false;
        while(!over){
            switch(turn%2){
                case 0:
                    makeMove(1,player1.getMove());
                    break;
                case 1:
                    makeMove(2,player2.getMove());
                    break;
                   
                    
                
            }
            if(gameOver() != -1){
                over = true;
            }
            turn++;
        }
    }
    
    private void makeMove(int player, int rowCol[]){
        boardModel[rowCol[0]][rowCol[1]] = player;
    }
    
    /**
     * Checks the board for a winner
     * @return The players number if there is a winner or -1 for no winner
     */
    private int gameOver(){
        for(int color = 1; color <= 2; color++){
            for(int i = 0; i < SIZE; i++){
                for(int j = 0; j < SIZE; j++){
                    if(toTheEast(color,i,j,0) == 5){
                        return color;
                    }
                    else if(toTheSouthEast(color,i,j,0) == 5){
                        return color;
                    }
                    else if(toTheSouth(color,i,j,0) == 5){
                        return color;
                    }
                    else if(toTheSouthWest(color,i,j,0) == 5){
                        return color;
                    }
                }
            }
        }
        
        return -1;
        
    }
    
    private int toTheEast(int colorInQuestion, int row, int col, int count){
        if(count == 5){
            return count;
        }
        else if(col == SIZE){
            return count;
        }
        else if(boardModel[row][col] != colorInQuestion){
           return count;
        }
        else{
            count++;
            return toTheEast(colorInQuestion,row,col+1,count);
        }
    }
    
    private int toTheSouthEast(int colorInQuestion, int row, int col, int count){
        if(count == 5){
            return count;
        }
        else if(col == SIZE || row == SIZE){
            return count;
        }
        else if(boardModel[row][col] != colorInQuestion){
           return count;
        }
        else{
            count++;
            return toTheEast(colorInQuestion,row +1,col+1,count);
        }
    }
    
    private int toTheSouth(int colorInQuestion, int row, int col, int count){
        if(count == 5){
            return count;
        }
        else if(row == SIZE){
            return count;
        }
        else if(boardModel[row][col] != colorInQuestion){
           return count;
        }
        else{
            count++;
            return toTheEast(colorInQuestion,row+1,col,count);
        }
    }
    
    private int toTheSouthWest(int colorInQuestion, int row, int col, int count){
        if(count == 5){
            return count;
        }
        else if(col == -1 || row == SIZE){
            return count;
        }
        else if(boardModel[row][col] != colorInQuestion){
           return count;
        }
        else{
            count++;
            return toTheEast(colorInQuestion,row+1,col-1,count);
        }
    }
    
    
}
