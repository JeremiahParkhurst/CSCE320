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
        if(msg.charAt(0) == 'W'){
            gvc.showWin();
        }
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
        while(true){
            try{
                int read = in.read(buffer);
                if(read > 0){
                    String msg = new String(buffer, 0, read);
                    if(msg.charAt(0) == 'M'){ // M, msg
                        String profile = msg.substring(3, msg.length());
                        gvc.appendGameViewChat(profile);
                    }
                    if(msg.charAt(0) == 'T'){ // T, row, column \n
                        String profile = msg.substring(4, msg.length()-1);
                        Scanner scan = new Scanner(profile).useDelimiter("\\s*,\\s*");
                        row = scan.next();
                        column = scan.next();
                        int r = Integer.parseInt(row);
                        int c = Integer.parseInt(column);
                        gb.updateMyGrid(r, c);
                        gvc.enableTurn();
                    }
                    if(msg.charAt(0) == 'W'){ // W, row, column \n
                        gvc.showLoss();
                    }
                }
            } catch (IOException ex) {
                System.out.println("Couldn't read msg.");
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
    
    /**
     * color is either GREEN, WHITE, YELLOW, or BLACK. The method returns
     * a count of the number of cells in this.grid that equal color
     * @param color the color used for the count
     * @return the number of cells equal to color
     */
    public int cellCount(int color) {
        int count = 0;
        //  for (int i=0; i<this.row; i++){
        //     for (int j=0; j<this.column; j++){
        //     if(this.grid[i][j] == color)
        //            count = count + 1;
        
        // }
        //    }
        // return count;
        return 0;
    }
}