package gomoku;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Brendan
 */
public class NetworkedPlayer implements Runnable{
    Thread t;
    Socket sock;
    private InputStream in;
    private OutputStream out;
    private final int SIZE = 1024;
    private byte[] buffer;
    GameViewController gvc;
    GameViewBoard gb;
    String row;
    String column;
    boolean listening = true;
    
    /**
     *
     * @param s
     */
    public NetworkedPlayer(Socket s, GameViewController gameViewCon){
        gvc = gameViewCon;
        sock = s;
        buffer = new byte[SIZE];
        
        try {
            in = sock.getInputStream();
            out = sock.getOutputStream();
            System.out.println("Socket streams built!");
        } catch (IOException ex) {
            System.out.println("Couldnt connect networked player streams");
        }
        
    }
    
    /**
     *
     */
    public void starter(){
        t = new Thread(this);
        t.start();
    }
    
    /**
     *
     */
    public void run() {
        while(sock.isConnected()){
            listen();
        }
    }
    
    /**
     *
     * @param msg
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
     *
     */
    public void listen(){
        while(listening){
            try{
                int read = in.read(buffer);
                if(read > 0){
                    String msg = new String(buffer, 0, read);
                    if(msg.charAt(0) == 'M'){ // M, msg
                        String profile = msg.substring(3, msg.length());
                        gvc.appendGameViewChat(profile);
                    }
                    if(msg.charAt(0) == 'T'){ // T, row, column \n
                        // Following 6 lines used to get r and c into integers for the updateGrid call
                        String profile = msg.substring(4, msg.length()-1);
                        Scanner scan = new Scanner(profile).useDelimiter("\\s*,\\s*");
                        row = scan.next();
                        column = scan.next();
                        int r = Integer.parseInt(row);
                        int c = Integer.parseInt(column);
                        
                        gvc.updateGrid(r, c);
                        gvc.enableTurn();
                    }
                    if(msg.charAt(0) == 'W'){ // W, row, column \n
                        // Following 6 lines used to get r and c into integers for the updateGrid call
                        String profile = msg.substring(4, msg.length()-1);
                        Scanner scan = new Scanner(profile).useDelimiter("\\s*,\\s*");
                        row = scan.next();
                        column = scan.next();
                        int r = Integer.parseInt(row);
                        int c = Integer.parseInt(column);
                        System.out.println("Win Condition Acheived");
                        
                        gvc.updateGrid(r, c);
                        gvc.showLoss();
                        
                    }
                }
            } catch (IOException ex) {
                System.out.println("Couldn't read msg.");
                System.out.println("Other user Disconnected \nExit to Matchmaking");
                listening = false;
                try {
                    sock.close();
                } catch (IOException ex1) {
                    System.out.println("Could not close the Networked Players socket");
                }
                break;
            }
        }
    }
    
    /**
     * returns the number of rows
     * @return the rows
     */
    public String getRows() {
        return row;
    }
    
    /**
     * returns the number of columns
     * @return the columns
     */
    public String getColumns() {
        return column;
    }
}