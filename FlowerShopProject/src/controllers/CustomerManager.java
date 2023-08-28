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
import models.Customer;

public class CustomerManager {

    private final List<Customer> customers;
    Config c = new Config();
    private final String customersFilePath;

    public CustomerManager() {
        c.loadConfigData();
        customersFilePath = c.getValue("customer file path");
        this.customers = new ArrayList<>();
    }

    public List<Customer> getCustomers() {
        return customers;
    }

    public void loadCustomers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(customersFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String customerId = parts[0].trim();
                    String name = parts[1].trim();
                    String address = parts[2].trim();
                    String phone = parts[3].trim();
                    Customer customer = new Customer(customerId, name, address, phone);
                    customers.add(customer);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading customers: " + e.getMessage());
        }
    }

    public void saveCustomersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(customersFilePath))) {
            for (Customer customer : customers) {
                String line = customer.getCustomerId() + ","
                        + customer.getName() + ","
                        + customer.getAddress() + ","
                        + customer.getPhone();
                writer.write(line);
                writer.newLine();
            }
            System.out.println("Successfully save data");
        } catch (IOException e) {
            System.out.println("Error saving customers to file: " + e.getMessage());
        }
    }

    public Customer getCustomer(String id) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(id)) {
                return customer;
            }
        }
        return null;
    }

    public String customerName(String customerId) {
        Customer customer = this.getCustomer(customerId);
        if (customer == null) {
            System.out.println("Customer is not found.");
        } else {
            return customer.getName();
        }
        return null;
    }

    //Update Profile
    public void updateProfile(String id) {

        Customer customer = this.getCustomer(id);
        if (customer == null) {
            System.out.println("Customer is not found.");
        } else {
            customer.update();
            System.out.println("Successfully update account");
        }

    }

}
