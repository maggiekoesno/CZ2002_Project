package scrame.boundary;

import java.util.Scanner;

/**
 * A boundary class that helps to restrict the action performed by the user based on student role
 */
public final class StudentForm {
  /**
   * A delimiter integer that helps in the implementation of other functions
   */
  private static int choice = -1;

  /**
   * A function that prints the options allowed for the admin role
   */
  public static void showInformation() {
    System.out.println(
      "\n1. Register for a course (this includes registering for Tutorial/Lab classes).\n" 
      + "2. Check available slots in a class (Class Vacancy).\n" 
      + "3. Print student transcript.\n"
    );

    System.out.println("Valid choices for the app's functionalities are from 1 to 3.");
    System.out.println("Enter 0 to exit the application.");
    System.out.println();
  }

  /**
   * Display and get the choice from user
   * @return  integer of the selected choide of action
   */
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

  /**
   * function that helps to validate whether a requested action is permissible
   * @param choice  the integer of choice chosen by user
   * @return        boolean whether the action is allowable or not
   */
  private static boolean validateChoice(int choice) {
    return (choice >= 0 && choice <= 3) ? true : false;
  }
}

