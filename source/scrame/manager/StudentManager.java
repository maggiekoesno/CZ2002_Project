package scrame.manager;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.nio.file.Files;
import java.util.Scanner;
import java.util.HashMap;
import java.util.HashSet;

import scrame.entity.Student;

public class StudentManager {
  private HashSet<Student> studentList;

  private static String fileName = "data/students.ser";
  // The name of the file to open.

  public void inputToTextFile(HashSet<Student> studentList) {
    try {
      ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName));
      out.writeObject(studentList);
      out.close();
      System.out.printf("Serialized data is saved in " + fileName);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
  
  public void loadFromTextFile() {
    try {
      ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName));
      studentList = (HashSet<Student>) in.readObject();
      in.close();
    } catch (IOException e) {
      e.printStackTrace();
    } catch (ClassNotFoundException e) {
      System.out.println("Hashset<Student> class not found.");
      e.printStackTrace();
    }
    // public void loadFromTextFile() {
    //   String line = null;
    //   String line_arr[] = new String[4];
    //   try {
    //     BufferedReader bufferedReader = new BufferedReader(
    //       new FileReader(fileName)
    //     );

    //     while (line = bufferedReader.readLine() != null) {
    //       line.split(",");
    //       studentList.add(
    //         new Student(line_arr[0], line_arr[1], line_arr[2], line_arr[3])
    //       );
    //     }
    //     bufferedReader.close();
    //   } catch(FileNotFoundException ex) {
    //     System.out.println("Unable to open file '" + fileName + "'");
    //   } catch(IOException ex) {
    //     System.out.println("Error reading file '" + fileName + "'");
    //   }
    // }
  }

  public void addStudent() {
    String name;
    String major;
    String enroll;
    String matric;

    Scanner sc = new Scanner(System.in);

    System.out.print("Enter new student's name: ");
    name = sc.nextLine();
    System.out.print("Enter " + name + "'s major: ");
    major = sc.nextLine();
    System.out.print("Enter " + name + "'s enrollment period: ");
    enroll = sc.nextLine();
    System.out.print("Enter " + name + "'s matriculation number: ");
    matric = sc.nextLine();

    try {
      Student student = new Student(name, major, enroll, matric);
      studentList.add(student);
      System.out.println("Student added successfully.");
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  /**
   * Check if a student is registered.
   * 
   * @param matric matric ID of the student
   * 
   * @return true if student in the list, false otherwise
   */
  public boolean isStudentInList(String matric) {
    for(Student s: studentList){
      if(s.getMatric() == matric){
        return true;
      }
    }
    return false;
  }
}

