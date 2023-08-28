/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package views;

import controllers.CheckInput;
import controllers.Hospital;

/**
 *
 * @author laptop
 */
public class NurseMenu {
    public static void display() {
        System.out.println("\nNurse's management:");
        System.out.println("1-Create a nurse");
        System.out.println("2-Find the nurse");
        System.out.println("3-Update the nurse");
        System.out.println("4-Delete the nurse");
        System.out.println("5-Back to Main Menu");

    }

    public static void ChoiceMenu() {
        int op;
        Hospital hospital = new Hospital();
        
        do {
            display();
            op = CheckInput.inputInt("Enter your choice: ", "Invalid input. Please enter a number.");
            switch (op) {
                case 1:
                    hospital.createANurse();
                    break;
                case 2:
                    
                    break;
                case 3:

                    break;
                case 4:
                   
                    break;

                default:
                      
                    //System.exit(0);
            }

        } while (op > 0 && op < 6);
            
    }
}
