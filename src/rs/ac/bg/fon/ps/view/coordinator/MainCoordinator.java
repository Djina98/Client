/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.coordinator;

import java.util.HashMap;
import java.util.Map;
import rs.ac.bg.fon.ps.domain.User;
import rs.ac.bg.fon.ps.view.controller.ConnectionController;
import rs.ac.bg.fon.ps.view.controller.CourseController;
import rs.ac.bg.fon.ps.view.controller.LecturerController;
import rs.ac.bg.fon.ps.view.controller.LoginController;
import rs.ac.bg.fon.ps.view.controller.MainController;
import rs.ac.bg.fon.ps.view.controller.SoftwareController;
import rs.ac.bg.fon.ps.view.controller.StudentController;
import rs.ac.bg.fon.ps.view.controller.ViewAllCoursesController;
import rs.ac.bg.fon.ps.view.controller.ViewAllStudentsController;
import rs.ac.bg.fon.ps.view.form.FrmConnection;
import rs.ac.bg.fon.ps.view.form.FrmCourse;
import rs.ac.bg.fon.ps.view.form.FrmLecturer;
import rs.ac.bg.fon.ps.view.form.FrmLogin;
import rs.ac.bg.fon.ps.view.form.FrmMain;
import rs.ac.bg.fon.ps.view.form.FrmSoftware;
import rs.ac.bg.fon.ps.view.form.FrmStudent;
import rs.ac.bg.fon.ps.view.form.FrmViewCourses;
import rs.ac.bg.fon.ps.view.form.FrmViewStudents;
import rs.ac.bg.fon.ps.view.form.util.FormMode;

/**
 *
 * @author Djina
 */
public class MainCoordinator {
    
    private static MainCoordinator instance;
    private final MainController mainController;
    private final Map<String, Object> params;

    private MainCoordinator() {
        mainController = new MainController(new FrmMain()) ;
        params = new HashMap<>();
    }
    
    public static MainCoordinator getInstance(){
        if(instance == null){
            instance = new MainCoordinator();
        }
        
        return instance;
    }
    
    public void addParam(String name, Object key) {
        params.put(name, key);
    }
    
    public Object getParam(String name){
        return params.get(name);
       
    }
    
    public void openLoginForm(){
        LoginController loginController = new LoginController(new FrmLogin());
        loginController.openForm();
    }
    
    public void openMainForm(){
        mainController.openForm();
    }

    public MainController getMainContoller() {
        return mainController;
    }

    public void openAddNewStudentForm() {
        StudentController studentController = new StudentController(new FrmStudent(mainController.getFrmMain(), true));
        studentController.openForm(FormMode.FORM_ADD);
    }

    public void openViewAllStudentsForm() {
        FrmViewStudents frmViewStudents = new FrmViewStudents(mainController.getFrmMain(), true);
        ViewAllStudentsController viewAllStudentsController = new ViewAllStudentsController(frmViewStudents);
        viewAllStudentsController.openForm();
    }

    public void openAddNewCourseForm() {
        FrmCourse frmCourse = new FrmCourse(mainController.getFrmMain(), true);
        CourseController courseController = new CourseController(frmCourse);
        courseController.openForm(FormMode.FORM_ADD);
    }

    public void openStudentDetailsForm() {
        FrmStudent frmStudent = new FrmStudent(mainController.getFrmMain(), true);
        StudentController studentController = new StudentController(frmStudent);
        studentController.openForm(FormMode.FORM_VIEW);
    }

    public void openViewAllCoursesForm() {
        FrmViewCourses frmViewCourses = new FrmViewCourses(mainController.getFrmMain(), true);
        ViewAllCoursesController viewAllCoursesController = new ViewAllCoursesController(frmViewCourses);
        viewAllCoursesController.openForm();
    }

    public void openCourseDetailsForm() {
        FrmCourse frmCourse = new FrmCourse(mainController.getFrmMain(), true);
        CourseController courseController = new CourseController(frmCourse);
        courseController.openForm(FormMode.FORM_VIEW);
    }

    public void openConnectionForm() {
        ConnectionController connectionController = new ConnectionController(new FrmConnection());
        connectionController.openForm();
    }
    
    public void openAboutSoftwareForm() {
        FrmSoftware frmSoftware = new FrmSoftware(mainController.getFrmMain(), true);
        SoftwareController softwareController = new SoftwareController(frmSoftware);
        softwareController.openForm();
    }
    
    public void openNewLecturerForm() {
        FrmLecturer frmlecturer = new FrmLecturer(mainController.getFrmMain(), true);
        LecturerController lecturerController = new LecturerController(frmlecturer);
        lecturerController.openForm();
    }


}
