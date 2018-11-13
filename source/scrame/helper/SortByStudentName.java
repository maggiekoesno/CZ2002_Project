package scrame.helper;

import java.util.Comparator;

import scrame.entity.Student;

public class SortByStudentName implements Comparator<Student> {
  public int compare(Student a, Student b) {
    return a.getName().compareTo(b.getName());
  }
}
