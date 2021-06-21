/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.view.form.components.table;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import rs.ac.bg.fon.ps.domain.Course;
import rs.ac.bg.fon.ps.domain.CourseItem;
import rs.ac.bg.fon.ps.domain.Student;

/**
 *
 * @author Djina
 */
public class CourseItemTableModel extends AbstractTableModel{

    private final Course course;
    private final ArrayList<CourseItem> deletedItems;
    private final String[] columnNames = new String[]{"ID", "Student", "Exam Score", "Total Score"};

    public CourseItemTableModel(Course course) {
        this.course= course;
        deletedItems = new ArrayList<>();
    }
 
    @Override
    public int getRowCount() {
        if (course != null) {
            return course.getCourseItems().size();
        }
        return 0;
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        CourseItem item = course.getCourseItems().get(rowIndex);
        
        switch(columnIndex){
            case 0: return item.getId();
            case 1: return item.getStudent();
            case 2: return item.getExamScore();
            case 3: return item.getTotalScore();
            default: return "n/a";
        }
    }
    
    public void addCourseItem(Student student, Double examScore, Double totalScore) {
        CourseItem item = new CourseItem();
        item.setCourse(course);
        item.setId(course.getCourseItems().size() + 1);
        item.setStudent(student);
        item.setExamScore(examScore);
        item.setTotalScore(totalScore);
        course.getCourseItems().add(item);
        fireTableRowsInserted(course.getCourseItems().size() - 1, course.getCourseItems().size() - 1);
    }
    
    public void removeCourseItem(int rowIndex) {
        CourseItem item = course.getCourseItems().get(rowIndex);
        course.getCourseItems().remove(rowIndex);
        setCourseItemId();
        fireTableRowsDeleted(course.getCourseItems().size() - 1, course.getCourseItems().size() - 1);
    }
    
    private void setCourseItemId() {
        int id = 0;
        for (CourseItem item : course.getCourseItems()) {
            item.setId(++id);
        }
    }
    
    public Course getCourse() {
        return course;
    }
    
   
    
    public CourseItem getCourseItem(int row){
        return course.getCourseItems().get(row);
    }

    public void removeCourseItemEdit(int rowIndex) {
        CourseItem deletedItem = course.getCourseItems().get(rowIndex);
        deletedItem.setStatus("deleted");
        deletedItems.add(deletedItem);
        course.getCourseItems().remove(rowIndex);
        fireTableRowsDeleted(course.getCourseItems().size() - 1, course.getCourseItems().size() - 1);
    }

    public void addEditedCourseItem(Student student, Double examScore, Double totalScore) {
        for (CourseItem item : course.getCourseItems()) {
            if(item.getStudent().equals(student)){
                item.setExamScore(examScore);
                item.setTotalScore(totalScore);
                item.setStatus("edited");
            }
        }
        
        fireTableDataChanged();
    }
    
    public Course getCourseForEdit(){
        ArrayList<CourseItem> all = (ArrayList<CourseItem>) course.getCourseItems();
        all.addAll(deletedItems);
        course.setCourseItems(all);
        /*for (CourseItem courseItem : course.getCourseItems()) {
            System.out.print(courseItem.getStatus()+" ");
        }*/
        return course;
    }

    public void addCourseItemEditMode(Student student, Double examScore, Double totalScore) {
        CourseItem item = new CourseItem();
        item.setCourse(course);
        item.setId(getLastItemID() + 1);
        item.setStudent(student);
        item.setExamScore(examScore);
        item.setTotalScore(totalScore);
        item.setStatus("added");
        course.getCourseItems().add(item);
        fireTableRowsInserted(course.getCourseItems().size() - 1, course.getCourseItems().size() - 1);
    }
  
    public int getLastItemID(){
        int id=0;
        for (int i = 0; i < course.getCourseItems().size(); i++) {
            id = course.getCourseItems().get(i).getId();
        }
        
        return id;
    }
}
