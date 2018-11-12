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
import java.util.ArrayList;
import java.util.Formatter;

import scrame.entity.Course;
import scrame.exception.IllegalCourseTypeException;
import scrame.helper.CourseType;

public final class CourseManager {
  private static HashSet<Course> courseList = new HashSet<Course>();
  private static String fileName = "../data/courses.ser";

  /**
   * Add course into the courseList and insert it into textfile.
   * 
   * @param courseName corurse name
   * @param courseType type of course
   * @param tempVacancies vacancies
   * @param tempWeightageList weightagelist
   */
  public static void addCourse(String courseName, CourseType courseType,
      HashMap<String, Integer> tempVacancies, HashMap<String, String[]> tempWeightageList) {

    if (isCourseInList(courseName)) {
      System.out.println(courseName + " has been registered.");
      return;
    }

    courseList.add(new Course(courseName, courseType, tempVacancies, tempWeightageList));   
    System.out.println("Course " + courseName + " added successfully!");
  }

  /**
   * Helper function to convert course type to enum.
   * 
   * @param tempCourseType string of course type
   * @return CourseType object
   * @throws IllegalCourseTypeException
   */
  public static CourseType courseTypeToEnum(String tempCourseType)
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
   * Get course object on course name.
   * 
   * @param courseName course name
   * @return course object
   * @throws IllegalArgumentException
   */
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
}
