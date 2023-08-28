/*
Dinh Quoc Tuan Dat - SE171685
 */
package models;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class Flower implements Serializable {

    private String flowerId;
    private String name;
    private double unitPrice;
    private LocalDate importDate;
    private static Scanner sc = new Scanner(System.in);

    public Flower() {
    }

    public Flower(String flowerId, String name, double unitPrice, LocalDate importDate) {
        this.flowerId = flowerId;
        this.name = name;
        this.unitPrice = unitPrice;
        this.importDate = importDate;
    }

    public String getFlowerId() {
        return flowerId;
    }

    public void setFlowerId(String flowerId) {
        this.flowerId = flowerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public LocalDate getImportDate() {
        return importDate;
    }

    public void setImportDate(LocalDate importDate) {
        this.importDate = importDate;
    }

    public String repeat(String s, int n) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < n; i++) {
            result.append(s);
        }
        return result.toString();
    }

    public static String inputId() {
        //id is in the Fxxx format
        while (true) {
            try {
                System.out.print("Enter flowerId FXXX: ");
                String id = sc.nextLine();
                //Kiem tra id co dung format
                if (!id.matches("^F\\d{3}$")) {
                    throw new Exception("");
                }
                return id;
            } catch (Exception ex) {
                //System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    // Input
    public static String inputName() {
        System.out.print("Enter name: ");
        return sc.nextLine();
    }

    public static double inputUnitPrice() {
        while (true) {
            try {
                System.out.print("Enter price: ");
                return Double.parseDouble(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid price.");
            }
        }
    }

    public static LocalDate inputImportDate() {
        while (true) {
            try {
                System.out.print("Enter date: ");
                String input = sc.nextLine();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                return LocalDate.parse(input, formatter);
            } catch (Exception e) {
                System.out.println("Invalid input. Please enter a date in the format yyyy/MM/dd.");
            }
        }
    }

    public static int inputQuantities() {
        while (true) {
            try {
                System.out.print("Enter quantities: ");
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid price.");
            }
        }
    }

    // In ra man hinh
    public void displayHeader() {
        System.out.println(repeat("#", 80));
        System.out.format("#%18s#%19s#%19s#%19s#\n", "id", "name", "price", "importDate");
        System.out.println(repeat("#", 80));
    }

    public void display() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedImportDate = importDate.format(formatter);
        String formattedUnitPrice = unitPrice + "";
        System.out.format("#%18s#%19s#%19s#%19s#\n", flowerId, name, formattedUnitPrice, formattedImportDate);
    }

    public void update() {
        this.name = inputName();
        this.unitPrice = inputUnitPrice();
        this.importDate = inputImportDate();
    }

}
