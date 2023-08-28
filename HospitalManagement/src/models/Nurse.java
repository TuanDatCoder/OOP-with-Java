/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import controllers.CheckInput;
import java.io.Serializable;
import java.util.HashMap;

/**
 *
 * @author laptop
 */
public class Nurse extends Person implements Serializable {

    private String staffID;
    private String department;
    private String shift;
    private double salary;
    private HashMap<String, Patient> patients;

    /**
     *
     */
    public Nurse() {

    }

    public Nurse( String staffID, String name, int age, String gender, String address, String phone, String department, String shift, double salary) {
        super(name, age, gender, address, phone);
        this.staffID = staffID;
        this.department = department;
        this.shift = shift;
        this.salary = salary;
        this.patients = new HashMap<>();
    }

    public String getStaffID() {
        return staffID;
    }

    public String getDepartment() {
        return department;
    }

    public String getShift() {
        return shift;
    }

    public double getSalary() {
        return salary;
    }

    public HashMap<String, Patient> getPatients() {
        return patients;
    }
    
    public void update(){
            this.name = CheckInput.inputStr("Enter Name", "Empty value");
            this.age = CheckInput.inputAge("Enter age", "The age field must be a positive number.");
            this.gender = CheckInput.inputStr("Enter Gender", "Empty value");
            this.address = CheckInput.inputStr("Enter Address", "Empty value");
            this.phone = CheckInput.inputPhone("Enter Phone", "The import phone field must be a valid phone");
            this.department = CheckInput.inputDepartment("Enter Department", "the department field must be from 3 to 50 characters");
            this.shift = CheckInput.inputStr("Enter Shift", "Empty value");
            this.salary = CheckInput.inputSalary("Enter Salary", "The salary field must be a positive number.");
    }
    
    @Override
    public String toString() {
        return "Nurse ID: " + this.staffID + "\n"
                + "Name: " + this.name + "\n"
                + "Age: " + this.age + "\n"
                + "Gender: " + this.gender + "\n"
                + "Address: " + this.address + "\n"
                + "Phone: " + this.phone + "\n"
                + "Department: " + this.department + "\n"
                + "Shift: " + this.shift + "\n"
                + "Salary: " + this.salary;
    }

}
