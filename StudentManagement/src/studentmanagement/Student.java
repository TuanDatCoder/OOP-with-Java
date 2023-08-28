
package studentmanagement;

import java.io.Serializable;
import java.util.Scanner;

/**
 *
 * @author laptop
 */
//implements Serializable: de co the luu cac doi tuong
//cua lop Student vao file
public class Student implements Serializable {

    private String id;
    private String name;
    private double point;

    private static Scanner sc = new Scanner(System.in);

    public Student() {
    }

    public Student(String id, String name, double point) {
        this.id = id;
        this.name = name;
        this.point = point;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPoint() {
        return point;
    }

    public void setPoint(double point) {
        this.point = point;
    }

    public static String inputId() {
        //id is in the Sxxx format
        while (true) {
            try {
                System.out.print("Id (Sxxx): ");
                String id = sc.nextLine();
                //Kiem tra id co dung format
                //Doc them: Regular Expression
                if (!id.matches("^S\\d{3}$")) {
                    throw new Exception("Id is incorrect");
                }
                return id;
            } catch (Exception ex) {
                System.out.println("Error: "+ex.getMessage());
            }
        }
    }
    
    public static String inputName(){
        //name's length is between 5 and 20
        while (true) {
            try {
                System.out.print("Name: ");
                String name = sc.nextLine();
                //Kiem tra tinh hop le cua du lieu = Data invalidation
                if (name.length() < 5 || name.length() > 20) {
                    throw new Exception("Invalid name");
                }
                return name;
            } catch (Exception ex) {
                System.out.println("Error: "+ex.getMessage());
            }
        }
    }
    
    public void update(){
        this.name = inputName();
        this.point = inputPoint();
    }
    
    
    public static double inputPoint(){
        //point is in [0,10]
        while (true) {
            try {
                System.out.print("Point: ");
                double point = Double.parseDouble(sc.nextLine());
                //Kiem tra tinh hop le cua du lieu = Data invalidation
                if (point < 0 || point > 10) {
                    throw new Exception("Invalid point");
                }
                return point;
            } catch (Exception ex) {
                System.out.println("Error: "+ex.getMessage());
            }
        }
    }

    public void input() {
        //Nhap thong tin cua mot student
        this.id = inputId();
        this.name = inputName();
        this.point = inputPoint();
    }

    public void output() {
        System.out.println("Id: " + id);
        System.out.println("Name: " + name);
        System.out.println("Point: " + point);
    }
}
