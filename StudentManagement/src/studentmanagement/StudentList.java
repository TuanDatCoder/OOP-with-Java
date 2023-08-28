/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package studentmanagement;


import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author laptop
 */
public class StudentList {

    private List<Student> list;
    private Scanner sc;

    public StudentList() {
        list = new ArrayList<>();
        sc = new Scanner(System.in);
    }

    //Them mot student vao list
    public void addStudent() {
        Student student = new Student();
        student.input();
        list.add(student);
    }

    public Student getStudent(String id) {
        for (Student student : list) {
            if (student.getId().equals(id)) {
                return student;
            }
        }
        return null;
    }

    //Liet ke tat ca students
    public void listStudents() {
        /*
        //C1
        for (int i = 0; i < list.size(); i++) {
        list.get(i).output();
        }
         */
 /*
        //C2
        for(Student student:list){
        student.output();
        }
         */
 /*
        //C3
        //Function programming
        //Lambda Expression <==> Method
        list.forEach((student) -> {
            student.output();
        });
         */
        //C4
        //La cach viet gon cua C3
        list.forEach(Student::output);
    }

    public void listStudentsById(boolean ascending) {
        //Sap thu tu list theo id tang dan
        /*
        C1:
        list.sort(new Comparator<Student>() {
            @Override
            public int compare(Student s1, Student s2) {
               return s1.getId().compareTo(s2.getId());
            }
        });
        if(!ascending){
            //Dao nguoc list
            Collections.reverse(list);
        }

        // Liet ke cac students
        list.forEach(Student::output);
         */
        //C2
        list.sort((Student s1, Student s2) -> s1.getId().compareTo(s2.getId()));
        if (!ascending) {
            //Dao nguoc list
            Collections.reverse(list);
        }

        // Liet ke cac students
        list.forEach(Student::output);
    }

    public void searchStudentsById() {
        /*
        -Nhap id
        Tim student co id = id da nhap trong list
         */
        String id = Student.inputId();
        for (Student student : list) {
            if (student.getId().equals(id)) {
                student.output();
                return;
            }
        }
        System.out.println("Student is not found.");
    }

    // Name
    public void listStudentsByName(boolean ascending) {

        //C2
        list.sort((Student s1, Student s2) -> s1.getName().compareTo(s2.getName()));
        if (!ascending) {
            //Dao nguoc list
            Collections.reverse(list);
        }

        // Liet ke cac students
        list.forEach(Student::output);
    }

    public void listStudentsByPoint(boolean ascending) {

        list.sort((Student s1, Student s2)
                -> Double.compare(s1.getPoint(), s2.getPoint()));

        if (!ascending) {
            //Dao nguoc list
            Collections.reverse(list);
        }
        // Liet ke cac students
        list.forEach(Student::output);
    }

    public void updateStudents() {
        /*
        - Nhap id cua suudent can cap nhat
        -Neu co student tuong ung voi id
            - Cap nhat
        -Nguoc lai
            -Thong bao: SInh vien khong ton tai
        
         */

        String id = Student.inputId();
        Student student = this.getStudent(id);
        if (student == null) {
            System.out.println("Student is not found.");
        } else {
            student.update();// chi update name, point
        }
    }
    // xoa Sinh Vien
    public void deleteStudents() {

        String id = Student.inputId();
        Student student = this.getStudent(id);
        if (student == null) {
            System.out.println("Student is not found.");
        } else {
            list.remove(student);

        }
    }
    
    // Calculate average points
    public void calculateAvaragePoints(){
        double s = 0;
        for (int i = 0; i < list.size(); i++) {
            s+= list.get(i).getPoint();
        }
        s /= list.size();
        System.out.format("Average points: %.2f\n",s);
    }
    
    //Luu list vao file nhi phan students.dat
    public void saveStudents() {
        try {
            FileOutputStream fos = new FileOutputStream("students.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            //Luu list vao file
            oos.writeObject(list);
            oos.close();
            fos.close();
            System.out.println("Saving students successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception: " + ex.getMessage());
        }
    }

    //Doc du lieu tu file nhi phan students.dat va de vao list
    public void loadStudents() {
        String fileName = "students.dat";
        //Kiem tra file ton tai hay khong
        File f = new File(fileName);
        if (!f.exists()) {
            return;
        }
        try {
            FileInputStream fis = new FileInputStream(fileName);
            ObjectInputStream ois = new ObjectInputStream(fis);
            //Doc list tu file
            list = (List<Student>) ois.readObject();
            ois.close();
            fis.close();
            System.out.println("Loading students successfully");
        } catch (Exception ex) {
            ex.printStackTrace();
            System.out.println("Exception: " + ex.getMessage());
        }
    }
}
