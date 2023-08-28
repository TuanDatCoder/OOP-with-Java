/*
Dinh Quoc Tuan Dat - SE171685
 */
package views;

import controllers.AccountManager;
import controllers.CustomerManager;
import controllers.FlowerManager;
import controllers.OrderManager;
import models.Account;

/**
 *
 * @author laptop
 */
public class UserMenu {

    public static void display() {
        System.out.println("USER MENU:");
        System.out.println("1-Update Profile ");
        System.out.println("2-View Flower List");
        System.out.println("3-Add Flower to Cart");
        System.out.println("4-View Order");
        System.out.println("5-Cancel Order");
        System.out.println("6-Quit");
    }

    public static void ChoiceMenu(int accountIndex) {
        int op;

        AccountManager accountManager = new AccountManager();
        accountManager.loadAccounts();

        CustomerManager customerManager = new CustomerManager();
        customerManager.loadCustomers();

        FlowerManager flowerManager = new FlowerManager();
        flowerManager.loadFlowers();

        OrderManager orderManager = new OrderManager();
        orderManager.loadOrders();
        boolean checkOrderfirst = false;
        String OrderID = null;

        Account account = accountManager.getAccounts().get(accountIndex);
        String CustomerID = account.getStaffOrCustomerId();

        do {
            display();
            op = CommonMenu.inputOp();
            switch (op) {
                case 1:
                    customerManager.updateProfile(CustomerID);
                    break;
                case 2:
                    flowerManager.ViewFlowerList();
                    break;
                case 3:
                    if (checkOrderfirst == false) {
                        checkOrderfirst = true;
                        OrderID = orderManager.addOrder(CustomerID);
                    }
                    orderManager.addFlowerToCart(OrderID, CustomerID);
                    break;
                case 4:
                    orderManager.ViewOrder(CustomerID);
                    break;
                case 5:
                    orderManager.cancelOrder(OrderID);
                    checkOrderfirst = false;
                    break;
                default:

                    if (CommonMenu.saveMenu() == true) {
                        // Nhung thu can luu khi log out
                        customerManager.saveCustomersToFile();
                        orderManager.saveOrdersToFile();
                    } else {
                        System.out.println("Data is not saved");
                    }
                    System.out.println("Sucessfully logout");

                    System.exit(0);
            }

        } while (op > 0 && op < 7);

    }

}
