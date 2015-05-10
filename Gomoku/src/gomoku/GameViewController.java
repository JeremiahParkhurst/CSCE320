package gomoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
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
    private WinLossPopupView winloss;
    private DifficultyViewController vcon;
    private JFrame app;
    private JFrame WLView;
    private ServerSocket serverSocket;
    private Socket socket;
    private Thread t;
    private InputStream in;
    private OutputStream out;
    private final int SIZE = 1024;
    private byte[] buffer;
    private boolean hasServerSocket;
    private NetworkedPlayer p2;
    
    /**
     * Constructor, initializes the GameViewController
     * @param vc, the DifficultyViewController that directs to the
     * GameViewController
     * @param difficulty, the difficulty of the AI
     */
    public GameViewController(DifficultyViewController vc, int difficulty){
        
        view = new GameView(this);
        view2 = new GameViewBoard(this);
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
     */
    public GameViewController(String username){
        view = new GameView(this);
        view2 = new GameViewBoard(this);
        app = new JFrame(username + "'s Game View");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(view, BorderLayout.EAST);
        app.add(view2, BorderLayout.WEST);
        app.pack();
        app.setBackground(new Color(204,204,255));
        buffer = new byte[SIZE];
        
        try {
            serverSocket = new ServerSocket(8080);
            System.out.println("Server Socket open");
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
     * @param IP
     * @param username
     */
    public GameViewController(String IP, String username){
        view = new GameView(this);
        view2 = new GameViewBoard(this);
        app = new JFrame(username + "'s Game View");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.add(view, BorderLayout.EAST);
        app.add(view2, BorderLayout.WEST);
        app.pack();
        app.setBackground(new Color(204,204,255));
        buffer = new byte[SIZE];
        
        try {
            socket = new Socket(IP,8080);
            in = socket.getInputStream();
            out = socket.getOutputStream();
            
            System.out.println("IP now:" + IP);
            
            p2 = new NetworkedPlayer(socket, this);
            in = socket.getInputStream();
            out = socket.getOutputStream();
            p2.starter();
            
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
    }
    
    /**
     * Attempts to creates a peer-to-peer connection by creating a serverSocket to
     * establish and input and output stream between the two players and 
     * initializes the thread.
     */
    public void run() {
        boolean connected = false;
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
        winloss = new WinLossPopupView();
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
        winloss = new WinLossPopupView();
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
    
    /**
     * Clears the move status area (helps prevent multiple messages
     * from clogging the statusTextArea
     */
    public void clearStatus(){
        view.clearMoveStatus();
    }
}