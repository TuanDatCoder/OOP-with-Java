/*
Dinh Quoc Tuan Dat - SE171685
 */
package models;

import java.io.Serializable;
import java.util.Scanner;

/**
 *
 * @author laptop
 */
public class Account implements Serializable {

    private String accountID;
    private String password;
    private String role;
    private String staffOrCustomerId;

    private static Scanner sc = new Scanner(System.in);

    public Account() {
    }

    public Account(String accountID, String password, String role, String staffOrCustomerId) {
        this.accountID = accountID;
        this.password = password;
        this.role = role;
        this.staffOrCustomerId = staffOrCustomerId;
    }

    public String getAccountID() {
        return accountID;
    }

    public void setAccountID(String accountID) {
        this.accountID = accountID;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getStaffOrCustomerId() {
        return staffOrCustomerId;
    }

    public void setStaffOrCustomerId(String staffOrCustomerId) {
        this.staffOrCustomerId = staffOrCustomerId;
    }

    public static String inputId() {
        //id is in the Axxx format
        while (true) {
            try {
                System.out.print("Enter id AXXX: ");
                String id = sc.nextLine();
                //Kiem tra id co dung format
                if (!id.matches("^A\\d{3}$")) {
                    throw new Exception("");
                }
                return id;
            } catch (Exception ex) {
                //System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    public static String inputPassword() {
        while (true) {
            try {
                System.out.print("Enter password: ");
                String password = sc.nextLine();
                if (!password.matches("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$")) {
                    throw new Exception("Password must be at lest 8 characters comrpised by at least one character, one digit, and one special symbol");
                }
                return password;
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

}
