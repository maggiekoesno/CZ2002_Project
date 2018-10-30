package scrame.manager;

import java.util.HashMap;
import java.util.Scanner;
import java.io.Serializable;

import scrame.entity.Course;
import scrame.manager.CourseManager;
import scrame.manager.StudentManager;

public class RegistrationManager {
  private HashSet<Record> recordList; 
  private static String fileName = "data/records.ser"; // The name of the file to open.

  public RegistrationManager() {
    recordList = new HashSet<Record>();
  }

  public void registerStudentCourse() {
    Scanner sc = new Scanner(System.in);

    System.out.print("Please input Matric:");
    String s = sc.next();
    //check apakah student sudah keregister di Hashmap , ini cukup aneh but let it be
    StudentManager sManager = new StudentManager();
    sManager.loadFromTextFile();
    if (!sManager.isStudentInList(s)) {
      System.out.println("Student is not registered, Add the student first.");
      return;
    }
    System.out.print("Please input Course ID:");
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
    courseFound.register(group);

    //need to create record of studentId  -> ( courseObject ! i think, mark)
  }

  public void inputToTextFile(HashSet<Record> recordList) {
    try {
      FileOutputStream fileOut = new FileOutputStream(fileName);
      ObjectOutputStream out = new ObjectOutputStream(fileOut);
      out.writeObject(recordList);
      out.close();
      fileOut.close();
      System.out.printf("Serialized data is saved in" + fileName);
    } catch (IOException i) {
      i.printStackTrace();
    }
  }
  public void loadFromTextFile() {
    try {
      FileInputStream fileIn = new FileInputStream(filename);
      ObjectInputStream in = new ObjectInputStream(fileIn);
      recordList = (HashSet<Record>) in.readObject();
      in.close();
      fileIn.close();
    } catch (IOException i) {
      i.printStackTrace();
      return;
    } catch (ClassNotFoundException c) {
      System.out.println("Hashset<Record> class not found");
      c.printStackTrace();
      return;
    }
}

}

