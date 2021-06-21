/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.form.components.table;

import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Faculty;
import rs.ac.bg.fon.ps.domain.Student;

/**
 *
 * @author Djina
 */
public class StudentTableModel extends AbstractTableModel{
    List<Student> students;
    String[] columnNames;
    

    public StudentTableModel(List<Student> students) {
        this.students = students;
        columnNames = new String[]{"ID", "FIRSTNAME", "LASTNAME", "SID", "YEAR OF STUDIES", "AVERAGE GRADE", "FACULTY"};
    }
    
    
    @Override
    public int getRowCount() {
        if(students == null){
            return 0;
        }
        
        return students.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Student student = students.get(rowIndex);
        
        switch(columnIndex){
            case 0: return student.getId();
            case 1: return student.getFirstname();
            case 2: return student.getLastname();
            case 3: return student.getStudentIDNumber();
            case 4: return student.getYearOfStudy();
            case 5: return student.getAverageGrade();
            case 6: return student.getFaculty().getName();
            default: return "n/a";
        }
    }

    @Override
    public String getColumnName(int column) {
        if(column > columnNames.length){
            return "n/a";
        }
        
        return columnNames[column];
    }

    public void addStudent(Student student){
        students.add(student);
        fireTableRowsInserted(students.size() - 1, students.size() - 1);
    }
    
    public Student getStudentAt(int selectedRow){
        return students.get(selectedRow);
    }
    
    public void deleteStudent(Student student){
        students.remove(student);
        fireTableDataChanged();
    }
 
}
