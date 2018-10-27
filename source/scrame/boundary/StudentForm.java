package scrame.boundary;

import java.util.Scanner;

public class StudentForm {
  private static int choice;
  private Scanner sc = new Scanner(System.in);
  
  // public static void main(String[] args){ //tester
  //   StudentForm sf = new StudentForm();
  //   int choice = 0;
  //   while(choice!= -1 ){
  //     choice = sf.display();
  //   }
  // }
  public StudentForm() {
    choice = -1;
  }

  public int display() {
    System.out.println("1. Register for a course (this includes registering for Tutorial/Lab classes).\n"
        + "2. Check available slots in a class (Class Vacancy)."
        + "3. Print student transcript."
        );

    System.out.print("Enter your choice: ");
    choice = sc.nextInt();
    while (choice < 1 || choice > 3){
        System.out.println("Invalid Choice. Try again");
        choice = sc.nextInt();
    }
    return choice;
  }
}