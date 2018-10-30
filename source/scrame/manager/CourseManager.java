package scrame.manager;

import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

import scrame.entity.Course;
import scrame.exception.IllegalCourseTypeException;
import scrame.helper.CourseType;

public final class CourseManager {
  private static HashSet<Course> courseList;
  private static String fileName = "data/courses.ser";

  /**
   * Search the course by courseId, and return the appropriate Course Object.
   * 
   * @param courseId the id of the course we want
   * @return Course Object
   */
  public static Course getCourse(int courseId) {
    Course courseFound = null;
    for (Course c : courseList) {
      if (c.getCourseId() == courseId) {
        courseFound = c;
        break;
      }
    }
    return courseFound;
  }

  public static void inputToFile(HashSet<Course> courseList) {
    try {
      ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream(fileName)
      );
      out.writeObject(courseList);
      out.close();
      System.out.printf("Serialized data is saved in " + fileName);
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * Function to read the textfile and insert it into courseList
   * 
   */
  public static void loadFromFile() // String line = null;
  // String line_arr[] = new String[3];
  // try {
  //   BufferedReader bufferedReader = new BufferedReader(
  //     new FileReader(fileName)
  //   );
  //   //HASHMAP INSERT SEP1 , 25
  //   while (line = bufferedReader.readLine() != null) {
  //     line_arr = line.split(",");
  //     length = line_arr.length;
  //     String name = line_arr[0];
  //     String courseType = line_arr[1];
  //     int courseVacancy = (int) line_arr[2];
  //     int i = 3;
  //     HashMap<String, Integer> tempVacancies = new HashMap<String, Integer>();
  //     tempVacancies.put("_LEC", courseVacancy);
  //     HashMap<String, String[]> tempWeightageList = new HashMap<
  //       String,
  //       String[]
  //     >();
  //     boolean readGroup = false;
  //     boolean readWeightage = false;
  //     if (courseType == "LEC") {
  //       while (i < length - 3) {
  //         if (line_arr[i] == "WEIGHTAGE") {
  //           readWeightage = true;
  //           i++;
  //         }
  //         if (readWeightage == true) {
  //           String weightageName = line_arr[i];
  //           String weightageContent = {
  //             line_arr[i + 1],
  //             line_arr[i + 2],
  //             line_arr[i + 3]
  //           };
  //           tempWeightageList.put(weightageName, weightageContent);
  //           i += 4;
  //           continue;
  //         }
  //         i++;
  //       }
  //       courseList.add(
  //         new Course(
  //           name,
  //           courseTypeToEnum(courseType),
  //           tempVacancies,
  //           tempWeightageList
  //         )
  //       );
  //     } else if (courseType == "TUT" || courseType == "LAB") {
  //       while (i < (length - 3)) {
  //         if (line_arr[i] == "GROUP") {
  //           readGroup = true;
  //           readWeightage = false;
  //           i++;
  //         } else if (line_arr[i] == "WEIGHTAGE") {
  //           readGroup = false;
  //           readWeightage = true;
  //           i++;
  //         }
  //         if (readGroup == true && readWeightage == false) {
  //           String groupName = line_arr[i];
  //           String groupVacancy = line_arr[i + 1];
  //           tempVacancies.put(groupName, groupVacancy);
  //           i += 2;
  //           continue;
  //         } else if (readWeightage == true && readGroup == false) {
  //           String weightageName = line_arr[i];
  //           String weightageContent = {
  //             line_arr[i + 1],
  //             line_arr[i + 2],
  //             line_arr[i + 3]
  //           };
  //           tempWeightageList.put(weightageName, weightageContent);
  //           i += 4;
  //           continue;
  //         }
  //         i++;
  //       }
  //       courseList.add(
  //         new Course(
  //           name,
  //           courseTypeToEnum(courseType),
  //           tempVacancies,
  //           tempWeightageList
  //         )
  //       );
  //     }
  //     System.out.println("Unable to open file '" + fileName + "'");
  //   }
  // } catch(FileNotFoundException ex) {
  //   System.out.println("Unable to open file '" + fileName + "'");
  // } catch(IOException ex) {
  //   System.out.println("Error reading file '" + fileName + "'");
  // }
  {
    try {
      ObjectInputStream in = new ObjectInputStream(
        new FileInputStream(fileName)
      );
      courseList = (HashSet<Course>) in.readObject();
      in.close();
    } catch(IOException e) {
      e.printStackTrace();
    } catch(ClassNotFoundException e) {
      System.out.println("Hashset<Course> class not found");
      e.printStackTrace();
    }
  }

  /**
   * Add course into the courseList and insert it into textfile
   */
  public static void addCourse() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter new course name: ");
    String name = sc.nextLine();
    System.out.print("Enter course type:");
    String type = sc.nextLine();
    System.out.print("Enter the course total vacancy: ");
    int totalVacancy = sc.nextInt();

    while (totalVacancy <= 0) {
      System.out.println(
        " Vacancy cannot be less than or equal to zero. try again"
      );
      totalVacancy = sc.nextInt();
    }
    HashMap<String, Integer> tempVacancies = new HashMap<String, Integer>();
    HashMap<String, String[]> tempWeightageList = new HashMap<String, String[]>(

    );
    if (type != "LEC") {
      System.out.println("Enter the Group name e.g \"SEP1\", -1 to exit");
      while (true) {
        String groupName = sc.next();
        if (groupName == "-1")
        break;
        System.out.print(
          "Enter the group vacancy (in the end all the group vacancies must be equal to the total vacancy)" + totalVacancy
        );
        int groupVacancy = sc.nextInt();
        while (groupVacancy <= 0) {
          System.out.println(
            " Vacancy cannot be less than or equal to zero. try again"
          );
        }
        tempVacancies.put(groupName, groupVacancy);
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

      while (true) {
        System.out.println("Enter the weightage, -1 to exit:");
        String tmp = sc.next();
        if (tmp == "-1") {
          break;
        }
        String parts[] = tmp.split(",");
        // tempWeightageList.add(parts[0],{parts[1],parts[2],parts[3]});
      }
    }
    Course course = new Course(name, type);
    courseList.add(course);
    System.out.println("Course added succesfully!");
  }

  private static CourseType courseTypeToEnum(String tempCourseType) {
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
        break;
    }
    return courseType;
  }

  /**
   * A function to check whether a course is in the list
   * 
   * @param courseId id of the course
   * @return true if the course is in the list, false otherwise
   */
  public static boolean isCourseInList(int courseId) {
    Course courseFound = null;
    for (Course c : courseList) {
      if (c.getCourseId() == courseId) {
        courseFound = c;
        break;
      }
    }
    return courseFound.getCourseId() == courseId;
  }

  /**
   * Function to take courseId and display all the vacancies of the following courses.
   */
  public static void checkVacancy() {
    Scanner sc = new Scanner(System.in);
    System.out.println("Please input the course id");
    int courseId = sc.nextInt();
    while (courseId < 0) {
      System.out.print("Invalid input, please try again : ");
      courseId = sc.nextInt();
    }
    System.out.println();
    System.out.println("++++++++++++++++++++++++++++++");
    System.out.println("++++++ Course Vacancies ++++++");
    System.out.println("++++++++++++++++++++++++++++++");
    for (Course c : courseList) {
      int vacancy;

      if (c.getCourseId() == courseId) {
        CourseType type = c.getCourseType();

        switch (type) {
          case LEC:
            try {
              String courseName = c.getCourseName();
              int lectureVacancy = c.checkLectureVacancy();
              System.out.println("++ Course Name ++++ Vacancy ++");
              System.out.println("++++++++++++++++++++++++++++++");
              System.out.print("++   " + courseName + "    ++++   ");
              System.out.printf("%3d" + lectureVacancy);
              System.out.println("   ++");
              System.out.println("++++++++++++++++++++++++++++++");
            } catch(IllegalCourseTypeException e) {
              System.out.println(e.getMessage());
            }
            break;
          case TUT:
          case LAB:HashMap<String, Integer> groups = c.getTutLabGroups();
            System.out.println("++ Group Name +++++ Vacancy ++");
            System.out.println("++++++++++++++++++++++++++++++");
            for (Map.Entry<String, Integer> entry : groups.entrySet()) {
              System.out.print("++    " + entry.getKey() + "     +++++    ");
              System.out.printf("%2d", entry.getValue());
              System.out.println("   ++");
            }
            System.out.println("++++++++++++++++++++++++++++++");
            break;
        }
        break;
      }
    }
  }

  public static void setCourseWeightage() {
    System.out.println("Modify course weightage for specific course id");
    System.out.print("Please input the courseId: ");
    int courseId = -1;

    Scanner sc = new Scanner(System.in);
    courseId = sc.nextInt();
    while (!isCourseInList(courseId)) {
      System.out.print("The course is not registered. Please try again");
      courseId = sc.nextInt();
    }
    Course course = getCourse(courseId);
    HashMap<String, String[]> tempWeightageList = new HashMap<String, String[]>(

    );
    if (type != "LEC") {
      System.out.println("Enter the Group name e.g \"SEP1\", -1 to exit");
      while (true) {
        String groupName = sc.next();
        if (groupName == "-1")
        break;
        System.out.print(
          "Enter the group vacancy (in the end all the group vacancies must be equal to the total vacancy)" + totalVacancy
        );
        int groupVacancy = sc.nextInt();
        while (groupVacancy <= 0) {
          System.out.println(
            " Vacancy cannot be less than or equal to zero. try again"
          );
        }
        tempVacancies.put(groupName, groupVacancy);
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

      while (true) {
        System.out.println("Enter the weightage, -1 to exit:");
        String tmp = sc.next();
        if (tmp == "-1") {
          break;
        }
        String parts[] = tmp.split(",");
        // tempWeightageList.add(parts[0],{parts[1],parts[2],parts[3]});
      }
      course.setWeightage(tempWeightageList);
      System.out.println("Weightage set successfully !");
    }
  }

}

