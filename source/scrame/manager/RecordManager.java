package scrame.manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import java.io.IOException;
import java.io.EOFException;

import java.io.Serializable;

import java.util.Scanner;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import scrame.entity.Course;
import scrame.entity.Record;
import scrame.entity.Student;

import scrame.exception.*;

import scrame.manager.CourseManager;
import scrame.manager.StudentManager;

import scrame.helper.CourseType;

/**
 * Static class that manages records list and perform related actions.
 */
public final class RecordManager {
  
  /**
   * The record list is stored as a hashset.
   */
  private static HashSet<Record> recordList = new HashSet<Record>();

  /**
   * The path to where the serialzed form of the data to be stored.
   */
  private static String fileName = "../data/records.ser";

  private static final int HAS_CHILD = 1;

  /**
   * Registers student on a course and store in recordList. Applicable for course of
   * type LEC.
   * 
   * @param matric                    matric number
   * @param courseName                course name
   * @throws DuplicateRecordException if the record already exists in record list
   */
  public static void registerStudentCourse(String matric, String courseName)
      throws DuplicateRecordException {
    Scanner sc = new Scanner(System.in);

    try {
      Student studentFound = StudentManager.findStudent(matric);
      Course courseFound = CourseManager.findCourse(courseName);

      validateRegisterStudentCourse(matric, courseName);
      
      courseFound.register();
      recordList.add(new Record(
        studentFound, courseFound, "_LEC", new HashMap<String, Float>()
      ));

      String studentName = studentFound.getName();
      System.out.println(
        studentName + " is succesfully registered on course " + courseName + "!"
      );
    } catch (IllegalCourseTypeException e) {
      System.out.println(e.getMessage());
    } catch (LectureFullException e) {
      System.out.println(e.getMessage());
    } catch (StudentNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (CourseNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Registers student on a course and store in recordList. Applicable for course of
   * type TUT or LAB.
   * 
   * @param matric                    matric number
   * @param courseName                course name
   * @param groupName                 group name
   * @throws DuplicateRecordException if record already exists in record list
   */
  public static void registerStudentCourse(String matric, String courseName, String groupName) 
      throws DuplicateRecordException {
    try {
      Student studentFound = StudentManager.findStudent(matric);
      Course courseFound = CourseManager.findCourse(courseName);
      
      validateRegisterStudentCourse(matric, courseName);

      courseFound.register(groupName);
      String studentName = studentFound.getName();
      System.out.println(
        studentName + " is succesfully registered on group " + groupName +
        " on course " + courseName + "!"
      );

      HashMap<String, Float> mark = null;
      Record r = new Record(studentFound, courseFound, groupName, mark);
      recordList.add(r);
    } catch (IllegalCourseTypeException e) {
      System.out.println(e.getMessage());
    } catch (GroupFullException e) {
      System.out.println(e.getMessage());
    } catch (StudentNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (CourseNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (GroupNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Check if the student has registered on the course.
   * 
   * @param matric                    student's matric number
   * @param courseName                course name
   * @throws DuplicateRecordException if the student has registered on the course
   */
  public static void validateRegisterStudentCourse(String matric, String courseName) 
      throws DuplicateRecordException {
    for (Record r: recordList) {
      if (r.getStudent().getMatric().equals(matric) &&
          r.getCourse().getCourseName().equals(courseName)) {
        throw new DuplicateRecordException(r.getStudent().getName(), courseName);
      }
    }
  }

  /**
   * Checks if student is registered in a course, given the student's matric number and course name.
   * 
   * @param matric     student's matric number
   * @param courseName course name
   * @return true if student is registered on course, else false
   */
  public static boolean isStudentRegisteredOnCourse(String matric, String courseName) {
    for (Record r : recordList) {
      if (r.getStudent().getMatric().equals(matric) &&
          r.getCourse().getCourseName().equals(courseName)) {
        return true;
      }
    }

    return false;
  }

  /**
   * Prints student list for a given course name.
   * 
   * @param courseName  course name
   * @param byGroup     true if print by group, else print by lecture
   */
  public static void printStudentList(String courseName, boolean byGroup) {
    if (!byGroup) {
      printStudentListByLecture(courseName);
    } else {
      printStudentListByGroup(courseName);
    }
  }

  /**
   * Prints student list by lecture, sorted by name in alphabetical order.
   * 
   * @param courseName course name
   */
  private static void printStudentListByLecture(String courseName) {
    ArrayList<String> tempStudentNames = new ArrayList<String>();

    for (Record r : recordList) {
      if (r.getCourse().getCourseName().equals(courseName)) {
        tempStudentNames.add(r.getStudent().getName());
      }
    }

    System.out.println();
    System.out.println("+----------------------------------+");
    System.out.println("|  Students Registered by Lecture  |");
    System.out.println("+----------------------------------+");

    Collections.sort(tempStudentNames);

    for (String s : tempStudentNames) {
      System.out.print("|  ");
      System.out.printf("%-30s", s);
      System.out.println("  |");
    }

    System.out.println("+----------------------------------+");
  }

  /**
   * Prints student list by group. Sorted by student name in alphabetical order.
   * 
   * @param courseName course name
   */
  private static void printStudentListByGroup(String courseName) {
    try {
      ArrayList<String> groupNames = CourseManager.findCourse(courseName).getGroupNames();
      ArrayList<String> tempStudentNames = new ArrayList<String>();

      while (!groupNames.isEmpty()) {
        String groupName = groupNames.remove(0);

        for (Record r : recordList) {
          if (r.getCourse().getCourseName().equals(courseName) &&
              r.getGroupName().equals(groupName)) {
            tempStudentNames.add(r.getStudent().getName());
          }
        }

        System.out.println();
        System.out.println("+----------------------------------+");
        System.out.println("|   Students Registered by Group   |");
        System.out.println("+--------+-------------------------+");

        boolean firstNameInCourse = true;
        Collections.sort(tempStudentNames);

        for (String s : tempStudentNames) {
          if (firstNameInCourse) {
            System.out.print("|  " + groupName + "  |  ");
            firstNameInCourse = false;
          } else {
            System.out.print("|        |  ");
          }

          System.out.printf("%-21s", s);
          System.out.println("  |");
        }

        System.out.println("+--------+-------------------------+");
        tempStudentNames.clear();
      }
    } catch (CourseNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Prints course statistics based on course name.
   * 
   * @param course                        course object
   * @throws NoRegisteredStudentException if there are no students registered to the course
   */
  public static void printCourseStatistics(Course course)
      throws NoRegisteredStudentException {
    int n = 0;
    float sum = 0;
    float mean = 0;
    double sumSquareDiff = 0;

    int markCount = 0;

    Map<String, String[]> tmpWeightage = course.getWeightage();
    String courseName = course.getCourseName();

    for (Map.Entry<String, String[]> entry : tmpWeightage.entrySet()) {
      boolean hasChild = entry.getValue()[HAS_CHILD].equals("false") ? false : true;
      if (!hasChild) {
        markCount++;
      }
    }
    
    if (course.getLectureTotalSize() - course.getLectureVacancy() == 0) {
      throw new NoRegisteredStudentException(courseName);
    }

    for (Record r1 : recordList) {
      if (r1.getCourse().getCourseName().equals(courseName)) {
        Map<String, Float> mark = r1.getMark();
        if (!r1.hasMark() || mark.size() < markCount) {
          System.out.println("Oops, there is a student that is not marked.");
          return;
        }
      }
    }

    for (Record r2 : recordList) {
      if (r2.getCourse().getCourseName().equals(courseName)) {
        sum += r2.calculateAverage();
        n++;
      }
    }

    mean = sum / n;

    for (Record r : recordList) {
      if (r.getCourse().getCourseName().equals(courseName)) {
        sumSquareDiff += Math.pow((r.calculateAverage() - mean), 2);
      }
    }
    
    float[] studentScore = new float[n];

    System.out.println();
    System.out.println("+----------------------------------------------------+");
    System.out.println("|  There are " + n + " students registered in this course.  |");
    System.out.println("|                                                    |");
    
    System.out.print("|  Overall score average         : ");
    System.out.printf("%-10.4f", mean);
    System.out.println("        |");

    System.out.print("|  Standard deviation            : ");
    System.out.printf("%-10.4f", (float) (Math.sqrt(sumSquareDiff / n)));
    System.out.println("        |");

    System.out.println("|                                                    |");

    int i = 0;
    for (Record r2 : recordList) {
      if (r2.getCourse().getCourseName().equals(courseName)) {
        studentScore[i] = r2.calculateAverage();
        i++;
      }
    }

    Arrays.sort(studentScore);

    int[] borderValueIndex = new int[3];
    borderValueIndex[0] = Math.round((float) (0.25 * (n + 1)));
    borderValueIndex[1] = Math.round((float) (0.5 * (n + 1)));
    borderValueIndex[2] = Math.round((float) (0.75 * (n + 1)));

    System.out.print("|  1st quartile (25%)            : ");
    System.out.printf("%-10.4f", studentScore[borderValueIndex[0] - 1]);
    System.out.println("        |");

    System.out.print("|  2nd quartile (50%)            : ");
    System.out.printf("%-10.4f", studentScore[borderValueIndex[1] - 1]);
    System.out.println("        |");

    System.out.print("|  3rd quartile (75%)            : ");
    System.out.printf("%-10.4f", studentScore[borderValueIndex[2] - 1]);
    System.out.println("        |");

    double sumExam = 0;
    double meanExam = 0;
    double sumSquareDiffExam = 0;
    double stdExam = 0;

    for (Record r3 : recordList) {
      if (r3.getCourse().getCourseName().equals(courseName)) {
        sumExam = sumExam + r3.getMark().get("Exam");
      }
    }

    meanExam = sumExam / n;

    for (Record r4 : recordList) {
      if (r4.getCourse().getCourseName().equals(courseName)) {
        sumSquareDiffExam  += Math.pow((r4.getMark().get("Exam") - meanExam), 2);
      }
    }

    stdExam = Math.sqrt(sumSquareDiffExam / n);
    System.out.println("|                                                    |");
    System.out.print("|  Exam average                  : ");
    System.out.printf("%-10.4f", meanExam);
    System.out.println("        |");
    System.out.print("|  Exam standard deviation       : ");
    System.out.printf("%-10.4f", stdExam);
    System.out.println("        |");

    System.out.println("|                                                    |");

    double sumOther = 0;
    double meanOther = 0;
    double sumSquareDiffOther = 0;
    double stdOther = 0;
    double m = 0;

    for (Record r5 : recordList) {
      if (r5.getCourse().getCourseName().equals(courseName)) {
        HashMap<String,Float> tempOther = r5.getMark();
        for(Map.Entry<String, Float> entry : tempOther.entrySet()) {
          if(!entry.getKey().equals("Exam")) {
            sumOther = sumOther + entry.getValue();
            m++;
          }
        }
      }
    }

    meanOther = sumOther / m;
    for (Record r5 : recordList) {
      if (r5.getCourse().getCourseName().equals(courseName)) {
        HashMap<String,Float> tempOther = r5.getMark();
        for (Map.Entry<String, Float> entry : tempOther.entrySet()) {
          if (!entry.getKey().equals("Exam")) {
            sumSquareDiffOther  += Math.pow((entry.getValue() - meanOther), 2);
          }
        }
      }
    }

    stdOther = Math.sqrt(sumSquareDiffOther / m);
    System.out.print("|  Coursework average            : ");
    System.out.printf("%-10.4f", meanOther);
    System.out.println("        |");
    System.out.print("|  Coursework standard deviation : ");
    System.out.printf("%-10.4f", stdOther);
    System.out.println("        |");
    System.out.println("+----------------------------------------------------+");
  }

  /**
   * Inputs record list to file.
   */
  public static void inputToFile() {
    try {
      ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream(fileName)
      );
      out.writeObject(recordList);
      out.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Loads record list from file.
   */
  public static void loadFromFile() {
    try {
      ObjectInputStream in = new ObjectInputStream(
        new FileInputStream(fileName)
      );
      recordList = (HashSet<Record>) in.readObject();
      in.close();
    } catch (EOFException e) {
      recordList = new HashSet<Record>();
    } catch(IOException e) {
      e.printStackTrace();
    } catch(ClassNotFoundException e) {
      System.out.println("Hashset<Record> class not found");
      e.printStackTrace();
    }
  }

  /**
   * Getter method for record list.
   * 
   * @return record list
   */
  public static HashSet<Record> getRecordList() {
    return recordList;
  }
}
