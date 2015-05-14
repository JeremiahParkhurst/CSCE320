package gomoku;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.*;
import javax.swing.JFrame;

/**
 * The MatchmakingViewController handles and listens to all actions preformed
 * from the MatchmakingView.
 * This class handles and updates the current list of users online and
 * messages being sent. It also handles game invitations between players, and
 * it will create a Gomoku game with the boardSize that the challenger picked.
 */
public class MatchmakingViewController implements Runnable{
    
    MatchmakingView view;
    JFrame app;
    ArrayList<String> onlineUsers;
    ArrayList<String> recInvites;
    ArrayList<String> sentInvites;
    SignInViewController vc;
    InputStream in;
    OutputStream out;
    String msg;
    Thread t;
    String USERNAME;
    int boardSize = 20;
    
    private byte[] buffer;
    private final int size = 1024;
    
    /**
     * Constructor initializes the MatchmakingViewController
     * @param vcon, the SignInViewController that directs to
     * the MatchmakingViewController
     * @param users, the lists of users online
     * @param s, the socket
     * @param username, the user's name
     */
    public MatchmakingViewController(SignInViewController vcon,
            ArrayList<String> users, Socket s, String username){
        view = new MatchmakingView(this);
        app = new JFrame("Gomoku");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setContentPane(view);
        app.pack();
        app.setResizable(false);
        recInvites = new ArrayList<>();
        sentInvites = new ArrayList<>();
        
        buffer = new byte[size];
        vc = vcon;
        onlineUsers = users;
        USERNAME = username;
        
        view.updateUName(USERNAME);
        
        try {
            out = s.getOutputStream();
            in = s.getInputStream();
        } catch (IOException ex) {
            System.out.println("New IO not set up");
        }
        for (String onlineUser : onlineUsers) {
            System.out.println(onlineUser);
        }
        view.updateUsers(onlineUsers, USERNAME);
    }
    
    /**
     * Sends a message to the Server with the user's username when they logoff
     */
    public void logOff(){
        String LOmsg = "X, " + USERNAME + "\n";
        sendMsg(LOmsg);
    }
    
    /**
     * Sends invite to another user that is connected to the server
     * @param toWhom, the users receiving the game invitation
     */
    public void sendInvite(String toWhom){
        String invite = "I, " + toWhom + ", " + USERNAME + "\n";
        sentInvites.add(toWhom);
        sendMsg(invite);
    }
    
    /**
     * Calls logoff method
     */
    public void shutDown(){
        logOff();
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
     * Displays this view and starts the thread
     */
    public void showView() {
        app.setVisible(true);
        starter();
    }
    
    /**
     * Hides this view
     */
    public void hideView() {
        app.setVisible(false);
        t.interrupt();
    }
    
    /**
     * Hides this view and returns to the title menu
     */
    public void menuBtn(){
        hideView();
        vc.vcon.showView();
    }
    
    /**
     * Initializes and starts the thread
     */
    public void starter(){
        t = new Thread(this);
        t.start();
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
                System.out.println("msgListen says: " + str);
                proccessMsg(str);
            }
        }
    }
    
    /**
     * Processes the message received
     * @param msg, the message being processed
     */
    public void proccessMsg(String msg){
        System.out.println("MatchmakingView prints says: " + msg);
        char key = msg.charAt(0);
        String username = "";
        char current = ' ';
        
        if(key == 'U'){//UPDATE
            onlineUsers.clear();
            
            for(int i = 3; i < msg.length(); i++){
                current = msg.charAt(i);
                if(current == ',' || current == '\n'){
                    onlineUsers.add(username.trim());
                    username = "";
                }
                else{
                    username += msg.charAt(i);
                }
            }
            System.out.println("In Matchmaking View Controller: Users are: " + onlineUsers);
            view.updateUsers(onlineUsers,USERNAME);
        }
        else if (key == 'R'){//for recieving an invite
            //notify about new invite and do stuff
            //if accept start up server socket and send msg to server
            //sendMsg(A) for accept and sendMsg(D) for dicline
            username = "";
            
            for(int i = 3; i < msg.length(); i++){
                current = msg.charAt(i);
                if(current == ',' || current == '\n'){
                    break;
                }
                else{
                    username += msg.charAt(i);
                }
            }
            updateRequest(username, "a");
        }
        else if (key == 'G'){//other users responce good
            String ip = "";
            String profile = msg.substring(3, msg.length()-1); // Format string: to, from
            Scanner scan = new Scanner(profile).useDelimiter("\\s*,\\s*");
            ip = scan.next(); // format ip variable
            String size = scan.next(); // format size variable
            int bSize = Integer.parseInt(size); // format size to integer
            System.out.println("IP: " + ip);
            String playerBusy = "B, " + USERNAME + "\n";
            sendMsg(playerBusy);
            GameViewController gameViewCon = new GameViewController(ip,USERNAME,vc,size);
            gameViewCon.showView();
            this.hideView();
        }
        else if (key == 'Q'){//other users responce quit
            //take care of the false responce to "to"
            username = "";
            for(int i = 3; i < msg.length(); i++){
                current = msg.charAt(i);
                if(current == ',' || current == '\n'){
                    break;
                }
                else{
                    username += msg.charAt(i);
                }
            }
            updateRequest(username, "r");
        }
        else if (key == 'B'){ // player is busy message
            String uName = "";
            for(int i = 3; i < msg.length(); i++){
                current = msg.charAt(i);
                if(current == '\n'){
                    break;
                }
                else{
                    uName += msg.charAt(i);
                }
            }
            updateRequest(uName,"r");
        }
    }
    
    /**
     * Displays a message announcing the new invitation
     * adds the invitation to a list of received invites
     * @param from the username of the person who sent the invite
     */
    public void updateRequest(String from, String ar){
        switch (ar) {
            case "a":
                recInvites.add(from);
                break;
            case "r":
                if(recInvites.contains(from)){
                    recInvites.remove(from);
                }
                break;
        }
        
        //update the list of updates on the view based on the recieved invites
        //some sort of notification about the new invite
        view.updateRequests(recInvites);
    }
    
    /**
     * Creates a serversocket
     * sends a message to the server saying that they accept
     * opens a new gameview connected to "from"s gameview
     * @param from, the player sending the invite
     */
    public void acceptInvite(String from, int size){
        boardSize = size;
        String accept = "A, " + USERNAME + ", " + from + ", " + boardSize + "\n";
        sendMsg(accept);
        String playerBusy = "B, " + USERNAME + "\n";
        sendMsg(playerBusy);
        GameViewController gameViewCon = new GameViewController(USERNAME,vc,boardSize);
        gameViewCon.showView();
        this.hideView();
        
    }
    
    /**
     * Declines the invite from the user who sent the invite.
     * @param from, the username of the player who sent the game invite.
     */
    public void declineInvite(String from){
        String decline = "D, " + USERNAME + ", " + from + "\n";
        updateRequest(from, "r");
        sendMsg(decline);
    }
    
    /**
     * Sends a message to the server to broadcast Saying this user is no longer
     * available for games
     */
    public void enteredGame(){
        String busyMsg = "B, " + USERNAME + "\n";
        sendMsg(busyMsg);
    }
    
    /**
     * Run will listen to messages when the user is connected to the server
     */
    public void run() {
        while(true){
            try{
                msgListen();
            }
            catch(IOException e){
                e.printStackTrace();
                System.out.println("Had an error listening for messages");
            }
        }
    }
}