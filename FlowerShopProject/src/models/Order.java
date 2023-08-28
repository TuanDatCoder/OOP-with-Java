/*
Dinh Quoc Tuan Dat - SE171685
 */
package models;

/**
 *
 * @author laptop
 */
import controllers.CustomerManager;
import controllers.FlowerManager;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Order {

    private String orderId;
    private LocalDate orderDate;
    private double totalPrice;
    private String customerId;
    private static Scanner sc = new Scanner(System.in);
    private Map<String, OrderItem> items;

    public List<OrderItem> getOrderItems() {
        return new ArrayList<>(items.values());
    }

    public Order(String orderId, LocalDate orderDate, double totalPrice, String customerId) {
        this.orderId = orderId;
        this.orderDate = orderDate;
        this.totalPrice = totalPrice;
        this.customerId = customerId;
        this.items = new HashMap<>();
    }

    public Order() {

    }

    public String getOrderId() {
        return orderId;
    }

    public LocalDate getOrderDate() {
        return orderDate;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    public void addItem(String flowerId, int quantity, double unitPrice) {
        items.put(flowerId, new OrderItem(flowerId, quantity, unitPrice));
    }

    public Map<String, OrderItem> getItems() {
        return items;
    }

    public class OrderItem {

        private String flowerId;
        private int quantity;
        private double unitPrice;

        public OrderItem(String flowerId, int quantity, double unitPrice) {
            this.flowerId = flowerId;
            this.quantity = quantity;
            this.unitPrice = unitPrice;
        }

        public String getFlowerId() {
            return flowerId;
        }

        public int getQuantity() {
            return quantity;
        }

        public double getUnitPrice() {
            return unitPrice;
        }
    }

    public static String inputId() {
        //id is in the Oxxx format
        while (true) {
            try {
                System.out.print("Enter flowerId OXXX: ");
                String id = sc.nextLine();
                //Kiem tra id co dung format
                if (!id.matches("^O\\d{3}$")) {
                    throw new Exception("");
                }
                return id;
            } catch (Exception ex) {
                //System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    // In ra man hinh
    public String repeat(String s, int n) {
        StringBuilder result = new StringBuilder();

        for (int i = 0; i < n; i++) {
            result.append(s);
        }
        return result.toString();
    }

    // Chi view cho UserMenu thoi
    public void displayHeader() {
        System.out.println(repeat("#", 60));
        System.out.format("#%18s#%19s#%19s#\n", "Order ID", "Date", "Buyer ID");
        System.out.println(repeat("#", 60));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedImportDate = getOrderDate().format(formatter);
        System.out.format("#%18s#%19s#%19s#\n", getOrderId(), formattedImportDate, getCustomerId());
        System.out.println(repeat("#", 60));
    }

    public void displayRow2() {
        System.out.format("#%18s#%19s#%19s#\n", "Flower Name", "Quantity", "Price");
        System.out.println(repeat("#", 60));
        FlowerManager flowerManager = new FlowerManager();
        flowerManager.loadFlowers();
        for (OrderItem item : items.values()) {
            String formattedUnitPrice = "$" + item.getUnitPrice();
            System.out.format("#%18s#%19d#%19s#\n", flowerManager.flowerName(item.getFlowerId()), item.getQuantity(), formattedUnitPrice);
        }
        System.out.println(repeat("#", 60));
        String formattedTotalPrice = "$" + totalPrice;
        String total = "Total: " + formattedTotalPrice;
        System.out.format("#%58s#\n", total);
        System.out.println(repeat("#", 60));

    }

    //------------
    // Ham dung de add
    public void addFlowerToOrder(String flowerId, int quantity, double unitPrice) {
        OrderItem item = items.get(flowerId);
        if (item != null) {
            // Cập nhật số lượng và giá trị cho bông hoa đã tồn tại trong đơn hàng
            item.quantity += quantity;
            item.unitPrice += unitPrice;
        } else {
            // Thêm mới bông hoa vào đơn hàng
            items.put(flowerId, new OrderItem(flowerId, quantity, unitPrice));
        }
        // Cập nhật tổng giá trị của đơn hàng
        updateTotalPrice();
        System.out.println("Successfully add to cart\n");
    }

    public void updateTotalPrice() {
        totalPrice = 0.0;
        for (OrderItem item : items.values()) {
            totalPrice += item.getUnitPrice();
        }
    }

    //in ra man hinh cho dev
    public void displayHeaderDev() {
        System.out.println(repeat("#", 120));
        System.out.format("#%18s#%19s#%19s#%19s#%19s#%19s#\n", "No.", "Order Id", "Order Date", "Customer", "Flower Count", "Order Total");
        System.out.println(repeat("#", 120));
    }

    public void displayDev(int no, int countFlower) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        String formattedOrderDate = getOrderDate().format(formatter);
        CustomerManager customerManager = new CustomerManager();
        customerManager.loadCustomers();
        String nameCustomer = customerManager.customerName(customerId);
        String formattedTotalPrice = "$" + totalPrice;
        System.out.format("#%18d#%19s#%19s#%19s#%19s#%19s#\n", no, getOrderId(), formattedOrderDate, nameCustomer, countFlower, formattedTotalPrice);
    }

    public void displayFooterDev(double total) {
        System.out.println(repeat("#", 120));
        String formattedTotal = "Total: $" + total;
        System.out.format("#%118s#\n", formattedTotal);
        System.out.println(repeat("#", 120));
    }

}
