/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.Socket;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.view.coordinator.MainCoordinator;
import rs.ac.bg.fon.ps.view.form.FrmConnection;
import rs.ac.bg.fon.ps.view.form.util.FormMode;

/**
 *
 * @author Djina
 */
public class ConnectionController {
    private final FrmConnection frmConnection;

    public ConnectionController(FrmConnection frmConnection) {
        this.frmConnection = frmConnection;
        addActionListeners();
    }

    private void addActionListeners() {
        frmConnection.btnConnectAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                Socket socket = new Socket(frmConnection.getTxtAddress().getText(), Integer.parseInt(frmConnection.getTxtPort().getText()));
                Communication.getInstance().setSocket(socket);
                JOptionPane.showMessageDialog(null, "Successfully connected!", "Success", JOptionPane.INFORMATION_MESSAGE);
                
                frmConnection.dispose();
                MainCoordinator.getInstance().openLoginForm();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Server is not listening on this port!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            }
        });
    }
    
    public void openForm(){
        frmConnection.setLocationRelativeTo(null);
        frmConnection.setVisible(true);
    }
}
