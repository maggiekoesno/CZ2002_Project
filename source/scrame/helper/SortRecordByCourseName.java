package scrame.helper;

import java.util.Comparator;

import scrame.entity.Record;

/**
 * A helper class that compares 2 courses.
 */
public class SortRecordByCourseName implements Comparator<Record> {

  /**
   * A function that compare 2 course objects.
   * 
   * @return the relation of the 2 courses' name
   */
  public int compare(Record a, Record b) {
    return a.getCourse().getCourseName().compareTo(b.getCourse().getCourseName());
  }
}
