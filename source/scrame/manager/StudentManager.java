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

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Collections;

import scrame.entity.Course;
import scrame.entity.Record;
import scrame.entity.Student;

import scrame.exception.IllegalCourseTypeException;
import scrame.exception.IllegalStudentArgumentException;
import scrame.exception.StudentNotFoundException;

import scrame.helper.CourseType;
import scrame.helper.SortRecordByCourseName;

import scrame.manager.CourseManager;
import scrame.manager.RecordManager;

/**
 * Static class that manages the student list and perform related actions.
 */
public final class StudentManager {
  /**
   * The student list is stored in a hashset.
   */
  private static HashSet<Student> studentList = new HashSet<Student>();
  /**
   * The path to where the serialized form of the data is stored.
   */
  private static String fileName = "../data/students.ser";
  
  /**
   * Constants for the String array inside weightage.
   */
  private static final int WEIGHT = 0;
  private static final int HAS_CHILD = 1;
  private static final int PARENT = 2;

  /**
   * Adds student with the given information.
   * 
   * @param name                              student's name
   * @param major                             student's major
   * @param enroll                            student's enrollment semester
   * @param matric                            student's matric number
   * @throws IllegalStudentArgumentException  if there is another student registered
   *                                          under the same matric number
   */
  public static void addStudent(String name, String major, String enroll, String matric)
      throws IllegalStudentArgumentException {
    if (isStudentInList(matric)) {
      System.out.println("Cannot add student with existing matric number.");
      return;
    }

    studentList.add(new Student(name, major, enroll, matric));
  }

  /**
   * Prints student transcript on student's matric number.
   * 
   * @param matric matriculation number
   */
  public static void printTranscript(String matric) {
    boolean studentFound = false;
    HashSet<Record> recordList = RecordManager.getRecordList();
    ArrayList<Record> recordFound = new ArrayList<Record>();

    if (!isStudentInList(matric)) {
      System.out.println("Holy guacamole, invalid matric number!");
      return;
    }

    for (Record r : recordList) {
      if (r.getStudent().getMatric().equals(matric)) {
        recordFound.add(r);
        studentFound = true;
      }
    }

    try {
      if (!studentFound) {
        System.out.println(
          "Oops, " + findStudent(matric).getName() + " hasn't registered to any course."
        );
        return;
      }

      for (Record r : recordFound) {
        if (!r.hasMark()) {
          System.out.println(
            "Oops, " + r.getStudent().getName() + " hasn't been marked on " +
            r.getCourse().getCourseName() + "!");
          return;
        }
      }
      
      System.out.println();
      System.out.println("+--------------------------------------------+");
      System.out.println("|             Student Transcript             |");
      System.out.println("+--------------------------------------------+");
  
      Collections.sort(recordFound, new SortRecordByCourseName());
  
      for (Record r : recordFound) {  
        System.out.print("|  Course name                : " + r.getCourse().getCourseName());
        System.out.println("       |");
        System.out.print("|  Course average             : ");
        System.out.printf("%-10.4f", r.calculateAverage());
        System.out.println("   |");
  
        System.out.println("|                                            |");
        for (Map.Entry<String, Float> entry : r.getMark().entrySet()) {
          String component = entry.getKey();
          float mark = entry.getValue();
          System.out.printf("%-23s", "|  Your " + component + " mark");
          System.out.print("       : ");
          System.out.printf("%-10.4f", mark);
          System.out.println("   |");
        }
  
        System.out.println("|                                            |");
        printIndividualAssessment(r);
        System.out.println("+--------------------------------------------+");
      }
    } catch (StudentNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Prints weightage as pairs of component and mark.
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
        System.out.printf("%-27s", "|  Weightage for " + component);
        System.out.print("   : " + info[WEIGHT]);
        System.out.println("          |");
        if (info[HAS_CHILD].equals("true")) {  
          printIndividualAssessment(r, component);
        }
      }
    }
  }

  /** 
   * Checks if a student is registered.
   * 
   * @param matric matric ID of the student
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

  /**
   * Returns student with a given matric number.
   * 
   * @param matric                    matric number
   * @return                          student object
   * @throws StudentNotFoundException if there are no students found with the supplied matric
   *                                  number
   */
  public static Student findStudent(String matric) throws StudentNotFoundException {
    if (!isStudentInList(matric)) {
      throw new StudentNotFoundException();
    }

    for (Student s : studentList) {
      if (s.getMatric().equals(matric)) {
        return s;
      }
    }

    return null;
  }

  /**
   * Getter method for student list.
   * 
   * @return student list
   */
  public static HashSet<Student> getStudentList() {
    return studentList;
  }

  public static void validateMatric(String matric) throws IllegalStudentArgumentException {
    if (!matric.matches("[gGnNuU][1][0-8]\\d{5}\\D")) {
      throw new IllegalStudentArgumentException(
        "Oops, you have entered an invalid matric number."
      );
    }
  }

  /**
   * Inputs student list to file.
   */
  public static void inputToFile() {
    try {
      ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream(fileName)
      );
      out.writeObject(studentList);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads student list from file.
   */
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
}
