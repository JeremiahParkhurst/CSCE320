package gomoku;

import gomoku.GameViewBoard.MyJButton;
import gomoku.GameViewModel.Cell;
import java.awt.BorderLayout;
import java.awt.Color;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import javax.swing.JFrame;

/**
 * The GameViewController handles and listens to all actions preformed
 * from the GameView. Such as chat messages between players, and messages
 * regarding their selected moves.
 * Creates a peer-to-peer connection between two players.
 *
 * NOTE: NOT FULLY IMPLEMENTED
 */
public class GameViewController implements Runnable{
    private GameView view;
    private GameViewBoard view2;
    private GameViewModel gmod;
    private WinLossPopupView winloss;
    private SignInViewController vc;
    private DifficultyViewController vcon;
    private JFrame app;
    private JFrame WLView;
    public ServerSocket serverSocket;
    public Socket socket;
    private Thread t;
    private InputStream in;
    private OutputStream out;
    private final int SIZE = 1024;
    private byte[] buffer;
    private boolean hasServerSocket;
    private NetworkedPlayer p2;
    public boolean connected = false;
    
    /**
     * Constructor, initializes the GameViewController
     * @param vc, the DifficultyViewController that directs to the
     * GameViewController
     * @param difficulty, the difficulty of the AI
     */
    public GameViewController(DifficultyViewController vc, int difficulty){
        view = new GameView(this);
        view2 = new GameViewBoard(this);
        gmod = new GameViewModel(view2.boardSize, view2.boardSize);
        app = new JFrame("Gomoku");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(view, BorderLayout.EAST);
        app.add(view2, BorderLayout.WEST);
        app.pack();
        app.setResizable(false);
        app.setBackground(new Color(204,204,255));
        vcon = vc;
    }
    
    /**
     * Creates the JFrame to hold the GameView (the chat JPanel) and the
     * GameViewBoard (the game board JPanel).
     * Attempts to create a serverSocket
     * Starts the thread
     * This Constructor
     */
    public GameViewController(String username, SignInViewController svc){
        view = new GameView(this);
        view2 = new GameViewBoard(this);
        gmod = new GameViewModel(view2.boardSize, view2.boardSize);
        app = new JFrame(username + "'s Game View");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(view, BorderLayout.EAST);
        app.add(view2, BorderLayout.WEST);
        app.pack();
        app.setBackground(new Color(204,204,255));
        buffer = new byte[SIZE];
        vc = svc;
        
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("Server Socket open");
            disableTurn();
            view2.count = 1;
        } catch (IOException ex) {
            ex.printStackTrace();
            System.out.println("Could not create port.");
        }
        
        starter();
        view.displayUserName(username);
    }
    
    /**
     * Receives the IP of the other players server
     * Creates a peer-to-peer connection using a ServerSocket
     * Creates the JFrame to hold the GameView (the chat JPanel) and the
     * GameViewBoard (the game board JPanel).
     * This Constructor will send their IP and username and attempt to connect
     * to the previous constructor that has created the 
     * @param IP
     * @param username
     */
    public GameViewController(String IP, String username, SignInViewController svc){
        view = new GameView(this);
        view2 = new GameViewBoard(this);
        gmod = new GameViewModel(view2.boardSize, view2.boardSize);
        app = new JFrame(username + "'s Game View");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(view, BorderLayout.EAST);
        app.add(view2, BorderLayout.WEST);
        app.pack();
        app.setBackground(new Color(204,204,255));
        buffer = new byte[SIZE];
        vc = svc;
        
        try {
            socket = new Socket(IP,8080);
            in = socket.getInputStream();
            out = socket.getOutputStream();
            
            System.out.println("IP now:" + IP);
            
            p2 = new NetworkedPlayer(socket, this);
            in = socket.getInputStream();
            out = socket.getOutputStream();
            p2.starter();
            enableTurn();
        } catch (IOException ex) {
            System.out.println("Could not connect to other player");
        }
        view.displayUserName(username);
    }
    
    /**
     * Displays the GameViewController
     */
    public void showView() {
        app.setVisible(true);
    }
    
    public void sendMsg(String msg){
        byte[] b = msg.getBytes();
        try{
            out.write(b);
        }
        catch (IOException ex) {
            System.out.println("Could not send message.");
        }
    }
    
    /**
     * Hides the GameViewController
     */
    public void hideView() {
        app.setVisible(false);
        WLView.setVisible(false);
    }
    
    /**
     * Attempts to creates a peer-to-peer connection by creating a serverSocket to
     * establish and input and output stream between the two players and
     * initializes the thread.
     */
    public void run() {
        //connected = false;
        while(!connected){
            System.out.println("Waiting for connection");
            try{
                socket = serverSocket.accept();
                System.out.println("Connection to other player made.");
                p2 = new NetworkedPlayer(socket, this);
                in = socket.getInputStream();
                out = socket.getOutputStream();
                p2.starter();
                connected = true;
            }
            catch(IOException ex){
                System.out.println("Error in GameViewController run");
            }
        }
    }
    
    /**
     * Starts the thread
     */
    public void starter(){
        t = new Thread(this);
        t.start();
    }
    
    /**
     * Appends "OPPONENT:" and their message
     * @param msg, the message to be sent
     */
    public void appendGameViewChat(String msg){
        view.appendChat("OPPONENT: " + msg);
    }
    
    /**
     * Displays the loss menu when the player loses.
     */
    public void showLoss(){
        winloss = new WinLossPopupView(this,vc);
        WLView = new JFrame("End Game");
        WLView.add(winloss);
        WLView.pack();
        WLView.setResizable(false);
        WLView.setVisible(true);
        winloss.winLabel.setVisible(false);
    }
    
    /**
     * Display the win menu when the player wins.
     */
    public void showWin(){
        winloss = new WinLossPopupView(this,vc);
        WLView = new JFrame("End Game");
        WLView.add(winloss);
        WLView.pack();
        WLView.setResizable(false);
        WLView.setVisible(true);
        winloss.lossLabel.setVisible(false);
    }
    
    /**
     * This message is displayed in the statusTextArea when the user
     * makes an invalid move.
     */
    public void invalidMoveMsg(){
        view.appendMoveStatus("Invalid Move Location");
    }
    
    /**
     * This message is displayed in the statusTextArea when the user
     * makes a valid move.
     */
    public void validMoveMsg(){
        view.appendMoveStatus("Move Location Valid");
    }
    
    /**
     * This message is displayed in the statusTextArea when the user
     * deselects a cell that they previously highlighted.
     */
    public void moveDeselected(){
        view.appendMoveStatus("Move Deselected");
    }
    
    public void updateGrid(int r, int c){
        view2.updateMyGrid(r, c);
    }
    
    /**
     * This method is called from the GameView when sendMove is selected.
     * Checks the board for a yellow cell by the findYellowCell and notifies
     * the player if they can still make a move, otherwise call
     * updateYellowCells which will change all yellow cells to black cells.
     * Retrieves the location of the yellow cell which will be used to update
     * the opposing player's board. sendMsg will disable your board, and when
     * you receive a msg from sendMsg, it will enable your board.
     * This method will also check from the win condition via the gameOver
     * method.
     */
    
    public void sendMoveButtonChosen(){
        Cell yellowCell = gmod.findYellowCell(view2.square);
        if(yellowCell == null){
            view.appendMoveStatus("Move Availible");
        }
        else{
            String stringT = "T, " + yellowCell.toString(); // formats turn msg
            String stringW = "W, " + yellowCell.toString(); // formats win msg
            // following 6 lines format r and c to be integers for calling updateYellowSquare
            String profile = stringT.substring(4, stringT.length()-1);
            Scanner scan = new Scanner(profile).useDelimiter("\\s*,\\s*");
            String row = scan.next();
            String column = scan.next();
            int r = Integer.parseInt(row);
            int c = Integer.parseInt(column);
            view2.updateYellowSquare(r,c);
            
            if(gameOver(view2.square) == false){
                disableTurn();
                p2.sendMsg(stringT); 
            }
            else{
                showWin();
                p2.sendMsg(stringW);
            }
        }
    }
    
    /**
     * Checks the board for a winner
     * @return The players number if there is a winner or -1 for no winner
     */
    public boolean gameOver(MyJButton[][] board){
        int color = 2;
        for(int i = 0; i < view2.boardSize; i++){
            for(int j = 0; j < view2.boardSize; j++){
                if(toTheEast(color,i,j,0,board) == 5){
                    return true;
                }
                else if(toTheSouthEast(color,i,j,0,board) == 5){
                    return true;
                }
                else if(toTheSouth(color,i,j,0,board) == 5){
                    return true;
                }
                else if(toTheSouthWest(color,i,j,0,board) == 5){
                    return true;
                }
            }
        }
        return false;
    }
    
    /**
     * Enables the game board and sendMove button.
     */
    public void enableTurn(){
         view2.setEnabled(true);
         view.sendMoveButton.setEnabled(true);
         view2.count = 0;
         view.turnTextArea.setText("Your");
    }
    
    /**
     * Disable the game board and sendMove button.
     */
    public void disableTurn(){
        view2.setEnabled(false);
        view.sendMoveButton.setEnabled(false);
        view.turnTextArea.setText("Opponent");
    }
    
    /**
     * Recursive method that will search search for cells to the east.
     * @param colorInQuestion, color this method is searching for
     * @param row, the row
     * @param col, the col
     * @param count, the number of pieces
     * @param boardModel, the cell location
     * @return the number of consecutive occurrences of the colorInQuestion
     */
    private int toTheEast(int colorInQuestion, int row, int col, int count, MyJButton[][] boardModel){
        if(count == 5){
            return count;
        }
        else if(row >= view2.boardSize || col >= view2.boardSize){
            return count;
        }
        else if(!boardModel[row][col].getBackground().equals(java.awt.Color.BLACK)){
            return count;
        }
        else if(boardModel[row][col].getBackground().equals(java.awt.Color.BLACK)){
            count++;
            return toTheEast(colorInQuestion,row,col+1,count,boardModel);
        }
        else{
            return count;
        }
    }
    
    /**
     * Recursive method that will search search for cells to the south-east.
     * @param colorInQuestion, color this method is searching for
     * @param row, the row
     * @param col, the col
     * @param count, the number of pieces
     * @param boardModel, the cell location
     * @return the number of consecutive occurrences of the colorInQuestion
     */
    private int toTheSouthEast(int colorInQuestion, int row, int col, int count, MyJButton[][] boardModel){
        if(count == 5){
            return count;
        }
        else if(row >= view2.boardSize || col >= view2.boardSize){
            return count;
        }
        else if(!boardModel[row][col].getBackground().equals(java.awt.Color.BLACK)){
            return count;
        }
        else if(boardModel[row][col].getBackground().equals(java.awt.Color.BLACK)){
            count++;
            return toTheSouthEast(colorInQuestion,row +1,col+1,count,boardModel);
        }
        else{
            return count;
        }
    }
    
    /**
     * Recursive method that will search search for cells to the south.
     * @param colorInQuestion, color this method is searching for
     * @param row, the row
     * @param col, the col
     * @param count, the number of pieces
     * @param boardModel, the cell location
     * @return the number of consecutive occurrences of the colorInQuestion
     */
    private int toTheSouth(int colorInQuestion, int row, int col, int count, MyJButton[][] boardModel){
        if(count == 5){
            return count;
        }
        else if(row >= view2.boardSize || col >= view2.boardSize){
            return count;
        }
        else if(!boardModel[row][col].getBackground().equals(java.awt.Color.BLACK)){
            return count;
        }
        else if(boardModel[row][col].getBackground().equals(java.awt.Color.BLACK)){
            count++;
            return toTheSouth(colorInQuestion,row+1,col,count,boardModel);
        }
        else{
            return count;
        }
    }
    
    /**
     * Recursive method that will search search for cells to the south-west.
     * @param colorInQuestion, color this method is searching for
     * @param row, the row
     * @param col, the col
     * @param count, the number of pieces
     * @param boardModel, the cell location
     * @return the number of consecutive occurrences of the colorInQuestion
     */
    private int toTheSouthWest(int colorInQuestion, int row, int col, int count, MyJButton[][] boardModel){
        if(count == 5){
            return count;
        }
        else if(col == -1 || row >= view2.boardSize || col >= view2.boardSize){
            return count;
        }
        else if(!boardModel[row][col].getBackground().equals(java.awt.Color.BLACK)){
            return count;
        }
        else if(boardModel[row][col].getBackground().equals(java.awt.Color.BLACK)){
            count++;
            return toTheSouthWest(colorInQuestion,row+1,col-1,count,boardModel);
        }
        else{
            return count;
        }
    }
}