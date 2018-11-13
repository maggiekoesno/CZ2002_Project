package scrame.helper;

import java.util.Comparator;

import scrame.entity.Course;

public class SortByCourseName implements Comparator<Course> {
  public int compare(Course a, Course b) {
    return a.getCourseName().compareTo(b.getCourseName());
  }
}
