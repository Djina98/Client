/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Lecturer;
import rs.ac.bg.fon.ps.view.coordinator.MainCoordinator;
import rs.ac.bg.fon.ps.view.form.FrmLecturer;

/**
 *
 * @author Djina
 */
public class LecturerController {
    
    private final FrmLecturer frmLecturer;

    public LecturerController(FrmLecturer frmLecturer) {
        this.frmLecturer = frmLecturer;
        addActionListeners();
    }

    public void openForm() {
        frmLecturer.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
        frmLecturer.setVisible(true);  
    }

    private void addActionListeners() {
        frmLecturer.btnAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveLecturer();
            }

            private void saveLecturer(){ 
                try{
                
                    String firstname = frmLecturer.getTxtFirstname().getText().trim();
                    String lastname = frmLecturer.getTxtLastname().getText().trim();
                    String profession = frmLecturer.getTxtProfession().getText().trim();

                    if(firstname.isEmpty() || lastname.isEmpty() || profession.isEmpty()){
                        JOptionPane.showMessageDialog(frmLecturer, "You need to input all data");
                        return;
                    }


                    Lecturer lecturer = new Lecturer();
                    lecturer.setFirstname(firstname);
                    lecturer.setLastname(lastname);
                    lecturer.setProfession(profession);

                    Lecturer saved = Communication.getInstance().addLecturer(lecturer);
                    JOptionPane.showMessageDialog(frmLecturer, "Lecturer successfully saved" , "Success", JOptionPane.INFORMATION_MESSAGE);
                    frmLecturer.dispose();
                    
                
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(frmLecturer, "System unable to save new lecturer!" , "Error", JOptionPane.ERROR_MESSAGE);
                }
                
                
            }
        });
    }
    
}
