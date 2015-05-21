package gomoku;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import javax.swing.JFrame;

/**
 * The ConnectingViewController handles and listens to all actions preformed
 * from the ConnectingView.
 * This class stores information about which AI difficulty the user chooses and
 * opens the GameView with the given AI difficulty.
 */
public class ConnectingViewController implements Runnable{
    private Socket s;
    private TitleViewController tvc;
    ConnectingView view;
    JFrame app;
    Thread t;
    private boolean cancelled = false;
    int attempt;
    private String IP;
    private final int PORT = 2525;
    boolean checkUser = false;
    String rejoinUser;
    String rejoinPass;
    
    /**
     * Constructor initializes the ConnectingViewController
     * @param tView, the TitleViewController that directs to the
     * ConnectingViewController
     * @param ip, the ip address of the server
     */
    public ConnectingViewController(TitleViewController tView, String ip){
        IP = ip;
        view = new ConnectingView(this);
        app = new JFrame("Gomoku");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setContentPane(view);
        app.pack();
        app.setResizable(false);
        tvc = tView;
        attempt = 0;
    }
    
    /**
     * Displays this view and starts the thread.
     */
    public void showView(){
        app.setVisible(true);
        starter();
    }
    
    /**
     * reJoinView is made to run the starter method for getting to the login screen
     * however, this method will pass a username and password so that login is automatic
     * @param user The username of the previously logged in player
     * @param pass The password of the previously logged in player
     */
    public void reJoinView(String user, String pass){
        checkUser = true;
        rejoinUser = user;
        rejoinPass = pass;
        starter();
    }
    
    /**
     * Hides this view.
     */
    public void hideView(){
        app.setVisible(false);
    }
    
    /**
     * If the connection with the server is successful, the SignInViewController
     * will be created and initialized.
     * Hides the this view.
     */
    private void connectionEstablished(){
        SignInViewController signIn = new SignInViewController(tvc,s);
        signIn.previousIP = IP;
        signIn.showView();
        this.hideView();
    }
    
    /**
     * If the connection with the server is successfully re-established, the SignInViewController
     * will be created but will skip the actual steps of entering a username and password and 
     * instead run the login method. This is used for re-joining the server after a game
     * Hides the this view.
     */
    private void connectionReEstablished(){
        SignInViewController signIn = new SignInViewController(tvc,s);
        signIn.SendLoginRequest(rejoinUser, rejoinPass);
        this.hideView();
    }
    
    /*
    * Thread Two: accepts connections and creates Connection Object
    * accepts connection and creates Connection object
    */
    public void run() {
        
        while(!cancelled){
            view.updatelog("Attemping to Connect to Server at IP: " + IP +"-"+ attempt);
            
            try{
                s = new Socket(IP,PORT);
                System.out.println("Connection established");
                cancelRun();
            }
            catch (IOException ex) {
                System.out.println("Connection failed");
                attempt++;
            }
        }
        if(checkUser == false)
            connectionEstablished();
        else 
            connectionReEstablished();
    }
    
    /**
     * Initializes and starts the thread
     */
    private void starter(){
        t = new Thread(this);
        t.start();
    }
    
    /**
     *
     */
    public void cancelRun(){
        cancelled = true;
    }
    
    /**
     * Hides this view.
     * Displays the TitleView
     */
    public void goBack(){
        this.hideView();
        tvc.showView();
    }
}