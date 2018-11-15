package scrame.helper;

import java.util.Comparator;

import scrame.entity.Student;

/**
 * A helper class that helps to compare 2 students name
 */
public class SortByStudentName implements Comparator<Student> {
  /**
   * Function that compares 2 student object
   * @return integer that represents the relation between the 2 students' name
   */
  public int compare(Student a, Student b) {
    return a.getName().compareTo(b.getName());
  }
}
