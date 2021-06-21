/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rs.ac.bg.fon.ps.communication;

import java.io.EOFException;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import rs.ac.bg.fon.ps.domain.Course;
import rs.ac.bg.fon.ps.domain.CourseItem;
import rs.ac.bg.fon.ps.domain.Faculty;
import rs.ac.bg.fon.ps.domain.Lecturer;
import rs.ac.bg.fon.ps.domain.Location;
import rs.ac.bg.fon.ps.domain.Student;
import rs.ac.bg.fon.ps.domain.User;

/**
 *
 * @author Cartman
 */
public class Communication {
    private Socket socket;
    private Sender sender;
    private Receiver receiver;
    private static Communication instance;
    
    private Communication() throws Exception{
       
    }
    
    public static Communication getInstance() throws Exception{
        if(instance == null){
            instance = new Communication();
        }
        return instance;
    }
    
    public User login(String username, String password) throws Exception {
        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        Request request = new Request(Operation.LOGIN, user);      
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException() == null){
            return (User)response.getResult();
        }else{
            throw response.getException();
        }
    }
    
    public boolean logout(User user) throws Exception {
        Request request = new Request();
        request.setOperation(Operation.LOGOUT);

        request.setArgument(user);
        sender.send(request);
        Response response = (Response)receiver.receive();
        
        
        return (boolean) response.getResult();
        
    }

    public List<Faculty> getAllFaculties() throws Exception{
        Request request = new Request(Operation.GET_ALL_FACULTIES, null);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException() == null){
            return (List<Faculty>)response.getResult();
        }else{
            throw response.getException();
        }
    }
    
    public List<Student> getAllStudents() throws Exception{
        Request request = new Request(Operation.GET_ALL_STUDENTS, null);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException() == null){
            return (List<Student>)response.getResult();
        }else{
            throw response.getException();
        }
    }
    
    public List<Location> getAllLocations() throws Exception{
        Request request = new Request(Operation.GET_ALL_LOCATIONS, null);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException() == null){
            return (List<Location>)response.getResult();
        }else{
            throw response.getException();
        }
    }
    
    public List<Lecturer> getAllLecturers() throws Exception{
        Request request = new Request(Operation.GET_ALL_LECTURERS, null);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException() == null){
            return (List<Lecturer>)response.getResult();
        }else{
            throw response.getException();
        }
    }

    public Student addStudent(Student student) throws Exception {
        Request request = new Request(Operation.ADD_STUDENT, student);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException() == null){
            Long index = (Long)response.getResult();
            student.setId(index);
            return student;
        }else{
            throw response.getException();
        }
    }
    
     public Lecturer addLecturer(Lecturer lecturer) throws Exception {
        Request request = new Request(Operation.ADD_LECTURER, lecturer);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException() == null){
            Long index = (Long)response.getResult();
            lecturer.setId(index);
            return lecturer;
        }else{
            throw response.getException();
        }
    }

    
    public void deleteStudent(Student student) throws Exception {
        Request request = new Request(Operation.DELETE_STUDENT, student);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            
        }else{
            throw response.getException();
        }
    }

    public void editStudent(Student student) throws Exception {
        Request request = new Request(Operation.EDIT_STUDENT, student);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException() == null){
            
        }else{
            throw response.getException();
        }
    }

    public Course addCourse(Course course) throws Exception {
        Request request = new Request(Operation.ADD_COURSE, course);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException() == null){
            Long index = (Long)response.getResult();
            course.setId(index);
            return course;
        }else{
            throw response.getException();
        }
    }

    public void deleteCourse(Course course) throws Exception {
        Request request = new Request(Operation.DELETE_COURSE, course);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException()==null){
            
        }else{
            throw response.getException();
        }
    }

    public List<Course> getAllCourses() throws Exception {
        Request request = new Request(Operation.GET_ALL_COURSES, null);
        sender.send(request);
        Response response = (Response)receiver.receive();
        if(response.getException() == null){
            return (List<Course>)response.getResult();
        }else{
            throw response.getException();
        }
    }
    
    public void editCourse(Course course) throws Exception {
        Request request = new Request(Operation.EDIT_COURSE, course);
        sender.send(request);
        Response response=(Response)receiver.receive();
        if(response.getException()==null){
            
        }else{
            throw response.getException();
        }
    }

    public Socket getSocket() {
        return socket;
    }

    public void setSocket(Socket socket) {
        this.socket = socket;
        sender = new Sender(socket);
        receiver = new Receiver(socket);
    }
    
    
}
