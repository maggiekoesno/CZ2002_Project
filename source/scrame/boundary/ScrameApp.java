package scrame.boundary;

import java.util.Scanner;

import java.util.Map;
import java.util.HashMap;

import scrame.boundary.AdminForm;
import scrame.boundary.StudentForm;

import scrame.manager.CourseManager;
import scrame.manager.RecordManager;
import scrame.manager.StudentManager;

import scrame.helper.CourseType;

public class ScrameApp {

  public static void main(String[] args) {
    StudentManager.loadFromFile();
    CourseManager.loadFromFile();
    RecordManager.loadFromFile();

    Scanner sc = new Scanner(System.in);
    int choice;
    String username;
    boolean flagWhile = true;

    choice = 1; // admin

    // do {
    //   System.out.println("Welcome to the SCRAME application!");
    //   System.out.println("1. Login as admin");
    //   System.out.println("2. Login as student");
    //   System.out.print("Enter choice: ");
    //   choice = sc.nextInt();
    // } while (choice != 1 && choice != 2);

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
      }

      switch (choice) {
        case 0:
          System.out.println("Exiting SCRAME application... Goodbye!");
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
          // StudentManager.addStudent();
          StudentManager.addStudent("Maggie", "CSC", "AY1718 S1", "U1720120H");
          StudentManager.addStudent("Kevin", "CSC", "AY1718 S1", "U1720121H");
          StudentManager.addStudent("Jason", "CSC", "AY1718 S1", "U1720122H");
          StudentManager.addStudent("Elbert", "CSC", "AY1718 S1", "U1720123H");
          break;
        case 5:
          //CourseManager.addCourse();

          HashMap<String, Integer> tempLecVacancy = new HashMap<String, Integer>();
          tempLecVacancy.put("_LEC", 200);

          HashMap<String, Integer> tempVacancies = new HashMap<String, Integer>();
          tempVacancies.put("SSP1", 60);
          tempVacancies.put("BCG2", 40);
          tempVacancies.put("_LEC", 100);

          HashMap<String, String[]> tempWeightageList = new HashMap<String, String[]>();
          tempWeightageList.put("Exam", new String[]{"60%", "false", ""});
          tempWeightageList.put("Coursework", new String[]{"40%", "true", ""});
          tempWeightageList.put("Assessment", new String[]{"70%", "false", "Coursework"});
          tempWeightageList.put("Attendance", new String[]{"30%", "false", "Coursework"});

          CourseManager.addCourse("CZ2001", CourseType.LEC, tempLecVacancy, tempWeightageList);
          CourseManager.addCourse("CZ2002", CourseType.TUT, tempVacancies, tempWeightageList);
          CourseManager.addCourse("CZ2003", CourseType.LAB, tempVacancies, tempWeightageList);
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

