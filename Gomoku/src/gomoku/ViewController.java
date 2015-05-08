package gomoku;

/**
 * Interface
 * All views will implement the showView and hideView methods
 */
public interface ViewController {
    
    /**
     * Abstract method
     * Displays the view
     */
    public void showView();
    
    /**
     * Abstract method
     * Hides the view
     */
    public void hideView();
}
