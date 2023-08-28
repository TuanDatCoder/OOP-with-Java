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
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import models.Config;

import models.Flower;

public class FlowerManager {

    private final List<Flower> flowers;
    Config c = new Config();
    private final String flowersFilePath;

    public FlowerManager() {
        c.loadConfigData();
        flowersFilePath = c.getValue("flower file path");
        this.flowers = new ArrayList<>();
    }

    public List<Flower> getFlowers() {
        return flowers;
    }

    public void loadFlowers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(flowersFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String flowerId = parts[0].trim();
                    String name = parts[1].trim();
                    double unitPrice = Double.parseDouble(parts[2].trim());
                    LocalDate importDate = LocalDate.parse(parts[3].trim(), DateTimeFormatter.ofPattern("yyyy/MM/dd"));

                    Flower flower = new Flower(flowerId, name, unitPrice, importDate);
                    flowers.add(flower);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading flowers: " + e.getMessage());
        }
    }

    public void saveFlowerToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(flowersFilePath))) {
            for (Flower flower : flowers) {
                String line = flower.getFlowerId() + ", " + flower.getName() + ", " + flower.getUnitPrice() + ", " + flower.getImportDate().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving flowers: " + e.getMessage());
        }
    }

    // Xem Danh sach
    public void ViewFlowerList() {
        Flower flower = new Flower();
        flower.displayHeader();
        for (Flower flowert : flowers) {
            flowert.display();
        }
        System.out.println(flower.repeat("#", 80));
        String total = "TOTAL: " + flowers.size() + " flower type[s]";
        System.out.format("#%78s#\n", total);
        System.out.println(flower.repeat("#", 80));
    }

    // Add flowers
    // Ham nay de cap nhat id moi tranh bi trung
    private String generateFlowerId() {
        int maxId = 0;
        for (Flower flower : flowers) {
            int id = Integer.parseInt(flower.getFlowerId().substring(1));
            if (id > maxId) {
                maxId = id;
            }
        }
        return "F" + String.format("%03d", maxId + 1);
    }

    public void addFlower() {

        String name = Flower.inputName();
        double unitPrice = Flower.inputUnitPrice();
        LocalDate importDate = Flower.inputImportDate();
        String flowerId = generateFlowerId();
        Flower flower = new Flower(flowerId, name, unitPrice, importDate);
        flowers.add(flower);
        System.out.println("Successfully add the flower");
    }

    public Flower getFlower(String id) {
        for (Flower flower : flowers) {
            if (flower.getFlowerId().equals(id)) {
                return flower;
            }
        }
        return null;
    }

    // Lay ten
    public String flowerName(String flowerId) {
        Flower flower = this.getFlower(flowerId);
        if (flower == null) {
            System.out.println("Flower is not found.");
        } else {
            return flower.getName();
        }
        return null;
    }

    //Modify Flower
    public void modifyFlower() {
        String flowerId = Flower.inputId();
        Flower flower = this.getFlower(flowerId);
        if (flower == null) {
            System.out.println("Flower is not found.");
        } else {
            flower.update();
            System.out.println("Successfully modify the flower");
        }
    }

    //Remove flower
    public void removeFlower() {
        String id = Flower.inputId();
        Flower flower = this.getFlower(id);
        if (flower == null) {
            System.out.println("Flower is not found.");
        } else {
            flowers.remove(flower);
            System.out.println("Successfully remove the flower");
        }
    }

}
