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
import scrame.entity.FacultyMember;

import scrame.exception.*;

import scrame.helper.CourseType;

/**
 * Static class that manage the individual course and all courses.
 */
public final class CourseManager {

  /**
   * The list of courses is stored in a HashSet.
   */
  private static HashSet<Course> courseList = new HashSet<Course>();

  /**
   * The path to where the serialized form of the data to be stored.
   */
  private static String fileName = "../data/courses.ser";

  /**
   * Adds course into the courseList and insert it into textfile.
   * 
   * @param courseName                  course name
   * @param courseType                  course type
   * @param tempVacancies               temporary vacancies
   * @param tempWeightageList           temporary weightage list
   * @param coordinator                 course coordinator
   * @throws DuplicateCourseException   if course with the same name already exists
   * @throws IllegalWeightageException  if weightage is not in proper format
   * @throws IllegalVacancyException    if vacancy is not in proper format
   */
  public static void addCourse(String courseName, CourseType courseType,
      HashMap<String, Integer> tempVacancies, HashMap<String, String[]> tempWeightageList,
      FacultyMember coordinator)
      throws DuplicateCourseException, IllegalWeightageException, IllegalVacancyException {

    if (isCourseInList(courseName)) {
      throw new DuplicateCourseException(courseName);
    }

    courseList.add(
      new Course(courseName, courseType, tempVacancies, tempWeightageList, coordinator)
    );
  }

  /**
   * Converts course type string to enum.
   * 
   * @param tempCourseType              string of course type
   * @return                            CourseType object
   * @throws IllegalCourseTypeException if course type is not LEC, TUT or LAB
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
   * Checks whether a course is in the list.
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
   * Finds the course object based on course name.
   * 
   * @param courseName                course name
   * @return                          course object
   * @throws CourseNotFoundException  if no course found
   */
  public static Course findCourse(String courseName)
      throws CourseNotFoundException {
    if (!isCourseInList(courseName)) {
      throw new CourseNotFoundException(courseName);
    }

    for (Course c : courseList) {
      if (c.getCourseName().equals(courseName)) {
        return c;
      }
    }

    return null;
  }

  /**
   * Getter to the courselist hashset.
   * 
   * @return the hashset of the course list
   */
  public static HashSet<Course> getCourseList() {
    return courseList;
  }

  /**
   * Writes list of all courses to file.
   */
  public static void inputToFile() {
    try {
      ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream(fileName)
      );
      out.writeObject(courseList);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Reads the text file and insert it into course list.
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
