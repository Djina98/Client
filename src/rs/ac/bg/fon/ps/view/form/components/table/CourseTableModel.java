/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.form.components.table;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Course;
import rs.ac.bg.fon.ps.domain.Student;

/**
 *
 * @author Djina
 */
public class CourseTableModel extends AbstractTableModel{
    
    private final List<Course> courses;
    private final String[] columnNames = new String[]{"ID", "Name", "Start date", "End date", "Capacity", "Lecturer", "Location"};

    public CourseTableModel(List<Course> courses) {
        this.courses = courses;
    }
    
    public String formatDate(Date date){
        DateFormat df = new SimpleDateFormat("dd.MM.yyyy.");
        
        return df.format(date);
    }
 
    @Override
    public int getRowCount() {
        if(courses == null){
            return 0;
        }
        
        return courses.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Course course = courses.get(rowIndex);
       
        switch(columnIndex){
            case 0: return course.getId();
            case 1: return course.getName();
            case 2: 
                String startDate = formatDate(course.getStartDate());
                return startDate;
            case 3: 
                String endDate = formatDate(course.getEndDate());
                return endDate;
            case 4: return course.getNumberOfStudents();
            case 5: return course.getLecturer();
            case 6: return course.getLocation();
            default: return "n/a";
        }
    }

    public Course getCourseAt(int row) {
        return courses.get(row);
    }

    public void deleteCourse(Course course) {
        courses.remove(course);
        fireTableDataChanged();
    }

    @Override
    public String getColumnName(int column) {
         if(column > columnNames.length){
            return "n/a";
        }
        
        return columnNames[column];
    }
    
}
