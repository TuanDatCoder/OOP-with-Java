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
public class Customer implements Serializable {

    private String customerId;
    private String name;
    private String address;
    private String phone;
    private static Scanner sc = new Scanner(System.in);

    public Customer(String customerId, String name, String address, String phone) {
        this.customerId = customerId;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public static String inputName() {
        System.out.print("Enter name: ");
        return sc.nextLine();
    }

    public static String inputAddress() {
        System.out.print("Enter address: ");
        return sc.nextLine();
    }

    public static String inputPhone() {
        //Phone length is between 9 and 11
        while (true) {
            try {
                System.out.print("Enter Phone number: ");
                String phone = sc.nextLine();
                //Kiem tra tinh hop le cua du lieu = Data invalidation
                if (phone.length() != 9 && phone.length() != 11) {
                    throw new Exception("Phone number must contain exactly 9 or 11 digit");
                }
                return phone;
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    public void update() {
        this.name = inputName();
        this.address = inputAddress();
        this.phone = inputPhone();
    }

}
