package server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * The ClientConnection file handles the connection of clients to the server
 */
public class ClientConnection extends Thread{
    InputStream in;
    OutputStream out;
    ServerController controller;
    Scanner scan;
    ArrayList userArray = new ArrayList();
    String name = "";
    Socket sock;
    boolean connect = true;
    
    private byte[] buffer;
    private final int size = 1024;
    
    /**
     * ClientConnection connects this file to the controller. As well as, it
     * gets the input and output streams and buffer for connecting clients to
     * the server
     * @param s, the socket being used
     * @param c, the ServerController
     */
    public ClientConnection(Socket s, ServerController c){
        controller = c;
        try {
            sock = s;
            in = s.getInputStream();
            out = s.getOutputStream();
            buffer = new byte[size];
            
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error setting up connection");
        }
    }
    
    /**
     * This run method will continue to listen for new strings while it is
     * still running
     */
    @Override
    public void run(){
        while(sock.isConnected()){
            try {
                listen();
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Could not read in message");
            }
        }
    }
    
    /**
     * @param s, the username
     * @return true if the username exists in the user text file, false otherwise
     * @throws IOException 
     */
    public boolean compareUser(String s) throws IOException{
        String fileName = "users.txt";
        String profile = s.substring(3, s.length()-1); // formats to "username, password"
        //controller.post(profile);
        try{
            FileReader file = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(file);
            String line;
            while ((line = buffer.readLine()) != null)   {
                if(line.equals(profile))
                    return true;
            }
            buffer.close();
        }
        catch(FileNotFoundException e){
            System.out.println("could not locate users file");
        }
        return false;
    }
    
    /**
     * Checks the text file to determine whether the username exists
     * @param s, the username
     * @return true if the username exists, false otherwise
     * @throws IOException 
     */
    public boolean checkUsername(String s) throws IOException{
        String fileName = "users.txt";
        // "Letter, Username, Password" --> "Username"
        String profile = s.substring(3, s.length()-1);
        Scanner scan = new Scanner(profile).useDelimiter("\\s*,\\s*");
        String username = scan.next();
        username.trim();
        
        try{
            FileReader file = new FileReader(fileName);
            BufferedReader buffer = new BufferedReader(file);
            String line = "";
            String UN = "";
            while ((line = buffer.readLine()) != null)   {
                UN = "";
                for(int i=0; i < line.length(); i++){
                    if(line.charAt(i) == ','){
                        break;
                    }
                    else{
                        UN += line.charAt(i);
                    }
                }
                if(UN.equals(username))
                    return true;
            }
            buffer.close();
        }
        catch(FileNotFoundException e){
            System.out.println("could not locate users file");
        }
        return false;
    }
    
    /**
     * Formats "Letter, Username, Password" --> "Username" 
     * @param s
     * @return username, the player's username
     */
    public String getUsername(String s){
        String profile = s.substring(3, s.length()-1);
        Scanner scan = new Scanner(profile).useDelimiter("\\s*,\\s*");
        String username = scan.next();
        username.trim();
        return username;
    }
    
    /**
     * @return the IP address
     */
    public String getIP(){
        return sock.getInetAddress().toString();
    }
    
    /**
     * Method to create a new user
     * @param s, the username
     * @throws IOException 
     */
    public void createUser(String s) throws IOException{
        String profile = s.substring(3, s.length()-1);
        //PrintWriter out = new PrintWriter("users.txt");
        //out.println(profile);    
        try
        {
            String filename = "users.txt";
            FileWriter fw = new FileWriter(filename,true); //the true will append the new data
            fw.write(profile + "\n");//appends the string to the file
            fw.close();
        }
        catch(IOException ioe)
        {
            System.err.println("IOException: " + ioe.getMessage());
        }
    }
    
    /**
     * The listen method will continue to read in messages from the buffer
     * and broadcast them to everyone on the server
     * @throws IOException throws error if listen() stops working
     */
    public void listen() throws IOException{
        
        while(connect){
            try{
                int read = in.read(buffer);
                if(read > 0){
                    String s = new String(buffer, 0, read);
                    //controller.post(s);
                    boolean existingUser = false;
                    boolean usernameExists = false;
                    
                    if(s.charAt(0) == 'C'){ // CREATE ACCOUNT
                        usernameExists = checkUsername(s);
                        if(usernameExists == true){
                            controller.post("This username already exists: " + s);
                            sendMsg("N");
                        }
                        else{
                            controller.post("Username accepted: " + s);
                            createUser(s);
                            existingUser = compareUser(s);
                            if(existingUser == true){
                                //controller.post("Able to create and login user: " + s);
                                name = getUsername(s);
                                controller.post("User created and logged in: ");
                                sendMsg("Y, " + controller.getOnlineUsers() + "\n");
                                controller.broadcast("U, " + controller.getOnlineUsers() + "\n");
                            }
                            else{
                                controller.post("Failed to login after creation: " + s);
                                sendMsg("N");
                            }
                        }
                    }
                    else if(s.charAt(0) == 'L'){ // LOGIN
                        existingUser = compareUser(s);
                        if(existingUser == true){
                            controller.post("This user exists in the text file: " + getUsername(s));
                            name = getUsername(s);
                            controller.post("Users currently logged in: " + controller.getOnlineUsers() + "\n");
                            sendMsg("Y, " + controller.getOnlineUsers() + "\n");
                            controller.broadcast("U, " + controller.getOnlineUsers() + "\n");
                        }
                        else{
                            controller.post("This user DOES NOT exists in the text file: " + s);
                            sendMsg("N");
                        }
                    }
                    else if(s.charAt(0) == 'X'){ // CLOSE
                        String userStr = s.substring(3, s.length()-1);
                        controller.removeOnlineUsers(userStr);
                    }
                    else if(s.charAt(0) == 'I'){ // INVITE, String is: I, to, from
                        controller.post("Invite sent: " + s);
                        String profile = s.substring(3, s.length()-1); // Format string: to, from
                        Scanner scan = new Scanner(profile).useDelimiter("\\s*,\\s*");
                        String to = scan.next(); // format from String: to
                        String from = scan.next(); // format to String: from
                        controller.gameInvite(from, to);
                    }
                    else if(s.charAt(0) == 'A'){ // ACCEPT INVITE, String is: A, to, from
                        controller.post("Accepted invite: " + s);
                        String profile = s.substring(3, s.length()-1); // Format string: to, from
                        Scanner scan = new Scanner(profile).useDelimiter("\\s*,\\s*");
                        String to = scan.next(); // format from String: to
                        String from = scan.next(); // format to String: from
                        controller.acceptInvite(from, to);
                    }
                    else if(s.charAt(0) == 'D'){ // DECLINE INVITE, String is: D, to, from
                        controller.post("Declined invite: " + s);
                        String profile = s.substring(3, s.length()-1); // Format string: to, from
                        Scanner scan = new Scanner(profile).useDelimiter("\\s*,\\s*");
                        String to = scan.next(); // format from String: to
                        String from = scan.next(); // format to String: from
                        controller.declineInvite(from, to);
                    }
                    else if(s.charAt(0) == 'B'){ // PLAYER BUSY, String is B, busyUser
                        controller.post("Busy Player: " + s);
                        String profile = s.substring(3, s.length()-1); 
                        Scanner scan = new Scanner(profile).useDelimiter("\\s*,\\s*");
                        String from = scan.next(); 
                        controller.removeOnlineUsers(from);
                        String busyMsg = "B, " + from+ "\n";
                        controller.broadcast(busyMsg);
                    }
                    else
                        sendMsg("N");
                }
            }
            catch(SocketException sockEx){
                System.out.println("Error: " + sockEx);
                sock.close();
                connect = false;
                controller.broadcast("U, " + controller.getOnlineUsers() + "\n");
            }
        }
    }
    
    /**
     * @return name, the player's username
     */
    public String postUsername(){
        return name;
    }
    
    /**
     * sendMsg will write out the string that was given to broadcast
     * @param msg The string that is passed from broadcast
     */
    public void sendMsg(String msg){
        /*if(msg.charAt(0) == 'U' || msg.charAt(0) == 'B'){ // no need for update or busy console messages
            // nothing
        }
        else{
            controller.post(msg); // post msg as long as it is not an update or repeat of busy message
        }*/
        
        byte[] b = msg.getBytes();
        try{
            out.write(b);
        }
        catch(IOException e){
            e.printStackTrace();
            System.out.println("Problem with sending a message");
        }
    }
    
    /**
     * Closes the socket
     */
    public void closeSocket() {
        try {
            sock.close();
        } catch (IOException ex) {
            System.out.println("Socket closing error");
        }
    }
    
    /**
     * Closes the socket by calling the closeSocket method
     * @return true if the socket is closed, false otherwise
     */
    public boolean socketClosed(){
        return sock.isClosed();
    }
}