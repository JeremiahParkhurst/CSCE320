package gomoku;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class displays the post-game view that displays "You Win!!!" if the
 * player has not receive a message from the gameOver method within the 
 * GameViewController, else the player will have a WinLossPopupView that 
 * displays "You Lose..."
 * This class also has a button that returns the user to the [][][][][][]
 * 
 * 
 * 
 * 
 * 
 * 
 */
public class WinLossPopupView extends javax.swing.JPanel {
    GameViewController gvc;
    SignInViewController svc;
    ConnectingViewController cvc;
    TitleViewController tvc;
    String ip = "152.117.243.155";
    
    public WinLossPopupView(GameViewController gv, SignInViewController sv){
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

        lossLabel = new javax.swing.JLabel();
        winLabel = new javax.swing.JLabel();
        mmButton = new javax.swing.JButton();
        tvButton = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 255));

        lossLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lossLabel.setText("You Lose...");

        winLabel.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        winLabel.setText("You Win!!!!");

        mmButton.setText("Return to Matchmaking");
        mmButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mmButtonMouseClicked(evt);
            }
        });

        tvButton.setLabel("Return to Title View");
        tvButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tvButtonMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(tvButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lossLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(mmButton, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(winLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lossLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(winLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(mmButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(tvButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void mmButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mmButtonMouseClicked
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
    }//GEN-LAST:event_mmButtonMouseClicked

    private void tvButtonMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tvButtonMouseClicked
        tvc = new TitleViewController(ip);
        tvc.showView();
    }//GEN-LAST:event_tvButtonMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public javax.swing.JLabel lossLabel;
    public javax.swing.JButton mmButton;
    public javax.swing.JButton tvButton;
    public javax.swing.JLabel winLabel;
    // End of variables declaration//GEN-END:variables
}
