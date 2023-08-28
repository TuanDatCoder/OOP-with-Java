/*
Dinh Quoc Tuan Dat - SE171685
 */
package controllers;

/**
 *
 * @author laptop
 */
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import models.Account;
import models.Config;

public class AccountManager {

    private final List<Account> accounts;
    Config c = new Config();
    private final String accountsFilePath;

    public AccountManager() {
        c.loadConfigData();
        accountsFilePath = c.getValue("account file path");
        this.accounts = new ArrayList<>();
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    // login tra ve vi tri trong list
    public int login() {
        String accountID = Account.inputId();
        String password = Account.inputPassword();
        int index = 0; // Biến đếm vị trí
        for (Account acc : accounts) {
            if (acc.getAccountID().equals(accountID) && acc.getPassword().equals(password)) {
                return index; // Trả về vị trí của tài khoản
            }
            index++; // Tăng giá trị biến đếm
        }

        return -1; // Không tìm thấy tài khoản trong mảng
    }

    public void loadAccounts() {
        try (BufferedReader reader = new BufferedReader(new FileReader(accountsFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 4) {
                    String accountId = parts[0].trim();
                    String password = parts[1].trim();
                    String role = parts[2].trim();
                    String staffOrCustomerId = parts[3].trim();
                    Account account = new Account(accountId, password, role, staffOrCustomerId);
                    accounts.add(account);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading accounts: " + e.getMessage());
        }
    }

}
