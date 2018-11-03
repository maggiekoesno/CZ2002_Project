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

// import org.apache.commons.math3;

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
  // The name of the file to open.

  public static void registerStudentCourse() {
    Scanner sc = new Scanner(System.in);
    
    System.out.print("Please input matric number: ");
    String matric = sc.nextLine();
    Student studentFound = StudentManager.findStudent(matric);
    
    if (studentFound == null) {
      System.out.println("Oops, student is not registered yet! Please register first.");
      return;
    }

    System.out.print("Please input course name: ");
    String courseName = sc.nextLine();
    
    if (!CourseManager.isCourseInList(courseName)) {
      System.out.println("Oops, course is not registered to the system yet.");
      return;
    }

    Course courseFound = CourseManager.findCourse(courseName);

    // for (Record r : recordList) {
    //   if(r.getStudent().getMatric.equals(s.toUpperCase())
    //     && r.getCourse().getCourseName().equals(cn)
    //   ){
    //     Syste
    //     return;
    //   }
    // }
    
    String groupName = null;

    if (courseFound.getCourseType() == CourseType.LEC) {
      groupName = "_LEC";
      try {
        courseFound.register();
      } catch (IllegalCourseTypeException e) {
        e.printStackTrace();
      } catch (LectureFullException e) {
        e.printStackTrace();
      }
    } else {
      try {
        courseFound.printAllGroups();
        System.out.println("Which group do you want to register into? ");
        groupName = sc.nextLine();
        courseFound.register(groupName);
        System.out.println("Succesfully registered!!");
      } catch (IllegalCourseTypeException e) {
        e.printStackTrace();
      } catch (GroupFullException e) {
        e.printStackTrace();
      }
    }
    HashMap<String, Float> mark = null;
    Record r = new Record(studentFound, courseFound, groupName, mark);
    recordList.add(r);
  }

  public static void registerStudentCourse(String matric, String courseName) {
    if (!StudentManager.isStudentInList(matric)) {
      System.out.println("Oops, student is not registered yet!");
      return;
    }

    if (!CourseManager.isCourseInList(courseName)) {
      System.out.println("Oops, course is not registered yet.");
      return;
    }

    Student studentFound = StudentManager.findStudent(matric);
    Course courseFound = CourseManager.findCourse(courseName);
    
    try {
      for (Record r : RecordManager.getRecordList()) {
        String tempCourseName = r.getCourse().getCourseName();
        String tempStudentName = r.getStudent().getName();
        String studentName = studentFound.getName();

        if (tempStudentName.equals(studentName) && tempCourseName.equals(courseName)) {
          throw new IllegalArgumentException(
            studentName + " is already registered on course " + courseName + "."
          );
        }
      }

      courseFound.register();
      String studentName = studentFound.getName();
      System.out.println(studentName + " is succesfully registered on course " + courseName + "!");
    } catch (IllegalCourseTypeException e) {
      e.printStackTrace();
    } catch (LectureFullException e) {
      e.printStackTrace();
    }

    HashMap<String, Float> mark = null;
    Record r = new Record(studentFound, courseFound, "_LEC", mark);
    recordList.add(r);
  }

  public static void registerStudentCourse(String matric, String courseName, String groupName) {
    if (!StudentManager.isStudentInList(matric)) {
      System.out.println("Oops, student is not registered yet!");
      return;
    }

    if (!CourseManager.isCourseInList(courseName)) {
      System.out.println("Oops, course is not registered yet.");
      return;
    }

    Student studentFound = StudentManager.findStudent(matric);
    Course courseFound = CourseManager.findCourse(courseName);

    try {
      courseFound.register(groupName);
      String studentName = studentFound.getName();
      System.out.println(studentName + " is succesfully registered on group " + groupName + " on course " + courseName + "!");
    } catch (IllegalCourseTypeException e) {
      System.out.println(e.getMessage());
    } catch (GroupFullException e) {
      e.printStackTrace();
    }

    HashMap<String, Float> mark = null;
    Record r = new Record(studentFound, courseFound, groupName, mark);
    recordList.add(r);
  }

  public static void setCourseworkMark() {
    Scanner sc = new Scanner(System.in);
    boolean check = false;
    String matric;
    Map<String, Float> mark;
    Map<String, String[]> weightage;
    float ans;

    System.out.print("Enter student matriculation ID: ");
    matric = sc.nextLine();
    while (!StudentManager.isStudentInList(matric)) {
      System.out.print("Matriculation ID doesn't exist! Try again: ");
      matric = sc.nextLine();
    }

    System.out.print("Enter course name: ");
    String courseName = sc.nextLine();
    while (CourseManager.isCourseInList(courseName)) {
      System.out.print("Course doesn't exist! Try again: ");
      courseName = sc.nextLine();
    }

    for (Record r : recordList) {
      if (r.getStudent().getMatric().equals(matric) && r.getCourse().getCourseName() == courseName) {
        check = true;
        mark = r.getMark();
        if (mark == null) {
          mark = new HashMap<String, Float>();
        }
        weightage = r.getCourse().getWeightage();
        for (Map.Entry<String, String[]> entry : weightage.entrySet()) {
          System.out.println(entry.getKey() + " = " + entry.getValue());
          
          if (entry.getValue()[1].equals("false") && !(entry.getKey().toLowerCase().equals("exam"))) {
            System.out.print("Do you want to enter mark for " + entry.getKey() + "? (y(1)/n(0)) ");
            ans = sc.nextInt();
            if (ans == 1) {
              System.out.print("Enter mark for " + entry.getKey());
              ans = sc.nextFloat();
              mark.put(entry.getKey(),ans);
            }
          }
        }
        r.setMark(mark);
        break;
      }
    }
    if (check == false) {
      System.out.println("Student is not taking that course!");
    }

    sc.close();
  }

  public static void setExamMark() {
    Scanner sc = new Scanner(System.in);
    boolean check = false;
    String matric;
    Map<String, Float> mark;
    float ans;

    System.out.print("Enter student matriculation ID: ");
    matric = sc.nextLine();
    while (!StudentManager.isStudentInList(matric)) {
      System.out.print("Matriculation ID doesn't exist! Try again: ");
      matric = sc.nextLine();
    }

    System.out.print("Enter course name: ");
    String courseName = sc.nextLine();
    while (CourseManager.isCourseInList(courseName)) {
      System.out.print("Course doesn't exist! Try again: ");
      courseName = sc.nextLine();
    }

    for (Record r : recordList) {
      if (r.getStudent().getMatric().equals(matric) &&
          r.getCourse().getCourseName() == courseName) {
        check = true;
        mark = r.getMark();
        if (mark == null) {
          mark = new HashMap<String, Float>();
        }
        System.out.print("Enter mark for exam: ");
        ans = sc.nextFloat();
        mark.put("Exam", ans);
        r.setMark(mark);
        break;
      }
    }
    if (check == false) {
      System.out.println("Student is not taking that course!");
    }

    sc.close();
  }

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

  public static HashSet<Record> getRecordList() {
    return recordList;
  }

  public static void printCourseStatistics() {
    Scanner sc = new Scanner(System.in);
    int n = 0;
    float sum = 0;
    float mean = 0;
    float std = 0;
    float sumSquareDiff = 0;

    System.out.println("Input the course name for statistics: ");
    String courseName = sc.nextLine();

    while (!CourseManager.isCourseInList(courseName)) {
      System.out.print("The course is not registered. Please try again");
      courseName = sc.nextLine();
    }

    int markCount = 0;

    Course courseFound = CourseManager.findCourse(courseName);
    markCount = courseFound.getWeightage().size();
    
    for (Record r: recordList) {
      if (r.getCourse().getCourseName() == courseName) {
        Map<String,Float> mark = r.getMark();
        if (mark.size() < markCount) {
          System.out.println("Whoops. the course hasnt been finished yet, there is a student who is not marked.");
          System.out.println("Student name: "+ r.getStudent().getName() + " with matric :" + r.getStudent().getMatric());
          return;
        }
      }
    }

    for (Record r : recordList) {
      if (r.getCourse().getCourseName() == courseName) {
        sum += r.calculateAverage();
        n++;
      }
    }
    for (Record r : recordList) {
      if (r.getCourse().getCourseName() == courseName) {
        sumSquareDiff += Math.pow((r.calculateAverage() - mean), 2);
      }
    }
    mean = sum / n;
    

    System.out.println(
      "There are " + n + " students registered in this course."
    );
    System.out.println("Average : " + mean);
    
    // std = Math.sqrt(sumSquareDiff / n);
    // System.out.println("Standard Deviation :" + std);

    // NormalDistribution distribution = new NormalDistribution(mean, std);

    // float[] percentile = new float[] { 0.25f, 0.5f, 0.75f };
    // float value;
    // float[] borderValue = new float[3];
    // int i = 0;
    // for(i = 0;i<3;i++) {
    //   value = distribution.inverseCumulativeProbability(percentile[i]);
    //   borderValue[i] = value*std + mean;
    // }
    // System.out.println("1st Quartile : " + borderValue[0]);
    // System.out.println("2nd Quartile : " + borderValue[1]);
    // System.out.println("3rd Quartile : " + borderValue[2]);

  }
}
