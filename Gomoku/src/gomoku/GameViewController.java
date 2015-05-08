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
 * from the GameView.
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
     * Creates the new serverSocket to connect to
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
    
    @Override
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
    
    public void starter(){
        t = new Thread(this);
        t.start();
    }
    
    public void appendGameViewChat(String msg){
        view.appendChat("OPPONENT: " + msg);
    }
    
    public void showLoss(){
        winloss = new WinLossPopupView();
        WLView = new JFrame("End Game");
        WLView.add(winloss);
        WLView.pack();
        WLView.setResizable(false);
        WLView.setVisible(true);
        winloss.winLabel.setVisible(false);
    }
    
    public void showWin(){
        winloss = new WinLossPopupView();
        WLView = new JFrame("End Game");
        WLView.add(winloss);
        WLView.pack();
        WLView.setResizable(false);
        WLView.setVisible(true);
        winloss.lossLabel.setVisible(false);
    }
    
    public void invalidMoveMsg(){
        view.appendMoveStatus("Invalid Move Location");
    }
    
    public void validMoveMsg(){
        view.appendMoveStatus("Move Location Valid");
    }
    
    public void moveDeselected(){
        view.appendMoveStatus("Move Deselected");
    }
    
    public void clearStatus(){
        view.clearMoveStatus();
    }
}