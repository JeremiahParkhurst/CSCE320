package gomoku;

import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * The SignInView sends information to the SignInViewController when an action
 * is preformed.
 * This view two textFields where the player inputs their username and password.
 * There are two buttons, login and create account, both of which sends
 * informations to the server through the SignInViewController.
 * The menu button returns the user to the TitleView.
 * There are two error messages in which the user can receive upon entering
 * a username with invalid characters, or inputing credentials that do no exist
 * in the server's text document (the text document has information about a
 * player's username and password).
 */
public class SignInView extends javax.swing.JPanel {
    SignInViewController vcon;
    
    /**
     * Creates new form SignInView
     * Sets error messages to false (invalid username, username doesn't exist)
     * @param vc, the SignInViewController
     */
    public SignInView(SignInViewController vc) {
        initComponents();
        vcon = vc;
        labError.setVisible(false);
        labInvalidChar.setVisible(false);
    }
    
    /**
     * @param tf, true if there is an error with the username or password
     */
    public void setLabError(boolean tf){
        labError.setVisible(tf);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        txtUsername = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        loginButton = new javax.swing.JButton();
        createButton = new javax.swing.JButton();
        menuButton = new javax.swing.JButton();
        labError = new javax.swing.JLabel();
        labInvalidChar = new javax.swing.JLabel();
        txtPassword = new javax.swing.JPasswordField();

        setBackground(new java.awt.Color(204, 204, 255));

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setText("Login");

        txtUsername.setColumns(15);

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel2.setText("Username:");

        jLabel3.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel3.setText("Password:");

        loginButton.setText("Login");
        loginButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                loginButtonActionPerformed(evt);
            }
        });

        createButton.setText("Create Account");
        createButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createButtonActionPerformed(evt);
            }
        });

        menuButton.setText("Menu");
        menuButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                menuButtonActionPerformed(evt);
            }
        });

        labError.setText("The username and password combo you have entered is not valid. Please try again.");
        labError.setFocusable(false);

        labInvalidChar.setText("There is an invalid character in the username or password.  '\\' & ','");

        txtPassword.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtPasswordActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(173, 173, 173)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(207, 207, 207))
            .addGroup(layout.createSequentialGroup()
                .addGap(86, 86, 86)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(3, 3, 3)))
                .addGap(18, 18, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(txtUsername)
                    .addComponent(txtPassword))
                .addGap(185, 185, 185))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(menuButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(createButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(loginButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(211, 211, 211))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(labError, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(49, 49, 49)
                        .addComponent(labInvalidChar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(58, 58, 58))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addGap(22, 22, 22)
                .addComponent(labInvalidChar, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtUsername, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(labError, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(loginButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(createButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(menuButton)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    
    /**
     * Sends the username and password to the SignInViewController and calls
     * the loginBtn method
     * @param evt, when the user selects the login button
     */
    private void loginButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_loginButtonActionPerformed
        String user = txtUsername.getText();
        String pass = txtPassword.getText();
        
        //check username and password for the forbiden chars
        if(isValidUserPass(user) && isValidUserPass(pass)){
            vcon.loginBtn(user, pass);
        }
        else{
            labInvalidChar.setVisible(true);
        }
        
    }//GEN-LAST:event_loginButtonActionPerformed
    
    /**
     * Calls mainMenu from the PostGameViewController
     * @param evt, when the user selects the menu button
     */
    private void menuButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_menuButtonActionPerformed
        vcon.mainMenu();
    }//GEN-LAST:event_menuButtonActionPerformed
    
    /**
     * Calls the createNewUser method from the SignInViewController
     * @param evt, when the user selects the create account button
     */
    private void createButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createButtonActionPerformed
        String user = txtUsername.getText();
        String pass = txtPassword.getText();
        
        //check username and password for the forbiden chars
        if(isValidUserPass(user) && isValidUserPass(pass)){
            vcon.createNewUser(user, pass);
        }
        else{
            labInvalidChar.setVisible(true);
        }
        
    }//GEN-LAST:event_createButtonActionPerformed
    
    private void txtPasswordActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtPasswordActionPerformed
        String user = txtUsername.getText();
        String pass = txtPassword.getText();
        
        //check username and password for the forbiden chars
        if(isValidUserPass(user) && isValidUserPass(pass)){
            vcon.loginBtn(user, pass);
        }
        else{
            labInvalidChar.setVisible(true);
        }
    }//GEN-LAST:event_txtPasswordActionPerformed
    
    /**
     * @param msg, the inputed msg in the username textfield
     * @return false if no characters are in the username textfield or
     * if any invalid characters are present, otherwise true
     */
    private boolean isValidUserPass(String msg){
        if(msg.length() == 0){
            return false;
        }
        else{
            for(int i = 0; i < msg.length(); i++){
                if(msg.charAt(i) == '\\' || msg.charAt(i) == ','|| msg.charAt(i) == ' '){//add any others during meeting
                    return false;
                }
            }
        }
        return true;
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton createButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel labError;
    private javax.swing.JLabel labInvalidChar;
    private javax.swing.JButton loginButton;
    private javax.swing.JButton menuButton;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsername;
    // End of variables declaration//GEN-END:variables
}