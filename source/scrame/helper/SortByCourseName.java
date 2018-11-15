package scrame.helper;

import java.util.Comparator;

import scrame.entity.Course;

/**
 * A helper class that help to compare 2 courses
 */
public class SortByCourseName implements Comparator<Course> {
  /**
   * A function that compare 2 course objects
   * @return integer that return the relation of the 2 courses' name
   */
  public int compare(Course a, Course b) {
    return a.getCourseName().compareTo(b.getCourseName());
  }
}
