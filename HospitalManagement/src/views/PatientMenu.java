/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.CheckInput;

/**
 *
 * @author laptop
 */
public class PatientMenu {
    public static void display() {
        System.out.println("\nPatient’s management:");
        System.out.println("1-Add a patient");
        System.out.println("2-List patients");
        System.out.println("3-Sort the patients list");
        System.out.println("4-Save data");
        System.out.println("5-Load data");
        System.out.println("6-Back to Main Menu");

    }

    public static void ChoiceMenu() {
        int op;
        do {
            display();
            op = CheckInput.inputInt("Enter your choice: ", "Invalid input. Please enter a number.");
            switch (op) {
                case 1:
                    
                    break;
                case 2:
                    
                    break;
                case 3:

                    break;
                case 4:
                   
                    break;
                case 5:
                    
                    break;
                default:

                    System.exit(0);
            }

        } while (op > 0 && op < 7);
            
    }
}
