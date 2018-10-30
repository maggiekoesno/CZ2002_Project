package scrame.manager;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.math3.distribution.NormalDistribution;

import scrame.entity.Course;
import scrame.entity.Record;
import scrame.entity.Student;
import scrame.exception.GroupFullException;
import scrame.exception.IllegalCourseTypeException;
import scrame.manager.CourseManager;
import scrame.manager.StudentManager;

public final class RecordManager {
  private static HashSet<Record> recordList = new HashSet<Record>();
  private static String fileName = "data/records.ser";
  // The name of the file to open.

  public static void registerStudentCourse() {
    Scanner sc = new Scanner(System.in);

    System.out.print("Please input matric number: ");
    String s = sc.next();
    //check apakah student sudah keregister di Hashmap , ini cukup aneh but let it be
    StudentManager sManager = new StudentManager();
    sManager.loadFromTextFile();
    if (!sManager.isStudentInList(s)) {
      System.out.println(
        "Oops, student is not registered yet! Please register first."
      );
      return;
    }
    System.out.print("Please input Course ID: ");
    int c = sc.nextInt();
    CourseManager cManager = new CourseManager();
    cManager.loadFromTextFile();
    if (!cManager.isCourseInList(c)) {
      System.out.println("Course is not registered, Add the course first.");
      return;
    }
    //display all the groups of specific courseid
    Course courseFound = cManager.getCourse(c);
    courseFound.printAllGroups();

    System.out.println("Which group do you want to register in ?");
    String group = sc.next();
    //register

    try {
      courseFound.register(group);
      System.out.println("Succesfully registered!!");
    } catch(IllegalCourseTypeException e) {
      e.printStackTrace();
    } catch(GroupFullException e) {
      e.printStackTrace();
    }
  }

  public static void setCourseworkMark() {
    Scanner sc = new Scanner(system.in);
    boolean check = false;
    String matric;
    int id;
    HashMap<String, Float> mark;
    int ans;

    System.out.print("Enter student matriculation ID: ");
    matric = sc.nextLine;
    while (StudentManager.isStudentInList(matric) == false) {
      System.out.print("Matriculation ID doesn't exist! Try again: ");
      matric = sc.nextLine;
    }
    System.out.print("Enter course ID: ");
    id = sc.nextInt();
    while (CourseManager.getCourse(id) == null) {
      System.out.print("Course doesn't exist! Try again: ");
      id = sc.nextInt();
    }
    for (Record r : recordList) {
      if (r.getStudent().getMatric().equals(matric) && r.getCourse(

      ).getCourseId() == id) {
        check = true;
        mark = r.getMark();
        for (Map.Entry<String, Float> entry : mark.entrySet()) {
          System.out.println(entry.getKey() + " = " + entry.getValue());
          if (entry.get)
          System.out.print(
            "Do you want to enter mark for " + entry.getKey() + "? (y(1)/n(0)) "
          );
          ans = sc.nextInt();
          if (ans == 1) {
            System.out.print("Enter mark for " + entry.getKey());
            entry.setValue(sc.nextFloat());
          }
        }
        r.setMark(mark);
        break;
      }
    }
    if (check == false)
    System.out.println("Student is not taking that course!");
  }

  public static void setExamMark() {}

  public static void inputToFile(HashSet<Record> recordList) {
    try {
      ObjectOutputStream out = new ObjectOutputStream(
        new FileOutputStream(fileName)
      );
      out.writeObject(recordList);
      out.close();
      System.out.printf("Serialized data is saved in" + fileName);
    } catch(IOException i) {
      i.printStackTrace();
    }
  }

  public static void loadFromFile() {
    try {
      ObjectInputStream in = new ObjectInputStream(
        new FileInputStream(fileName)
      );
      recordList = (HashSet<Record>) in.readObject();
      in.close();
    } catch(IOException e) {
      e.printStackTrace();
      return;
    } catch(ClassNotFoundException e) {
      System.out.println("Hashset<Record> class not found");
      e.printStackTrace();
      return;
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
    System.out.println("Input the course id for statistics: ");

    while (!CourseManager.isCourseInList(courseId)) {
      System.out.print("The course is not registered. Please try again");
      courseId = sc.nextInt();
    }
    for (Record record : recordList) {
      if (record.getCourse().getCourseId() == courseId) {
        sum += record.calculateAverage();
        n++;
      }
    }
    for (Record record : recordList) {
      if (record.getCourse().getCourseId() == courseId) {
        sumSquareDiff += Math.pow((record.calculateAverage() - mean), 2);
      }
    }
    mean = sum / n;
    std = Math.sqrt(sumSquareDiff / n);

    System.out.println(
      "There are " + n + " students registered in this course."
    );
    System.out.println("Average : " + mean);
    System.out.println("Standard Deviation :" + std);

    NormalDistribution distribution = new NormalDistribution(mean, std);

    float[] percentile = new float[] { 0.25f, 0.5f, 0.75f };
    float value = distribution.inverseCumulativeProbability(
      riskProbabilityLevel
    );
    //TODO std * value + mean
  }

}

