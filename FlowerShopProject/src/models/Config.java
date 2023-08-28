/*
Dinh Quoc Tuan Dat - SE171685
 */
package models;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Config {

    private static final String CONFIG_FILE_PATH = "data/config.dat";
    private final Map<String, String> configData;

    public Config() {
        configData = new HashMap<>();
    }

    public void loadConfigData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CONFIG_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(": ");
                if (parts.length == 2) {
                    String key = parts[0].trim();
                    String value = parts[1].trim();
                    configData.put(key, value);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading config data: " + e.getMessage());
        }
    }

    public String getValue(String key) {
        return configData.get(key);
    }

    public void setValue(String key, String value) {
        configData.put(key, value);
    }

    public void saveConfigData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(CONFIG_FILE_PATH))) {
            writer.write("account file path: " + configData.getOrDefault("account file path", ""));
            writer.newLine();
            writer.write("flower file path: " + configData.getOrDefault("flower file path", ""));
            writer.newLine();
            writer.write("order file path: " + configData.getOrDefault("order file path", ""));
            writer.newLine();
            writer.write("customer file path: " + configData.getOrDefault("customer file path", ""));
            writer.newLine();
            writer.write("staff file path: " + configData.getOrDefault("staff file path", ""));
        } catch (IOException e) {
            System.out.println("Error saving config data: " + e.getMessage());
        }
    }

}
