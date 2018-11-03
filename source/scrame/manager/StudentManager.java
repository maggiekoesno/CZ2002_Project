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

import java.lang.IllegalArgumentException;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

import java.util.Scanner;

import scrame.entity.Course;
import scrame.entity.Record;
import scrame.entity.Student;
import scrame.exception.IllegalCourseTypeException;
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
      System.out.println("Serialized data is saved in " + fileName);
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

      System.out.println("Student named " + name + " added successfully.");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }

    sc.close();
  }

  public static void addStudent(String name, String major, String enroll, String matric)
      throws IllegalArgumentException {
    if (isStudentInList(matric)) {
      throw new IllegalArgumentException("Another student with matric number of " + matric + " has been registered.");
    }

    studentList.add(new Student(name, major, enroll, matric));
    System.out.println("Student named " + name + " added successfully.");
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

  public static Student findStudent(String matric){
    Student studentFound = null;
    for (Student s : studentList) {
      if (s.getMatric().equals(matric)) {
        studentFound = s;
      }
    }
    return studentFound;
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
      sc.close();
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

    sc.close();
  }

  /**
   * Print student list for a given course name.
   * 
   * If the course is of type LEC, then go straight to print the list of all the students.
   * Else if the course is of type TUT or LAB, then ask the user for the group name, then
   * proceed to print the list of the students in that group.
   */
  public static void printStudentList() {
    System.out.print("Enter course name: ");
    Scanner sc = new Scanner(System.in);
    String courseName = sc.nextLine();
    Course c = CourseManager.findCourse(courseName);

    if (c.getCourseType() == CourseType.LEC) {
      HashSet<Record> recordList = RecordManager.getRecordList();
      int counterStudentList=0;
      for (Record r : recordList) {
        //if (r.getGroupName().equals(gname) &&
            //c.getCourseName() == r.getCourse().getCourseName()) {
        if (c.getCourseName() == r.getCourse().getCourseName()){
          System.out.println(r.getStudent().getName());
          counterStudentList++;
        }
      }
      System.out.println("Total number of students : " + Integer.toString(counterStudentList));      
    } else {
      System.out.println("The list of groups: ");
      try {
        c.printAllGroups();
      } catch (IllegalCourseTypeException e) {
        e.printStackTrace();
      }
      //HashMap<String, Integer> tutLabGroups = c.getTutLabGroups();
      System.out.print("\nEnter group name: ");

      String groupName = sc.nextLine();
      HashSet<Record> recordList = RecordManager.getRecordList();
      int counterStudentList=0;
      for (Record r : recordList) {
        if (r.getGroupName().equals(groupName) &&
            c.getCourseName() == r.getCourse().getCourseName()) {
          System.out.println(r.getStudent().getName());
          counterStudentList++;
        }
      }
      System.out.println("Total number of students : " + Integer.toString(counterStudentList));
    }
  }

  public static void printStudentList(String courseName, String groupName) {
    if (!CourseManager.isCourseInList(courseName)) {
      throw new IllegalArgumentException(
        "Oops, it seems that course " + courseName + " has not been registered to the system yet!"
      );
    }

    Course c = CourseManager.findCourse(courseName);
    CourseType courseType = c.getCourseType();

    if (courseType != CourseType.LEC) {
      HashMap<String, Integer> groups = c.getTutLabGroups();
      if (!groups.containsKey(groupName)) {
        throw new IllegalArgumentException("Oops, there is no group " + groupName + " in course " + courseName + ".");
      }
    }

    ArrayList<String> studentsFound = new ArrayList<String>();
    if (courseType == CourseType.LEC) {
      HashSet<Record> recordList = RecordManager.getRecordList();
      int counterStudentList = 0;

      for (Record r : recordList) {
        String tempCourseName = c.getCourseName();
        String courseNameFromRecord = r.getCourse().getCourseName();

        if (tempCourseName.equals(courseNameFromRecord)) {
          studentsFound.add(r.getStudent().getName());
        }
      }

      int numberOfStudentsFound = studentsFound.size();

      if (numberOfStudentsFound == 0) {
        System.out.println("No students have registered to group " + groupName + " on course " + courseName + ".");
      } else if (numberOfStudentsFound == 1) {
        System.out.println("1 student has registered to course " + courseName + ".");
      } else {
        System.out.println(Integer.toString(numberOfStudentsFound) + " students have registered to course " + courseName + ".");
      }
    } else {
      HashSet<Record> recordList = RecordManager.getRecordList();
      int counterStudentList = 0;

      for (Record r : recordList) {
        String tempCourseName = c.getCourseName();
        String courseNameFromRecord = r.getCourse().getCourseName();
        String groupNameFromRecord = r.getGroupName();

        if (groupNameFromRecord.equals(groupName) && tempCourseName.equals(courseNameFromRecord)) {
          System.out.println(r.getStudent().getName());
          counterStudentList++;
        }
      }
      System.out.println("Total number of students: " + Integer.toString(counterStudentList));
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
          System.out.println(component + ", weightage: " + info[WEIGHT]);
          printIndividualAssessment(r, component);
        } else {
          System.out.println(component + ", weightage: " + info[WEIGHT]);
        }
      }
    }
  }
}
