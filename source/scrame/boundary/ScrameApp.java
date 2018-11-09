package scrame.boundary;

import java.util.Scanner;

import java.util.Map;
import java.util.HashMap;

import scrame.boundary.AdminForm;
import scrame.boundary.StudentForm;

import scrame.entity.Course;

import scrame.exception.IllegalCourseTypeException;

import scrame.manager.CourseManager;
import scrame.manager.RecordManager;
import scrame.manager.StudentManager;

import scrame.helper.CourseType;

public class ScrameApp {

  public static void main(String[] args) {
    StudentManager.loadFromFile();
    CourseManager.loadFromFile();
    RecordManager.loadFromFile();

    int userChoice;
    int functionChoice;
    boolean flagWhile = true;
    String matric;
    String courseName;
    Scanner sc = new Scanner(System.in);
    
    userChoice = 1; // admin

    // do {
    //   System.out.println("Welcome to the SCRAME application!");
    //   System.out.println("1. Login as admin");
    //   System.out.println("2. Login as student");
    //   System.out.print("Enter choice: ");
    //   choice = sc.nextInt();
    // } while (choice != 1 && choice != 2);
    // StudentManager.addStudent("Maggie", "CSC", "AY1718 S1", "U1720120H");
    // StudentManager.addStudent("Kevin", "CSC", "AY1718 S1", "U1720121H");
    // StudentManager.addStudent("Jason", "CSC", "AY1718 S1", "U1720122H");
    // StudentManager.addStudent("Elbert", "CSC", "AY1718 S1", "U1720123H");
    // HashMap<String, Integer> tempLecVacancy = new HashMap<String, Integer>();
    // tempLecVacancy.put("_LEC", 200);

    // HashMap<String, Integer> tempVacancies = new HashMap<String, Integer>();
    // tempVacancies.put("SSP1", 60);
    // tempVacancies.put("BCG2", 40);
    // tempVacancies.put("_LEC", 100);

    // HashMap<String, String[]> tempWeightageList = new HashMap<String, String[]>();
    // tempWeightageList.put("Exam", new String[] { "60%", "false", "" });
    // tempWeightageList.put("Coursework", new String[] { "40%", "true", "" });
    // tempWeightageList.put("Assessment", new String[] { "70%", "false", "Coursework" });
    // tempWeightageList.put("Attendance", new String[] { "30%", "false", "Coursework" });

    // CourseManager.addCourse("CZ2001", CourseType.LEC, tempLecVacancy, tempWeightageList);
    // CourseManager.addCourse("CZ2002", CourseType.TUT, tempVacancies, tempWeightageList);
    // CourseManager.addCourse("CZ2003", CourseType.LAB, tempVacancies, tempWeightageList);
    // RecordManager.registerStudentCourse("U1720122H", "CZ2001");
    // RecordManager.registerStudentCourse("U1720121H", "CZ2002", "BCG2");
    // RecordManager.registerStudentCourse("U1720123H", "CZ2003", "SSP1");
    if (userChoice == 2) {
      System.out.print("Enter matriculation number: ");
      matric = sc.nextLine();

      if (!StudentManager.isStudentInList(matric)) {
        System.out.println("Oh no! This matriculation number is not registered yet :(");
      }
    }

    while (flagWhile) {
      if (userChoice == 2)
        functionChoice = StudentForm.display();
      else 
        functionChoice = AdminForm.display();

      switch (functionChoice) {
        case 0:
          System.out.println("Exiting SCRAME application... Goodbye!");
          flagWhile = false;
          break;

        case 1:
          System.out.print("Please input matric number: ");
          matric = sc.nextLine();
          System.out.print("Please input course name: ");
          courseName = sc.nextLine();

          Course courseFound = CourseManager.findCourse(courseName);

          if (courseFound.getCourseType() == CourseType.LEC) {
            RecordManager.registerStudentCourse(matric, courseName);
          } else {
            courseFound.printAllGroups();
            System.out.print("Which group do you want to register into? ");
            String groupName = sc.nextLine();
            RecordManager.registerStudentCourse(matric, courseName, groupName);
          }
          
          // RecordManager.registerStudentCourse("U1720120H", "CZ2001");
          // RecordManager.registerStudentCourse("U1720120H", "CZ2002", "BCG2");
          // RecordManager.registerStudentCourse("U1720121H", "CZ2002", "SSP1");
          // RecordManager.registerStudentCourse("U1720121H", "CZ2003", "BCG2");
          // RecordManager.registerStudentCourse("U1720122H", "CZ2003", "SSP1");

          break;

        case 2:
          CourseManager.checkVacancy();
          break;

        case 3:
          StudentManager.printTranscript();
          break;

        case 4:
          System.out.print("Enter new student's name: ");
          String name = sc.nextLine();
          System.out.print("Enter " + name + "'s major (e.g. CSC): ");
          String major = sc.nextLine();
          System.out.print("Enter " + name + "'s enrollment period (e.g. AY1718 S1): ");
          String enroll = sc.nextLine();
          System.out.print("Enter " + name + "'s matriculation number: ");
          matric = sc.nextLine();

          try {
            StudentManager.addStudent(name, major, enroll, matric);
          } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
          }

          // StudentManager.addStudent("Maggie", "CSC", "AY1718 S1", "U1720120H");
          // StudentManager.addStudent("Kevin", "CSC", "AY1718 S1", "U1720121H");
          // StudentManager.addStudent("Jason", "CSC", "AY1718 S1", "U1720122H");
          // StudentManager.addStudent("Elbert", "CSC", "AY1718 S1", "U1720123H");

          break;

        case 5:
          HashMap<String, Integer> tempVacancies = new HashMap<String, Integer>();
          HashMap<String, String[]> tempWeightageList = new HashMap<String, String[]>();

          System.out.print("Enter new course name: ");
          courseName = sc.nextLine();

          System.out.print("Enter " + courseName + "'s course type (e.g. LEC, TUT, LAB): ");
          String typeInput = sc.nextLine();

          CourseType courseType = CourseType.LEC;

          try {
            courseType = CourseManager.courseTypeToEnum(typeInput);
          } catch (IllegalCourseTypeException e) {
            System.out.println(e.getMessage());
          }

          System.out.print("Enter " + courseName + "'s total vacancy: ");
          int totalVacancy = sc.nextInt();

          while (totalVacancy <= 0) {
            System.out.println(
              "Vacancy cannot be less than or equal to zero. Please try again!"
            );
            totalVacancy = sc.nextInt();
          }

          tempVacancies.put("_LEC", totalVacancy);

          if (courseType != CourseType.LEC) {
            int groupVacancy;
            String groupName;

            while (true) {
              sc.nextLine();
              System.out.print("Enter group name (e.g. SSP1) or -1 to exit: ");
              groupName = sc.nextLine();
              if (groupName.equals("-1")) {
                break;
              }
              System.out.print("Enter the group vacancy: ");
              groupVacancy = sc.nextInt();
              if (groupVacancy <= 0) {
                System.out.println("Vacancy cannot be less than or equal to zero.");
                continue;
              }
              tempVacancies.put(groupName, groupVacancy);
            }
          }

          System.out.println("Enter the weightage of the exam, -1 to exit:"); // TODO: should simplify inputting process?
          System.out.println(
            "Format weightagename,percentage,true (if have child else false), \"\" (if no parent else \"nameOfParent\")"
          );
          System.out.println("Example: ");
          System.out.println(
            "Exam,60%,false,\"\" <----- false, because it has no subcoursework. and \"\" because it has no parent"
          );
          System.out.println(
            "Coursework,40%,true,\"\" <----- true, because coursework is divided into more subcategories"
          );
          System.out.println(
            "Assignment,70%,false,Coursework <-------, Coursework because its parent is Coursework"
          );
          System.out.println("Attendance,30%,false,Coursework <-------, same");

          System.out.println("Course Weightage Structure: ");
          System.out.println("             100%                    ");
          System.out.println("           /    \\                  ");
          System.out.println("      60% Exam     40% Coursework           ");
          System.out.println("                    /           \\          ");
          System.out.println("            70% Assignment       30% Attendance   ");

          String tmp;
          while (true) {
            System.out.println("Enter the weightage, -1 to exit:");
            tmp = sc.next();
            if (tmp.equals("-1")) {
              break;
            }
            String parts[] = tmp.split(",");
            if(parts[3].equals("\"\"")){
              parts[3] = "";
            }
            
            // System.out.println(parts[0]);      
            // System.out.println(parts[1] + " " + parts[2] + " " + parts[3]);
            tempWeightageList.put(parts[0], new String[]{parts[1], parts[2], parts[3]});
          }

          try {
            CourseManager.addCourse(courseName, courseType, tempVacancies, tempWeightageList);
          } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
          }

          // HashMap<String, Integer> tempVacancies = new HashMap<String, Integer>();
          // HashMap<String, Integer> tempVacanciesLec = new HashMap<String, Integer>();
          
          // tempVacanciesLec.put("_LEC", 100);
          // tempVacancies.put("_LEC", 100);
          // tempVacancies.put("SSP1", 60);
          // tempVacancies.put("BCG2", 40);

          // HashMap<String, String[]> tempWeightageList = new HashMap<String, String[]>();
          // tempWeightageList.put("Exam", new String[]{"60%", "false", ""});
          // tempWeightageList.put("Coursework", new String[]{"40%", "true", ""});
          // tempWeightageList.put("Assessment", new String[]{"70%", "false", "Coursework"});
          // tempWeightageList.put("Attendance", new String[]{"30%", "false", "Coursework"});

          // CourseManager.addCourse("CZ2001", CourseType.LEC, tempVacanciesLec, tempWeightageList);
          // CourseManager.addCourse("CZ2002", CourseType.TUT, tempVacancies, tempWeightageList);
          // CourseManager.addCourse("CZ2003", CourseType.LAB, tempVacancies, tempWeightageList);

          break;

        case 6:
          RecordManager.printStudentList();
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
        case 10: 
          RecordManager.printCourseStatistics();
          break;
      }
    }

    StudentManager.inputToFile();
    CourseManager.inputToFile();
    RecordManager.inputToFile();
  }
}
