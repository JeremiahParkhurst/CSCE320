package server;

import java.io.*;
import java.net.*;
import java.util.*;

/**
 * The ServerController file is used for starting the server, connecting clients
 * to the server and broadcasting messages that are posted to every client
 */
public class ServerController implements Runnable{
    
    Thread worker;
    ArrayList<ClientConnection> connectionList;
    ServerSocket serverSocket;
    Socket sock;
    ClientConnection connect;
    ServerView view;
    
    /**
     * ServerController will connect the view to the controller, create a
     * new array list for handling clients and will create the port number
     * @param s The ServerView file
     */
    public ServerController(ServerView s){
        view = s;
        connectionList = new ArrayList<>();
        try {
            serverSocket = new ServerSocket(2525);
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Could not create port");
        }
    }
    
    /**
     * broadcast uses a passed in string to send to every client that is connected
     * @param msg the string passed in from the listen() method in ClientConnection
     */
    public void post(String msg){
        view.postMsg(msg);
    }
    
    /**
     * Broadcasts all messages sent to the server to all players connected
     * to the server
     * @param msg, the message to be broadcast
     */
    public void broadcast(String msg){
        for(int i = 0; i<connectionList.size(); i++){
            if(!connectionList.get(i).socketClosed()){
                connectionList.get(i).sendMsg(msg);
            }
            else{
                removeOnlineUsers(connectionList.get(i).postUsername());
            }
        }
        //post("Broadcast sent: ");
    }
    
    /**
     * This run method will handle the creation of new client connections to
     * the server
     */
    @Override
    public void run() {
        while(true){
            try {
                sock = serverSocket.accept();
                connect = new ClientConnection(sock, this);
                post(">>> A new user has entered the server <<<");
                connectionList.add(connect);
                connect.start();
                view.postNumConnections(connectionList.size());
                
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("Error connecting clients to server");
            }
        }
    }
    
    /**
     * Retrieves the username of all players connected to the server
     * @return the username
     */
    public String getOnlineUsers(){
        try{
            String name = "";
            name = connectionList.get(0).postUsername();
            String users = name;
            for(int i = 1; i < connectionList.size();i++){
                name = connectionList.get(i).postUsername();
                users += ", " + name;
            }
            return users;
        }
        catch(IndexOutOfBoundsException e){
            post("No users are online!");
            return "";
        }
    }
    
    /**
     * Removes the user from the list of users connected to the server
     * @param s, the username
     */
    public void removeOnlineUsers(String s){
        //System.out.println();
        //System.out.println("Connection before remove: " + connectionList.size());
        int location = -1;
        // ******* Find method to find user based on the string username
        // ******* Then call connectionList.remove on what is found
        for(int i = 0; i < connectionList.size(); i++){
            if(connectionList.get(i).postUsername().equals(s)){
                location = i;
                //System.out.println("Location is: " + location);
            }
        }
        if(location > -1){
            if(connectionList.size() > 0){
                connectionList.remove(location);
                broadcast("U, " + getOnlineUsers() + "\n");
                post("This user logged off: " + s);
                //System.out.println("Connection after remove: " + connectionList.size());
                //System.out.println();
            }
        }
    }
    
    /**
     * listenForConnection will start the worker thread which will allow for
     * clients to connect
     */
    public void listenForConnection(){
        worker = new Thread(this);
        worker.start();
    }
    
    /**
     * stopListening stops the clients from using the server
     */
    public void stopListening(){
        connectionList = null;
        serverSocket = null;
    }
    
    /**
     * Sends a game invite from a player to a player
     * @param from, the invite from
     * @param to, the invite to
     */
    public void gameInvite(String from, String to){
        for(ClientConnection c: connectionList){
            if(c.postUsername().equals(to)){
                post("Sent request: From: " + from + " To: " + to + ", Message sent: ");
                c.sendMsg("R, " + from + "\n");
            }
        }
    }
    
    /**
     * Accepts the invite sent from a player to a player
     * @param from, the invite sent from
     * @param to, the invite sent to
     */
    public void acceptInvite(String from, String to) {
        // send "to", "from's" IP address with key of G: "G, IP address"
        String IP = "";
        for(int i = 0; i < connectionList.size(); i++){
            if(connectionList.get(i).postUsername().equals(to)){
                IP = connectionList.get(i).getIP();
                IP = IP.substring(1, IP.length());
            }
        }
        for(int i = 0; i < connectionList.size(); i++){
            if(connectionList.get(i).postUsername().equals(from)){
                post("Sent " + to + "'s IP to: " + from + "   IP Message: ");
                connectionList.get(i).sendMsg("G, " + IP + "\n");
            }
        }
    }
    
    /**
     * Declines the invite sent from a player to a player
     * @param from, the invite sent from
     * @param to, the invite sent to
     */
    public void declineInvite(String from, String to) {
        // send "from", that "to" declined: "Q, to"
        for(ClientConnection c: connectionList){
            if(c.postUsername().equals(from)){
                post("Declined request: Invite, From: " + from + " To: " + to + ", Message sent: ");
                c.sendMsg("Q, " + to + "\n");
            }
        }
    }
}