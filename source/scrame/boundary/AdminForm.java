package scrame.boundary;

import java.util.Scanner;

public final class AdminForm {
  private static int choice = -1;

  public static void showInformation() {
    System.out.println(
      "\n1. Register student for a course (this includes registering for Tutorial/Lab classes).\n" 
      + "2. Check available slots in a class (Class Vacancy).\n" 
      + "3. Print student transcript.\n" 
      + "4. Add a student.\n" 
      + "5. Add a course.\n" 
      + "6. Print student list by lecture, tutorial or laboratory session for a course.\n" 
      + "7. Enter coursework mark – inclusive of its components.\n" 
      + "8. Enter exam mark.\n" 
      + "9. Print course statistics.\n"
    );

    System.out.println("Valid choices for the app's functionalities are from 1 to 9.");
    System.out.println("Enter 0 to exit the application.");
    System.out.println();
  }

  public static int display() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Select the app's functionality: ");
    choice = sc.nextInt();

    while (!validateChoice(choice)) {
      System.out.println("Invalid choice. Try again!");
      System.out.print("Select the app's functionality: ");
      choice = sc.nextInt();
    }

    return choice;
  }

  private static boolean validateChoice(int choice) {
    return (choice >= 0 && choice <= 19) ? true : false;
  }
}

