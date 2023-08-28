/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.HashMap;

/**
 *
 * @author laptop
 */
public class Patient extends Person implements Serializable {
    private String id;
    private String diagnosis;
    private LocalDate admissionDate;
    private LocalDate dischargeDate;
    private HashMap<String, Nurse> nursesAssigned;

    /**
     *
     */
    public Patient() {

    }

    public Patient(String id, String name, int age, String gender, String address, String phone, String diagnosis, LocalDate admissionDate, LocalDate dischargeDate) {
        super(name, age, gender, address, phone);
        this.id=id;
        this.diagnosis = diagnosis;
        this.admissionDate = admissionDate;
        this.dischargeDate = dischargeDate;
        this.nursesAssigned = new HashMap<>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public LocalDate getAdmissionDate() {
        return admissionDate;
    }

    public LocalDate getDischargeDate() {
        return dischargeDate;
    }

    public HashMap<String, Nurse> getNursesAssigned() {
        return nursesAssigned;
    }

    @Override
    public String toString() {
        return "Patient ID: " + this.id + "\n"
                + "Name: " + this.name + "\n"
                + "Age: " + this.age + "\n"
                + "Gender: " + this.gender + "\n"
                + "Address: " + this.address + "\n"
                + "Phone: " + this.phone + "\n"
                + "Diagnosis: " + this.diagnosis + "\n"
                + "Admission Date: " + this.admissionDate + "\n"
                + "Discharge Date: " + this.dischargeDate;
    }

}
