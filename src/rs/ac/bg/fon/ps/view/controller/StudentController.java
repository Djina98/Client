/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Course;
import rs.ac.bg.fon.ps.domain.CourseItem;
import rs.ac.bg.fon.ps.domain.Faculty;
import rs.ac.bg.fon.ps.domain.Student;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.view.constants.Constants;
import rs.ac.bg.fon.ps.view.coordinator.MainCoordinator;
import rs.ac.bg.fon.ps.view.form.FrmStudent;
import rs.ac.bg.fon.ps.view.form.util.FormMode;

/**
 *
 * @author Djina
 */
public class StudentController {
    
    private final FrmStudent frmStudent;

    public StudentController(FrmStudent frmStudent) {
        this.frmStudent = frmStudent;
        addActionListeners();
    }

    private void addActionListeners() {
        frmStudent.btnSaveAddActionListeners(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                saveStudent();
            }

            private void saveStudent() {
                Student student = null;
                try{
                    try{
                        student = getStudentFromForm();
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(frmStudent, ex.getMessage(), "Warning", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    
                    Student saved = Communication.getInstance().addStudent(student);
                    
                    JOptionPane.showMessageDialog(frmStudent, "Student successfully saved with id: " +saved.getId(), "Success", JOptionPane.INFORMATION_MESSAGE);
                    frmStudent.dispose();
                    
                }catch(Exception ex){
                    Logger.getLogger(FrmStudent.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmStudent, "System unable to save new student!" , "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            
        });
        
        frmStudent.btnEnableChangesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                setupComponents(FormMode.FORM_EDIT);
            }
        });
        
        frmStudent.btnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cancel();
            }

            private void cancel() {
                frmStudent.dispose();
            }
        });
        
        frmStudent.btnDeleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                delete();
            }

            private void delete() {    
                try{
                    Student student = getStudentFromForm();
                    List<Course> courses = Communication.getInstance().getAllCourses();
                    ArrayList<CourseItem> itemsToDelete = new ArrayList<>();
                    
                    for (Course course : courses) {
                        for (CourseItem item : course.getCourseItems()) {
                            if(item.getStudent().getId().equals(student.getId())){
                                itemsToDelete.add(item);
                            }
                        }
                    }
                    
                    student.setItems(itemsToDelete);
      
                    int response = JOptionPane.showConfirmDialog(frmStudent, 
                                "Are you sure you want to delete student with id: " 
                                + student.getId() + "?");
                        if (response == JOptionPane.YES_OPTION) {
                            Communication.getInstance().deleteStudent(student);
                            JOptionPane.showMessageDialog(frmStudent, "Student successfully deleted!\n", "Success", JOptionPane.INFORMATION_MESSAGE);
                            frmStudent.dispose();
                        }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(frmStudent, "Error deleting student!\n" /*+ ex.getMessage()*/, "Error", JOptionPane.ERROR_MESSAGE);
                    Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        frmStudent.btnEditAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                editStudent();
            }

            private void editStudent() {
                Student student = null;
                try{
                    try{
                        student = getStudentFromForm();
                    }catch(Exception ex){
                        JOptionPane.showMessageDialog(frmStudent, ex.getMessage(), "Warning", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    int response = JOptionPane.showConfirmDialog(frmStudent, 
                                "Do you want to save changes on student with id: " 
                                + student.getId() + "?");
                        if (response == JOptionPane.YES_OPTION) {
                            Communication.getInstance().editStudent(student);
                            JOptionPane.showMessageDialog(frmStudent, "Student successfully changed!\n", "Success", JOptionPane.INFORMATION_MESSAGE);
                            frmStudent.dispose();
                        }
                }catch(Exception ex){
                    JOptionPane.showMessageDialog(frmStudent, "Error editting student!\n" /*+ ex.getMessage()*/, "Error", JOptionPane.INFORMATION_MESSAGE);
                    Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
    public void openForm(FormMode formMode){
        frmStudent.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
        prepareView(formMode);
        frmStudent.setVisible(true);    
    }
    
    private void prepareView(FormMode formMode) {
        fillCmbFaculty();
        setupComponents(formMode);
    }
    
    private void setupComponents(FormMode formMode) {
        switch (formMode) {
            case FORM_ADD:
                frmStudent.getBtnCancel().setEnabled(true);
                frmStudent.getBtnDelete().setEnabled(false);
                frmStudent.getBtnEdit().setEnabled(false);
                frmStudent.getBtnEnableChanges().setEnabled(false);
                frmStudent.getBtnSave().setEnabled(true);

                frmStudent.getTxtID().setEnabled(false);
                frmStudent.getTxtFirstname().setEnabled(true);
                frmStudent.getTxtLastname().setEnabled(true);
                frmStudent.getTxtStudentIDNumber().setEnabled(true);
                frmStudent.getTxtAverageGrade().setEnabled(true);
                frmStudent.getTxtYearOfStudy().setEnabled(true);
                frmStudent.getCmbFaculty().setEnabled(true);
                User user = (User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
                frmStudent.getTxtUser().setText(user.getFirstname() + " " + user.getLastname());
                break;
            case FORM_VIEW:
                frmStudent.getBtnCancel().setEnabled(true);
                frmStudent.getBtnDelete().setEnabled(true);
                frmStudent.getBtnEdit().setEnabled(false);
                frmStudent.getBtnEnableChanges().setEnabled(true);
                frmStudent.getBtnSave().setEnabled(false);

                //disable value changes
                frmStudent.getTxtID().setEnabled(false);
                frmStudent.getTxtFirstname().setEnabled(false);
                frmStudent.getTxtLastname().setEnabled(false);
                frmStudent.getTxtStudentIDNumber().setEnabled(false);
                frmStudent.getTxtAverageGrade().setEnabled(false);
                frmStudent.getTxtYearOfStudy().setEnabled(false);
                frmStudent.getCmbFaculty().setEnabled(false);

                //get student
                Student student = (Student) MainCoordinator.getInstance().getParam(Constants.PARAM_STUDENT);
                frmStudent.getTxtID().setText(student.getId() + "");
                frmStudent.getTxtFirstname().setText(student.getFirstname());
                frmStudent.getTxtLastname().setText(student.getLastname());
                frmStudent.getTxtStudentIDNumber().setText(student.getStudentIDNumber());
                frmStudent.getTxtAverageGrade().setText(String.valueOf(student.getAverageGrade()));
                frmStudent.getTxtYearOfStudy().setText(String.valueOf(student.getYearOfStudy()));
                frmStudent.getCmbFaculty().setSelectedItem(student.getFaculty());
                frmStudent.getTxtUser().setText(student.getUser().getFirstname() + " " + student.getUser().getLastname());
                break;
            case FORM_EDIT:
                frmStudent.getBtnCancel().setEnabled(true);
                frmStudent.getBtnDelete().setEnabled(false);
                frmStudent.getBtnEdit().setEnabled(true);
                frmStudent.getBtnEnableChanges().setEnabled(false);
                frmStudent.getBtnSave().setEnabled(false);

                //disable value changes
                frmStudent.getTxtID().setEnabled(false);
                frmStudent.getTxtFirstname().setEnabled(true);
                frmStudent.getTxtLastname().setEnabled(true);
                frmStudent.getTxtStudentIDNumber().setEnabled(true);
                frmStudent.getTxtAverageGrade().setEnabled(true);
                frmStudent.getTxtYearOfStudy().setEnabled(true);
                frmStudent.getCmbFaculty().setEnabled(true);
                break;
        }
    }

   
    private void fillCmbFaculty() {
        try {
            frmStudent.getCmbFaculty().removeAllItems();
            List<Faculty> faculties = Communication.getInstance().getAllFaculties();
            frmStudent.getCmbFaculty().setModel(new DefaultComboBoxModel<>(faculties.toArray()));
        } catch (Exception ex) {
            Logger.getLogger(StudentController.class.getName()).log(Level.SEVERE, null, ex);
            // moze jop
        }
    }
    
    private Student getStudentFromForm() throws Exception {
        Student student = new Student();
        
        student.setFirstname(frmStudent.getTxtFirstname().getText().trim());
        student.setLastname(frmStudent.getTxtLastname().getText().trim());
        student.setStudentIDNumber(frmStudent.getTxtStudentIDNumber().getText().trim());
        
        if(!frmStudent.getTxtID().getText().isEmpty()){
            student.setId(Long.parseLong(frmStudent.getTxtID().getText().trim()));
        }
        
        
        String averageGrade = frmStudent.getTxtAverageGrade().getText().trim();
        String yearOfStudy = frmStudent.getTxtYearOfStudy().getText().trim();
                    
        if(averageGrade.isEmpty() || yearOfStudy.isEmpty()){
             throw new Exception("You need to input all data");
        }
         
        try{
            student.setAverageGrade(Double.valueOf(averageGrade));
        }catch(NumberFormatException ex){
            throw new Exception("Average grade must be number!");
        }
        
        try{
            student.setYearOfStudy(Integer.valueOf(yearOfStudy));
        }catch(NumberFormatException ex){
            throw new Exception("Year of study must be number!");
        }
        
        
        student.setFaculty((Faculty) frmStudent.getCmbFaculty().getSelectedItem());
        student.setUser((User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER));
        boolean valid = validateIfEmpty(student);
                    
        if(!valid){
            throw new Exception("You need to input all data");
        }
                    
        if(student.getAverageGrade() < 6 || student.getAverageGrade() > 10){
            throw new Exception("Average grade range is from 6 to 10");
        }
                    
        if(student.getYearOfStudy() <= 0){
            throw new Exception("Year of study must be greater than 0");
        }

        return student;
    }
    
    private boolean validateIfEmpty(Student student) {
        if(student.getFirstname().isEmpty() || student.getLastname().isEmpty()|| student.getStudentIDNumber().isEmpty()){
            return false;
        }
                
        return true;
    }
 
}
