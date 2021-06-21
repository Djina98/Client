/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.EOFException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.view.constants.Constants;
import rs.ac.bg.fon.ps.view.coordinator.MainCoordinator;
import rs.ac.bg.fon.ps.view.form.FrmLogin;

/**
 *
 * @author Djina
 */
public class LoginController {
    
    private final FrmLogin frmLogin;

    public LoginController(FrmLogin frmLogin) {
        this.frmLogin = frmLogin;
        addActionListeners();
    }
    
    public void openForm(){
        frmLogin.setVisible(true);
    }

    private void addActionListeners() {
        frmLogin.btnLoginAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                loginUser(actionEvent);
            }

            private void loginUser(ActionEvent actionEvent) {
                resetForm();
                try{
                    String username = frmLogin.getTxtUsername().getText().trim();
                    String password = String.copyValueOf(frmLogin.getTxtPassword().getPassword());
                    
                    validateForm(username, password);
                    
                    
                    User user = Communication.getInstance().login(username, password);
                    JOptionPane.showMessageDialog(
                            frmLogin,
                            "System has successfully logged you in!",
                            "Login", JOptionPane.INFORMATION_MESSAGE
                    );
                    frmLogin.dispose();
                    MainCoordinator.getInstance().addParam(Constants.CURRENT_USER, user);
                    MainCoordinator.getInstance().openMainForm();
                    
                } catch (EOFException ex) {
                    JOptionPane.showMessageDialog(frmLogin, "Server is not listening. Unable to connect.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frmLogin, "Server is not listening. Unable to connect.", "Error", JOptionPane.ERROR_MESSAGE);
                    System.exit(0);     
                }catch(Exception ex){
                    Logger.getLogger(FrmLogin.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmLogin, "System unable to log you in!" , "Login error", JOptionPane.ERROR_MESSAGE);
                }
                
            }

            private void resetForm() {
                frmLogin.getLblUsernameError().setText("");
                frmLogin.getLblPasswordError().setText("");
            }

            private void validateForm(String username, String password) throws Exception {
                String errorMessage = "";
                if (username.isEmpty()) {
                    frmLogin.getLblUsernameError().setText("Username can not be empty!");
                    errorMessage += "Username can not be empty!\n";
                }
                if (password.isEmpty()) {
                    frmLogin.getLblPasswordError().setText("Password can not be empty!");
                    errorMessage += "Password can not be empty!\n";
                }
                if (!errorMessage.isEmpty()) {
                    throw new Exception(errorMessage);
                }
            }
        });
    }
}
