package gomoku;

/**
 * The ConnectingView displays the status of the connection between
 * the client and the server through a textArea.
 */
public class ConnectingView extends javax.swing.JPanel {
    ConnectingViewController vcon;
    
    /**
     * Constructor, creates and initializes the ConnectingView components
     * @param con, the ConnectingViewController
     */
    public ConnectingView(ConnectingViewController con) {
        vcon = con;
        initComponents();
    }
    
    /**
     * Sets the textArea with the msg
     * @param msg, the message that displays the status of the connection
     */
    public void updatelog(String msg){
        txtLog.setText(msg);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        txtLog = new javax.swing.JTextField();
        btnCancel = new javax.swing.JButton();

        setBackground(new java.awt.Color(204, 204, 255));

        btnCancel.setText("Cancel");
        btnCancel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLog)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(btnCancel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(315, 315, 315)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txtLog, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnCancel)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Calls the cancelRun and goBack method from the ConnectionViewController
     * @param evt, when cancel is selected
     */
    private void btnCancelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCancelActionPerformed
        vcon.cancelRun();
        vcon.goBack();
    }//GEN-LAST:event_btnCancelActionPerformed
    
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCancel;
    private javax.swing.JTextField txtLog;
    // End of variables declaration//GEN-END:variables
}