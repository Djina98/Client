/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import rs.ac.bg.fon.ps.communication.Communication;
import rs.ac.bg.fon.ps.domain.Course;
import rs.ac.bg.fon.ps.domain.CourseItem;
import rs.ac.bg.fon.ps.domain.Lecturer;
import rs.ac.bg.fon.ps.domain.Location;
import rs.ac.bg.fon.ps.domain.Student;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.view.constants.Constants;
import rs.ac.bg.fon.ps.view.coordinator.MainCoordinator;
import rs.ac.bg.fon.ps.view.form.FrmCourse;
import rs.ac.bg.fon.ps.view.form.components.table.CourseItemTableModel;
import rs.ac.bg.fon.ps.view.form.components.table.CourseTableModel;
import rs.ac.bg.fon.ps.view.form.util.FormMode;

/**
 *
 * @author Djina
 */
public class CourseController {
    
    private final FrmCourse frmCourse;
    private FormMode formMode; 
    private CourseItem selectedItemForEdit;
    
    public CourseController(FrmCourse frmCourse) {
        this.frmCourse = frmCourse;
        addActionListeners();
    }
    
    public void openForm(FormMode formMode) {
        fillForm();
        this.formMode = formMode;
        User user = (User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER);
        frmCourse.getTxtUser().setText(user.getFirstname() + " " + user.getLastname());
        fillTblCourseItems();
        setupComponents(formMode);
        frmCourse.setLocationRelativeTo(MainCoordinator.getInstance().getMainContoller().getFrmMain());
        frmCourse.setVisible(true);
    }

    private void fillForm() {
        try {
            frmCourse.getCmbLecturer().setModel(
                    new DefaultComboBoxModel(Communication.getInstance().getAllLecturers().toArray())
            );
            
            frmCourse.getCmbStudent().setModel(
                    new DefaultComboBoxModel(Communication.getInstance().getAllStudents().toArray())
            );
            
            frmCourse.getCmbLocation().setModel(
                    new DefaultComboBoxModel(Communication.getInstance().getAllLocations().toArray())
            );
          
            frmCourse.getCmbStudent().setSelectedIndex(-1);
            frmCourse.getTxtExamScore().setEnabled(false);
            frmCourse.getCmbStudent().addItemListener(new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    changeStudent(e);
                }

                private void changeStudent(ItemEvent e) {
                    if (e.getStateChange() == ItemEvent.SELECTED) {
                        frmCourse.getTxtExamScore().setEnabled(true);
                        Student student = (Student) e.getItem();
                        frmCourse.getTxtAverageGrade().setText(String.valueOf(student.getAverageGrade()));
                    }
                }
            });
                    
        } catch (Exception ex) {
            Logger.getLogger(CourseController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void setupComponents(FormMode formMode) {
        switch(formMode){
           case FORM_ADD:
               frmCourse.getLblID().setVisible(false);
               frmCourse.getTxtID().setVisible(false);
               frmCourse.getBtnEdit().setEnabled(false);
               frmCourse.getBtnDelete().setEnabled(false);
               frmCourse.getBtnAdd().setEnabled(true);
               frmCourse.getBtnCancel().setEnabled(true);
               frmCourse.getBtnEnableChanges().setEnabled(false);
               frmCourse.getTxtNumberOfStudentsLeft().setEnabled(false);
               frmCourse.getBtnEditItem().setEnabled(false);
               break;
            case FORM_VIEW:
                frmCourse.getBtnCancel().setEnabled(true);
                frmCourse.getBtnDelete().setEnabled(true);
                frmCourse.getBtnEdit().setEnabled(false);
                frmCourse.getBtnEnableChanges().setEnabled(true);
                frmCourse.getBtnSave().setEnabled(false);
                frmCourse.getTxtNumberOfStudentsLeft().setEnabled(false);
                frmCourse.getBtnEditItem().setEnabled(false);

                //disable value changes
                frmCourse.getTxtID().setEnabled(false);
                frmCourse.getTxtName().setEnabled(false);
                frmCourse.getTxtStartDate().setEnabled(false);
                frmCourse.getTxtEndDate().setEnabled(false);
                frmCourse.getTxtNumberOfStudents().setEnabled(false);
                frmCourse.getCmbLecturer().setEnabled(false);
                frmCourse.getCmbLocation().setEnabled(false);
                frmCourse.getCmbStudent().setEnabled(false);
                frmCourse.getTxtExamScore().setEnabled(false);
                frmCourse.getTblCourseItems().setEnabled(false);

                //get course
                Course course = (Course) MainCoordinator.getInstance().getParam(Constants.PARAM_COURSE);    
                frmCourse.getTxtID().setText(course.getId() + "");
                frmCourse.getTxtName().setText(course.getName());
                String startDate = formatDate(course.getStartDate());
                frmCourse.getTxtStartDate().setText(startDate);
                String endDate = formatDate(course.getEndDate());
                frmCourse.getTxtEndDate().setText(endDate);
                frmCourse.getTxtNumberOfStudents().setText(String.valueOf(course.getNumberOfStudents()));
                frmCourse.getCmbLecturer().setSelectedItem(course.getLecturer());
                frmCourse.getCmbLocation().setSelectedItem(course.getLocation());
                frmCourse.getTxtUser().setText(course.getUser().getFirstname() + " " + course.getUser().getLastname());
                CourseItemTableModel model = new CourseItemTableModel(course);
                frmCourse.getTblCourseItems().setModel(model);
                int number = Integer.valueOf(frmCourse.getTxtNumberOfStudents().getText().trim());
                CourseItemTableModel ctm = (CourseItemTableModel) frmCourse.getTblCourseItems().getModel();
                int rowCount = ctm.getRowCount();
                int value = number - rowCount;
                frmCourse.getTxtNumberOfStudentsLeft().setText(String.valueOf(value));
                
                break;
            case FORM_EDIT:
                frmCourse.getBtnCancel().setEnabled(true);
                frmCourse.getBtnDelete().setEnabled(false);
                frmCourse.getBtnEdit().setEnabled(true);
                frmCourse.getBtnEnableChanges().setEnabled(false);
                frmCourse.getBtnSave().setEnabled(false);
                frmCourse.getTxtNumberOfStudentsLeft().setEnabled(false);
                frmCourse.getBtnEditItem().setEnabled(false);

                //disable value changes
                frmCourse.getTxtID().setEnabled(false);
                frmCourse.getTxtName().setEnabled(true);
                frmCourse.getTxtStartDate().setEnabled(true);
                frmCourse.getTxtEndDate().setEnabled(true);
                frmCourse.getTxtNumberOfStudents().setEnabled(true);
                frmCourse.getCmbLecturer().setEnabled(true);
                frmCourse.getCmbLocation().setEnabled(true);
                frmCourse.getTblCourseItems().setEnabled(true);
                frmCourse.getCmbStudent().setEnabled(true);
                frmCourse.getTxtExamScore().setEnabled(true);
                break;
       }
    }

    private void addActionListeners() {
        frmCourse.txtExamScoreAddKeyListener(new KeyListener() {
            @Override
            public void keyReleased(KeyEvent e) {
                double averageGrade = Double.valueOf(frmCourse.getTxtAverageGrade().getText().trim());
                double examScore = Double.valueOf(frmCourse.getTxtExamScore().getText().trim());
                double totalScore = 1.5*averageGrade + 0.85*examScore;
                frmCourse.getTxtTotalScore().setText(String.valueOf(totalScore));
            }

            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            
        });
        
        frmCourse.txtNumberOfStudentsAddKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
            }

            @Override
            public void keyPressed(KeyEvent e) {
                
            }

            @Override
            public void keyReleased(KeyEvent e) {
                
                int number = Integer.valueOf(frmCourse.getTxtNumberOfStudents().getText().trim());
               
                
                CourseItemTableModel ctm = (CourseItemTableModel) frmCourse.getTblCourseItems().getModel();
                int rowCount = ctm.getRowCount();
                int value = number - rowCount;
                frmCourse.getTxtNumberOfStudentsLeft().setText(String.valueOf(value));
            }
        });
        
        frmCourse.btnAddCourseItemAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addCourseItem();
                
            }

            private void addCourseItem() {
                try {
                    
                    if(frmCourse.getTxtExamScore().getText().isEmpty()){
                        JOptionPane.showMessageDialog(frmCourse, "You need to input all data", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    Student student = (Student) frmCourse.getCmbStudent().getSelectedItem();
                    Double examScore = Double.valueOf(frmCourse.getTxtExamScore().getText().trim());
                    
                    if(examScore < 0 || examScore > 100){
                        JOptionPane.showMessageDialog(frmCourse, "Exam score range is from 0 to 100! ", "Warning", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    Double totalScore = Double.valueOf(frmCourse.getTxtTotalScore().getText().trim());
                    CourseItemTableModel model = (CourseItemTableModel) frmCourse.getTblCourseItems().getModel();
                    
                    ArrayList<CourseItem> courseItems = (ArrayList<CourseItem>) model.getCourse().getCourseItems();
                    
                    for(CourseItem item: courseItems){
                        if(item.getStudent().getId().equals(student.getId())){
                            JOptionPane.showMessageDialog(frmCourse, "This student is already added!", "Warning", JOptionPane.INFORMATION_MESSAGE);
                            return;
                        }
                    }
                    
                    int number = -1;
                    try{
                        number = Integer.valueOf(frmCourse.getTxtNumberOfStudentsLeft().getText());
                    }catch(NumberFormatException ex){
                        JOptionPane.showMessageDialog(frmCourse, "You have to specify number of students for course first!", "Warning", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    
                    if(number == 0){
                        JOptionPane.showMessageDialog(frmCourse, "Maximum number of students for this course is entered!", "Warning", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    if(formMode.equals(FormMode.FORM_ADD)){
                        model.addCourseItem(student, examScore, totalScore);
                    }else if(formMode.equals(FormMode.FORM_EDIT) || formMode.equals(FormMode.FORM_VIEW)){
                        model.addCourseItemEditMode(student, examScore, totalScore);
                    }
                    
                    number = number - 1;
                    frmCourse.getTxtNumberOfStudentsLeft().setText(String.valueOf(number));
                    deleteData();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmCourse, "Invalid student data!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            
        });
        
        frmCourse.btnRemoveCourseItemAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeCourseItem();
            }

            private void removeCourseItem() {
                int rowIndex = frmCourse.getTblCourseItems().getSelectedRow();
                CourseItemTableModel model = (CourseItemTableModel) frmCourse.getTblCourseItems().getModel();
                if (rowIndex >= 0) {
                    
                    if(formMode.equals(FormMode.FORM_ADD)){
                        model.removeCourseItem(rowIndex);
                    }else if(formMode.equals(FormMode.FORM_EDIT) || formMode.equals(FormMode.FORM_VIEW)){
                        model.removeCourseItemEdit(rowIndex);
                    }
                    
                    int number = Integer.valueOf(frmCourse.getTxtNumberOfStudentsLeft().getText());
                    number = number + 1;
                    frmCourse.getTxtNumberOfStudentsLeft().setText(String.valueOf(number));
                    frmCourse.getCmbStudent().setEnabled(true);
                    frmCourse.getCmbStudent().setSelectedIndex(-1);
                    frmCourse.getTxtAverageGrade().setText("");
                    frmCourse.getTxtExamScore().setText("");
                    frmCourse.getTxtTotalScore().setText("");
                    frmCourse.getBtnEditItem().setEnabled(false);
                    frmCourse.getBtnAdd().setEnabled(true);
                    frmCourse.getTblCourseItems().clearSelection();
                    
                } else {
                    JOptionPane.showMessageDialog(frmCourse, "Course item is not selected!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        
        frmCourse.btnSaveCourseAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveCourse();
            }

            private void saveCourse() {
                try {
                    
                    if(frmCourse.getTxtName().getText().isEmpty() || 
                            frmCourse.getTxtNumberOfStudents().getText().isEmpty() ||
                            frmCourse.getTxtStartDate().getText().isEmpty() ||
                            frmCourse.getTxtEndDate().getText().isEmpty()){
                        JOptionPane.showMessageDialog(frmCourse, "You need to input all data", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                   
                    Date startDate;
                    
                    try {
                        startDate = parseDate(frmCourse.getTxtStartDate().getText().trim());
                    } catch (ParseException ex) { 
                        JOptionPane.showMessageDialog(frmCourse, "Start date format is not correct!", "Warning", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    Date endDate;
                    
                    try {
                        endDate = parseDate(frmCourse.getTxtEndDate().getText().trim());
                    } catch (ParseException ex) { 
                        JOptionPane.showMessageDialog(frmCourse, "End date format is not correct!", "Warning", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                   
                    if(startDate.before(new Date())){
                        JOptionPane.showMessageDialog(frmCourse, "Start date must be after today!", "Warning", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    if(endDate.before(new Date())){
                        JOptionPane.showMessageDialog(frmCourse, "End date must be after today!", "Warning", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    long difference = endDate.getTime() - startDate.getTime();
                    long days = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
                    
                    if(startDate.after(endDate)){
                        JOptionPane.showMessageDialog(frmCourse, "End date must be after start date");
                        return;
                    }
                    
                    if(days < 1){
                        JOptionPane.showMessageDialog(frmCourse, "Difference between start and end date must be at least 1 day");
                        return;
                    }
                    
                    int numberOfStudents = Integer.valueOf(frmCourse.getTxtNumberOfStudents().getText().trim());
                    
                    if(numberOfStudents < 1){
                        JOptionPane.showMessageDialog(frmCourse, "Number of students must be greater than 0");
                        return;
                    }
                    
                    CourseItemTableModel model = (CourseItemTableModel) frmCourse.getTblCourseItems().getModel();
                    Course course = model.getCourse();
                    course.setId(-1L);
                    course.setName(frmCourse.getTxtName().getText().trim());
                    course.setStartDate(startDate);
                    course.setEndDate(endDate);
                    course.setNumberOfStudents(numberOfStudents);
                    course.setLecturer((Lecturer) frmCourse.getCmbLecturer().getSelectedItem());
                    course.setLocation((Location) frmCourse.getCmbLocation().getSelectedItem());
                    course.setUser((User) MainCoordinator.getInstance().getParam(Constants.CURRENT_USER));
                    Course saved = Communication.getInstance().addCourse(course);
                    frmCourse.getTxtID().setText(String.valueOf(course.getId()));
                    JOptionPane.showMessageDialog(frmCourse, "Course is successfully saved with id: " + saved.getId());
                    frmCourse.dispose();
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmCourse, "Error saving new course! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

                }
            }
        });
        
        frmCourse.btnEnableChangesAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                formMode = FormMode.FORM_EDIT;
                setupComponents(FormMode.FORM_EDIT);
            }
        });
        
        frmCourse.tblCourseItemsAddActionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (frmCourse.getTblCourseItems().getSelectedRow() > -1) {
                    CourseItem item = new CourseItem();
                    int row = frmCourse.getTblCourseItems().getSelectedRow();
                    CourseItemTableModel cim = (CourseItemTableModel) frmCourse.getTblCourseItems().getModel();
                    item = cim.getCourseItem(row);
                    
                    selectedItemForEdit = item;
                    
                    frmCourse.getCmbStudent().setSelectedItem(item.getStudent());
                    frmCourse.getTxtAverageGrade().setText(String.valueOf(item.getStudent().getAverageGrade()));
                    frmCourse.getTxtExamScore().setText(String.valueOf(item.getExamScore()));
                    frmCourse.getTxtTotalScore().setText(String.valueOf(item.getTotalScore()));
                    frmCourse.getCmbStudent().setEnabled(false);
                    frmCourse.getBtnAdd().setEnabled(false);
                    frmCourse.getBtnEditItem().setEnabled(true);
                    
                }
            }
        });
        
        frmCourse.btnCancelAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                cancel();
            }

            private void cancel() {
                frmCourse.dispose();
            }
        });
        
        frmCourse.btnEditItemAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                try {
                    
                    if(frmCourse.getTxtExamScore().getText().isEmpty()){
                        JOptionPane.showMessageDialog(frmCourse, "You need to input all data", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    Student student = (Student) frmCourse.getCmbStudent().getSelectedItem();
                    Double examScore = Double.valueOf(frmCourse.getTxtExamScore().getText().trim());
                    
                    if(examScore < 0 || examScore > 100){
                        JOptionPane.showMessageDialog(frmCourse, "Exam score range is from 0 to 100! ", "Warning", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                    
                    Double totalScore = Double.valueOf(frmCourse.getTxtTotalScore().getText().trim());
                    CourseItemTableModel model = (CourseItemTableModel) frmCourse.getTblCourseItems().getModel();
                    
                    
                    int number = Integer.valueOf(frmCourse.getTxtNumberOfStudentsLeft().getText());
                    
                    model.addEditedCourseItem(student, examScore, totalScore);
                    deleteData();
                    frmCourse.getBtnAdd().setEnabled(true);
                    frmCourse.getCmbStudent().setEnabled(true);
                    frmCourse.getBtnEditItem().setEnabled(false);
                } catch (Exception ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frmCourse, "Invalid student data!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

            
        });
        
        frmCourse.btnEditAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            try {
                    
                if(frmCourse.getTxtName().getText().isEmpty() || 
                    frmCourse.getTxtNumberOfStudents().getText().isEmpty() ||
                    frmCourse.getTxtStartDate().getText().isEmpty() ||
                    frmCourse.getTxtEndDate().getText().isEmpty()){
                        JOptionPane.showMessageDialog(frmCourse, "You need to input all data", "Error", JOptionPane.INFORMATION_MESSAGE);
                        return;
                }
                    
                
                Date startDate = parseDate(frmCourse.getTxtStartDate().getText().trim());
                Date endDate = parseDate(frmCourse.getTxtEndDate().getText().trim());
                long difference = endDate.getTime() - startDate.getTime();
                long days = TimeUnit.DAYS.convert(difference, TimeUnit.MILLISECONDS);
                    
                if(startDate.after(endDate)){
                    JOptionPane.showMessageDialog(frmCourse, "End date must be after start date");
                    return;
                }
                    
                if(days < 1){
                    JOptionPane.showMessageDialog(frmCourse, "Difference between start and end date must be at least 1 day");
                    return;
                }
                    
                int numberOfStudents = Integer.valueOf(frmCourse.getTxtNumberOfStudents().getText().trim());
                    
                if(numberOfStudents < 1){
                    JOptionPane.showMessageDialog(frmCourse, "Number of students must be greater than 0");
                    return;
                }
                    
                CourseItemTableModel model = (CourseItemTableModel) frmCourse.getTblCourseItems().getModel();
                Course course = model.getCourseForEdit();
                course.setName(frmCourse.getTxtName().getText().trim());
                course.setStartDate(startDate);
                course.setEndDate(endDate);
                course.setNumberOfStudents(numberOfStudents);
                course.setLecturer((Lecturer) frmCourse.getCmbLecturer().getSelectedItem());
                course.setLocation((Location) frmCourse.getCmbLocation().getSelectedItem());
                Course c = (Course) MainCoordinator.getInstance().getParam(Constants.PARAM_COURSE);
                course.setUser(c.getUser());
                Communication.getInstance().editCourse(course);
                
                JOptionPane.showMessageDialog(frmCourse, "Course is successfully edited");
                frmCourse.dispose();
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frmCourse, "Error editing course! " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);

            }
            
        }
        });
        
        frmCourse.btnDeleteAddActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try{
                    Course course = (Course) MainCoordinator.getInstance().getParam(Constants.PARAM_COURSE);
                    int response = JOptionPane.showConfirmDialog(frmCourse, 
                                    "Are you sure you want to delete course with id: " 
                                    + course.getId() + "?");
                    if (response == JOptionPane.YES_OPTION) {     
                        Communication.getInstance().deleteCourse(course);
                        JOptionPane.showMessageDialog(frmCourse, "Course successfully deleted!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        return;
                    }
                }catch(Exception ex){
                    Logger.getLogger(FrmCourse.class.getName()).log(Level.SEVERE, null, ex);
                    JOptionPane.showMessageDialog(frmCourse, "Unable to delete course!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
                
        });

    }

    private void fillTblCourseItems() {
        CourseItemTableModel model = new CourseItemTableModel(new Course());
        frmCourse.getTblCourseItems().setModel(model);
    }

    private void deleteData() {
        frmCourse.getCmbStudent().setSelectedIndex(-1);
        frmCourse.getTxtExamScore().setText("");
        frmCourse.getTxtAverageGrade().setText("");
        frmCourse.getTxtTotalScore().setText("");
    }
    
    public String formatDate(Date date){
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
        
        return df.format(date);
    }
    
     public Date parseDate(String date) throws ParseException{
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
        
        return df.parse(date);
    }
    
}
