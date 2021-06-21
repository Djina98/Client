/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
import rs.ac.bg.fon.ps.domain.Student;
import rs.ac.bg.fon.ps.view.constants.Constants;
import rs.ac.bg.fon.ps.view.coordinator.MainCoordinator;
import rs.ac.bg.fon.ps.view.form.FrmViewCourses;
import rs.ac.bg.fon.ps.view.form.FrmViewStudents;
import rs.ac.bg.fon.ps.view.form.components.table.CourseTableModel;
import rs.ac.bg.fon.ps.view.form.components.table.StudentTableModel;

/**
 *
 * @author Djina
 */
public class ViewAllCoursesController {
    
    private final FrmViewCourses frmViewCourses;
    private List<Course> courses;

    public ViewAllCoursesController(FrmViewCourses frmCourses) {
        this.frmViewCourses = frmCourses;
        addActionListeners();
    }
    
    private void addActionListeners() {
        frmViewCourses.btnDetailsAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int row = frmViewCourses.getTblCourses().getSelectedRow();
    
                if(row >= 0){
                    CourseTableModel ctm = (CourseTableModel) frmViewCourses.getTblCourses().getModel();
                    Long id = (Long) frmViewCourses.getTblCourses().getValueAt(row, 0);
                    
                    for (Course course : courses) {
                        if(course.getId() == id){
                            MainCoordinator.getInstance().addParam(Constants.PARAM_COURSE, course);
                            MainCoordinator.getInstance().openCourseDetailsForm();
                        }
                    }       
                    
                }else{
                    JOptionPane.showMessageDialog(frmViewCourses, "You must select a course", "Course details", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmViewCourses.addWindowListener(new WindowAdapter() {
            @Override
            public void windowActivated(WindowEvent e) {
                fillTblCourses();
            }

        });
        
        frmViewCourses.btnAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainCoordinator.getInstance().openAddNewCourseForm();
            }
        });
        
        frmViewCourses.btnRemoveAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteCourse();
            }
        

            private void deleteCourse() {
                try{
                    int row = frmViewCourses.getTblCourses().getSelectedRow();
                    if(row >= 0){
                        CourseTableModel ctm = (CourseTableModel) frmViewCourses.getTblCourses().getModel();
                        Long id = (Long) frmViewCourses.getTblCourses().getValueAt(row, 0);
                        Course course = null;
                        for (Course c : courses) {
                            if(c.getId() == id){
                                course = c;  
                            }
                        }   
                        
                        int response = JOptionPane.showConfirmDialog(frmViewCourses, 
                                        "Are you sure you want to delete course with id: " 
                                        + course.getId() + "?");
                        if (response == JOptionPane.YES_OPTION) {           
                            Communication.getInstance().deleteCourse(course);
                            ctm.deleteCourse(course);
                            JOptionPane.showMessageDialog(frmViewCourses, "Course successfully deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                        
                    }else{
                        JOptionPane.showMessageDialog(frmViewCourses, "You must select a course", "Course details", JOptionPane.ERROR_MESSAGE);
                    }
            
                }catch(Exception ex){
                    Logger.getLogger(FrmViewCourses.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmViewCourses, "Unable to delete course!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });
        
        frmViewCourses.txtFilterAddKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                filter(frmViewCourses.getTxtSearch().getText());
            }

            private void filter(String text) {
                TableModel tm = frmViewCourses.getTblCourses().getModel();
                TableRowSorter<TableModel> trs = new TableRowSorter<>(tm);

                frmViewCourses.getTblCourses().setRowSorter(trs);
                trs.setRowFilter(RowFilter.regexFilter(text));
            }
            
            
        });
    }
    
    public void openForm(){
        frmViewCourses.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
        frmViewCourses.setVisible(true);
    }
    
    public void fillTblCourses(){
        
        try {
            courses = Communication.getInstance().getAllCourses();
            CourseTableModel ctm = new CourseTableModel(courses);
            frmViewCourses.getTblCourses().setModel(ctm);
            
            TableColumnModel tcm = frmViewCourses.getTblCourses().getColumnModel();

            tcm.getColumn(0).setPreferredWidth(5);
            tcm.getColumn(1).setPreferredWidth(150);
            tcm.getColumn(2).setPreferredWidth(5);
            tcm.getColumn(3).setPreferredWidth(5);
            tcm.getColumn(4).setPreferredWidth(5);
            tcm.getColumn(5).setPreferredWidth(5);
            tcm.getColumn(6).setPreferredWidth(150);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(frmViewCourses, "Error: " + ex.getMessage(), "Error details", JOptionPane.ERROR_MESSAGE);
            Logger.getLogger(ViewAllCoursesController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void refresh() {
        fillTblCourses();
    }
 
}
