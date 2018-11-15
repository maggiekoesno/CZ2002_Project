package scrame.entity;

import java.io.Serializable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import scrame.entity.Course;
import scrame.entity.Student;

import scrame.helper.CourseType;

/**
 * Record is the class representing a class-student pair
 * indicating that a student is enrolled in a class
 */
public class Record implements Serializable {
  /**
   * Constants for the String array inside weightage.
   */
  private static final int WEIGHT = 0;
  private static final int HAS_CHILD = 1;
  private static final int PARENT = 2;
  
  /**
   * Reference to the student object.
   */
  private Student student;
  
  /**
   * Reference to the course object.
   */
  private Course course;

  /**
   * The group name in which the student is registered in.
   */
  private String groupName;

  /**
   * Store the value of the leaf nodes in weightage of the course
   * to later be calculated as the average score.
   */
  private HashMap<String, Float> mark;

  /**
   * Constructor for Record class.
   * 
   * @param student   the student object
   * @param course    the course object
   * @param groupName string of the groupname, "_LEC" for lecture type course
   * @param mark      hashMap of the mark (contains leaf nodes of the weightage)
   */
  public Record(Student student, Course course, String groupName, HashMap<String, Float> mark) {
    this.student = student;
    this.course = course;
    this.groupName = groupName;
    this.mark = mark;
  }

  /**
   * Helps abstracting the recursive functionality of calculateAverage.
   * 
   * @return the average of one's mark in class
   */
  public float calculateAverage() {
    return calculateAverage("");
  }

  /**
   * Return the average by calculating Mark based on Course weightage.
   * 
   * @param check the parent name whose children tally need to be calculated
   * @return      the average of the parent's child based on course weightage
   */
  private float calculateAverage(String check) {
    HashMap<String, String[]> weightage = course.getWeightage();
    float sum = 0.0f;

    for (Map.Entry<String, String[]> entry : weightage.entrySet()) {
      String component = entry.getKey();
      String[] info = entry.getValue();

      if (info[PARENT].equals(check)) {
        if (info[HAS_CHILD].equals("true")) {
          String w = info[WEIGHT];
          float val = 0.01f * Integer.parseInt(w.substring(0, w.length() - 1));
          sum += calculateAverage(component) * val;
        } else {
          String w = info[WEIGHT];
          float val = 0.01f * Integer.parseInt(w.substring(0, w.length() - 1));
          sum += (mark.get(component) * val);
        }
      }
    }

    return sum;
  }
  
  /**
   * Represents the record object in human readable way.
   * 
   * @return the human readable representation of the record object
   */
  @Override
  public String toString() {
    return student.getName() + ", " + course.getCourseName() + ", " + groupName;
  }
  
  /**
   * Getter to the student object.
   * 
   * @return the student object
   */
  public Student getStudent() {
    return student;
  }

  /**
   * Getter to the course object.
   * 
   * @return the course object
   */
  public Course getCourse() {
    return course;
  }

  /**
   * Getter to the mark object.
   * 
   * @return the mark hashmap
   */
  public HashMap<String, Float> getMark() {
    if (!hasMark()) {
      return new HashMap<String, Float>();
    }
    
    return mark;
  }

  /**
   * Checks whether the mark has already been filled.
   * 
   * @return boolean of whether the mark is null or set
   */
  public boolean hasMark() { 
    return mark != null ;
  }

  /**
   * Getter to the group name registered.
   * 
   * @return the string of group name
   */
  public String getGroupName() {
    return groupName;
  }

  /**
   * Setter to the mark hashmap.
   */
  public void setMark(HashMap<String, Float> mark) {
    this.mark = mark;
  }
}
