/*
Dinh Quoc Tuan Dat - SE171685
 */
package controllers;

/**
 *
 * @author laptop
 */
import models.Order;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import models.Config;
import models.Flower;
import models.Order.OrderItem;

public class OrderManager {

    private final List<Order> orders;
    private final Scanner sc = new Scanner(System.in);
    Config c = new Config();
    private final String ordersFilePath;

    public OrderManager() {
        c.loadConfigData();
        ordersFilePath = c.getValue("order file path");
        this.orders = new ArrayList<>();
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void loadOrders() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ordersFilePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 5) {
                    String orderId = parts[0].trim();
                    LocalDate orderDate = LocalDate.parse(parts[1].trim(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    double totalPrice = Double.parseDouble(parts[2].trim());
                    String customerId = parts[3].trim();
                    Order order = new Order(orderId, orderDate, totalPrice, customerId);
                    for (int i = 4; i < parts.length; i++) {
                        String[] itemInfo = parts[i].split(":");
                        if (itemInfo.length == 3) {
                            String flowerId = itemInfo[0].trim();
                            int quantity = Integer.parseInt(itemInfo[1].trim());
                            double unitPrice = Double.parseDouble(itemInfo[2].trim());
                            order.addItem(flowerId, quantity, unitPrice);
                        }
                    }
                    orders.add(order);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading orders: " + e.getMessage());
        }
    }

    public void saveOrdersToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(ordersFilePath))) {
            for (Order order : orders) {
                writer.write(order.getOrderId() + ",");
                writer.write(order.getOrderDate().toString() + ",");
                writer.write(Double.toString(order.getTotalPrice()) + ",");
                writer.write(order.getCustomerId());

                for (Order.OrderItem item : order.getItems().values()) {
                    writer.write("," + item.getFlowerId() + ":");
                    writer.write(Integer.toString(item.getQuantity()) + ":");
                    writer.write(Double.toString(item.getUnitPrice()));
                }

                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving orders to file: " + e.getMessage());
        }
    }

    public void ViewOrder(String customerId) {
        Order latesOrder = null;
        for (Order order : orders) {
            // no duyet tu dau den cuoi nen no se in ra customerid cuoi cung
            if (order.getCustomerId().equals(customerId)) {
                latesOrder = order;
            }
        }

        if (latesOrder != null) {
            latesOrder.displayHeader();
            latesOrder.displayRow2();

        } else {
            System.out.println("No order found for Customer " + customerId);
        }

    }

    // Add Flower List
    private String generateOrderId() {
        int maxId = 0;
        for (Order order : orders) {
            int id = Integer.parseInt(order.getOrderId().substring(1));
            if (id > maxId) {
                maxId = id;
            }
        }
        return "O" + String.format("%03d", maxId + 1);
    }

    // ham nay no se tra ve id order
    public String addOrder(String customerId) {
        String orderId = generateOrderId();
        LocalDate orderDate = LocalDate.now();
        double totalPrice = 0.0;
        Order order = new Order(orderId, orderDate, totalPrice, customerId);
        orders.add(order);
        return orderId;
    }

    public Order getOrder(String idOrder) {
        for (Order order : orders) {
            if (order.getOrderId().equals(idOrder)) {
                return order;
            }
        }
        return null;
    }

    public void addFlowerToCart(String orderId, String customerId) {
        Order order = this.getOrder(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }

        // Nhap thong tin bong hoa
        String flowerID = Flower.inputId();
        int quantity = Flower.inputQuantities();

        FlowerManager flowerManager = new FlowerManager();
        flowerManager.loadFlowers();
        Flower f = flowerManager.getFlower(flowerID);
        if (f == null) {
            System.out.println("Flower not found.");
            return;
        }

        double unitPrice = f.getUnitPrice() * quantity;

        order.addFlowerToOrder(flowerID, quantity, unitPrice);

    }

    //input 
    // input ngay thang
    public LocalDate inputDateFormTo(boolean op) {
        while (true) {
            try {
                if (op) {
                    System.out.print("Enter fromDate(You can ignore by just enter): ");
                } else {
                    System.out.print("Enter toDate(You can ignore by just enter): ");
                }

                String input = sc.nextLine();
                if (input.trim().isEmpty()) {
                    LocalDate minDate = null;
                    LocalDate maxDate = null;
                    for (Order order : orders) {
                        LocalDate orderDate = order.getOrderDate();
                        if (minDate == null || orderDate.isBefore(minDate)) {
                            minDate = orderDate;
                        }
                        if (maxDate == null || orderDate.isAfter(maxDate)) {
                            maxDate = orderDate;
                        }
                    }
                    if (op) {
                        return minDate;
                    } else {
                        return maxDate;
                    }

                }

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");
                LocalDate date = LocalDate.parse(input, formatter);

                // Kiểm tra nếu chuỗi đã nhập không trùng khớp với định dạng
                // thì hiển thị thông báo lỗi và tiếp tục vòng lặp
                if (!input.equals(date.format(formatter))) {
                    throw new DateTimeParseException("Invalid date format", input, 0);
                }
                return date;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid input. Please enter a date in the format yyyy/MM/dd.");
            }
        }
    }

    public String inputSortTerm() {
        while (true) {
            try {
                System.out.print("Enter sort term (count, date, or price): ");
                String term = sc.nextLine().toLowerCase();
                if (term.equals("count") || term.equals("date") || term.equals("price")) {
                    return term;
                } else {
                    throw new Exception("Sort Term (count, date, or price)");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    public String inputType() {
        while (true) {
            try {
                System.out.print("Enter type(asc, desc): ");
                String type = sc.nextLine().toLowerCase();
                if (type.equals("asc") || type.equals("desc")) {
                    return type;
                } else {
                    throw new Exception("Type (asc, desc)");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }

        }
    }

    private int calculateTotalFlowerCount(Order order) {
        int totalCount = 0;
        for (OrderItem orderItem : order.getOrderItems()) {
            totalCount += orderItem.getQuantity();
        }
        return totalCount;
    }

    //Sort
    private List<Order> sortOrders(String sortTerm, String type, LocalDate fromDate, LocalDate toDate) {
        List<Order> sortedOrders = new ArrayList<>(orders);

        if (sortTerm.equals("date")) {
            sortedOrders.sort((o1, o2) -> o1.getOrderDate().compareTo(o2.getOrderDate()));
        } else if (sortTerm.equals("price")) {
            sortedOrders.sort((o1, o2) -> Double.compare(o1.getTotalPrice(), o2.getTotalPrice()));
        } else if (sortTerm.equals("count")) {
            sortedOrders.sort((o1, o2) -> {
                int count1 = calculateTotalFlowerCount(o1);
                int count2 = calculateTotalFlowerCount(o2);
                return Integer.compare(count1, count2);
            });
        }

        if (type.equals("desc")) {
            Collections.reverse(sortedOrders);
        }

        // Gioi han formDate va toDate
        if (fromDate != null && toDate != null) {
            List<Order> filteredOrders = new ArrayList<>();
            for (Order order : sortedOrders) {
                LocalDate orderDate = order.getOrderDate();
                if ((orderDate.isAfter(fromDate) || orderDate.isEqual(fromDate))
                        && (orderDate.isBefore(toDate) || orderDate.isEqual(toDate))) {
                    filteredOrders.add(order);
                }
            }
            sortedOrders = filteredOrders;
        }
        return sortedOrders;
    }

    // view order for dev
    public void viewSortedOrder() {
        Order order = new Order();
        String sortTerm = inputSortTerm();
        String type = inputType();
        LocalDate fromDate = inputDateFormTo(true);
        LocalDate toDate = inputDateFormTo(false);

        // kiem tra neu nhu fromDate ma lon hon toDate la loi
        if (fromDate.compareTo(toDate) > 0) {
            System.out.println("Error: fromDate cannot be greater than toDate.");
            return;
        }

        List<Order> sortedOrders = sortOrders(sortTerm, type, fromDate, toDate);
        order.displayHeaderDev();
        int no = 0;
        double total = 0.0;
        for (Order sortedOrder : sortedOrders) {
            total += sortedOrder.getTotalPrice();
            int count = calculateTotalFlowerCount(sortedOrder);
            sortedOrder.displayDev(no, count);
            no++;
        }
        order.displayFooterDev(total);
    }

    // remove Order
    public void cancelOrder(String id) {
        Order order = this.getOrder(id);
        if (order == null) {
            System.out.println("Order is not found.");
        } else {
            orders.remove(order);
            System.out.println("Successfully cancel the order");
        }
    }

    public void removeOrder() {
        String id = Order.inputId();
        Order order = this.getOrder(id);
        if (order == null) {
            System.out.println("Order is not found.");
        } else {
            orders.remove(order);
            System.out.println("Successfully remove the order");
        }
    }

}
