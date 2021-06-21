/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.view.constants.Constants;
import rs.ac.bg.fon.ps.view.coordinator.MainCoordinator;
import rs.ac.bg.fon.ps.view.form.FrmMain;

/**
 *
 * @author Djina
 */
public class MainController {
    
    private final FrmMain frmMain;
   
    
    public MainController(FrmMain frmMain) {
        this.frmMain = frmMain;
        addActionListeners();
    }

    private void addActionListeners() {
        frmMain.jmiNewStudentAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jmiNewStudentActionPerformed(actionEvent);
            }

            private void jmiNewStudentActionPerformed(ActionEvent actionEvent) {
                MainCoordinator.getInstance().openAddNewStudentForm();
            }
        });
        
        frmMain.jmiNewLecturerAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openNewLecturerForm();
            }
        });
        
        frmMain.jmiShowAllStudentsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jmiShowAllStudentsActionPerformed(actionEvent);
            }

            private void jmiShowAllStudentsActionPerformed(ActionEvent actionEvent) {
                MainCoordinator.getInstance().openViewAllStudentsForm();
            }
        });
        
        frmMain.jmiShowAllCoursesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openViewAllCoursesForm();
            }
        });
        
        frmMain.jmiAboutSoftwareAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openAboutSoftwareForm();
            }
        });
        
        frmMain.jmiNewCourseAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                jmiAddNewCourseActionPerformed(actionEvent);
            }

            private void jmiAddNewCourseActionPerformed(ActionEvent actionEvent) {
                MainCoordinator.getInstance().openAddNewCourseForm();
            }
        });
        
        frmMain.btnLogoutAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            int response = JOptionPane.showConfirmDialog(frmMain, "Do you want to log out?");
            if (response == JOptionPane.YES_OPTION) {

                try {           
                    boolean success = Communication.getInstance().logout((User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER));           
                    if (success == true) {
                        frmMain.dispose();
                    }

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(frmMain, "Unable to log out current user.", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                }
            }  
          }
        });
    }

    public void openForm() {
        User user = (User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
        frmMain.getLblUser().setText(user.getFirstname() + " " + user.getLastname());
        frmMain.setVisible(true);
    }
    
    public FrmMain getFrmMain() {
        return frmMain;
    }
}
