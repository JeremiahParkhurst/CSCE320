package server;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.io.*;

/**
 * This Server file is the main method used to run the program. It starts up
 * a GUI that allows for a user to start and stop a server. It also shows the
 * number of connections and the messages being sent to the server.
 */
public class Server {
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        JFrame app = new JFrame("Server View");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        ServerView view = new ServerView();
        app.setContentPane(view);
        app.pack();
        app.setVisible(true);
        app.setResizable(false);
    }
}
