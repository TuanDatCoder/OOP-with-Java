
package studentmanagement;

import java.util.Scanner;

/**
 *
 * @author laptop
 */
public class Menu {
    public static void display(){
        System.out.println("----------------M E N U----------------");
        System.out.println(" 1. Add students");
        System.out.println(" 2. Search students by id");
        System.out.println(" 3. Update students");
        System.out.println(" 4. Delete students");
        System.out.println(" 5. List students by id ascending");
        System.out.println(" 6. List students by id descending");
        System.out.println(" 7. List students by name ascending");
        System.out.println(" 8. List students by name descending");
        System.out.println(" 9. List students by point ascending");
        System.out.println("10. List students by point descending");
        System.out.println("11. Save students");
        System.out.println("12. Calculate average points");
        System.out.println(" _. Other to exit");
        System.out.println("---------------------------------------");
    }
    
    public static String choose(){
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter your choice: ");
        return sc.nextLine();
    }
}
