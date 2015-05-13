package gomoku;

import java.io.*;
import java.net.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;

/**
 * The SignInViewController handles and listens to all actions preformed
 * from the SignInView.
 * This class handles login, account creation, connection sockets and
 * communication between the client and the server.
 */
class SignInViewController implements Runnable{
    SignInView view;
    JFrame app;
    public TitleViewController vcon;
    
    Socket s;
    InputStream in;
    OutputStream out;
    
    String msg;
    Thread t;
    
    private byte[] buffer;
    private final int size = 1024;
    MatchmakingViewController mvcon;
    
    ArrayList<String> onlineUsers;
    String USERNAME = "";
    // username and password accessed by WinLossPopupView for returning to matchmaking
    String globalUser; 
    String globalPassword;
    
    /**
     * Constructor initializes the SignInViewController
     * @param vc, the TitleViewController that directs to the TitleViewController
     */
    public SignInViewController(TitleViewController vc, Socket socket){
        view = new SignInView(this);
        app = new JFrame("Gomoku");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setContentPane(view);
        app.pack();
        app.setResizable(false);
        vcon = vc;
        
        buffer = new byte[size];
        
        onlineUsers = new ArrayList<>();
        
        this.s = socket;
        
        try {
            out = s.getOutputStream();
            in = s.getInputStream();
        } catch (IOException ex) {
            System.out.println("Error connecting Socket streams");
            
        }
        
        
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
        t.interrupt();
    }
    
    /**
     * Hides this view and opens the TitleView
     */
    public void mainMenu(){
        vcon.showView();
        this.hideView();
    }
    
    /**
     * This method calls SendLoginRequest with the given username and password
     * @param user
     * @param pass
     */
    public void loginBtn(String user, String pass){
        SendLoginRequest(user,pass);
    }
    
    /**
     * Requests login with the given username and password
     * @param user, the username of the user
     * @param pass, the password of the user
     */
    public void SendLoginRequest(String username, String password){
        globalUser = username; // sets user to class variable for later access
        globalPassword = password; // sets password to class variable for later access
        String msg = "L, " + username + ", " + password + "\n";
        USERNAME = username;
        sendMsg(msg);
        System.out.println("Login request sent: " + msg);
        perform();
    }
    
    /**
     * Creates a new user with the given username and password
     * @param user, the username of the user
     * @param pass, the password of the user
     */
    public void createNewUser(String user, String pass){
        globalUser = user; // sets user to class variable for later access
        globalPassword = pass; // sets password to class variable for later access
        String msg = "C, " + user + ", " + pass + "\n";
        USERNAME = user;
        sendMsg(msg);
        perform();
    }
    
    /**
     * The sendMsg is called by the view whenever a message is entered into
     * the message TextField, it writes the message to the server
     * @param s the string passed in by the message input
     */
    public void sendMsg(String s){
        byte[] b = s.getBytes();
        try{
            out.write(b);
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Problem with sending a message");
        }
    }
    
    /**
     * @param msg, the message
     * @return false if
     */
    private boolean processRead(String msg){
        System.out.println("processRead says: " + msg);
        String username = "";
        char current = ' ';
        if(msg.charAt(0) == 'N'){
            return false;
        }
        else if (msg.charAt(0) == 'Y'){
            for(int i = 3; i < msg.length(); i++){
                current = msg.charAt(i);
                if(current == ','){
                    onlineUsers.add(username.trim());
                    username = "";
                }
                else if(current == '\n'){
                    break;
                }
                else{
                    username += msg.charAt(i);
                }
            }
            return true;
        }
        else{
            System.out.println("Something else was picked up in SignInView");
            if(mvcon != null){
                mvcon.proccessMsg(msg);
            }
            return false;
        }
        
        
    }
    
    /**
     * The msgListen method is called by run, msgListen looks for messages
     * written to the server as long as the user is connected to the server
     * @throws IOException used for server connection
     */
    public void msgListen() throws IOException{
        boolean connect = true;
        
        while(connect){
            int read = in.read(buffer);
            if(read > 0){
                String str = new String(buffer, 0, read);
                if(processRead(str)){
                    mvcon = new MatchmakingViewController(this, onlineUsers,s,USERNAME);
                    mvcon.showView();
                    this.hideView();
                }
                else{
                    view.setLabError(true);
                }
            }
        }
    }
    
    /**
     * Listens to user messages that are sent to the server
     */
    public void run() {
        while(s.isConnected()){
            try{
                msgListen();
            }
            catch(IOException e){
                e.printStackTrace();
                System.out.println("Had an error listening for messages");
            }
        }
    }
    
    /**
     * The perform method creates and starts a new thread
     */
    public void perform(){
        t = new Thread(this);
        t.start();
    }
}