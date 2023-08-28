/*
Dinh Quoc Tuan Dat - SE171685
 */
package views;

import controllers.AccountManager;
import controllers.FlowerManager;
import controllers.OrderManager;
import controllers.StaffManager;
import models.Account;

/**
 *
 * @author laptop
 */
public class DevMenu {

    public static void display() {
        System.out.println("DEV MENU:");
        System.out.println("1-Update Profile ");
        System.out.println("2-View Flower List");
        System.out.println("3-Add Flower");
        System.out.println("4-Modify Flower");
        System.out.println("5-Remove Flower");
        System.out.println("6-View Sorted Orders");
        System.out.println("7-Remove Order");
        System.out.println("8-Quit");

    }

    public static void ChoiceMenu(int accountIndex) {
        int op;

        AccountManager accountManager = new AccountManager();
        accountManager.loadAccounts();

        StaffManager staffManager = new StaffManager();
        staffManager.loadStaffs();

        FlowerManager flowerManager = new FlowerManager();
        flowerManager.loadFlowers();

        OrderManager orderManager = new OrderManager();
        orderManager.loadOrders();

        Account account = accountManager.getAccounts().get(accountIndex);
        String staffID = account.getStaffOrCustomerId();
        do {
            display();
            op = CommonMenu.inputOp();
            switch (op) {
                case 1:
                    staffManager.updateProfile(staffID);
                    break;
                case 2:
                    flowerManager.ViewFlowerList();
                    break;
                case 3:
                    // Add flower Dev
                    flowerManager.addFlower();
                    break;
                case 4:
                    flowerManager.modifyFlower();
                    break;
                case 5:
                    flowerManager.removeFlower();
                    break;
                case 6:
                    orderManager.viewSortedOrder();
                    break;
                case 7:
                    orderManager.removeOrder();
                    break;
                default:
                    if (CommonMenu.saveMenu() == true) {
                        staffManager.saveStaffToFile();
                        flowerManager.saveFlowerToFile();
                        orderManager.saveOrdersToFile();
                    } else {
                        System.out.println("Data is not saved");
                    }
                    System.out.println("Sucessfully logout");

                    System.exit(0);
            }

        } while (op > 0 && op < 9);

    }
}
