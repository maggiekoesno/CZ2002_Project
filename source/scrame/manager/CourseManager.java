package scrame.manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;

import java.io.IOException;
import java.io.EOFException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;
import java.util.Formatter;

import scrame.entity.Course;
import scrame.exception.IllegalCourseTypeException;
import scrame.helper.CourseType;

public final class CourseManager {
  private static HashSet<Course> courseList;
  private static String fileName = "../data/courses.ser";

  // /**
  //  * Search the course by courseId, and return the appropriate Course Object.
  //  * 
  //  * @param courseId the id of the course we want
  //  * @return Course Object
  //  */
  // public static Course findCourse(int courseId) {
  //   Course courseFound = null;
  //   for (Course c : courseList) {
  //     if (c.getCourseId() == courseId) {
  //       courseFound = c;
  //       break;
  //     }
  //   }
  //   return courseFound;
  // }

  public static Course findCourse(String courseName)
      throws IllegalArgumentException {
    if (!isCourseInList(courseName)) {
      throw new IllegalArgumentException(
        "Oops, it seems that the course " + courseName + " has not been registered to the system yet!"
      );
    }

    for (Course c : courseList) {
      if (c.getCourseName().equals(courseName)) {
        return c;
      }
    }

    return null;
  }

  /**
   * Write list of all courses to file.
   * 
   * @param courseList list of courses 
   */
  public static void inputToFile() {
    try {
      ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream(fileName)
      );
      out.writeObject(courseList);
      out.close();
      System.out.println("Serialized data is saved in " + fileName);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Read the textfile and insert it into courseList.
   */
  public static void loadFromFile() {
    try {
      ObjectInputStream in = new ObjectInputStream(
        new FileInputStream(fileName)
      );
      courseList = (HashSet<Course>) in.readObject();
      in.close();
    } catch (EOFException e) {
      courseList = new HashSet<Course>();
    } catch (IOException e) {
      e.printStackTrace();
      System.exit(1);
    } catch (ClassNotFoundException e) {
      System.out.println("Hashset<Course> class not found.");
      e.printStackTrace();
      System.exit(1);      
    }
  }

  /**
   * Add course into the courseList and insert it into textfile
   */
  public static void addCourse() {
    Scanner sc = new Scanner(System.in);

    HashMap<String, Integer> tempVacancies = new HashMap<String, Integer>();
    HashMap<String, String[]> tempWeightageList = new HashMap<String, String[]>();

    System.out.print("Enter new course name: ");
    String name = sc.nextLine();

    System.out.print("Enter course type: ");
    String typeInput = sc.nextLine();

    CourseType type = CourseType.LEC;
    try {
      type = courseTypeToEnum(typeInput);
    } catch (IllegalCourseTypeException e) {
      System.out.println(e.getMessage());
      System.exit(1);
    }

    System.out.print("Enter the course total vacancy: ");
    int totalVacancy = sc.nextInt();

    while (totalVacancy <= 0) {
      System.out.println(
        "Vacancy cannot be less than or equal to zero. Please try again!"
      );
      totalVacancy = sc.nextInt();
    }
    tempVacancies.put("_LEC",totalVacancy);

    if (type != CourseType.LEC) {
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
    Course added = new Course(name, type, tempVacancies, tempWeightageList);
    courseList.add(added);
    System.out.println("Course " + name + " added succesfully!");
  }

  /**
   * Overloaded function for testing.
   * 
   * @param courseName corurse name
   * @param courseType type of course
   * @param tempVacancies vacancies
   * @param tempWeightageList weightagelist
   */
  public static void addCourse(String courseName, CourseType courseType,
      HashMap<String, Integer> tempVacancies, HashMap<String, String[]> tempWeightageList)
      throws IllegalArgumentException {

    if (isCourseInList(courseName)) {
      throw new IllegalArgumentException(courseName + " has been registered.");
    }

    courseList.add(new Course(courseName, courseType, tempVacancies, tempWeightageList));   
    System.out.println("Course " + courseName + " added succesfully!");
  }

  /**
   * Helper function to convert course type to enum.
   */
  private static CourseType courseTypeToEnum(String tempCourseType)
      throws IllegalCourseTypeException {
    CourseType courseType = null;
    switch (tempCourseType) {
      case "LEC":
        courseType = CourseType.LEC;
        break;
      case "TUT":
        courseType = CourseType.TUT;
        break;
      case "LAB":
        courseType = CourseType.LAB;
        break;
      default:
        throw new IllegalCourseTypeException("Must be either LEC, TUT, or LAB.");
    }

    return courseType;
  }

  /**
   * A function to check whether a course is in the list
   * 
   * @param courseName id of the course
   * @return true if the course is in the list, false otherwise
   */
  public static boolean isCourseInList(String courseName) {
    for (Course c : courseList) {
      if (c.getCourseName().equals(courseName)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Take course name and display all the vacancies of the following courses.
   */
  public static void checkVacancy() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Please input the course name: ");
    String courseName = sc.nextLine();

    while (!isCourseInList(courseName)) {
      System.out.println("The course name you requested is not found!");
      System.out.print("Please input the course name: ");
      courseName = sc.nextLine();
    }

    Course courseFound = findCourse(courseName);
    CourseType courseType = courseFound.getCourseType();

    System.out.println();
    System.out.println("++++++++++++++++++++++++++++++");
    System.out.println("++++++ Course Vacancies ++++++");
    System.out.println("++++++++++++++++++++++++++++++");

    switch (courseType) {
      case LEC:
        int lectureVacancy = courseFound.checkLectureVacancy();
        System.out.println("++ Course Name ++++ Vacancy ++");
        System.out.println("++++++++++++++++++++++++++++++");
        System.out.print("++   " + courseName + "    ++++   ");
        System.out.printf("%3d", lectureVacancy);
        System.out.println("   ++");
        System.out.println("++++++++++++++++++++++++++++++");
        break;
      case TUT:
      case LAB:
        HashMap<String, Integer> groups = courseFound.getTutLabGroups();
        System.out.println("++ Group Name +++++ Vacancy ++");
        System.out.println("++++++++++++++++++++++++++++++");
        for (Map.Entry<String, Integer> entry : groups.entrySet()) {
          if (entry.getKey().equals("_LEC")) {
            continue;
          }
          System.out.print("++   " + entry.getKey() + "     +++++    ");
          System.out.printf("%2d", entry.getValue());
          System.out.println("   ++");
        }
        System.out.println("++++++++++++++++++++++++++++++");
        break;
    }
  }


  /**
   * Modify course weightage.
   */
  public static void setCourseWeightage() {
    System.out.println("Modify course weightage for specific course id");
    System.out.print("Please input the course name: ");

    Scanner sc = new Scanner(System.in);
    String courseName = sc.nextLine();

    while (!isCourseInList(courseName)) {
      System.out.print("The course is not registered. Please try again");
      courseName = sc.nextLine();
    }

    Course course = findCourse(courseName);
    HashMap<String, String[]> tempWeightageList = new HashMap<String, String[]>();

    // TODO NO IMPLEMENTATION YET
    // if (course.getCourseType() != courseTypeToEnum("LEC")) {
    //   System.out.println("Enter the Group name e.g \"SEP1\", -1 to exit");

    //   while (true) {
    //     String groupName = sc.next();
    //     if (groupName == "-1") {
    //       break;
    //     }
    //     System.out.print(
    //       "Enter the group vacancy (in the end all the group vacancies must be equal to the total vacancy)" + Integer.toString(course.getLectureVacancy())
    //     );
    //     int groupVacancy = sc.nextInt();
    //     while (groupVacancy <= 0) {
    //       System.out.println(
    //         " Vacancy cannot be less than or equal to zero. try again"
    //       );
    //     }
    //     tempVacancies.put(groupName, groupVacancy);
    //   }

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

    while (true) {
      System.out.println("Enter the weightage, -1 to exit:");
      String tmp = sc.next();
      if (tmp == "-1") {
        break;
      }
      String parts[] = tmp.split(",");
      tempWeightageList.put(parts[0], new String[]{parts[1],parts[2],parts[3]});
    }
    course.setWeightage(tempWeightageList);
    System.out.println("Weightage set successfully !");

    sc.close();
  }
}
