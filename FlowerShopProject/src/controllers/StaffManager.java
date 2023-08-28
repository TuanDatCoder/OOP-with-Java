/*
Dinh Quoc Tuan Dat - SE171685
 */
package controllers;

/**
 *
 * @author laptop
 */
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.Config;
import models.Staff;

public class StaffManager {

    private final List<Staff> staffs;
    Config c = new Config();
    private final String staffsFilePath;

    public StaffManager() {
        c.loadConfigData();
        staffsFilePath = c.getValue("staff file path");
        staffs = new ArrayList<>();
    }

    public List<Staff> getStaffs() {
        return staffs;
    }

    public void loadStaffs() {
        try (BufferedReader reader = new BufferedReader(new FileReader(staffsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String staffId = parts[0].trim();
                    String name = parts[1].trim();
                    String address = parts[2].trim();
                    String phone = parts[3].trim();

                    Staff staff = new Staff(staffId, name, address, phone);
                    staffs.add(staff);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading staffs: " + e.getMessage());
        }
    }

    public void saveStaffToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(staffsFilePath))) {
            for (Staff staff : staffs) {
                String line = staff.getStaffId() + ","
                        + staff.getName() + ","
                        + staff.getAddress() + ","
                        + staff.getPhone();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Successfully save data");
        } catch (IOException e) {
            System.out.println("Error saving customers to file: " + e.getMessage());
        }
    }

    public Staff getStaff(String id) {
        for (Staff staff : staffs) {
            if (staff.getStaffId().equals(id)) {
                return staff;
            }
        }
        return null;
    }

    //Update Profile
    public void updateProfile(String id) {

        Staff staff = this.getStaff(id);
        if (staff == null) {
            System.out.println("Customer is not found.");
        } else {
            staff.update();
            System.out.println("Successfully update account");
        }

    }

}
