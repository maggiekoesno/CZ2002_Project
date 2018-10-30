package scrame.manager;

import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Scanner;

import scrame.entity.Course;
import scrame.entity.CourseType;

public class CourseManager {
  private HashSet<Course> courseList;

  private static String fileName = "data/courses.ser"; // The name of the file to open.

  /**
   * Search the course by courseId, and return the appropriate Course Object.
   * 
   * @param courseId the id of the course we want
   * @return Course Object
   */
  public Course getCourse(int courseId) {
    Iterator value = courseList.iterator();
    Course courseFound = null;
    while (value.hashNext()) {
      if (value.next().getCourseId() == courseId) {
        courseFound = tmp;
        break;
      }
    }
    return courseFound;
  }
  
  public void inputToTextFile(HashSet<Course> courseList) {
    try {
      FileOutputStream fileOut = new FileOutputStream(fileName);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(courseList);
      out.close();
      fileOut.close();
      System.out.printf("Serialized data is saved in "+fileName);
    } catch (IOException i) {
      i.printStackTrace();
    }
  }
  /**
   * Function to read the textfile and insert it into courseList
   * 
   */
  public void loadFromTextFile() {
    try {
      FileInputStream fileIn = new FileInputStream(filename);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      courseList = (HashSet<Course>) in.readObject();
      in.close();
      fileIn.close();
    } catch (IOException i) {
      i.printStackTrace();
      return;
    } catch (ClassNotFoundException c) {
      System.out.println("Hashset<Course> class not found");
      c.printStackTrace();
      return;
    }

    // String line = null;
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
  }

  /**
   * Add course into the courseList and insert it into textfile
   */
  public void addCourse() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Enter new course name: ");
    String name = sc.nextLine();
    System.out.print("Enter course type:");
    String type = sc.nextLine();
    System.out.print("Enter the course total vacancy: ");
    String totalVacancy = sc.nextInt();

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
      ); //TODO BEAUTIFY
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
    }
    Course course = new Course(name, type);
    courseList.add(course);

    myText = name + "," + type;

    System.out.print("");
    try {
      Files.write(
        Paths.get(filename),
        myText.getBytes(),
        StandardOpenOption.APPEND
      );
      System.out.println("Course added successfully.");
    } catch(IOException e) {
      System.out.println("Unable to write new student into file.");
    }
  }

  public void writeToTextFile() {
    String name;
    String type;
    String vacancy;
    String gName;
    String gVacancy;
    Map<String, String[]> weightage;
    String wName;
    String wContent;
    String noParent;
    String parent;

    boolean fw_value = false;
    FileWriter fw = new FileWriter(filename, false);
    BufferedWriter bw = new BufferedWriter(fw);

    for (Course c : courseList) {}
  }

  private CourseType courseTypeToEnum(String tempCourseType) {
    switch (tempCourseType) {
      case "LEC":
        courseType = courseType.LEC;
        break;
      case "TUT":
        courseType = courseType.TUT;
        break;
      case "LAB":
        courseType = CourseType.LAB;
        break;
      default:
        break;
    }
  }

  /**
   * A function to check whether a course is in the list
   * 
   * @param courseId id of the course
   * @return true if the course is in the list, false otherwise
   */
  public boolean isCourseInList(int courseId) {
    Iterator value = courseList.iterator();

    Course courseFound = null;
    while (value.hashNext()) {
      Course tmp = value.next();
      if (tmp.getCourseId() == courseId) {
        courseFound = tmp;
        break;
      }
    }
    return courseFound.getCourseId() == courseId;
  }

  /**
   * Function to take courseId and display all the vacancies of the following courses.
   */
  public void checkVacancy() {
    Scanner sc = new Scanner(System.in);
    Iterator value = courseList.iterator();

    System.out.println();
    System.out.println("++++++++++++++++++++++++++++++");
    System.out.println("++++++ Course Vacancies ++++++");
    System.out.println("++++++++++++++++++++++++++++++");

    while (value.hashNext()) {
      Course tmp = value.next();
      int vacancy;

      if (tmp.getCourseId() == courseId) {
        CourseType type = tmp.getCourseType();

        switch (type) {
          case "LEC":
            System.out.println("++ Course Name ++++ Vacancy ++");
            System.out.println("++++++++++++++++++++++++++++++");
            System.out.print("++   " + tmp.getCourseName() + "    ++++   ");
            System.out.printf("%3d", tmp.checkLectureVacancy());
            System.out.println("   ++");
            System.out.println("++++++++++++++++++++++++++++++");
            break;
          case "TUT":
          case "LAB":
            HashMap<String, Integer> groups = tmp.getTutLabGroups();
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

}

