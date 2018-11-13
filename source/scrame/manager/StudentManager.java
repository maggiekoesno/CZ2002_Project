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
import scrame.exception.IllegalCourseTypeException;
import scrame.exception.IllegalStudentArgumentException;
import scrame.exception.StudentNotFoundException;
import scrame.helper.CourseType;

import scrame.manager.CourseManager;
import scrame.manager.RecordManager;

public final class StudentManager {
  private static HashSet<Student> studentList = new HashSet<Student>();
  private static String fileName = "../data/students.ser";
  private static final int WEIGHT = 0;
  private static final int HAS_CHILD = 1;
  private static final int PARENT = 2;

  /**
   * Add student with the given information.
   * 
   * @param name student's name
   * @param major student's major
   * @param enroll student's enrollment semester
   * @param matric student's matric number
   */
  public static void addStudent(String name, String major, String enroll, String matric)
      throws IllegalStudentArgumentException {
    if (isStudentInList(matric)) {
      System.out.println("Cannot add student with existing matric number.");
      return;
    }

    studentList.add(new Student(name, major, enroll, matric));
    System.out.println("Student named " + name + " added successfully.");
  }

  /**
   * Print student transcript on student's matric number.
   * 
   * @param matric matriculation number
   */
  public static void printTranscript(String matric) {
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
      if (!r.hasMark()) {
        System.out.println(
          "Oops. " + r.getStudent().getName() + " haven't been marked on " +
          r.getCourse().getCourseName() + "!");
        return;
      }
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

  /** 
   * Check if a student is registered.
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
   * Return student with a given matric number.
   * 
   * @param matric matric number
   * @return student object
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
   * Input student list to file.
   */
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

  /**
   * Load student list from file.
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
