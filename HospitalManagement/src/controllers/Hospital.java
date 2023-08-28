/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import models.Nurse;
import models.Patient;

/**
 *
 * @author laptop
 */
public class Hospital {

    private HashMap<String, Nurse> nurses;
    private HashMap<String, Patient> patients;
    boolean changeData = false;

    public Hospital() {
        nurses = new HashMap<>();
        patients = new HashMap<>();
    }

    public HashMap<String, Nurse> getNurses() {
        return nurses;
    }

    public HashMap<String, Patient> getPatients() {
        return patients;
    }

    // ---------------Function 1: Create a nurse-----------------
    public void createANurse() {
        while (true) {
            String staffId = CheckInput.inputStaffID(nurses, "Enter StaffID", "Empty value");
            String name = CheckInput.inputStr("Enter Name", "Empty value");
            int age = CheckInput.inputAge("Enter age", "The age field must be a positive number.");
            String gender = CheckInput.inputStr("Enter Gender", "Empty value");
            String address = CheckInput.inputStr("Enter Address", "Empty value");
            String phone = CheckInput.inputPhone("Enter Phone", "The import phone field must be a valid phone");
            String department = CheckInput.inputDepartment("Enter Department", "the department field must be from 3 to 50 characters");
            String shift = CheckInput.inputStr("Enter Shift", "Empty value");
            double salary = CheckInput.inputSalary("Enter Salary", "The salary field must be a positive number.");

            Nurse nurse = new Nurse(staffId, name, age, gender, address, phone, department, shift, salary);
            nurses.put(nurse.getStaffID(), nurse);
            System.out.println("Successfully create a Nurse\n");
            this.changeData = true;
            // Hỏi người dùng có tiếp tục thêm y tá mới hay không
            boolean choice = CheckInput.InputOpTF("Continue adding a new nurse ('1/T/Y') or back to the main menu ('0/F/N')");
            if (!choice) {
                break; // Thoát khỏi vòng lặp nếu người dùng không muốn tiếp tục
            }

        }
    }

    // ---------------Function 2: Find a nurse-----------------
    public Nurse getNurseName(String name) {
        for (Nurse nurse : nurses.values()) {
            if (nurse.getName().toLowerCase().contains(name.toLowerCase())) {
                return nurse;
            }
        }
        return null;
    }

    public void findANurse() {

        if (nurses.isEmpty()) {
            System.out.println("The nurse does not exist!");
        } else {
            String name = CheckInput.inputStr("The nurse's name", "Empty value");
            for (Nurse nurse : nurses.values()) {
                if (nurse.getName().toLowerCase().contains(name.toLowerCase())) {
                    System.out.println("-------");
                    System.out.println(nurse);
                }
            }
        }
    }

    // ---------------Function 3: Update a nurse-----------------
    public Nurse getNurseID(String id) {
        for (Nurse nurse : nurses.values()) {
            if (nurse.getStaffID().equals(id)) {
                return nurse;
            }

        }
        return null;
    }

    public void updateANurse() {
        
        String staffId = CheckInput.inputStr("Enter StaffID", "Empty value");
        Nurse nurse = this.getNurseID(staffId);
        if (nurse == null) {
            System.out.println("The nurse does not exist");
            System.out.println("Update failed!");
        } else {
            nurse.update();
            System.out.println("Update successful!");
            this.changeData = true;
        }
        
    }

    // ---------------Function 4: Delete a nurse-----------------
    public void deleteANurse() {
        String staffId = CheckInput.inputStr("Enter StaffID", "Empty value");
        Nurse nurse = this.getNurseID(staffId);
        if (nurse == null) {
            System.out.println("The nurse does not exist");
            System.out.println("Delete failed!");
        } else {
            boolean opTF = CheckInput.InputOpTF("Do you want to remove a nurse (enter '1/T/Y') or otherwise (enter '0/F/N')");
            if (opTF) {
                if (nurse.getPatients() == null || nurse.getPatients().isEmpty()) {
                    nurses.remove(nurse.getStaffID());
                    System.out.println("Delete successfully!");
                    this.changeData = true;
                } else {
                    System.out.println("The nurse cannot be deleted if she has a task (look after a patient).");
                }
            } else {
                System.out.println("Canceled delete!");
            }
        }

    }

    // ---------------Function 5: Add a patient-----------------
    public String repeat(String s, int n) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < n; i++) {
            result.append(s);
        }
        return result.toString();
    }

    public int printChoiceNurse() {
        System.out.println("\n" + repeat("#", 55));
        System.out.format("%9s%37s%9s", repeat("#", 9), "( Nurses with less than 2 patients ) ", repeat("#", 9));
        System.out.println("\n" + repeat("#", 55));
        int index = 0;
        if (nurses.isEmpty()) {
            System.out.format("#%53s#\n", "Data is empty, Need to load data.");
        } else {
            for (Nurse nurse : nurses.values()) {
                if (nurse.getPatients().size() < 2) {
                    System.out.format("#%17d#%17s#%17s#\n", index, nurse.getStaffID(), nurse.getName());
                    index++;
                }

            }
        }
        System.out.println(repeat("#", 55));
        return index;
    }

    public boolean checkPaOfNu(String id) {
        Nurse nurse = this.getNurseID(id);
        if (nurse == null) {
            System.out.println("The nurse does not exist");
            return false;
        } else if (nurse.getPatients().size() > 1) {
            System.out.println("Nurse has reached the maximum number of patients to care for.");
            return false;
        } else {
            return true;
        }
    }

    public void addAPatient() {
        while (true) {
            int nurseCount = 0;
            String id = CheckInput.inputStr("Enter ID", "Empty value");
            String name = CheckInput.inputStr("Enter Name", "Empty value");
            int age = CheckInput.inputAge("Enter Age", "Invalid age");
            String gender = CheckInput.inputStr("Enter Gender", "Empty value");
            String address = CheckInput.inputStr("Enter Address", "Empty value");
            String phone = CheckInput.inputPhone("Enter Phone", "Invalid phone");
            String diagnosis = CheckInput.inputStr("Enter Diagnosis", "Empty value");
            LocalDate admissionDate = CheckInput.inputDate("Enter Admission Date (dd/mm/yyyy)", "Invalid date");
            LocalDate dischargeDate = CheckInput.inputAfterDate(admissionDate, "Enter Discharge Date (dd/mm/yyyy)", "Invalid date");

            // cac thong tin quan trong da duoc them vao
            Patient patient = new Patient(id, name, age, gender, address, phone, diagnosis, admissionDate, dischargeDate);

            // Bay gio chu luu
            // them them dieu kien 1 benh chi co 2 y ta va 1 y ta co them cham soc toi da 2 benh nhan
            String checkFirstN = null;
            while (nurseCount < 2) {
                int count = printChoiceNurse();
                if (count < 2) {
                    System.out.println("Not enough nurses are qualified to care for a patient.");
                    System.out.println("Fail create a Patient");
                    return;
                }
                String opNurse = CheckInput.inputStr("Enter Nurse ID", "Empty value");

                if (checkPaOfNu(opNurse)) {
                    // mot benh nhan phai co 2 y ta khong cho trung
                    if (opNurse.equals(checkFirstN)) {
                        System.out.println("No two nurses can be the same");

                        continue;
                    }
                    checkFirstN = opNurse;

                    Nurse nurse = nurses.get(opNurse);// lúc này nó luôn đúng checkPaOfNu đã kiểm tra cái id đó rồi
                    nurse.getPatients().put(patient.getId(), patient);
                    patient.getNursesAssigned().put(nurse.getStaffID(), nurse);
                    nurseCount++;
                    System.out.println("Successfully added nurse " + nurse.getName());

                }
            }

            patients.put(patient.getId(), patient);
            System.out.println("Successfully create a Patient");
            this.changeData = true;
            // Hỏi người dùng có tiếp tục thêm y tá mới hay không
            boolean choice = CheckInput.InputOpTF("Continue adding a new patient ('1/T/Y')or back to the main menu ('0/F/N')");
            if (!choice) {
                break; // Thoát khỏi vòng lặp nếu người dùng không muốn tiếp tục
            }

        }

    }

    // ---------------Function 6: List a patients-----------------
    public void displayHeaderPatient() {
        System.out.println(repeat("-", 85));
        System.out.format("|%5s | %-12s|%16s | %-15s|%11s | %-13s|\n", "No.", "Patient Id", "Admission Date", "Full name", "Phone", "Diagonsis");
        System.out.println(repeat("-", 85));
    }

    public void listAPatients() {
        System.out.println("LIST OF PATIENTS");
        LocalDate start = CheckInput.inputDate("Start date", "Invalid date");
        LocalDate end = CheckInput.inputAfterDate(start, "End date", "Invalid date");
        displayHeaderPatient();
        int index = 1;
        for (Patient patient : patients.values()) {
            if (patient.getAdmissionDate().isAfter(start) && patient.getAdmissionDate().isBefore(end)) {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String formattedAdmissionDate = patient.getAdmissionDate().format(formatter);
                System.out.format("|%5d | %-12s|%16s | %-15s|%11s |%13s |\n", index, patient.getId(), formattedAdmissionDate, patient.getName(), patient.getPhone(), patient.getDiagnosis());
                System.out.println(repeat("-", 85));
                index++;

            }
        }
    }

    // ---------------Function 7: Sort patients-------------------
    public void sortPatients() {
        System.out.println("LIST OF PATIENTS");
        String sortedBy = CheckInput.inputSortBy();
        String sortOrder = CheckInput.inputType();

        Comparator<Patient> comparator;
        if (sortedBy.equalsIgnoreCase("patient id")) {
            comparator = Comparator.comparing(Patient::getId);
        } else if (sortedBy.equalsIgnoreCase("patient's name")) {
            comparator = Comparator.comparing(Patient::getName);
        } else {
            System.out.println("Invalid input for sort by.");
            return;
        }

        List<Patient> sortedPatients = patients.values().stream()
                .filter(person -> person instanceof Patient)
                .map(person -> (Patient) person)
                .sorted(sortOrder.equalsIgnoreCase("ASC") ? comparator : comparator.reversed())
                .collect(Collectors.toList());

        displayHeaderPatient();
        for (Patient patient : sortedPatients) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedAdmissionDate = patient.getAdmissionDate().format(formatter);
            System.out.format("|%5s | %-12s|%16s | %-15s|%11s |%13s |\n", "", patient.getId(), formattedAdmissionDate, patient.getName(), patient.getPhone(), patient.getDiagnosis());
            System.out.println(repeat("-", 85));
        }
    }

//    public void sortPatients() {
//        System.out.println("LIST OF PATIENTS");
//        String sortedBy = CheckInput.inputSortBy();
//        String sortOrder = CheckInput.inputType();
//
//        // xu ly
//        displayHeaderPatient();
//
//        int index = 1;
//        for (Patient patient : sortedPatients.values()) {
//            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
//            String formattedAdmissionDate = patient.getAdmissionDate().format(formatter);
//            System.out.format("|%5d | %-12s|%16s | %-15s|%11s |%13s |\n", index, patient.getId(), formattedAdmissionDate, patient.getName(), patient.getPhone(), patient.getDiagnosis());
//            System.out.println(repeat("-", 85));
//            index++;
//        }
//
//    }
    // ---------------Function 8: Save data-----------------------
    // Phương thức để lưu dữ liệu vào file data/nurses.dat
    public void saveNursesToFile() {

        try {
            FileOutputStream fos = new FileOutputStream("data/nurses.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(nurses);
            oos.close();
            fos.close();
            System.out.println("Successfully saved nurse file.");
        } catch (IOException e) {
            System.out.println("Failed to save nurse file." + e.getMessage());
        }
    }

    // Phương thức để lưu dữ liệu vào file patients.dat
    public void savePatientsToFile() {

        try {
            FileOutputStream fos = new FileOutputStream("data/patients.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(patients);
            oos.close();
            fos.close();
            System.out.println("Successfully saved patient file.");
        } catch (IOException e) {
            System.out.println("Failed to save patient file." + e.getMessage());
        }
    }

    // ---------------Function 9: Load data-----------------
    public void loadNursesFromFile(String title) {
        try {
            File nursesFile = new File("data/nurses.dat");

            if (!nursesFile.exists()) {
                nursesFile.createNewFile();
                nurses = new HashMap<>();
            } else {
                FileInputStream fis = new FileInputStream(nursesFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                nurses = (HashMap<String, Nurse>) ois.readObject();
                ois.close();
                fis.close();
            }
            System.out.print(title);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load nurse file." + e.getMessage());
        }
    }

    public void loadPatientsFromFile(String title) {
        try {
            File patientsFile = new File("data/patients.dat");

            if (!patientsFile.exists()) {
                patientsFile.createNewFile();
                patients = new HashMap<>();
            } else {
                FileInputStream fis = new FileInputStream(patientsFile);
                ObjectInputStream ois = new ObjectInputStream(fis);
                patients = (HashMap<String, Patient>) ois.readObject();
                ois.close();
                fis.close();
            }
            System.out.print(title);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Failed to load patient file." + e.getMessage());
        }
    }

    // ---------------Function 10: Quit-----------------
//    public void quit(){
//        
//        while(true){
//            if(CheckInput.InputOpTF("Exit the program(1/T/Y) or continue(0/F/N)")){  
//                if(changeData){
//                    saveNursesToFile();
//                    savePatientsToFile();
//                }
//                System.out.println("Exit the program!");
//                System.exit(0);
//            }
//            else{
//                
//            }
//        }
//        
//    }
    public boolean quit() {
        return changeData;
    }

    // ------------------ Other ------------------------
    public void displayData() {
        System.out.println("Nurses:");
        for (Nurse nurse : nurses.values()) {
            System.out.println("----");
            System.out.println(nurse);
        }
//        if (patients.isEmpty()) {
//            System.out.println("Rong");
//        }
//        System.out.println("--------------");
//        System.out.println("Patients:");
//        for (Patient patient : patients.values()) {
//            System.out.println(patient);
//        }
    }

    public void printPatientNurses() {
        for (Patient patient : patients.values()) {
            System.out.println("");
            System.out.println("Patient ID: " + patient.getId());
            System.out.println("Patient Name: " + patient.getName());
            System.out.println("Patients assigned to the nurse:");

            if (patient.getNursesAssigned() != null && !patient.getNursesAssigned().isEmpty()) {
                for (Nurse nurse : patient.getNursesAssigned().values()) {
                    System.out.println("Nurse ID: " + nurse.getStaffID());
                    System.out.println("Nurse Name: " + nurse.getName());
                }
            } else {
                System.out.println("No patient assigned to this nurse");
            }

            System.out.println(); // Xuống dòng để phân tách thông tin giữa các y tá
        }
    }

    public void printNursesAndPatients() {
        for (Nurse nurse : nurses.values()) {
            System.out.println("");
            System.out.println("Nurse ID: " + nurse.getStaffID());
            System.out.println("Nurse Name: " + nurse.getName());
            System.out.println("Patients under care:");

            if (nurse.getPatients() != null && !nurse.getPatients().isEmpty()) {
                for (Patient patient : nurse.getPatients().values()) {
                    System.out.println("Patient ID: " + patient.getId());
                    System.out.println("Patient Name: " + patient.getName());
                    // In ra thông tin bệnh nhân khác tùy theo yêu cầu của bạn
                }
            } else {
                System.out.println("No patients under care.");
            }

            System.out.println(); // Xuống dòng để phân tách thông tin giữa các y tá
        }
    }

}
