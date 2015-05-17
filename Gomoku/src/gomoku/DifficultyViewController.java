package gomoku;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.JFrame;

/**
 * The DifficultyViewController handles and listens to all actions preformed
 * from the DifficultyView.
 * This class stores information about which AI difficulty the user chooses and
 * opens the GameView with the given AI difficulty.
 */
public class DifficultyViewController implements ViewController{
    private DifficultyView view;
    private JFrame app;
    private TitleViewController tvc;
    
    /**
     * Constructor, initializes the DifficultyViewController
     * @param vc, the TitleViewController that directs to
     * the DifficultyViewController
     */
    public DifficultyViewController(TitleViewController vc){
        view = new DifficultyView(this);
        app = new JFrame("Gomoku");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setContentPane(view);
        app.pack();
        app.setResizable(false);
        tvc = vc;
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
     * Hides this view and opens the TitleView
     */
    public void mainMenu(){
        System.out.println("Entered teh main menu function");
        this.hideView();
        tvc.showView();
    }
    
    /**
     * This method is called when the player selects easy
     * @param difficulty the difficulty of the AI
     */
    public void easyButtonChosen(int difficulty){
        GameViewController gcon = new GameViewController(this, 0);
        gcon.showView();
        this.hideView();
    }
    
    /**
     * This method is called when the player selects medium
     * @param difficulty the difficulty of the AI
     */
    public void mediumButtonChosen(int difficulty){
        GameViewController gcon = new GameViewController(this, 1);
        gcon.showView();
        this.hideView();
    }
    
    /**
     * This method is called when the player selects hard
     * @param difficulty the difficulty of the AI
     */
    public void hardButtonChosen(int difficulty){
        GameViewController gcon = new GameViewController(this, 2);
        gcon.showView();
        this.hideView();
    }
}