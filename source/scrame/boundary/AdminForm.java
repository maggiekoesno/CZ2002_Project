package scrame.boundary;

import java.util.Scanner;

public final class AdminForm {
  public static int display() {
    Scanner sc = new Scanner(System.in);
    int choice = -1;

    System.out.println(
      "\n1. Register student for a course (this includes registering for Tutorial/Lab classes).\n" 
      + "2. Check available slots in a class (Class Vacancy).\n" 
      + "3. Print student transcript.\n" 
      + "4. Add a student.\n" 
      + "5. Add a course.\n" 
      + "6. Print student list by lecture, tutorial or laboratory session for a course.\n" 
      + "7. Enter course assessment components weightage.\n" 
      + "8. Enter coursework mark – inclusive of its components.\n" 
      + "9. Enter exam mark.\n" 
      + "10. Print course statistics.\n"
      + "Enter 0 to exit SCRAME\n"
    );

    System.out.print("Enter your choice: ");
    choice = sc.nextInt();

    while (choice < 0 || choice > 10) {
      System.out.println("Invalid choice. Try again!");
      choice = sc.nextInt();
    }

    return choice;
  }
}

