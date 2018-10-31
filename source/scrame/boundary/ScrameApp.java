package scrame.boundary;

import java.util.Scanner;

import scrame.boundary.AdminForm;
import scrame.boundary.StudentForm;

import scrame.manager.CourseManager;
import scrame.manager.RecordManager;
import scrame.manager.StudentManager;

public class ScrameApp {

  public static void main(String[] args) {
    StudentManager.loadFromFile();
    CourseManager.loadFromFile();
    RecordManager.loadFromFile();

    Scanner sc = new Scanner(System.in);
    int choice;
    String username;
    boolean flagWhile = true;

    // choice = 1; // admin

    do {
      System.out.println("Welcome to the SCRAME application!");
      System.out.println("1. Login as admin");
      System.out.println("2. Login as student");
      System.out.print("Enter choice: ");
      choice = sc.nextInt();
    } while (choice != 1 && choice != 2);

    while (flagWhile) {
      if (choice == 2) {
        System.out.print("Enter username: ");
        username = sc.nextLine().toLowerCase();

        if (!StudentManager.isStudentInList(username)) {
          System.out.println(
            "Oops, student is not in the list, please try again."                                     
          );
          flagWhile = false;
        } else {
          choice = StudentForm.display();
        }
      } else {
        choice = AdminForm.display();
        //choice = 1;
      }

      switch (choice) {
        case 0:
          System.out.println("Exiting Scrame... \n Goodbye!");
          flagWhile = false;
          break;
        case 1:
          RecordManager.registerStudentCourse();
          break;
        case 2:
          CourseManager.checkVacancy();
          break;
        case 3:
          StudentManager.printTranscript();
          break;
        case 4:
          StudentManager.addStudent();
          break;
        case 5:
          CourseManager.addCourse();
          break;
        case 6:
          StudentManager.printStudentList();
          break;
        case 7:
          CourseManager.setCourseWeightage();
          break;
        case 8:
          RecordManager.setCourseworkMark();
          break;
        case 9:
          RecordManager.setExamMark();
          break;
        case 10: //TODO Elbert
          break;
      }
    }
    StudentManager.inputToFile();
    CourseManager.inputToFile();
    RecordManager.inputToFile();

  }

}

