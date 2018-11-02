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
import scrame.exception.GroupFullException;
import scrame.exception.IllegalCourseTypeException;
import scrame.exception.LectureFullException;
import scrame.helper.CourseType;
import scrame.manager.CourseManager;
import scrame.manager.StudentManager;

public final class RecordManager {
  private static HashSet<Record> recordList = new HashSet<Record>();
  private static String fileName = "../data/records.ser";
  // The name of the file to open.

  public static void registerStudentCourse() {
    Scanner sc = new Scanner(System.in);
    System.out.print("Please input matric number: ");
    String s = sc.nextLine();
    //check apakah student sudah keregister di Hashmap , ini cukup aneh but let it be
    if (!StudentManager.isStudentInList(s)) {
      System.out.println(
        "Oops, student is not registered yet! Please register first."
      );
      return;
    }
    System.out.print("Please input Course Name: ");
    String cn = sc.nextLine();
    Course courseFound = CourseManager.getCourse(cn);

    if(courseFound == null){
      System.out.println("Course is not registered, Add the course first.");
      return;
    }
    
    if(courseFound.getCourseType() == CourseType.LEC){
      try{
        courseFound.register();
      } catch (IllegalCourseTypeException e){
        e.printStackTrace();
      } catch (LectureFullException e){
        e.printStackTrace();
      }
    }
    else{
      courseFound.printAllGroups();
      System.out.println("Which group do you want to register into? ");
      String group = sc.next();
      try {
        courseFound.register(group);
        System.out.println("Succesfully registered!!");
      } catch(IllegalCourseTypeException e) {
        e.printStackTrace();
      } catch(GroupFullException e) {
        e.printStackTrace();
      }
    }
  }

  public static void setCourseworkMark() {
    Scanner sc = new Scanner(System.in);
    boolean check = false;
    String matric;
    int id;
    Map<String, Float> mark;
    Map<String, String[]> weightage;
    float ans;

    System.out.print("Enter student matriculation ID: ");
    matric = sc.nextLine();
    while (!StudentManager.isStudentInList(matric)) {
      System.out.print("Matriculation ID doesn't exist! Try again: ");
      matric = sc.nextLine();
    }

    System.out.print("Enter course ID: ");
    id = sc.nextInt();
    while (CourseManager.getCourse(id) == null) {
      System.out.print("Course doesn't exist! Try again: ");
      id = sc.nextInt();
    }

    for (Record r : recordList) {
      if (r.getStudent().getMatric().equals(matric) && r.getCourse().getCourseId() == id) {
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
    int id;
    Map<String, Float> mark;
    float ans;

    System.out.print("Enter student matriculation ID: ");
    matric = sc.nextLine();
    while (StudentManager.isStudentInList(matric) == false) {
      System.out.print("Matriculation ID doesn't exist! Try again: ");
      matric = sc.nextLine();
    }

    System.out.print("Enter course ID: ");
    id = sc.nextInt();
    while (CourseManager.getCourse(id) == null) {
      System.out.print("Course doesn't exist! Try again: ");
      id = sc.nextInt();
    }

    for (Record r : recordList) {
      if (r.getStudent().getMatric().equals(matric) && r.getCourse().getCourseId() == id) {
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
    double mean = 0;
    double std = 0;
    double sumSquareDiff = 0;
    int courseId;
    System.out.println("Input the course id for statistics: ");
    courseId = sc.nextInt();

    while (!CourseManager.isCourseInList(courseId)) {
      System.out.print("The course is not registered. Please try again");
      courseId = sc.nextInt();
      sc.nextLine();
    }

    int markCount = 0;

    markCount = CourseManager.getCourse(courseId).getWeightage().size();
    
    for (Record record: recordList) {
      if(record.getCourse().getCourseId() == courseId) {
        Map<String,Float> mark = record.getMark();
        if(mark.size() < markCount){
          System.out.println("Whoops. the course hasnt been finished yet, there is a student who is not marked.");
          System.out.println("Student name: "+ record.getStudent().getName() + "With id :" + record.getStudent().getMatric());
          return;
        }
    }

    for (Record r : recordList) {
      if (r.getCourse().getCourseId() == courseId) {
        sum += r.calculateAverage();
        n++;
      }
    }
    for (Record r : recordList) {
      if (r.getCourse().getCourseId() == courseId) {
        sumSquareDiff += Math.pow((r.calculateAverage() - mean), 2);
      }
    }
    mean = sum / n;
    
    float[] studentScore = new float[n];

    System.out.println(
      "There are " + n + " students registered in this course."
    );
    System.out.println("Average : " + mean);
    
    std = Math.sqrt(sumSquareDiff / n);
    System.out.println("Standard Deviation :" + std);

    int i = 0;
    for (Record record2: recordList) {
      if(record2.getCourse().getCourseId() == courseId) {
        studentScore[i] = record2.calculateAverage();
        i++;
    }
    
    Arrays.sort(studentScore);
    int[] borderValueIndex = new int[3];
    borderValueIndex[0] = (int)1/4*(n+1);
    borderValueIndex[1] = (int)2/4*(n+1);
    borderValueIndex[2] = (int)3/4*(n+1);
    System.out.println("1st Quartile : " + studentScore[borderValueIndex[0]]);
    System.out.println("2nd Quartile : " + studentScore[borderValueIndex[1]]);
    System.out.println("3rd Quartile : " + studentScore[borderValueIndex[2]]);
  
  }
}

}
}


