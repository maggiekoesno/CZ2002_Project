import java.util.Scanner;

public class StudentForm {
  private static int choice;
  private Scanner sc = new Scanner(System.in);
  
  public StudentForm() {
    choice = -1;
  }

  public int display() {

    System.out.println("1. Add a student\n" + "2. Add a course\n"
        + "3. Register student for a course (this include registering for Tutorial/Lab classes)\n"
        + "4. Check available slot in a class (vacancy in a class)\n"
        + "5. Print student list by lecture, tutorial or laboratory session for a course.\n"
        + "6. Enter course assessment components weightage\n"
        + "7. Enter coursework mark – inclusive of its components.\n" + "8. Enter exam mark\n"
        + "9. Print course statistics\n"
        +  "10. Print student transcript.\n"
        );

    System.out.print("Enter your choice: ");
    choice = sc.nextInt();
    while (choice < 1 && choice > 10) {
      System.out.println("Invalid Choice. Try again");
    }
    return choice;
  }
}