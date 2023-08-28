/*
Dinh Quoc Tuan Dat - SE171685
 */
package views;

import java.util.Scanner;

/**
 *
 * @author laptop
 */
public class CommonMenu {

    private static Scanner sc = new Scanner(System.in);

    public static int inputOp() {
        int op;
        while (true) {
            try {
                System.out.print("Enter your choice: ");
                String input = sc.nextLine();
                op = Integer.parseInt(input);
                break;
            } catch (NumberFormatException ex) {
                System.out.println("Invalid input. Please enter a number.");
            }
        }

        return op;
    }

    public static boolean saveMenu() {
        String op;

        while (true) {
            try {
                System.out.print("Do you want to data[1/0-Y/N-T/F]: ");
                op = sc.nextLine();
                op = op.toLowerCase();
                String validChars = "10yntf";
                //Kiem tra tinh hop le cua du lieu = Data invalidation
                if (!validChars.contains(op)) {
                    throw new Exception("1/0-Y/N-T/F");
                }
                break;
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
        if (op.equals("t") || op.equals("1") || op.equals("y")) {
            return true;
        }

        return false;

    }

}
