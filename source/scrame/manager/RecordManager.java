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

  public static void registerStudentCourse() {
    Scanner sc = new Scanner(System.in);
    
    System.out.print("Please input matric number: ");
    String matric = sc.nextLine();
    Student studentFound = StudentManager.findStudent(matric);
    
    if (studentFound == null) {
      System.out.println(
        "Oops, student is not registered yet! Please register first."
      );
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

    if (!validateRegisterStudentCourse(matric, courseName)) {
      return;
    }
     
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

    if (!validateRegisterStudentCourse(matric, courseName)) {
      return;
    }

    try {
      for (Record r : RecordManager.getRecordList()) {
        String tempCourseName = r.getCourse().getCourseName();
        String tempStudentName = r.getStudent().getName();
        String studentName = studentFound.getName();

        if (tempStudentName.equals(studentName) &&
            tempCourseName.equals(courseName)) {
          throw new IllegalArgumentException(
            studentName + " is already registered on course " + courseName + "."
          );
        }
      }

      courseFound.register();
      String studentName = studentFound.getName();
      System.out.println(
        studentName + " is succesfully registered on course " + courseName + "!"
      );
    } catch (IllegalCourseTypeException e) {
      e.printStackTrace();
    } catch (LectureFullException e) {
      e.printStackTrace();
    }

    recordList.add(new Record(
      studentFound, courseFound, "_LEC", new HashMap<String, Float>()
    ));
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
    
    if (!validateRegisterStudentCourse(matric, courseName)) {
      return;
    }

    try {
      courseFound.register(groupName);
      String studentName = studentFound.getName();
      System.out.println(
        studentName + " is succesfully registered on group " + groupName +
        " on course " + courseName + "!"
      );
    } catch (IllegalCourseTypeException e) {
      System.out.println(e.getMessage());
    } catch (GroupFullException e) {
      e.printStackTrace();
    }

    HashMap<String, Float> mark = null;
    Record r = new Record(studentFound, courseFound, groupName, mark);
    recordList.add(r);
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

  // public static void setCourseworkMark() {
  //   Scanner sc = new Scanner(System.in);
  //   boolean check = false;
  //   String matric;
  //   Map<String, Float> mark;
  //   Map<String, String[]> weightage;
  //   float ans;

  //   System.out.print("Enter student matriculation number: ");
  //   matric = sc.nextLine();
  //   while (!StudentManager.isStudentInList(matric)) {
  //     System.out.print("Matriculation ID doesn't exist! Try again (enter -1 to exit):  ");
  //     matric = sc.nextLine();
  //     if(matric.equals("-1")) return;
  //   }

  //   System.out.print("Enter course name: ");
  //   String courseName = sc.nextLine();
  //   while (!CourseManager.isCourseInList(courseName)) {
  //     System.out.print("Course doesn't exist! Try again (enter -1 to exit): ");
  //     courseName = sc.nextLine();
  //     if(courseName.equals("-1")) return;
  //   }

  //   for (Record r : recordList) {
  //     if (r.getStudent().getMatric().equals(matric) && r.getCourse().getCourseName().equals(courseName)) {
  //       check = true;
  //       mark = r.getMark();
  //       if (mark == null) {
  //         mark = new HashMap<String, Float>();
  //       }
  //       weightage = r.getCourse().getWeightage();
  //       for (Map.Entry<String, String[]> entry : weightage.entrySet()) {
  //         System.out.println(entry.getKey() + " = " + entry.getValue()); //TODO : entry.getValue() buat apa?
          
  //         if (entry.getValue()[1].equals("false") &&
  //             !(entry.getKey().toLowerCase().equals("exam"))) {
  //           System.out.print(
  //             "Do you want to enter mark for " + entry.getKey() + "? (y(1)/n(0)) "
  //           );
  //           ans = sc.nextInt();
  //           if (ans == 1) {
  //             System.out.print("Enter mark for " + entry.getKey() + " :");
  //             ans = sc.nextFloat();
  //             while(ans<0 || ans>100){
  //               System.out.println("WHOOPS, MARK IS OUT OF RANGE BOI");
  //               System.out.print("Try Again:");
  //               ans = sc.nextFloat();
  //             }
  //             mark.put(entry.getKey(),ans);
  //           }
  //         }
  //       }
  //       r.setMark(mark);
  //       break;
  //     }
  //   }
  //   if (check == false) {
  //     System.out.println("Student is not taking that course!");
  //   }
  // }

  // public static void setExamMark() {
  //   Scanner sc = new Scanner(System.in);
  //   boolean check = false;
  //   String matric;
  //   HashMap<String, Float> mark;
  //   float ans;

  //   System.out.print("Enter student matriculation ID: ");
  //   matric = sc.nextLine();
  //   while (!StudentManager.isStudentInList(matric)) {
  //     System.out.print("Matriculation ID doesn't exist! Try again (enter -1 to exit): ");
  //     matric = sc.nextLine();
  //     if(matric.equals("-1")) return;
  //   }

  //   System.out.print("Enter course name: ");
  //   String courseName = sc.nextLine();
  //   while (!CourseManager.isCourseInList(courseName)) {
  //     System.out.print("Course doesn't exist! Try again (enter -1 to exit): ");
  //     courseName = sc.nextLine();
  //     if(courseName.equals("-1")) return;
  //   }

  //   for (Record r : recordList) {
  //     if (r.getStudent().getMatric().equals(matric) &&
  //         r.getCourse().getCourseName().equals(courseName)) {
  //       check = true;
  //       mark = r.getMark();
  //       if (mark == null) {
  //         mark = new HashMap<String, Float>();
  //       }
  //       System.out.print("Enter mark for exam: ");
  //       ans = sc.nextFloat();
  //       while(ans<0 || ans>100){
  //         System.out.println("WHOOPS, MARK IS OUT OF RANGE BOI");
  //         System.out.print("Try Again: ");
  //         ans = sc.nextFloat();
  //       }
  //       mark.put("Exam", ans);
  //       r.setMark(mark);
  //       break;
  //     }
  //   }
  //   if (check == false) {
  //     System.out.println("Student is not taking that course!");
  //   }
  // }

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

    Course courseFound = CourseManager.findCourse(courseName);
    Map<String, String[]> tmpWeightage = courseFound.getWeightage();
    for(Map.Entry<String, String[]> entry : tmpWeightage.entrySet()){
      if(entry.getValue()[1].equals("false")){
        markCount++;
      }
    }
    // System.out.println("mark count : " + markCount);
    
    for (Record r1: recordList) {
      if (r1.getCourse().getCourseName().equals(courseName)) {
        Map<String,Float> mark = r1.getMark();
        System.out.println(mark.size());
        if ( !r1.hasMark() || mark.size() < markCount) {
          System.out.println("Whoops, the course hasnt been finished yet. There is a student who is not marked.");
          System.out.println(
            "Student name: "+ r1.getStudent().getName() +
            " (Matric number: " + r1.getStudent().getMatric() + ")");
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
    System.out.println("Average : " + mean);
    
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
    System.out.println("1st Quartile : " + studentScore[borderValueIndex[0]]);
    System.out.println("2nd Quartile : " + studentScore[borderValueIndex[1]]);
    System.out.println("3rd Quartile : " + studentScore[borderValueIndex[2]]);
  }
}
