package scrame.boundary;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Collections;

import scrame.boundary.AdminForm;
import scrame.boundary.StudentForm;

import scrame.entity.Course;
import scrame.entity.Record;
import scrame.entity.Student;
import scrame.entity.FacultyMember;

import scrame.exception.*;

import scrame.manager.CourseManager;
import scrame.manager.RecordManager;
import scrame.manager.StudentManager;

import scrame.helper.CourseType;
import scrame.helper.SortByStudentName;
import scrame.helper.SortByCourseName;

import scrame.boundary.Form;

/**
 * ScrameApp is the main boundary app that interacts with other manager classes.
 */
public class ScrameApp {

  /**
   * Delimiter variable to be used in other functions.
   */
  private static final int HAS_CHILD = 1;

  /**
   * Prints the list of registered students.
   */
  private static void printRegisteredStudents() {
    System.out.println();
    System.out.println("+-------------------------------------------------+");
    System.out.println("|               Registered Students               |");
    System.out.println("+----------------------------+--------------------+");
    System.out.println("|        Student Name        |   Matric. Number   |");
    System.out.println("+----------------------------+--------------------+");

    ArrayList<Student> tempStudentList = new ArrayList<Student>(StudentManager.getStudentList());
    Collections.sort(tempStudentList, new SortByStudentName());

    for (Student s : tempStudentList) {
      System.out.print("|  ");
      System.out.printf("%-24s", s.getName());
      System.out.print("  |     ");
      System.out.println(s.getMatric() + "      |");
    }

    System.out.println("+----------------------------+--------------------+");
  }

  /**
   * Prints the list of registered courses and its coordinators.
   */
  private static void printRegisteredCourses() {
    System.out.println();
    System.out.println("+--------------------------------------------+");
    System.out.println("|             Registered Courses             |");
    System.out.println("+-----------------+--------------------------+");
    System.out.println("|   Course Name   |    Course Coordinator    |");
    System.out.println("+-----------------+--------------------------+");

    ArrayList<Course> tempCourseList = new ArrayList<Course>(CourseManager.getCourseList());
    Collections.sort(tempCourseList, new SortByCourseName());

    for (Course c : tempCourseList) {
      System.out.print("|     " + c.getCourseName() + "      |   ");
      System.out.printf("%-20s", c.getCoordinator().getName());
      System.out.println("   |");
    }

    System.out.println("+-----------------+--------------------------+");
  }

  /**
   * Main function that are run in the console.
   * 
   * @param args the default parameter for main function, not used in this case
   */
  public static void main(String[] args) {
    StudentManager.loadFromFile();
    CourseManager.loadFromFile();
    RecordManager.loadFromFile();

    int userChoice;
    int functionChoice;
    boolean firstLoop = true;
    boolean flagWhile = true;

    String matric;
    String courseName;
    Student studentFound;
    Course courseFound;
    HashMap<String, Integer> tempVacancies;
    HashMap<String, String[]> tempWeightageList;
    HashSet<Record> recordList;
    HashMap<String, Float> mark;
    HashMap<String, String[]> weightage;
    float ans;
    String yesOrNo;
    String tmp;
    boolean check;
    
    Form form;
    Scanner sc = new Scanner(System.in);

    System.out.println();
    System.out.println("Welcome to the SCRAME application!");
    System.out.println("1. Login as admin");
    System.out.println("2. Login as student");
    System.out.print("Enter choice: ");
    userChoice = sc.nextInt();
    sc.nextLine();

    while (userChoice != 1 && userChoice != 2) {
      System.out.println("Invalid choice, please try again!");
      System.out.print("Enter choice: ");
      userChoice = sc.nextInt();
      sc.nextLine();
    }

    if (userChoice == 2) {
      System.out.print("Enter matric number: ");
      matric = sc.next();

      while (!StudentManager.isStudentInList(matric)) {
        System.out.println("Oh no! This matric number is not registered yet :(");
        System.out.print("Enter matric number: ");
        matric = sc.next();
      }

      try {
        System.out.println("Welcome, " + StudentManager.findStudent(matric).getName() + "!");
      } catch (StudentNotFoundException e) {
        System.out.println(e.getMessage());
      }

      form = new StudentForm();
    }
    else {
      form = new AdminForm();
    }

    while (flagWhile) {
      if (firstLoop) {
        form.showInformation();
        firstLoop = false;  
      }
      functionChoice = form.display();

      switch (functionChoice) {
        case 0:
          System.out.println("Exiting SCRAME application... Goodbye!");
          flagWhile = false;
          break;

        case 1:
          try {
            System.out.print("Please enter matric number: ");
            matric = sc.next();

            sc.nextLine();

            StudentManager.validateMatric(matric);
            StudentManager.findStudent(matric);

            System.out.print("Please enter course name: ");
            courseName = sc.next();
            sc.nextLine();

            courseFound = CourseManager.findCourse(courseName);

            RecordManager.validateRegisterStudentCourse(matric, courseName);

            if (courseFound.getCourseType() == CourseType.LEC) {
              RecordManager.registerStudentCourse(matric, courseName);
            } else {
              courseFound.printAllGroups();
              System.out.print("Which group do you want to register into? ");
              String groupName = sc.next();
              RecordManager.registerStudentCourse(matric, courseName, groupName);
            }
          } catch (IllegalStudentArgumentException e) {
            System.out.println(e.getMessage());
          } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
          } catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());
          } catch (DuplicateRecordException e) {
            System.out.println(e.getMessage());
          } catch (IllegalCourseTypeException e) {
            System.out.println(e.getMessage());
          }

          break;

        case 2:
          System.out.print("Please enter course name: ");
          courseName = sc.next();

          try {
            courseFound = CourseManager.findCourse(courseName);

            System.out.println();
            System.out.println("+-----------------------------------------------+");
            System.out.println("|                Course Vacancies               |");
            System.out.println("+----------------+-------------+----------------+");

            switch (courseFound.getCourseType()) {
              case LEC:
                System.out.println("|   Course Name  |   Vacancy   |   Total Size   |");
                System.out.println("+----------------+-------------+----------------+");
                int lectureVacancy = courseFound.getLectureVacancy();
                int lectureTotalSize = courseFound.getLectureTotalSize();
                System.out.print("|     " + courseName + "     |     ");
                System.out.printf("%3d", lectureVacancy);
                System.out.print("     |       ");
                System.out.printf("%3d", lectureTotalSize);
                System.out.println("      |");
                break;
              case TUT:
              case LAB:
                System.out.println("|   Group Name   |   Vacancy   |   Total Size   |");
                System.out.println("+----------------+-------------+----------------+");

                ArrayList<String> groupNames = courseFound.getGroupNames();

                HashMap<String, Integer[]> groups = courseFound.getTutLabGroups();
                for (Map.Entry<String, Integer[]> entry : groups.entrySet()) {
                  if (entry.getKey().equals("_LEC")) {
                    continue;
                  }
                  System.out.print("|      " + entry.getKey() + "      |     ");
                  System.out.printf("%2s", entry.getValue()[0]);
                  System.out.print("      |       ");
                  System.out.printf("%2s", entry.getValue()[1]);
                  System.out.println("       |");
                }
            }
  
            System.out.println("+----------------+-------------+----------------+");
            break;
          } catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());
          }

        case 3:
          System.out.print("Enter your matric number: ");
          matric = sc.next();
          sc.nextLine();
          StudentManager.printTranscript(matric);
          break;

        case 4:
          System.out.print("Enter new student's name: ");
          String name = sc.nextLine();
          System.out.print("Enter " + name + "'s major (e.g. CSC): ");
          String major = sc.nextLine();
          System.out.print("Enter " + name + "'s enrollment period (e.g. AY1718 S1): ");
          String enroll = sc.nextLine();
          System.out.print("Enter " + name + "'s matric number: ");
          matric = sc.nextLine();

          try {
            StudentManager.addStudent(name, major, enroll, matric);
            printRegisteredStudents();
          } catch (IllegalStudentArgumentException e) {
            System.out.println(e.getMessage());
          }
          
          break;

        case 5:
          tempVacancies = new HashMap<String, Integer>();
          tempWeightageList = new HashMap<String, String[]>();

          System.out.print("Enter new course name: ");
          courseName = sc.next();

          if (CourseManager.isCourseInList(courseName)) {
            System.out.println("Another course with this course name has been registered.");
            break;
          }

          System.out.print("Enter " + courseName + "'s course type (e.g. LEC, TUT, LAB): ");
          String typeInput = sc.next();

          CourseType courseType = CourseType.LEC;

          try {
            courseType = CourseManager.courseTypeToEnum(typeInput);
          } catch (IllegalCourseTypeException e) {
            System.out.println(e.getMessage());
            break;
          }

          System.out.print("Enter " + courseName + "'s total vacancy: ");
          int totalVacancy = sc.nextInt();

          while (totalVacancy <= 0) {
            System.out.println("Vacancy cannot be less than or equal to zero. Please try again!");
            System.out.print("Enter " + courseName + "'s total vacancy: ");
            totalVacancy = sc.nextInt();
          }

          tempVacancies.put("_LEC", totalVacancy);

          if (courseType != CourseType.LEC) {
            int groupVacancy;
            String groupName;

            System.out.println("Enter -1 on group name to exit.");

            while (true) {
              sc.nextLine();
              System.out.print("Enter group name (e.g. SSP1): ");
              groupName = sc.next();
              if (groupName.equals("-1")) {
                break;
              }
              System.out.print("Enter vacancy for group " + groupName + ": ");
              groupVacancy = sc.nextInt();
              if (groupVacancy <= 0) {
                System.out.println("Vacancy cannot be less than or equal to zero.");
                continue;
              }
              tempVacancies.put(groupName, groupVacancy);
            }
          }

          ArrayList<String> components = new ArrayList<String>();

          String cur = "";
          int tempSum = 0;

          System.out.println();
          System.out.println("Enter -1 on component when input is done.");
          System.out.println("Skip input on parent component if the component doesn't have a parent component.");

          sc.nextLine();

          while (true) {
            System.out.println();
            System.out.print("Enter component (e.g. Exam): ");
            String component = sc.nextLine();

            if (component.equals("-1")) {
              break;
            }

            System.out.print("Enter percentage for '" + component + "' (e.g. 60%): ");
            String percentage = sc.nextLine();
            components.add(percentage);
            System.out.print("Does '" + component + "' have any subcomponent(s) (y/n)? ");
            String hasSubcomponents = (sc.nextLine().toLowerCase() == "y" ? "true" : "false");
            components.add(hasSubcomponents);
            System.out.print("Enter parent component for '" + component + "' (e.g. ''): ");
            String parent = sc.nextLine();
            components.add(parent);

            tempWeightageList.put(component, components.toArray(new String[0]));
            components.clear();
          }

          System.out.println();

          System.out.print("Enter course coordinator's name: ");
          String coordinatorName = sc.nextLine();
          System.out.print("Enter " + coordinatorName + "'s ID: ");
          String coordinatorId = sc.nextLine();
          System.out.print("Enter the faculty in which " + coordinatorName + " is a member of: ");
          String coordinatorFaculty = sc.nextLine();

          try {
            CourseManager.addCourse(
              courseName, courseType, tempVacancies, tempWeightageList,
              new FacultyMember(coordinatorName, coordinatorId, coordinatorFaculty, true)
            );
            
            printRegisteredCourses();
          } catch (DuplicateCourseException e) {
            System.out.println(e.getMessage());
          } catch (IllegalWeightageException e) {
            System.out.println(e.getMessage());
          } catch (IllegalVacancyException e) {
            System.out.println(e.getMessage());
          }

          break;

        case 6:
          System.out.print("Enter course name: ");
          courseName = sc.next();

          try {
            courseFound = CourseManager.findCourse(courseName);

            if (courseFound.getCourseType() == CourseType.LEC) {
              RecordManager.printStudentList(courseName, false);
            } else if (courseFound.getCourseType() == CourseType.TUT) {
              System.out.println("(1) Print by lecture");
              System.out.println("(2) Print by tutorial group(s)");
              System.out.print("Enter your choice: ");
              int printBy = sc.nextInt();
  
              while (printBy < 1 || printBy > 2) {
                System.out.println("Invalid choice, try again!");
                System.out.print("Enter your choice: ");
                printBy = sc.nextInt();
              }
  
              switch (printBy) {
                case 1:
                  RecordManager.printStudentList(courseName, false);
                  break;
                case 2:
                  RecordManager.printStudentList(courseName, true);
                  break;
              }
            } else {
              System.out.println("(1) Print by lecture");
              System.out.println("(2) Print by tutorial group(s)");
              System.out.println("(3) Print by laboratory group(s)");
              System.out.print("Enter your choice: ");
              int printBy = sc.nextInt();
  
              while (printBy < 1 || printBy > 3) {
                System.out.println("Invalid choice, try again!");
                System.out.print("Enter your choice: ");
                printBy = sc.nextInt();
              }
  
              switch (printBy) {
                case 1:
                  RecordManager.printStudentList(courseName, false);
                  break;
                case 2:
                case 3:
                  RecordManager.printStudentList(courseName, true);
                  break;
              }
            }
          } catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());
          }

          break;

        case 7:          
          try {
            check = false;

            System.out.print("Enter matric number of a registered student: ");
            matric = sc.next();
            
            String studentName = StudentManager.findStudent(matric).getName();
            
            System.out.print("Enter course name which " + studentName + " is registered on: ");
            courseName = sc.next();
  
            if (!CourseManager.isCourseInList(courseName)) {
              System.out.println("Oops, you have entered an invalid course name.");
              break;
            }
  
            if (!RecordManager.isStudentRegisteredOnCourse(matric, courseName)) {
              System.out.println(
                "Oops, " + studentName + " is not registered in course " + courseName + "."
              );
              break;
            }
  
            for (Record r : RecordManager.getRecordList()) {
              if (r.getStudent().getMatric().equals(matric) &&
                  r.getCourse().getCourseName().equals(courseName)) {
                check = true;
                mark = (r.getMark() != null) ? r.getMark() : new HashMap<String, Float>();
                weightage = r.getCourse().getWeightage();
  
                for (Map.Entry<String, String[]> entry : weightage.entrySet()) {
                  String component = entry.getKey();
                  String[] info = entry.getValue();
                  boolean leaf = info[HAS_CHILD].equals("true") ? false : true;
                  boolean isComponentExam = component.toLowerCase().equals("exam");
  
                  if (leaf && !isComponentExam) {
                    System.out.print(
                      "Do you want to enter mark for " + entry.getKey() + "? (y/n) "
                    );
                    yesOrNo = sc.next().toLowerCase();

                    while (!yesOrNo.equals("y") && !yesOrNo.equals("n")) {
                      System.out.println("Please enter 'y' or 'n'.");
                      System.out.print(
                        "Do you want to enter mark for " + entry.getKey() + "? (y/n) "
                      );
                      yesOrNo = sc.next().toLowerCase();
                    }
  
                    if (yesOrNo.equals("y")) {
                      System.out.print("Enter mark for " + entry.getKey() + ": ");
                      ans = sc.nextFloat();
  
                      while (ans < 0 || ans > 100) {
                        System.out.println(
                          "Oops, you have entered an invalid mark, Please try again!"
                        );
                        System.out.print("Enter mark for " + entry.getKey() + ": ");
                        ans = sc.nextFloat();
                      }
  
                      mark.put(entry.getKey(), ans);
                    }
                  }
                }
  
                r.setMark(mark);
                break;
              }
            }
  
            if (check == false) {
              System.out.println("Student is not taking that course!");
            } else {
              System.out.println("Coursework mark set successfully.");
            }
          } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
          }
          
          break;

        case 8:
          try {
            check = false;

            System.out.print("Enter matric number of a registered student: ");
            matric = sc.next();
            
            String studentName = StudentManager.findStudent(matric).getName();
            
            System.out.print("Enter course name which " + studentName + " is registered on: ");
            courseName = sc.next();
  
            if (!CourseManager.isCourseInList(courseName)) {
              System.out.println("Oops, you have entered an invalid course name.");
              break;
            }
  
            if (!RecordManager.isStudentRegisteredOnCourse(matric, courseName)) {
              System.out.println(
                "Oops, " + studentName + " is not registered in course " + courseName + "."
              );
              break;
            }

            for (Record r : RecordManager.getRecordList()) {
              if (r.getStudent().getMatric().equals(matric) &&
                  r.getCourse().getCourseName().equals(courseName)) {
                check = true;
                mark = (r.getMark() != null) ? r.getMark() : new HashMap<String, Float>();

                System.out.print("Enter mark for exam: ");
                ans = sc.nextFloat();

                while (ans < 0 || ans > 100) {
                  System.out.println("Oops, mark should be between 0 and 100. Please try again!");
                  System.out.print("Enter mark for exam: ");
                  ans = sc.nextFloat();
                }

                mark.put("Exam", ans);
                r.setMark(mark);
                break;
              }
            }

            if (check == false) {
              System.out.println("Student is not taking that course!");
            } else {
              System.out.println("Exam mark set successfully!");
            }
          } catch (StudentNotFoundException e) {
            System.out.println(e.getMessage());
          }
          
          break;

        case 9:
          try {
            System.out.print("Input the course name for statistics: ");
            courseName = sc.next();
            sc.nextLine();

            RecordManager.printCourseStatistics(CourseManager.findCourse(courseName));
          } catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());
          } catch (NoRegisteredStudentException e) {
            System.out.println(e.getMessage());
          }
          
          break;
      }

      System.out.println();
    }

    StudentManager.inputToFile();
    CourseManager.inputToFile();
    RecordManager.inputToFile();
  }
}
