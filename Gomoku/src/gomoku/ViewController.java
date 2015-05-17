/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gomoku;

/**
 *Interface
 *All views will implement showView and hideView
 */
interface ViewController {
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
