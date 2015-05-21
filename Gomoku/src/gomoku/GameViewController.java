package gomoku;

import gomoku.GameViewBoard.MyJButton;
import gomoku.GameViewModel.Cell;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
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
 * Creates a peer-to-peer connection between two players. When gameOver is
 * called the winning player will be able to see the WinLossPopupView
 * displaying that they won, otherwise a losing message will be displayed.
 * If the player played against an AI, then they will have the option of
 * returning to the title view. If the player played against another person,
 * then the WinLossPopupView will have the option of returning to the 
 * matchmaking view or the title view.
 */
public class GameViewController implements Runnable{
    private GameView view;
    private GameViewBoard view2;
    private GameViewModel gmod;
    private WinLossPopupView winloss;
    private SignInViewController svc;
    private DifficultyViewController dvc;
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
    private NetworkedPlayer nwp;
    public boolean connected = false;
    public int dif = -1;
    public AIPlayer ai;
    
    /**
     * Constructor, initializes the GameViewController, and enables the
     * the buttons on the GameView and GameViewBoard to allow the player to
     * make the first move against the AI.
     * @param vc, the DifficultyViewController that directs to the 
     * GameViewController
     * @param difficulty, the difficulty of the AI
     */
    public GameViewController(DifficultyViewController vc, int difficulty){
        view = new GameView(this);
        view2 = new GameViewBoard(this, 15);
        gmod = new GameViewModel(view2.boardSize, view2.boardSize);
        app = new JFrame("Gomoku");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(view, BorderLayout.EAST);
        app.add(view2, BorderLayout.WEST);
        app.pack();
        app.setResizable(false);
        app.setBackground(new Color(204,204,255));
        dvc = vc;
        dif = difficulty;
        ai = new AIPlayer(dif);
        enableTurn();
    }
    
    /**
     * Creates the JFrame to hold the GameView (the chat JPanel) and the
     * GameViewBoard (the game board JPanel).
     * Attempts to create a serverSocket and initializes the thread.
     * This Constructor represents the player who sent the invite, and
     * create a server socket, and allows this player to make the first move
     * within the Gomoku game.
     */
    public GameViewController(String username, SignInViewController vc, int boardSize){
        view = new GameView(this);
        view2 = new GameViewBoard(this, boardSize);
        gmod = new GameViewModel(view2.boardSize, view2.boardSize);
        app = new JFrame(username + "'s Game View");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(view, BorderLayout.EAST);
        app.add(view2, BorderLayout.WEST);
        app.pack();
        app.setBackground(new Color(204,204,255));
        buffer = new byte[SIZE];
        svc = vc;
        
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
     * Receives the IP of the other player's server socket.
     * Creates a peer-to-peer connection using a ServerSocket.
     * Creates the JFrame to hold the GameView (the chat JPanel) and the
     * GameViewBoard (the game board JPanel).
     * This Constructor will send their IP and username and attempt to connect
     * to the previous constructor that has created the server socket.
     * @param IP, the ip address
     * @param username, the username of this player
     * @param boardSize, the size of the board to be passed into the
     * GameViewController, which will be passed onto the GameViewBoard to set
     * the size of the board for the Gomoku game.
     */
    public GameViewController(String IP, String username, SignInViewController vc, int boardSize){
        view = new GameView(this);
        view2 = new GameViewBoard(this, boardSize);
        gmod = new GameViewModel(view2.boardSize, view2.boardSize);
        app = new JFrame(username + "'s Game View");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(view, BorderLayout.EAST);
        app.add(view2, BorderLayout.WEST);
        app.pack();
        app.setBackground(new Color(204,204,255));
        buffer = new byte[SIZE];
        svc = vc;
        
        try {
            socket = new Socket(IP,8080);
            in = socket.getInputStream();
            out = socket.getOutputStream();
            System.out.println("IP now:" + IP);
            nwp = new NetworkedPlayer(socket, this);
            in = socket.getInputStream();
            out = socket.getOutputStream();
            nwp.starter();
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
    
    /**
     * @param msg, the message to be sent
     */
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
     * Attempts to creates a peer-to-peer connection by creating a serverSocket 
     * to establish and input and output stream between the two players and
     * initializes the thread.
     */
    public void run() {
        //connected = false;
        while(!connected){
            System.out.println("Waiting for connection");
            try{
                socket = serverSocket.accept();
                System.out.println("Connection to other player made.");
                nwp = new NetworkedPlayer(socket, this);
                in = socket.getInputStream();
                out = socket.getOutputStream();
                nwp.starter();
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
        winloss = new WinLossPopupView(this,svc);
        WLView = new JFrame("End Game");
        WLView.add(winloss);
        WLView.pack();
        WLView.setResizable(false);
        WLView.setVisible(true);
        winloss.winLossJTextArea.setText("You Lose..."); // Display the lose message
        winloss.winLossJTextArea.setEditable(false);
        winloss.tvButton.setVisible(false); // Hides the Return to TitleViewButton
    }
    
    /**
     * Display the win menu when the player wins.
     */
    public void showWin(){
        winloss = new WinLossPopupView(this,svc);
        WLView = new JFrame("End Game");
        WLView.add(winloss);
        WLView.pack();
        WLView.setResizable(false);
        WLView.setVisible(true);
        winloss.winLossJTextArea.setText("You Win!!!"); //Display the win message
        winloss.winLossJTextArea.setEditable(false);
        winloss.tvButton.setVisible(false); // Hides the Return to TitleViewButton
    }
    
    /**
     * Displays the loss menu when the player loses.
     */
    public void showSingleLoss(){
        winloss = new WinLossPopupView(this,svc);
        WLView = new JFrame("End Game");
        WLView.add(winloss);
        WLView.pack();
        WLView.setResizable(false);
        WLView.setVisible(true);
        winloss.winLossJTextArea.setText("You Lose..."); // Displays the losing message
        winloss.winLossJTextArea.setEditable(false);
        winloss.mmButton.setVisible(false); // Hides the Return To Matchmaking Button
    }
    
    /**
     * Display the win menu when the player wins.
     */
    public void showSingleWin(){
        winloss = new WinLossPopupView(this,svc);
        WLView = new JFrame("End Game");
        WLView.add(winloss);
        WLView.pack();
        WLView.setResizable(false);
        WLView.setVisible(true);
        winloss.winLossJTextArea.setText("You Win!!!"); // Displays the winning message.
        winloss.winLossJTextArea.setEditable(false);
        winloss.mmButton.setVisible(false); // Hides the Return to TitleViewButton
    }
    
    /**
     * This method is called by the networked player if your opponent leaves during the game.
     * It will take give the player a popup for exiting to the matchmaking screen.
     */
    public void showPlayerDiscon(){
        disableTurn();
        winloss = new WinLossPopupView(this,svc);
        WLView = new JFrame("Player Left");
        WLView.add(winloss);
        WLView.pack();
        WLView.setResizable(false);
        WLView.setVisible(true);
        Font font = winloss.winLossJTextArea.getFont(); // retreive font in order to change size
        float size = font.getSize() - 30.0f; // make font size smaller
        winloss.winLossJTextArea.setFont(font.deriveFont(size)); // set the smaller font size
        winloss.winLossJTextArea.setText("Opponent Left Game"); // Display opponent left message
        winloss.winLossJTextArea.setEditable(false);
        winloss.tvButton.setVisible(false); // Hides the Return to TitleViewButton
    }
    
    /**
     * Closes the WinLossPopup for a single player match
     */
    public void closeSingleEndGame(){
        WLView.setVisible(false);
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
    
    /**
     * Updates the GameViewBoard at location r, row and c, column.
     * @param r, the row
     * @param c, the column
     */
    public void updateGrid(int r, int c){
        view2.updateMyGrid(r, c);
    }
    
    /**
     * This method is called from the GameView when sendMove is selected.
     * Checks the board for a yellow cell by the findYellowCell method 
     * and notifies the player if they can still make a move, otherwise call
     * updateYellowSquare which will change the yellow cell at location (r,c) to
     * a black cell.
     * Retrieves the location of the yellow cell which will be used to update
     * the opposing player's board. sendMsg will disable your board, and when
     * you receive a msg from sendMsg, it will enable your board.
     * This method will also check fom the win condition via the gameOver
     * method.
     */
    public void sendMoveButtonChosen(){
        Cell yellowCell = gmod.findYellowCell(view2.square);
        if(yellowCell == null){
            view.appendMoveStatus("Move Availible");
        }
        else if(dif == -1){
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
                nwp.sendMsg(stringT); // sends T, row, column
            }
            else{
                showWin();
                nwp.sendMsg(stringW);
                nwp.winConMet = true;
            }
        }
        else{ // for offline gameplay
            String s = yellowCell.toString();
            System.out.println("s: " + s);
            String profile = s.substring(1, s.length()-1);
            Scanner scan = new Scanner(profile).useDelimiter("\\s*,\\s*");
            String row = scan.next();
            String column = scan.next();
            int r = Integer.parseInt(row);
            int c = Integer.parseInt(column);
            view2.updateYellowSquare(r,c);
            gmod.updateGrid(r, c, 0);
            if(gameOver(view2.square) == false){ // enter turn, if win condition false
                disableTurn();
                int array[] = ai.getMove(gmod.grid);
                gmod.updateGrid(array[0], array[1], 1);
                view2.updateMyGrid(array[0], array[1]);
                if(!gameOverSingle(view2.square)){ // if AI, win condition false
                    enableTurn();
                }
                else{ // if AI, win condition true
                    app.setVisible(false);
                    showSingleLoss();
                }
            }
            else{ // if win condition is true
                app.setVisible(false);
                showSingleWin();
            }
        }
    }
    
    /**
     * Checks the board for a winner.
     * @param board, the grid of button that represents the GameViewBoard
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
     * Checks the board for a winner in single player
     * @return The players number if there is a winner or -1 for no winner
     */
    public boolean gameOverSingle(MyJButton[][] board){
        int color = 3;
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
        if(colorInQuestion == 2){
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
        else if(colorInQuestion == 3){
            if(count == 5){
                return count;
            }
            else if(row >= view2.boardSize || col >= view2.boardSize){
                return count;
            }
            else if(!boardModel[row][col].getBackground().equals(java.awt.Color.WHITE)){
                return count;
            }
            else if(boardModel[row][col].getBackground().equals(java.awt.Color.WHITE)){
                count++;
                return toTheEast(colorInQuestion,row,col+1,count,boardModel);
            }
            else{
                return count;
            }
        }
        else{
            return 0;
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
        if(colorInQuestion == 2){
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
        else if(colorInQuestion == 3){
            if(count == 5){
                return count;
            }
            else if(row >= view2.boardSize || col >= view2.boardSize){
                return count;
            }
            else if(!boardModel[row][col].getBackground().equals(java.awt.Color.WHITE)){
                return count;
            }
            else if(boardModel[row][col].getBackground().equals(java.awt.Color.WHITE)){
                count++;
                return toTheSouthEast(colorInQuestion,row +1,col+1,count,boardModel);
            }
            else{
                return count;
            }
        }
        else{
            return 0;
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
        if(colorInQuestion == 2){
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
        else if(colorInQuestion == 3){
            if(count == 5){
                return count;
            }
            else if(row >= view2.boardSize || col >= view2.boardSize){
                return count;
            }
            else if(!boardModel[row][col].getBackground().equals(java.awt.Color.WHITE)){
                return count;
            }
            else if(boardModel[row][col].getBackground().equals(java.awt.Color.WHITE)){
                count++;
                return toTheSouth(colorInQuestion,row+1,col,count,boardModel);
            }
            else{
                return count;
            }
        }
        else{
            return 0;
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
        if(colorInQuestion == 2){
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
        else if(colorInQuestion == 3){
            if(count == 5){
                return count;
            }
            else if(col == -1 || row >= view2.boardSize || col >= view2.boardSize){
                return count;
            }
            else if(!boardModel[row][col].getBackground().equals(java.awt.Color.WHITE)){
                return count;
            }
            else if(boardModel[row][col].getBackground().equals(java.awt.Color.WHITE)){
                count++;
                return toTheSouthWest(colorInQuestion,row+1,col-1,count,boardModel);
            }
            else{
                return count;
            }
        }
        else{
            return 0;
        }
    }
}