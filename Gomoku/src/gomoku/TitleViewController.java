package gomoku;

import javax.swing.JFrame;

/**
 * The TitleViewController handles and listens to all actions preformed
 * from the TitleView, such as when the player selects multiplayer or single
 * player.
 * This class stores information about the IP address where the IP is passed
 * into the SignInController and will attempt to connect to the server when
 * the player selects login or create account.
 */
public class TitleViewController implements ViewController{
    TitleView view;
    JFrame app;
    String IP;
    
    /**
     * Constructor
     * Initializes the TitleView
     * @param ip, the ip address of the server
     */
    public TitleViewController(String ip){
        IP = ip;
        view = new TitleView(this);
        app = new JFrame("Gomoku");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setContentPane(view);
        app.pack();
        app.setResizable(false);
        view.ipTF.setText(IP);
    }
    
    /**
     * Displays the view
     */
    public void showView() {
        app.setVisible(true);
    }
    
    /**
     * Hides the view
     */
    public void hideView() {
        app.setVisible(false);
    }
    
    /**
     * Exits the game
     */
    public void exitBtnPress(){
        hideView();
        System.exit(0);
    }
    
    /**
     * This method is called when the player selects singleplayer
     * Transitions to the DifficultyView
     */
    public void singlePlayerChosen(){
        DifficultyViewController dif = new DifficultyViewController(this);
        dif.showView();
        this.hideView();
    }
    
    /**
     * This method is called when the player selects mutliplayer
     * Transitions to the SignInView
     */
    public void multiPlayerChosen(){
        IP = view.ipTF.getText();
        ConnectingViewController connecting = new ConnectingViewController(this, IP);
        connecting.showView();
        this.hideView();
    }
    
    /**
     * rejoinMultiPlayer is a method for connecting back to the server and forwarding to the 
     * matchmaking screen after successfully completing a game of Gomoku in multiplayer
     * The username and password are sent into this in order to forward them on for automatic login
     * @param user The username of the player who was logged on previously
     * @param pass The password of the player who was logged on previously
     */
    public void rejoinMultiPlayer(String user, String pass){
        ConnectingViewController connecting = new ConnectingViewController(this, IP);
        connecting.reJoinView(user, pass);
        this.hideView();
    }
}