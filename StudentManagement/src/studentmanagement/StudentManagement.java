
package studentmanagement;

/**
 *
 * @author laptop
 */
public class StudentManagement {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        StudentList list = new StudentList();
        list.loadStudents();
        while(true){
            Menu.display();
            String choice = Menu.choose();
            switch(choice){
                case "1":
                    list.addStudent();
                    break;
                case "2":
                    list.searchStudentsById();
                    break;
                case "3":
                    list.updateStudents();
                    break;
                case "4":
                    list.deleteStudents();
                    break;
                case "5":
                    //list.listStudents();
                    list.listStudentsById(true);
                    break;
                case "6":
                    list.listStudentsById(false);
                    break;
                case "7":
                    //list.listStudents();
                    list.listStudentsByName(true);
                    break;
                case "8":
                    list.listStudentsByName(false);
                    break;   
                case "9":
                    list.listStudentsByPoint(true);
                    break;
                case "10":
                    list.listStudentsByPoint(false);
                    break; 
                case "11":
                    list.saveStudents();
                    break;
                case "12":
                        list.calculateAvaragePoints();
                    break;   
                default:
                    //ket thuc chuong trinh
                    //C1:
                    //return; //chi dung duoc khi trong ham main()
                    //C2:
                    System.exit(0); //dung duoc trong moi tinh huong
            }
        }
    }
    
}
