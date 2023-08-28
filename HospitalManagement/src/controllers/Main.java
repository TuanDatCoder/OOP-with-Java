/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.IOException;
import java.time.LocalDate;
import models.Nurse;
import models.Patient;
import views.NurseMenu;
import views.PatientMenu;

/**
 *
 * @author laptop
 */
public class Main {

    public static void displayPatient() {
        System.out.println("Patient’s management:");
        System.out.println("2.1-Add a patient");
        System.out.println("2.2-List patients");
        System.out.println("2.3-Sort the patients list");
        System.out.println("2.4-Save data");
        System.out.println("2.5-Load data");
        System.out.println("Others – Quit.");

    }

    public static void displayNurse() {
        System.out.println("Nurse's management:");
        System.out.println("1.1-Create a nurse");
        System.out.println("1.2-Find the nurse");
        System.out.println("1.3-Update the nurse");
        System.out.println("1.4-Delete the nurse");

    }

    public static void main(String[] args) {
        String op;
        boolean changeData = false;
        Hospital hospital = new Hospital();
        boolean checkLoad = false;
        while (true) {
            System.out.println("");
            displayNurse();
            System.out.println("");
            displayPatient();
            op = CheckInput.inputStr("Enter your choice", "Need to enter a value for the string!");
            switch (op) {
                case "1":
                    System.out.println("Select each item in the Patient section");
                    break;
                case "1.1":
                    hospital.createANurse();
                    break;
                case "1.2":
                    hospital.findANurse();
                    break;
                case "1.3":
                    hospital.updateANurse();
                    break;
                case "1.4":
                    hospital.deleteANurse();
                    break;
                case "2":
                    System.out.println("Select each item in the Nurse section");
                    break;
                case "2.1":
                    hospital.addAPatient();
                    break;
                case "2.2":
                    hospital.listAPatients();
                    break;
                case "2.3":
                    hospital.sortPatients();

                    break;
                case "2.4":
                    // thêm voi để gài cho chắc nó đỡ phải duyệt nhiều
                    if (checkLoad == false) { //không can cai nay voi vi no chi them nhưng thang khong trung id voi no
                        Hospital TempHospital = new Hospital();
                        // neu khong load thì khi luu se mat het du lieu cu
                        TempHospital.loadNursesFromFile("");
                        TempHospital.loadPatientsFromFile("");
                        hospital.getNurses().putAll(TempHospital.getNurses());
                        hospital.getPatients().putAll(TempHospital.getPatients());
                    }

                    hospital.saveNursesToFile();
                    hospital.savePatientsToFile();
                    break;
                case "2.5":
                    checkLoad = true;
                    hospital.loadNursesFromFile("Loaded nurse file successfully.\n");
                    hospital.loadPatientsFromFile("Loaded patient file successfully.\n");
                    break;

                default:

                    while (true) {
                        //
                        if (CheckInput.InputOpTF("Exit the program(1/T/Y) or continue(0/F/N)")) {
                            // Nếu mà có thay đổi
                                if (hospital.quit()) { 
                                    if (checkLoad == false) { 
                                        Hospital TempHospital = new Hospital();
                                        TempHospital.loadNursesFromFile("");
                                        TempHospital.loadPatientsFromFile("");
                                        hospital.getNurses().putAll(TempHospital.getNurses());
                                        hospital.getPatients().putAll(TempHospital.getPatients());
                                    }
                                    hospital.saveNursesToFile();
                                    hospital.savePatientsToFile();
                                    
                                }
                            System.out.println("Exit the program!");
                            System.exit(0);
                        } else {
                            break;
                        }
                    }

            }

        }
    }
}
