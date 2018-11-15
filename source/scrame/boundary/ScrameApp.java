package scrame.boundary;

import java.util.ArrayList;
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

public class ScrameApp {

  public static void main(String[] args) {
    StudentManager.loadFromFile();
    CourseManager.loadFromFile();
    RecordManager.loadFromFile();

    int userChoice;
    int functionChoice;
    boolean firstLoop = true;
    boolean flagWhile = true;
    boolean quit = false;

    String matric;
    String courseName;
    Student studentFound;
    Course courseFound;
    HashMap<String, Integer> tempVacancies;
    HashMap<String, String[]> tempWeightageList;
    HashSet<Record> recordList;
    String tmp;
    boolean check;
    
    Scanner sc = new Scanner(System.in);
    
    // userChoice = 1; // admin

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
      System.out.print("Enter matriculation number: ");
      matric = sc.next();

      while (!StudentManager.isStudentInList(matric)) {
        System.out.println("Oh no! This matriculation number is not registered yet :(");
        System.out.print("Enter matriculation number: ");
        matric = sc.next();
      }
    }

    while (flagWhile) {
      if (userChoice == 2) {
        functionChoice = StudentForm.display();
      } else if (firstLoop) {
        functionChoice = AdminForm.display();
        firstLoop = false;
      } else {
        functionChoice = AdminForm.displayMini();
      }

      switch (functionChoice) {
        case 0:
          System.out.println("Exiting SCRAME application... Goodbye!");
          flagWhile = false;
          break;

        case 1:
          try {
            System.out.print("Please enter matric number: ");
            matric = sc.next();

            StudentManager.validateMatric(matric);
            StudentManager.findStudent(matric);

            System.out.print("Please enter course name: ");
            courseName = sc.next();
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
          
          // RecordManager.registerStudentCourse("U1720120H", "CZ2001");
          // RecordManager.registerStudentCourse("U1720120H", "CZ2002", "BCG2");
          // RecordManager.registerStudentCourse("U1720121H", "CZ2002", "SSP1");
          // RecordManager.registerStudentCourse("U1720121H", "CZ2003", "BCG2");
          // RecordManager.registerStudentCourse("U1720122H", "CZ2003", "SSP1");

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
          System.out.print("Enter your matriculation number: ");
          matric = sc.next();
          StudentManager.printTranscript(matric);
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

            System.out.println();
            System.out.println("+-------------------------------------------------+");
            System.out.println("|               Registered Students               |");
            System.out.println("+----------------------------+--------------------+");
            System.out.println("|        Student Name        |   Matric. Number   |");
            System.out.println("+----------------------------+--------------------+");

            for (Student s : StudentManager.getStudentList()) {
              System.out.print("|  ");
              System.out.printf("%-24s", s.getName());
              System.out.print("  |     ");
              System.out.println(s.getMatric() + "      |");
            }

            System.out.println("+----------------------------+--------------------+");
          } catch (IllegalStudentArgumentException e) {
            System.out.println(e.getMessage());
          }

          // try {
          //   StudentManager.addStudent("Margaret Claire", "CSC", "AY1718 S1", "U1720120H");
          //   StudentManager.addStudent("Kevin Winata", "CSC", "AY1718 S1", "U1720121H");
          //   StudentManager.addStudent("Jason Sebastian", "CSC", "AY1718 S1", "U1720122H");
          //   StudentManager.addStudent("Elbert Widjaja", "CSC", "AY1718 S1", "U1720123H");
  
          //   System.out.println();
          //   System.out.println("+-------------------------------------------------+");
          //   System.out.println("|               Registered Students               |");
          //   System.out.println("+----------------------------+--------------------+");
          //   System.out.println("|        Student Name        |   Matric. Number   |");
          //   System.out.println("+----------------------------+--------------------+");
  
          //   ArrayList<Student> tempStudentList = new ArrayList<Student>(StudentManager.getStudentList());
          //   Collections.sort(tempStudentList, new SortByStudentName());

          //   for (Student s : tempStudentList) {
          //     System.out.print("|  ");
          //     System.out.printf("%-24s", s.getName());
          //     System.out.print("  |     ");
          //     System.out.println(s.getMatric() + "      |");
          //   }

          //   System.out.println("+----------------------------+--------------------+");
          // } catch (IllegalStudentArgumentException e) {
          //   System.out.println(e.getMessage());
          // }
          
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
            
            System.out.println();
            System.out.println("+--------------------------------------------+");
            System.out.println("|             Registered Courses             |");
            System.out.println("+-----------------+--------------------------+");
            System.out.println("|   Course Name   |    Course Coordinator    |");
            System.out.println("+-----------------+--------------------------+");

            for (Course c : CourseManager.getCourseList()) {
              System.out.print("|     " + c.getCourseName() + "      |   ");
              System.out.printf("%-20s", c.getCoordinator().getName());
              System.out.println("   |");
            }

            System.out.println("+-----------------+--------------------------+");
          } catch (DuplicateCourseException e) {
            System.out.println(e.getMessage());
          } catch (IllegalWeightageException e) {
            System.out.println(e.getMessage());
          } catch (IllegalVacancyException e) {
            System.out.println(e.getMessage());
          }

          // tempVacancies = new HashMap<String, Integer>();
          // HashMap<String, Integer> tempVacanciesLec = new HashMap<String, Integer>();
          
          // tempVacanciesLec.put("_LEC", 2);
          // tempVacancies.put("_LEC", 2);
          // tempVacancies.put("SSP1", 1);
          // tempVacancies.put("BCG2", 1);

          // tempWeightageList = new HashMap<String, String[]>();
          // tempWeightageList.put("Exam", new String[]{"60%", "false", ""});
          // tempWeightageList.put("Coursework", new String[]{"40%", "true", ""});
          // tempWeightageList.put("Assessment", new String[]{"70%", "false", "Coursework"});
          // tempWeightageList.put("Attendance", new String[]{"30%", "false", "Coursework"});

          // try {
          //   CourseManager.addCourse("CZ2001", CourseType.LEC, tempVacanciesLec, tempWeightageList,
          //     new FacultyMember("Arvind Easwaran", "101", "CSC", true)
          //   );
          //   CourseManager.addCourse("CZ2002", CourseType.TUT, tempVacancies, tempWeightageList,
          //     new FacultyMember("Goh Wooi Boon", "102", "CSC", true)
          //   );
          //   CourseManager.addCourse("CZ2003", CourseType.LAB, tempVacancies, tempWeightageList,
          //     new FacultyMember("Lua Rui Ping", "103", "CSC", true)
          //   );

          //   System.out.println();
          //   System.out.println("+--------------------------------------------+");
          //   System.out.println("|             Registered Courses             |");
          //   System.out.println("+-----------------+--------------------------+");
          //   System.out.println("|   Course Name   |    Course Coordinator    |");
          //   System.out.println("+-----------------+--------------------------+");

          //   ArrayList<Course> tempCourseList = new ArrayList<Course>(CourseManager.getCourseList());
          //   Collections.sort(tempCourseList, new SortByCourseName());

          //   for (Course c : tempCourseList) {
          //     System.out.print("|     " + c.getCourseName() + "      |   ");
          //     System.out.printf("%-20s", c.getCoordinator().getName());
          //     System.out.println("   |");
          //   }

          //   System.out.println("+-----------------+--------------------------+");
          // } catch (DuplicateCourseException e) {
          //   System.out.println(e.getMessage());
          // } catch (IllegalWeightageException e) {
          //   System.out.println(e.getMessage());
          // } catch (IllegalVacancyException e) {
          //   System.out.println(e.getMessage());
          // }

          break;

        case 6:
          // RecordManager.printStudentList();

          System.out.print("Enter course name: ");
          courseName = sc.next();

          try {
            Course c = CourseManager.findCourse(courseName);

            if (c.getCourseType() == CourseType.LEC) {
              RecordManager.printStudentList(courseName);
            } else {
              System.out.println("The list of groups: ");
              c.printAllGroups();
  
              System.out.print("\nEnter group name: ");
              String groupName = sc.next();
  
              RecordManager.printStudentList(courseName, groupName);
            }
          } catch (CourseNotFoundException e) {
            System.out.println(e.getMessage());
          } catch (IllegalCourseTypeException e) {
            System.out.println(e.getMessage());
          }

          break;

        case 7:
          // RecordManager.setCourseworkMark();
          
          check = false;
          quit = false;
          HashMap<String, Float> mark;
          HashMap<String, String[]> weightage;
          float ans;
          
          //----------------------------------------------------------------------------------------
          //- THIS IS THE AUTOMATION FOR MARK AND COURSEWORK
          //----------------------------------------------------------------------------------------
        //  int auto = 1;
        //   String automatric;
          
          // float examScore = 50;
          // float courseWorkscore = 80;
          // recordList = RecordManager.getRecordList();
          // for(auto =1;auto<16;auto++){
          //   if(auto<10){
          //     automatric = "U172010"+Integer.toString(auto)+"H";
          //   }
          //   else{
          //     automatric = "U17201"+Integer.toString(auto)+"H";
          //   }
          //   for (Record r : recordList) {
          //     if (r.getStudent().getMatric().equals(automatric) &&
          //         r.getCourse().getCourseName().equals("CZ2001")) {
          //           mark = r.getMark();
          //           mark.put("Exam", examScore);
          //           mark.put("Coursework",courseWorkscore);
          //           if(examScore == 100 || courseWorkscore == 100){
          //             examScore = 50;
          //             courseWorkscore = 70;
          //           }
          //           examScore +=5;
          //           courseWorkscore+=5;
          //           r.setMark(mark);
                    
          //       }
          //   }
          // } 
          



          System.out.print("Enter student matriculation number: ");
          matric = sc.next();
          
          while (!StudentManager.isStudentInList(matric)) {
            System.out.print("No registered student with that matriculation number! ");
            System.out.print("Try again (enter -1 to exit): ");
            matric = sc.next();
            if (matric.equals("-1")) {
              quit = true;
              break;
            }
          }

          if (quit) {
            break;
          }
          
          System.out.print("Enter course name: ");
          courseName = sc.next();
          while (!CourseManager.isCourseInList(courseName)) {
            System.out.print("Course doesn't exist! Try again (enter -1 to exit): ");
            courseName = sc.next();
            if (courseName.equals("-1")) {
              quit = true;
              break;
            }
          }

          if (quit) {
            break;
          }

          recordList = RecordManager.getRecordList();

          for (Record r : recordList) {
            if (r.getStudent().getMatric().equals(matric) &&
                r.getCourse().getCourseName().equals(courseName)) {
              check = true;
              mark = r.getMark();
              weightage = r.getCourse().getWeightage();

              for (Map.Entry<String, String[]> entry : weightage.entrySet()) {
                // System.out.println(entry.getKey() + " = " + entry.getValue());
                //TODO : entry.getValue() buat apa?
                
                if (entry.getValue()[1].equals("false") && !(entry.getKey().toLowerCase().equals("exam"))) {
                  System.out.print(
                    "Do you want to enter mark for " + entry.getKey() + "? (y(1)/n(0)) "
                  );
                  ans = sc.nextInt();

                  if (ans == 1) {
                    System.out.print("Enter mark for " + entry.getKey() + ": ");
                    ans = sc.nextFloat();
                    while (ans < 0 || ans > 100) {
                      System.out.println("WHOOPS, MARK IS OUT OF RANGE BOI");
                      System.out.print("Try Again: ");
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
          
          break;

        case 8:
          // RecordManager.setExamMark();
          check = false;
          quit = false;
          System.out.print("Enter student matriculation number: ");
          matric = sc.next();
          while (!StudentManager.isStudentInList(matric)) {
            System.out.print("Matriculation number doesn't exist! Try again (enter -1 to exit): ");
            matric = sc.next();
            if (matric.equals("-1")) {
              quit = true;
              break;
            }
          }
          if(quit) break;
          System.out.print("Enter course name: ");
          courseName = sc.next();
          while (!CourseManager.isCourseInList(courseName)) {
            System.out.print("Course doesn't exist! Try again (enter -1 to exit): ");
            courseName = sc.next();

            if (courseName.equals("-1")) {
              quit = true;
              break;
            }
          }
          if(quit) break;
          recordList = RecordManager.getRecordList();

          for (Record r : recordList) {
            if (r.getStudent().getMatric().equals(matric) &&
                r.getCourse().getCourseName().equals(courseName)) {
              check = true;
              mark = r.getMark();
              if (mark == null) {
                mark = new HashMap<String, Float>();
              }
              System.out.print("Enter mark for exam: ");
              ans = sc.nextFloat();
              while(ans < 0 || ans > 100){
                System.out.println("WHOOPS, MARK IS OUT OF RANGE BOI");
                System.out.print("Try Again: ");
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

          break;

        case 9:
        
          quit = false;
          System.out.print("Input the course name for statistics: ");
          courseName = sc.next();

          while (!CourseManager.isCourseInList(courseName)) {
            System.out.print("The course is not registered. Please try again (enter -1 to exit): ");
            courseName = sc.next();
            if (courseName.equals("-1")) {
              quit = true;
              break;
            }
          }
          if(quit) break;
          RecordManager.printCourseStatistics(courseName);

          // RecordManager.printCourseStatistics("CZ2001");
          break;
      }

      System.out.println();
    }

    StudentManager.inputToFile();
    CourseManager.inputToFile();
    RecordManager.inputToFile();
  }
}
