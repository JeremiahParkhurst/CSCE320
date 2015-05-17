package gomoku;

import java.io.IOException;

/**
 * This class displays the post-game view that displays "You Win!!!" if the
 * player has not receive a message from the gameOver method within the
 * GameViewController, else the player will have a WinLossPopupView that
 * displays "You Lose..."
 * This class also has a button that returns the user to the matchmaking view 
 * if they were playing against another player or to return to the title view, 
 * otherwise the player will only have option of returning to the title view if 
 * they were playing versus an AI.
 */
public class WinLossPopupView extends javax.swing.JPanel {
    GameViewController gvc;
    SignInViewController svc;
    TitleViewController tvc;
    Gomoku go;
    String ip = go.IPaddress;
    
    /**
     * Creates new form WinLossPopupView2
     */
    public WinLossPopupView(GameViewController gv, SignInViewController sv) {
        gvc = gv;
        svc = sv;
        initComponents();
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        mmButton = new javax.swing.JButton();
        tvButton = new javax.swing.JButton();
        winLossJTextArea = new javax.swing.JTextField();

        setBackground(new java.awt.Color(204, 204, 255));

        mmButton.setText("Return to Matchmaking");
        mmButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mmButtonActionPerformed(evt);
            }
        });

        tvButton.setText("Return to Title");
        tvButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                tvButtonActionPerformed(evt);
            }
        });

        winLossJTextArea.setFont(new java.awt.Font("Tahoma", 0, 72)); // NOI18N
        winLossJTextArea.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(winLossJTextArea)
                    .addComponent(tvButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mmButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(winLossJTextArea, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mmButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tvButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Creates a new TitleViewController with this player's IP address passed
     * in, and calls the method within the TitleViewController to alow the 
     * player to relog/rejoin and appear on the MatchmakingView.
     * @param evt, when the "Return to Matchmaking" button is chosen
     */
    private void mmButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mmButtonActionPerformed
        tvc = new TitleViewController(ip);
        tvc.rejoinMultiPlayer(svc.globalUser, svc.globalPassword);
        gvc.hideView(); // hides game view and popup view
        try { // tries to close the socket
            gvc.socket.close();
            try{ // tries to close the serverSocket
                //(in its own try catch because only one client with have created a serverSocket
                gvc.serverSocket.close();
            }
            catch(NullPointerException np){
                System.out.println("This client cannot close the serverSocket, Other player will do so");
            }
        } catch (IOException ex) {
            System.out.println("Server and/or Server Socket not closed properly");
        }
    }//GEN-LAST:event_mmButtonActionPerformed
    
    /**
     * Displays the TitleView and creates a new TitleViewController using
     * this player's IP.
     * @param evt, when the "Return to Title" is chosen.
     */
    private void tvButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_tvButtonActionPerformed
        tvc = new TitleViewController(ip);
        tvc.showView();
        gvc.closeSingleEndGame();
    }//GEN-LAST:event_tvButtonActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JButton mmButton;
    public javax.swing.JButton tvButton;
    public javax.swing.JTextField winLossJTextArea;
    // End of variables declaration//GEN-END:variables
}