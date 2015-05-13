package gomoku;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * The GameView sends information to the GameController when an action is
 * preformed, such as when the user selects send message to send a message
 * that they have typed in the textField, which will be displayed to the the
 * textArea by calling the actionlistener methods in this class which calls
 * the methods in the GameController.
 * This class also has the view of the chat components and some of the
 * GameBoardView components, such as a text area to display whether a valid move
 * has been made.
 * This view has the send message button and the game board which displays
 * the Gomoku game, and a Turn indicator textArea that displays whose turn it is
 */
public class GameView extends javax.swing.JPanel {
    GameViewController vcon;
    String user;
    
    /**
     * Constructor, creates and initializes the GameView
     * Notifies the player that their Gomoku game piece is black.
     */
    public GameView(GameViewController con) {
        initComponents();
        vcon = con;
        txtLog.append("Your Game Piece Color is Black" + "\n");
    }
    
    /**
     * Displays the username of the player
     * @param name, the username of the player
     */
    public void displayUserName(String name){
        user = name;
    }
    
    /**
     * Post the player's text messages onto the text area
     * @param msg, the message to be sent
     */
    public void appendChat(String msg){
        txtLog.append(msg + "\n");
    }
    
    /**
     * Appends a message such as "illegal move", or "valid move" to text area
     * @param msg, the message to be displayed on the status text area
     */
    public void appendMoveStatus(String msg){
        statusTextArea.setText(null);
        statusTextArea.append(msg);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        txtLog = new javax.swing.JTextArea();
        txtToSend = new javax.swing.JTextField();
        chatLabel = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        sendMoveButton = new javax.swing.JButton();
        turnTextLabel = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        turnTextArea = new javax.swing.JTextArea();
        jScrollPane3 = new javax.swing.JScrollPane();
        statusTextArea = new javax.swing.JTextArea();
        stuatsLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 255));

        txtLog.setColumns(20);
        txtLog.setRows(5);
        jScrollPane1.setViewportView(txtLog);

        txtToSend.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtToSendActionPerformed(evt);
            }
        });

        chatLabel.setText("Press Enter to Send Message");

        jLabel2.setText("Chat Box:");

        sendMoveButton.setText("Send Move");
        sendMoveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMoveButtonActionPerformed(evt);
            }
        });

        turnTextLabel.setText("Turn:");

        jScrollPane2.setViewportView(turnTextArea);

        jScrollPane3.setViewportView(statusTextArea);

        stuatsLabel.setText("Status:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane1)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(turnTextLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(chatLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 211, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(sendMoveButton, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                            .addComponent(jScrollPane3)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtToSend, javax.swing.GroupLayout.PREFERRED_SIZE, 166, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(stuatsLabel)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(turnTextLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtToSend, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(stuatsLabel))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(sendMoveButton)
                            .addComponent(chatLabel))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        turnTextLabel.getAccessibleContext().setAccessibleName("turnText");
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Appends the player's username and their message to the txtLog
     * Clears the txtToSend textArea after the message has been sent
     * @param evt
     */
    private void txtToSendActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtToSendActionPerformed
        String msg = txtToSend.getText();
        String formattedMsg = "M, " + msg;
        vcon.sendMsg(formattedMsg);
        txtLog.append(user + ": " + msg + "\n");
        txtToSend.setText("");
    }//GEN-LAST:event_txtToSendActionPerformed
    
    /**
     * This method is called when the uses selects the Send Move button.
     * @param evt, calls the SendMoveButtonChosen method within the
     * GameViewController.
     */
    private void sendMoveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMoveButtonActionPerformed
        vcon.sendMoveButtonChosen();
    }//GEN-LAST:event_sendMoveButtonActionPerformed
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel chatLabel;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    public javax.swing.JButton sendMoveButton;
    private javax.swing.JTextArea statusTextArea;
    private javax.swing.JLabel stuatsLabel;
    public javax.swing.JTextArea turnTextArea;
    private javax.swing.JLabel turnTextLabel;
    private javax.swing.JTextArea txtLog;
    private javax.swing.JTextField txtToSend;
    // End of variables declaration//GEN-END:variables
}