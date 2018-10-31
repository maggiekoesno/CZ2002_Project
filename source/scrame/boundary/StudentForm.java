package scrame.boundary;

import java.util.Scanner;

public final class StudentForm {
  private static int choice = -1;
  private static Scanner sc = new Scanner(System.in);

  public static int display() {
    System.out.println(
      "\n1. Register for a course (this includes registering for Tutorial/Lab classes).\n" 
      + "2. Check available slots in a class (Class Vacancy).\n" 
      + "3. Print student transcript.\n"
      + "Enter 0 to exit SCRAME\n"
    );

    System.out.print("Enter your choice: ");
    choice = sc.nextInt();
    while (choice < 0 || choice > 3) {
      System.out.println("Invalid Choice. Try again");
      choice = sc.nextInt();
    }
    return choice;
  }

}

