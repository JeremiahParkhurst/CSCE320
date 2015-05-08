package gomoku;

import javax.swing.JFrame;

/**
 * The PostGameViewController handles and listens to all actions preformed
 * from the PostGameView.
 * This class displays "You Win" or "You Lose" depending on whether the player
 * has won or loss. The menu button directs the user back to the
 *
 * NOTE: NOT FULLY IMPLEMENTED
 */
public class PostGameViewController implements ViewController{
    private PostGameView view;
    private JFrame app;
    private GameViewController vcon;
    
    /**
     * Constructor, initializes the PostGameViewController
     * @param vc, the GameViewController that directs to
     * the PostGameViewController
     */
    public PostGameViewController(GameViewController vc){
        view = new PostGameView(this);
        app = new JFrame("Gomoku");
        app.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        app.setContentPane(view);
        app.pack();
        app.setResizable(false);
        vcon = vc;
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
        vcon.showView();
    }
}