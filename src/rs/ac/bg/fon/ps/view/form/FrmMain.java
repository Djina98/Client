/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.form;

import java.awt.event.ActionListener;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import rs.ac.bg.fon.ps.view.form.util.FormMode;

/**
 *
 * @author Djina
 */
public class FrmMain extends javax.swing.JFrame {

    /**
     * Creates new form FrmMain
     */
    public FrmMain() {
        initComponents();
        setExtendedState(JFrame.MAXIMIZED_BOTH);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        lblForUser = new javax.swing.JLabel();
        lblUser = new javax.swing.JLabel();
        btnLogout = new javax.swing.JButton();
        jmbMain = new javax.swing.JMenuBar();
        jmiStudent = new javax.swing.JMenu();
        jmiNewStudent = new javax.swing.JMenuItem();
        jmiShowAllStudents = new javax.swing.JMenuItem();
        jmiCourse = new javax.swing.JMenu();
        jmiNewLecturer = new javax.swing.JMenuItem();
        jmiNewCourse = new javax.swing.JMenuItem();
        jmiShowAllCourses = new javax.swing.JMenuItem();
        jmiAbout = new javax.swing.JMenu();
        jmiSoftware = new javax.swing.JMenuItem();

        jMenuItem1.setText("jMenuItem1");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("MAIN FORM");

        jPanel1.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblForUser.setText("User:");

        lblUser.setText("jLabel1");

        btnLogout.setText("Log out");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblForUser)
                .addGap(18, 18, 18)
                .addComponent(lblUser, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 125, Short.MAX_VALUE)
                .addComponent(btnLogout)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap(217, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnLogout)
                    .addComponent(lblForUser)
                    .addComponent(lblUser))
                .addContainerGap())
        );

        jmiStudent.setText("Student");

        jmiNewStudent.setText("New");
        jmiStudent.add(jmiNewStudent);

        jmiShowAllStudents.setText("Show All");
        jmiStudent.add(jmiShowAllStudents);

        jmbMain.add(jmiStudent);

        jmiCourse.setText("Course");

        jmiNewLecturer.setText("New Lecturer");
        jmiCourse.add(jmiNewLecturer);

        jmiNewCourse.setText("New");
        jmiCourse.add(jmiNewCourse);

        jmiShowAllCourses.setText("Show All");
        jmiCourse.add(jmiShowAllCourses);

        jmbMain.add(jmiCourse);

        jmiAbout.setText("About");

        jmiSoftware.setText("Software");
        jmiAbout.add(jmiSoftware);

        jmbMain.add(jmiAbout);

        setJMenuBar(jmbMain);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

   

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLogout;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JMenuBar jmbMain;
    private javax.swing.JMenu jmiAbout;
    private javax.swing.JMenu jmiCourse;
    private javax.swing.JMenuItem jmiNewCourse;
    private javax.swing.JMenuItem jmiNewLecturer;
    private javax.swing.JMenuItem jmiNewStudent;
    private javax.swing.JMenuItem jmiShowAllCourses;
    private javax.swing.JMenuItem jmiShowAllStudents;
    private javax.swing.JMenuItem jmiSoftware;
    private javax.swing.JMenu jmiStudent;
    private javax.swing.JLabel lblForUser;
    private javax.swing.JLabel lblUser;
    // End of variables declaration//GEN-END:variables

    public void jmiNewStudentAddActionListener(ActionListener actionListener) {
        jmiNewStudent.addActionListener(actionListener);
    }

    public JLabel getLblUser() {
        return lblUser;
    }

    public void jmiShowAllStudentsAddActionListener(ActionListener actionListener) {
        jmiShowAllStudents.addActionListener(actionListener);
    }

    public void jmiNewCourseAddActionListener(ActionListener actionListener) {
        jmiNewCourse.addActionListener(actionListener);
    }

    public void jmiShowAllCoursesAddActionListener(ActionListener actionListener) {
        jmiShowAllCourses.addActionListener(actionListener);
    }

    public void btnLogoutAddActionListener(ActionListener actionListener) {
       btnLogout.addActionListener(actionListener);
    }

    public void jmiAboutSoftwareAddActionListener(ActionListener actionListener) {
       jmiSoftware.addActionListener(actionListener);
    }

    public JMenuItem getJmiNewLecturer() {
        return jmiNewLecturer;
    }

    public void setJmiNewLecturer(JMenuItem jmiNewLecturer) {
        this.jmiNewLecturer = jmiNewLecturer;
    }

    public void jmiNewLecturerAddActionListener(ActionListener actionListener) {
        jmiNewLecturer.addActionListener(actionListener);
    }
    
    
}