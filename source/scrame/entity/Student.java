package scrame.entity;

import java.io.Serializable;

import scrame.exception.IllegalStudentArgumentException;

public class Student implements Serializable {
  /**
   * The name of the student
   */
  private String name;
  
  /**
   * The major of the student as a string
   */
  private String major;

  /**
   * The period in which the student is matriculated
   */
  private String enroll;

  /**
   * The matriculation number of the student as a string
   */
  private String matric;

  /**
   * Constructor for Student object.
   * 
   * @param name name of the student
   * @param major major of the student
   * @param enroll enroll time of the student
   * @param matric matric of the student
   */
  public Student(String name, String major, String enroll, String matric)
      throws IllegalStudentArgumentException {
    if (!enroll.matches("[aA][yY]\\d{4} [sS][1-2]")) {
      throw new IllegalStudentArgumentException(
        "Sorry, your enrollment period is of inappropriate format."
      );
    }

    if (!matric.matches("[gGnNuU][1][0-8]\\d{5}\\D")) {
      throw new IllegalStudentArgumentException(
        "Oops, you have entered an invalid matric number."
      );
    }

    this.name = name.substring(0, 1).toUpperCase() + name.substring(1);
    this.major = major.toUpperCase();
    this.enroll = enroll.toUpperCase();
    this.matric = matric.toUpperCase();
  }

  /**
   * Function to convert all of the student attribute to string
   * 
   * @return string containing all of student attributes
   */
  @Override
  public String toString() {
    return (
      "Name: " + name + "\n" +
      "Major: " + major + "\n" +
      "Enrolled in: " + enroll + "\n" +
      "Matric No.: " + matric + "\n"
    );
  }

  /**
   * Getter method for student name.
   * 
   * @return student name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Getter method for student major.
   * 
   * @return student major
   */
  public String getMajor() {
    return this.major;
  }

  /**
   * Getter method for enrolled time.
   * 
   * @return student enrollment time
   */
  public String getEnroll() {
    return this.enroll;
  }

  /**
   * Getter method for student matric.
   * 
   * @return student matric
   */
  public String getMatric() {
    return this.matric;
  }
}
