package gomoku;

import java.util.ArrayList;

/**
 * The MatchmakingView sends information to the MatchMakingController 
 * when an action is preformed.
 * This view has two buttons, one to send an invite to another player, and
 * one for going back to the SignInView. This view also has a JList where
 * players can view players that are currently online.
 */
public class MatchmakingView extends javax.swing.JPanel {
MatchmakingViewController vcon;
    /**
     * Creates new form MatchmakingView
     */
    public MatchmakingView(MatchmakingViewController vc) {
        initComponents();
        vcon = vc;
    }

    /**
     * Updates the list of current users online
     * @param a, the list of the users
     */
    public void updateUsers(ArrayList<String> a, String username){
        a.remove(username);
        String[] s = new String[a.size()];
        for(int i = 0; i < a.size(); i++){
            s[i] = a.get(i);
        }
        playerList.setListData(s);
    }
    
    public void updateRequests(ArrayList<String> a){
        String[] s = new String[a.size()];
        for(int i = 0; i < a.size(); i++){
            s[i] = a.get(i);
        }
        requestList.setListData(s);
    }
    
    public void updateUName(String name){
        matchmakingLabel.setText("Matchmaking for: " + name);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        matchmakingLabel = new javax.swing.JLabel();
        playersOnlineLabel = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        playerList = new javax.swing.JList();
        sendgameButton = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        requestList = new javax.swing.JList();
        acceptButton = new javax.swing.JButton();
        declineLabel = new javax.swing.JButton();
        requestsLabel = new javax.swing.JLabel();

        setBackground(new java.awt.Color(204, 204, 255));

        matchmakingLabel.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        matchmakingLabel.setText("Matchmaking");

        playersOnlineLabel.setText("Players Online:");

        jScrollPane1.setViewportView(playerList);

        sendgameButton.setText("Send Game Invite");
        sendgameButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendgameButtonActionPerformed(evt);
            }
        });

        menuButton.setText("Menu");
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButtonActionPerformed(evt);
            }
        });

        jScrollPane2.setViewportView(requestList);

        acceptButton.setText("Accept");
        acceptButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                acceptButtonActionPerformed(evt);
            }
        });

        declineLabel.setText("Decline");
        declineLabel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                declineLabelActionPerformed(evt);
            }
        });

        requestsLabel.setText("Requests:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(matchmakingLabel)
                            .addComponent(playersOnlineLabel)
                            .addComponent(menuButton)
                            .addComponent(sendgameButton))
                        .addGap(0, 120, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(acceptButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(declineLabel)))
                    .addComponent(requestsLabel))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(matchmakingLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(playersOnlineLabel)
                    .addComponent(requestsLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 172, Short.MAX_VALUE)
                    .addComponent(jScrollPane1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendgameButton)
                    .addComponent(acceptButton)
                    .addComponent(declineLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(menuButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void sendgameButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendgameButtonActionPerformed
         String selected = playerList.getSelectedValue().toString();
        vcon.sendInvite(selected);
    }//GEN-LAST:event_sendgameButtonActionPerformed

    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
        vcon.logOff();
        vcon.menuBtn();
    }//GEN-LAST:event_menuButtonActionPerformed

    private void acceptButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_acceptButtonActionPerformed
        String from = requestList.getSelectedValue().toString();
        if(from == null){
            from = requestList.getComponent(0).toString();
        }
        vcon.acceptInvite(from);
    }//GEN-LAST:event_acceptButtonActionPerformed

    private void declineLabelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_declineLabelActionPerformed
       String from = requestList.getSelectedValue().toString();
        if(from == null){
            from = requestList.getComponent(0).toString();
        }
        vcon.declineInvite(from);
    }//GEN-LAST:event_declineLabelActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton acceptButton;
    private javax.swing.JButton declineLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel matchmakingLabel;
    private javax.swing.JButton menuButton;
    private javax.swing.JList playerList;
    private javax.swing.JLabel playersOnlineLabel;
    private javax.swing.JList requestList;
    private javax.swing.JLabel requestsLabel;
    private javax.swing.JButton sendgameButton;
    // End of variables declaration//GEN-END:variables
}