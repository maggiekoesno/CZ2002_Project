package scrame.manager;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.IOException;
import java.io.EOFException;

import java.io.Serializable;
import java.nio.file.Files;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

import java.util.Scanner;

import scrame.entity.Course;
import scrame.entity.Record;
import scrame.entity.Student;

import scrame.helper.CourseType;

import scrame.manager.CourseManager;
import scrame.manager.RecordManager;

public final class StudentManager {
  private static HashSet<Student> studentList = new HashSet<Student>();
  private static String fileName = "../data/students.ser";
  private static final int WEIGHT = 0;
  private static final int HAS_CHILD = 1;
  private static final int PARENT = 2;
  
  public static void inputToFile() {
    try {
      ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream(fileName)
      );
      out.writeObject(studentList);
      out.close();
      System.out.printf("Serialized data is saved in " + fileName);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public static void loadFromFile() {
    try {
      ObjectInputStream in = new ObjectInputStream(
        new FileInputStream(fileName)
      );
      studentList = (HashSet<Student>) in.readObject();
      in.close();
    } catch (EOFException e) {
      studentList = new HashSet<Student>();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.println("Hashset<Student> class not found.");
      e.printStackTrace();
    }
  }

  public static void addStudent() {
    String name;
    String major;
    String enroll;
    String matric;

    Scanner sc = new Scanner(System.in);

    System.out.print("Enter new student's name: ");
    name = sc.nextLine();
    System.out.print("Enter " + name + "'s major (e.g. CSC): ");
    major = sc.nextLine();
    System.out.print("Enter " + name + "'s enrollment period (e.g. AY1718 S1): ");
    enroll = sc.nextLine();
    System.out.print("Enter " + name + "'s matriculation number: ");
    matric = sc.nextLine();

    try {
      Student student = new Student(name, major, enroll, matric);
      studentList.add(student);
      
      for (Student s : studentList) {
        System.out.println(s.toString());
      }

      System.out.println("Student added successfully.");
    } catch(IllegalArgumentException e) {
      System.out.println(e.getMessage());
    } catch(Exception e) {
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
      if (s.getMatric().equals(matric)) {
        return true;
      }
    }
    return false;
  }

  public static void printTranscript() {
    Scanner sc = new Scanner(System.in);

    System.out.print("Enter your matriculation number: ");
    String matric = sc.nextLine();

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
      System.out.println(
        "Course average: " + Float.toString(r.calculateAverage())
      );

      System.out.println("Individual assessment: ");
      for (Map.Entry<String, Float> entry : r.getMark().entrySet()) {
        String component = entry.getKey();
        float mark = entry.getValue();
        System.out.println(component + ": " + Float.toString(mark));
      }
      printIndividualAssessment(r);
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

    Scanner sc = new Scanner(System.in);

    int courseId = sc.nextInt();
    Course c = CourseManager.getCourse(courseId);
    if (c.getCourseType() == CourseType.LEC) {
      String gname = "_LEC";
      HashSet<Record> recordList = RecordManager.getRecordList();
      for (Record r : recordList) {
        if (r.getGroupName().equals(gname) && c.getCourseId() == r.getCourse(

        ).getCourseId()) {
          System.out.println(r.getStudent().getName());
        }
      }
    } else {
      System.out.println("The list of groups: ");
      HashMap<String, Integer> tutLabGroups = c.getTutLabGroups();
      for (Map.Entry<String, Integer> entry : tutLabGroups.entrySet()) {
        System.out.println(entry.getKey());
      }
      System.out.print("Enter group name: ");

      String gname = sc.nextLine();
      HashSet<Record> recordList = RecordManager.getRecordList();
      for (Record r : recordList) {
        if (r.getGroupName().equals(gname) && c.getCourseId() == r.getCourse(

        ).getCourseId()) {
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
          System.out.println(component + ", weightage: " + info[WEIGHT]);
          printIndividualAssessment(r, component);
        } else {
          String w = info[WEIGHT];
          System.out.println(component + ", weightage: " + info[WEIGHT]);
        }
      }
    }
  }

}

