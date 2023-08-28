/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Iterator;
import java.util.Map;
import java.util.Scanner;
import models.Nurse;

/**
 *
 * @author laptop
 */
public class CheckInput {

    private static Scanner sc = new Scanner(System.in);

    public static int inputInt(String title, String error) {
        int numInt;
        while (true) {
            try {
                System.out.print(title + ": ");
                numInt = Integer.parseInt(sc.nextLine());
                break;
            } catch (NumberFormatException ex) {
                System.out.println(error);
            }
        }
        return numInt;
    }

    public static double inputDouble(String title, String error) {
        double numD;
        while (true) {
            try {
                System.out.print(title + ": ");
                numD = Double.parseDouble(sc.nextLine());
                break;
            } catch (NumberFormatException ex) {
                System.out.println(error);
            }
        }
        return numD;
    }

    public static String inputStr(String title, String error) {
        String input;
        while (true) {
            System.out.print(title + ": ");
            input = sc.nextLine();
            if (!input.isEmpty()) {
                break;
            }
            System.out.println(error);
        }
        return input;
    }

    public static String inputStaffID(Map<String, Nurse> ls, String title, String error) {
        String staffID;
        boolean isUnique;

        do {
            staffID = inputStr(title, error);
            isUnique = !ls.containsKey(staffID);

            if (!isUnique) {
                System.out.println("This staff ID already exists. Please enter a unique staff ID.");
            }
        } while (!isUnique);

        return staffID;
    }

    public static String inputPhone(String title, String error) {
        String phone;
        while (true) {
            try {
                System.out.print(title + ": ");
                phone = sc.nextLine();
                //Kiem tra tinh hop le cua du lieu = Data invalidation
                if (phone.length() != 10 && phone.length() != 11) {
                    throw new Exception(error);
                }
                return phone;
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    public static String inputDepartment(String title, String error) {
        String department;
        while (true) {
            try {
                System.out.print(title + ": ");
                department = sc.nextLine();
                if (department.length() < 3 || department.length() > 50) {
                    throw new Exception(error);
                }
                return department;
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    public static int inputAge(String title, String error) {
        int age;
        while (true) {
            try {
                age = inputInt(title, "Invalid input. Please enter a number.");
                if (age < 0) {
                    throw new Exception(error);
                }
                return age;
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    public static double inputSalary(String title, String error) {
        double salary;
        while (true) {
            try {
                salary = inputDouble(title, "Invalid input. Please enter a number.");
                if (salary < 0) {
                    throw new Exception(error);
                }
                return salary;
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    public static boolean InputOpTF(String title) {
        String op;

        while (true) {
            try {
                System.out.print(title + ": ");
                op = sc.nextLine();
                op = op.toLowerCase();
                String validChars = "10yntf";
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

    public static LocalDate inputDate(String title, String error) {
        LocalDate date = null;
        boolean validDate = false;

        while (!validDate) {
            try {
                String dateStr = inputStr(title, error);
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                date = LocalDate.parse(dateStr, formatter);
                validDate = true;
            } catch (DateTimeParseException e) {
                System.out.println(error);
            }
        }

        return date;
    }

    public static LocalDate inputAfterDate(LocalDate dateBefore, String title, String error) {
        LocalDate dateAfter = null;

        while (true) {
            dateAfter = inputDate(title, error);

            if (dateAfter.isAfter(dateBefore)) {
                // thoat ra neu nhu nam sau lớn hơn nam trước.
                break;
            } else {
                System.out.println("The later date must be greater than the earlier date");
            }
        }

        return dateAfter;
    }

       public static String inputSortBy() {
        while (true) {
            try {
                String term = inputStr("Sorted by", "Empty value").toLowerCase();
                if (term.equals("patient id") || term.equals("patient's name") || term.equals("price")) {
                    return term;
                } else {
                    throw new Exception("Sort By (patient id, patient’s name )");
                }
            } catch (Exception ex) {
                System.out.println("Error: " + ex.getMessage());
            }
        }
    }

    public static String inputType() {
        while (true) {
            try {
                String type = inputStr("Sort order", "Empty value").toLowerCase();
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
    
    
}
