package entity;

import java.lang.IllegalArgumentException;

public class Student {
  private String name;
  private String major;
  private String enroll;
  private String matric;
  /**
   * Constructor for Student object
   * 
   * @param name name of the student
   * @param major major of the student
   * @param enroll enroll time of the student
   * @param matric matric of the student
   */
  public Student(String name, String major, String enroll, String matric) {
    this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
    this.major = major.toUpperCase();

    if (!enroll.matches("[aA][yY]\\d{4} [sS][1-2]")) {
      throw new IllegalArgumentException("Oops, your enrollment seems a bit inappropriate.");
    } else {
      this.enroll = enroll.toUpperCase();
    }

    if (!matric.matches("\\D\\d{7}\\D")) {
      throw new IllegalArgumentException("Oops, you have entered an invalid matric number.");
    } else {
      this.matric = matric.toUpperCase();
    }
  }
  /**
   * Function to convert all of the student attribute to string
   * 
   * @return string containing all of student attributes
   */
  public String toString() {
    return (
      "Name: " + name + "\n" + "Major: " + major + "\n" +
      "Enrolled in: " + enroll + "\n" + "Matric No.: " + matric + "\n"
    );
  }
  /**
   * Getter method for student name
   * 
   * @return student name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Getter method for student major
   * @return student major
   */
  public String getMajor() {
    return this.major;
  }
  /**
   * Getter method for enrolled time
   * @return student enrollment time
   */
  public String getEnroll() {
    return this.enroll;
  }
  /**
   * Getter method for student matric
   * @return student matric
   */
  public String getMatric() {
    return this.matric;
  }

  public static void main(String[] args) {
    try {
      Student[] students = new Student[]{
        new Student("Alice", "ACC", "AY1718 S1", "U1723456A"),
        new Student("bob", "bus", "ay1718 s1", "u1734567b"),
        new Student("eve", "ene", "ay1617 s2", "u1645678e")
      };
  
      for (Student s : students) {
        System.out.println(s.toString());
      }
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}