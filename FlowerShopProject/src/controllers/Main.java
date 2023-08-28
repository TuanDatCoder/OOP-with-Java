/*
Dinh Quoc Tuan Dat - SE171685
 */
package controllers;

import models.Account;
import views.DevMenu;
import views.UserMenu;

public class Main {

    public static void main(String[] args) {

        AccountManager accountManager = new AccountManager();
        accountManager.loadAccounts();
        int accountIndex = accountManager.login();

        if (accountIndex >= 0) {
            System.out.println("Successfully login.");
            Account account = accountManager.getAccounts().get(accountIndex);
            String role = account.getRole();
            // Kiểm tra role và hiển thị thông tin tương ứng
            if (role.equals("DEV")) {
                // Thực hiện công việc của Dev
                DevMenu.ChoiceMenu(accountIndex);
            } else if (role.equals("USER")) {
                // Thực hiện công việc của User
                UserMenu.ChoiceMenu(accountIndex);
            }
        } else {
            System.out.println("Login failed!");
        }

    }
}
