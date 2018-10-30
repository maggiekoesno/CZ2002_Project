package scrame.manager;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Serializable;
import java.nio.file.Files;
import java.util.Scanner;

import scrame.entity.Student;

public class StudentManager {
  private HashSet<Student> studentList;

  private static String fileName = "data/students.txt";
  // The name of the file to open.

  public void inputToTextFile(HashSet<Student> studentList) {
    try {
      FileOutputStream fileOut = new FileOutputStream(fileName);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(studentList);
      out.close();
      fileOut.close();
      System.out.printf("Serialized data is saved in " + fileName);
    } catch (IOException i) {
      i.printStackTrace();
    }
  }
  
  public void loadFromTextFile() {
    try {
      FileInputStream fileIn = new FileInputStream(fileName);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      studentList = (HashSet<Student>) in.readObject();
      in.close();
      fileIn.close();
    } catch (IOException i) {
      i.printStackTrace();
      return;
    } catch (ClassNotFoundException c) {
      System.out.println("Hashset<Student> class not found");
      c.printStackTrace();
      return;
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

    Student student = new Student(name, major, enroll, matric);
    studentList.add(student);

    String myText = "\n" + name + "," + major + "," + enroll + "," + matric;

    try {
      Files.write(
        Paths.get(filename),
        myText.getBytes(),
        StandardOpenOption.APPEND
      );
      System.out.println("Student added successfully.");
    } catch(IOException e) {
      System.out.println("Unable to write new student into file.");
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
    return studentList.contains(matric);
  }

}

