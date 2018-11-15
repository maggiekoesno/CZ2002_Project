package scrame.boundary;

import java.util.Scanner;

import scrame.boundary.Form;
/**
 * A boundary class that helps to restrict the action performed by the user based on admin role
 */
public final class AdminForm implements Form {
  /**
   * A delimiter integer that helps in the implementation of other functions
   */
  private int choice = -1;

  /**
   * A function that prints the options allowed for the admin role
   */
  public void showInformation() {
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

  /**
   * Display and get the choice from user
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
   * function that helps to validate whether a requested action is permissible
   * @param choice  the integer of choice chosen by user
   * @return        boolean whether the action is allowable or not
   */
  public boolean validateChoice(int choice) {
    return (choice >= 0 && choice <= 19) ? true : false;
  }
}

