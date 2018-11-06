package scrame.entity;

import java.io.Serializable;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import scrame.entity.Course;
import scrame.entity.Student;

import scrame.helper.CourseType;

public class Record implements Serializable {
  private static final long serialVersionUID = 3L;
  private static final int WEIGHT = 0;
  private static final int HAS_CHILD = 1;
  private static final int PARENT = 2;

  private Student student;
  private Course course;
  private String groupName;
  private Map<String, Float> mark = null;

  public Record(Student student, Course course, String groupName, HashMap<String, Float> mark) {
    this.student = student;
    this.course = course;
    this.groupName = groupName;
    this.mark = mark;
  }

  public float calculateAverage() {
    return calculateAverage("");
  }

  private float calculateAverage(String check) {
    Map<String, String[]> weightage = course.getWeightage();
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

  @Override
  public String toString() {
    return student.getName() + ", " + course.getCourseName() + ", " + groupName;
  }

  public Student getStudent() {
    return student;
  }

  public Course getCourse() {
    return course;
  }

  public Map<String, Float> getMark() {
    return mark;
  }

  public boolean hasMark() { 
    return mark.equals(null);
  }

  public String getGroupName() {
    return groupName;
  }

  public void setMark(Map<String, Float> mark) {
    this.mark = mark;
  }
}
