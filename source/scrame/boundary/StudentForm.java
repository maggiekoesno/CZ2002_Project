package scrame.boundary;

import java.util.Scanner;

import scrame.boundary.Form;

/**
 * A boundary class that helps to restrict the action performed by the user based on student role.
 */
public final class StudentForm implements Form {

  /**
   * A delimiter integer that helps in the implementation of other functions.
   */
  private int choice = -1;

  /**
   * Prints the options allowed for the admin role.
   */
  public void showInformation() {
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
   * Display and get the choice from user.
   * 
   * @return  integer of the selected choide of action
   */
  public int display() {
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
   * Validates whether a requested action is permissible.
   * 
   * @param choice  the integer of choice chosen by user
   * @return        boolean whether the action is allowable or not
   */
  public boolean validateChoice(int choice) {
    return (choice >= 0 && choice <= 3) ? true : false;
  }
}

