package manager;

import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Scanner;

import entity.Course;
import entity.CourseType;


public class CourseManager {
  private HashSet<Course> courseList;

  private static String fileName = "data/courses.txt"; // The name of the file to open.
  
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

  /**
   * Function to read the textfile and insert it into courseList
   * 
   */
  public void loadFromTextFile() {
    String line = null;
    String line_arr[] = new String[3];
    try {
      BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
      //"CZ2002, LEC, 50, "WEIGHTAGE", "EXAM", "60%", "false", "",...    "
      //"CZ2002, TUT, 50, GROUP, SEP1, 25, SEP2, 25, WEIGHTAGE"//
      //HASHMAP <> INSERT SEP1 , 25

      while ((line = bufferedReader.readLine()) != null) {
        line_arr = line.split(",");
        length = line_arr.length;
        String name = line_arr[0];
        String courseType = line_arr[1];
        int courseVacancy = (int)line_arr[2];
        int i = 3;

        HashMap<String, Integer> tempVacancies = new HashMap<String, Integer>;
        tempVacancies.put("_LEC",courseVacancy);

        HashMap<String, String[]> tempWeightageList = new HashMap<String, String[]>; //TODO


        boolean readGroup = false;
        boolean readWeightage = false;

        if (courseType == "LEC") {
          while(i<length-3) {
            if(line_arr[i] == "WEIGHTAGE") {
              readWeightage = true;
            }

            if(readWeightage == true){
              String weightageName = line_arr[i];
              String weightageContent = {line_arr[i+1],line_arr[i+2],line_arr[i+3]};
              tempWeightageList.put(weightageName,weightageContent);
              i+=4;
            }

          }

          courseList.add(new Course(name, courseTypeToEnum(courseType), tempVacancies, tempWeightageList));

        } else if (courseType == "TUT") {
            
        }



        System.out.println("Unable to open file '" + fileName + "'");
      }
    } catch (IOException ex) {
      System.out.println("Error reading file '" + fileName + "'");
    }
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

    Course course = new Course(name,type);
    courseList.add(course);

    myText = name + "," + type;
      
    System.out.print("");
    try {
      Files.write(Paths.get(filename), myText.getBytes(), StandardOpenOption.APPEND);
      System.out.println("Course added successfully.");
    } catch (IOException e) {
      System.out.println("Unable to write new student into file.");
    }
  }
  
  /**
   * Function to assign enumerate coursetype to the course
   * 
   * 
   * @param tempCourseType string contained the type of the lecture
   * @return generated courseType
   */
  private CourseType courseTypeToEnum(String tempCourseType) {
    CourseType courseType;
  
    switch (tempCourseType.toUpperCase()) {
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
        throw new IllegalCourseTypeException("Course type input is invalid.");
    }

    return courseType;
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
            HashMap<String,Integer> groups = tmp.getTutLabGroups();

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