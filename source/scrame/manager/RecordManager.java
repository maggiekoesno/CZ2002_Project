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
import java.util.Arrays;

import scrame.entity.Course;
import scrame.entity.Record;
import scrame.entity.Student;

import scrame.exception.*;

import scrame.manager.CourseManager;
import scrame.manager.StudentManager;

import scrame.helper.CourseType;

public final class RecordManager {
  private static HashSet<Record> recordList = new HashSet<Record>();
  private static String fileName = "../data/records.ser";

  /**
   * Register student on a course and store in recordList. Applicable for course of
   * type LEC.
   * 
   * @param matric matric number
   * @param courseName course name
   */
  public static void registerStudentCourse(String matric, String courseName)
      throws DuplicateRecordException {
    Scanner sc = new Scanner(System.in);

    try {
      Student studentFound = StudentManager.findStudent(matric);
      Course courseFound = CourseManager.findCourse(courseName);

      if (courseFound.getCourseType() == CourseType.LEC) {
        RecordManager.printStudentList(courseName);
      } else {
        System.out.println("The list of groups: ");
        courseFound.printAllGroups();

        System.out.print("\nEnter group name: ");
        String groupName = sc.next();

        RecordManager.printStudentList(courseName, groupName);
      }

      if (!validateRegisterStudentCourse(matric, courseName)) {
        return;
      }

      for (Record r : RecordManager.getRecordList()) {
        String tempCourseName = r.getCourse().getCourseName();
        String tempStudentName = r.getStudent().getName();
        String studentName = studentFound.getName();

        if (tempStudentName.equals(studentName) &&
            tempCourseName.equals(courseName)) {
          throw new DuplicateRecordException(studentName, courseName);
        }
      }

      courseFound.register();
      String studentName = studentFound.getName();
      System.out.println(
        studentName + " is succesfully registered on course " + courseName + "!"
      );

      recordList.add(new Record(
        studentFound, courseFound, "_LEC", new HashMap<String, Float>()
      ));
    } catch (IllegalCourseTypeException e) {
      e.printStackTrace();
    } catch (LectureFullException e) {
      e.printStackTrace();
    } catch (StudentNotFoundException e) {
      System.out.println(e.getMessage());
    } catch (CourseNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Register student on a course and store in recordList. Applicable for course of
   * type TUT or LAB.
   * 
   * @param matric matric number
   * @param courseName course name
   * @param groupName group name
   */
  public static void registerStudentCourse(String matric, String courseName, String groupName) {
    try {
      Student studentFound = StudentManager.findStudent(matric);
      Course courseFound = CourseManager.findCourse(courseName);
      
      if (!validateRegisterStudentCourse(matric, courseName)) {
        return;
      }

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

  public static boolean validateRegisterStudentCourse(String matric, String courseName) {
    for (Record r: recordList) {
      if (r.getStudent().getMatric().equals(matric) &&
          r.getCourse().getCourseName().equals(courseName)) {
        System.out.println("The student is registered already to this course!");
        return false;
      }
    }
    return true;
  }

  /**
   * Print student list for a given course name for courses of type LEC.
   * 
   * @param courseName course name
   */
  public static void printStudentList(String courseName) {
    int counterStudentList = 0;

    for (Record r : recordList) {
      if (r.getCourse().getCourseName().equals(courseName)) {
        System.out.println(r.getStudent().getName());
        counterStudentList++;
      }
    }

    System.out.println("Total number of students: " + Integer.toString(counterStudentList));
  }

  /**
   * Print student list for a given course name for courses of type TUT or LAB.
   * 
   * @param courseName course name
   * @param groupName group name
   */
  public static void printStudentList(String courseName, String groupName) {
    HashSet<Record> recordList = RecordManager.getRecordList();
    int counterStudentList = 0;

    for (Record r : recordList) {
      if (r.getGroupName().equals(groupName) && r.getCourse().getCourseName().equals(courseName)) {
        System.out.println(r.getStudent().getName());
        counterStudentList++;
      }
    }

    System.out.println(
      "Total number of students in " + groupName + " is: " +
      Integer.toString(counterStudentList));
  }

  /**
   * Print course statistics based on course name.
   * 
   * @param courseName course name
   */
  public static void printCourseStatistics(String courseName) {
    int n = 0;
    float sum = 0;
    float mean = 0;
    double std = 0;
    double sumSquareDiff = 0;

    int markCount = 0;

    try {
      Course courseFound = CourseManager.findCourse(courseName);
      if (courseFound == null) {
        System.out.println("Course is not registered !");
        return;
      }
      Map<String, String[]> tmpWeightage = courseFound.getWeightage();
      for (Map.Entry<String, String[]> entry : tmpWeightage.entrySet()) {
        if (entry.getValue()[1].equals("false")) {
          markCount++;
        }
      }
      
      for (Record r1: recordList) {
        if (r1.getCourse().getCourseName().equals(courseName)) {
          Map<String,Float> mark = r1.getMark();
          // System.out.println(mark.size());
          // System.out.println(r1.hasMark());
          if ( !r1.hasMark() || mark.size() < markCount) {
            System.out.println("Oops. there is a student that is not marked.");
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

      for (Record r : recordList) {
        if (r.getCourse().getCourseName().equals(courseName)) {
          sumSquareDiff += Math.pow((r.calculateAverage() - mean), 2);
        }
      }
      mean = sum / n;
      
      float[] studentScore = new float[n];

      System.out.println(
        "There are " + n + " students registered in this course."
      );
      System.out.println("***********************************************");
      System.out.println("Overall Score Average : " + mean);

      std = Math.sqrt(sumSquareDiff / n);
      System.out.println("Standard Deviation :" + std);

      int i = 0;
      for (Record record2: recordList) {
        if(record2.getCourse().getCourseName().equals(courseName)) {
          studentScore[i] = record2.calculateAverage();
          i++;
        }
      }

      Arrays.sort(studentScore);
      int[] borderValueIndex = new int[3];
      borderValueIndex[0] = (int)1/4*(n+1);
      borderValueIndex[1] = (int)2/4*(n+1);
      borderValueIndex[2] = (int)3/4*(n+1);
      System.out.println("1st Quartile (25%) : " + studentScore[borderValueIndex[0]]);
      System.out.println("2nd Quartile (50%) : " + studentScore[borderValueIndex[1]]);
      System.out.println("3rd Quartile (75%) : " + studentScore[borderValueIndex[2]]);
      System.out.println("***********************************************");

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
      System.out.println("***********************************************");
      System.out.println("Exam Average :" + meanExam);
      System.out.println("Exam Std     :" + stdExam);
      System.out.println("***********************************************");

      double sumOther = 0;
      double meanOther = 0;
      double sumSquareDiffOther = 0;
      double stdOther = 0;
      double m = 0;

      for (Record r5 : recordList) {
        if (r5.getCourse().getCourseName().equals(courseName)) {
          HashMap<String,Float> tempOther = r5.getMark();
          for(Map.Entry<String, Float> entry : tempOther.entrySet()){
            if(!entry.getKey().equals("Exam")){
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
      System.out.println("Other Coursework Average :" + meanOther);
      System.out.println("Other Coursework std     :" + stdOther);
    } catch (CourseNotFoundException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Input record list to file.
   */
  public static void inputToFile() {
    try {
      ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream(fileName)
      );
      out.writeObject(recordList);
      out.close();
      System.out.println("Serialized data is saved in" + fileName);
    } catch (IOException e) {
      e.printStackTrace();
    } catch (Exception e) {
      // e.printStackTrace();
    }
  }

  /**
   * Load record list from file.
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
