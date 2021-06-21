/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.RowFilter;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Course;
import rs.ac.bg.fon.ps.domain.CourseItem;
import rs.ac.bg.fon.ps.domain.Student;
import rs.ac.bg.fon.ps.view.constants.Constants;
import rs.ac.bg.fon.ps.view.coordinator.MainCoordinator;
import rs.ac.bg.fon.ps.view.form.FrmStudent;
import rs.ac.bg.fon.ps.view.form.FrmViewStudents;
import rs.ac.bg.fon.ps.view.form.components.table.StudentTableModel;

/**
 *
 * @author Djina
 */
public class ViewAllStudentsController {
    
    private final FrmViewStudents frmViewStudents;
    private List<Student> students;

    public ViewAllStudentsController(FrmViewStudents frmViewStudents) {
        this.frmViewStudents = frmViewStudents;
        addActionListeners();
    }

    private void addActionListeners() {
        frmViewStudents.btnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = frmViewStudents.getTblStudents().getSelectedRow();
                
                if(row >= 0){
                   
                    StudentTableModel stm = ((StudentTableModel) frmViewStudents.getTblStudents().getModel());
                    Long id = (Long) frmViewStudents.getTblStudents().getValueAt(row, 0);
                    
                    for (Student student : students) {
                        if(student.getId() == id){
                            MainCoordinator.getInstance().addParam(Constants.PARAM_STUDENT, student);
                            MainCoordinator.getInstance().openStudentDetailsForm();
                        }
                    }
                    
                }else{
                    JOptionPane.showMessageDialog(frmViewStudents, "You must select a student", "Students details", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmViewStudents.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblStudents();
            }

        });
        
        frmViewStudents.btnAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openAddNewStudentForm();
            }
        });
        
        frmViewStudents.btnRemoveAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        

            private void deleteStudent() {
                try{
                    int row = frmViewStudents.getTblStudents().getSelectedRow();
                    if(row >= 0){
                        StudentTableModel stm = ((StudentTableModel) frmViewStudents.getTblStudents().getModel());
                        Long id = (Long) frmViewStudents.getTblStudents().getValueAt(row, 0);
                        Student student = null;
                        
                        for (Student s : students) {
                            if(s.getId() == id){
                                student = s;
                            }
                        }
                        
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
                        int response = JOptionPane.showConfirmDialog(frmViewStudents, 
                                "Are you sure you want to delete student with id: " 
                                + student.getId() + "?");
                        if (response == JOptionPane.YES_OPTION) {
                            
                            Communication.getInstance().deleteStudent(student);  
                            stm.deleteStudent(student);
                            JOptionPane.showMessageDialog(frmViewStudents, "Student successfully removed", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                        
                        
                    }else{
                        JOptionPane.showMessageDialog(frmViewStudents, "You must select a student", "Student details", JOptionPane.ERROR_MESSAGE);
                    }
            
                }catch(Exception ex){
                    Logger.getLogger(FrmViewStudents.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmViewStudents, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        
        frmViewStudents.txtFilterAddKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                filter(frmViewStudents.getTxtSearch().getText());
            }

            private void filter(String text) {
                TableModel tm = frmViewStudents.getTblStudents().getModel();
                TableRowSorter<TableModel> trs = new TableRowSorter<>(tm);

                frmViewStudents.getTblStudents().setRowSorter(trs);
                trs.setRowFilter(RowFilter.regexFilter(text));
            }
        });
    }
    
    public void openForm(){
        frmViewStudents.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
        frmViewStudents.setVisible(true);
    }
    
    public void fillTblStudents(){
        
        try {
            students = Communication.getInstance().getAllStudents();
            StudentTableModel stm = new StudentTableModel(students);
            frmViewStudents.getTblStudents().setModel(stm);
            
            TableColumnModel tcm = frmViewStudents.getTblStudents().getColumnModel();

            tcm.getColumn(0).setPreferredWidth(5);
            tcm.getColumn(1).setPreferredWidth(5);
            tcm.getColumn(2).setPreferredWidth(5);
            tcm.getColumn(3).setPreferredWidth(5);
            tcm.getColumn(4).setPreferredWidth(10);
            tcm.getColumn(5).setPreferredWidth(10);
            tcm.getColumn(6).setPreferredWidth(250);
            
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmViewStudents, "Error: " + ex.getMessage(), "Error details", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ViewAllStudentsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void refresh() {
        fillTblStudents();
    }
  
}
