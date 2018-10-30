package scrame.manager;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;

import scrame.entity.Student;
import scrame.entity.Record;
import scrame.manager.CourseManager;
import scrame.manager.RecordManager;
import scrame.helper.CourseType;

public final class StudentManager {
  private static HashSet<Student> studentList = new HashSet<Record>();
  private static String fileName = "data/students.ser";

  public static void inputToFile(HashSet<Student> studentList) {
    try {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
      out.writeObject(studentList);
      out.close();
      System.out.printf("Serialized data is saved in " + fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public static void loadFromFile() {
    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
      studentList = (HashSet<Student>) in.readObject();
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.println("Hashset<Student> class not found.");
      e.printStackTrace();
    }
    // public void loadFromTextFile() {
    //   String line = null;
    //   String line_arr[] = new String[4];
    //   try {
    //     BufferedReader bufferedReader = new BufferedReader(
    //       new FileReader(fileName)
    //     );

    //     while (line = bufferedReader.readLine() != null) {
    //       line.split(",");
    //       studentList.add(
    //         new Student(line_arr[0], line_arr[1], line_arr[2], line_arr[3])
    //       );
    //     }
    //     bufferedReader.close();
    //   } catch(FileNotFoundException ex) {
    //     System.out.println("Unable to open file '" + fileName + "'");
    //   } catch(IOException ex) {
    //     System.out.println("Error reading file '" + fileName + "'");
    //   }
    // }
  }

  public static void addStudent() {
    String name;
    String major;
    String enroll;
    String matric;

    Scanner sc = new Scanner(System.in);

    System.out.print("Enter new student's name: ");
    name = sc.nextLine();
    System.out.print("Enter " + name + "'s major: ");
    major = sc.nextLine();
    System.out.print("Enter " + name + "'s enrollment period: ");
    enroll = sc.nextLine();
    System.out.print("Enter " + name + "'s matriculation number: ");
    matric = sc.nextLine();

    try {
      Student student = new Student(name, major, enroll, matric);
      studentList.add(student);
      System.out.println("Student added successfully.");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Check if a student is registered.
   * 
   * @param matric matric ID of the student
   * 
   * @return true if student in the list, false otherwise
   */
  public static boolean isStudentInList(String matric) {
    for (Student s : studentList) {
      if (s.getMatric() == matric) {
        return true;
      }
    }

    return false;
  }

  public static void printTranscript() {
    Scanner sc = new Scanner(System.in);
    
    System.out.print("Enter your matriculation number: ");
    String matric = sc.nextLine;

    boolean studentFound = false;
    HashSet<Record> recordList = RecordManager.getRecordList();

    HashSet<Record> recordFound = new HashSet<Record>();
    for (Record r : recordList) {
      if (r.getStudent().getMatric().equals(matric)) {
        recordFound.add(r);
        studentFound = true;
      }
    }

    if (!studentFound) {
      System.out.println("Holy guacamole, invalid matric number!");
      return;
    }
        
    for (Record r : recordFound) {
      System.out.println("Course name: " + r.getCourse().getCourseName());
      System.out.println("Course average: " + Float.toString(r.calculateAverage());

      System.out.println("Individual assessment: ");
      for (Map.Entry<String, Float> entry : r.getMark().entrySet()) {
        String component = entry.getKey();
        float mark = entry.getValue();
        System.out.println(component + ": " + Float.toString(mark));
      }

      printIndividualAssessment(r.getCourse().getWeightage());
    }
  }

  /**
   * Print student list for a given course id.
   * 
   * If the course is of type LEC, then go straight to print the list of all the students.
   * Else if the course is of type TUT or LAB, then ask the user for the group name, then
   * proceed to print the list of the students in that group.
   */
  public static void printStudentList() {
    System.out.print("Enter course id: ");
    int courseId = sc.nextInt();
    Course c = CourseManager.getCourse(courseId);
    if(c.getCourseType() == CourseType.LEC){
      String gname = "_LEC";
      HashSet<Record> recordList = RecordManager.getRecordList();
      for (Record r : recordList) {
        if (r.getGroupName.equals(gname) && c.getCourseId()==r.getCourse().getCourseId()) {
          System.out.println(r.getStudent().getName());
        }
      }
    }
    else{
      System.out.print("Enter group name: ");
      // TODO PRINT ALL AVAILABLE GROUP NAMES
      String gname = sc.nextLine();
      HashSet<Record> recordList = RecordManager.getRecordList();
      for (Record r : recordList) {
        if (r.getGroupName.equals(gname) && c.getCourseId()==r.getCourse().getCourseId()) {
          System.out.println(r.getStudent().getName());
        }
      }
    }
  }

  /**
   * Private method to print weightage as pairs of component and mark.
   * 
   * @param r individual record of student and course 
   */
  private static void printIndividualAssessment(Record r) {
    printIndividualAssessment(r, "");
  }

  /**
   * Overloaded method to print weightage as pairs of component and mark.
   * 
   * @param r individual record of student and course
   * @param check focus on the string check
   */
  private static void printIndividualAssessment(Record r, String check) {
    Map<String, String[]> weightage = r.getCourse().getWeightage();
    for (Map.Entry<String, String[]> entry : weightage.entrySet()) {
      String component = entry.getKey();
      String[] info = entry.getValue();
      if (info[PARENT].equals(check)) {
        if (info[HAS_CHILD].equals("true")) {
          String w = info[WEIGHT];
          System.out.println(component + ", weightage: "+info[WEIGHT]);
          printIndividualAssessment(r, component);   
        } else {
          String w = info[WEIGHT];
          System.out.println(component + ", weightage: "+info[WEIGHT]);          
        }
      }
    }
  }
}

