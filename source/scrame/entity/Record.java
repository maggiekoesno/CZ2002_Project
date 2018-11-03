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
  private Student student;
  private Course course;
  private String groupName;
  private Map<String, Float> mark;
  private static final int WEIGHT = 0;
  private static final int HAS_CHILD = 1;
  private static final int PARENT = 2;

  public Record(
    Student student,
    Course course,
    String groupName,
    HashMap<String, Float> mark
  ) {
    this.student = student;
    this.course = course;
    this.groupName = groupName;

    // whose values[1] == "false" (don't have child)

    // HashSet<String> fromMark = new HashSet<String>();
    // HashSet<String> fromCourse = new HashSet<String>();
    // for (Map.Entry<String, Float> entry : mark.entrySet()) {
    //   String component = entry.getKey();
    //   fromMark.add(component);
    // }
    // for (Map.Entry<String, String[]> entry : course.getWeightage().entrySet()) {
    //   String component = entry.getKey();
    //   String[] info = entry.getValue();
    //   if (info[HAS_CHILD].equals("false")) {
    //     fromCourse.add(component);
    //   }
    // }
    // if (!fromMark.containsAll(fromCourse) || !fromCourse.containsAll(
    //   fromMark
    // )) {
    //   throw new IllegalArgumentException("Illegal mark set.");
    // }
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

  public Student getStudent() {
    return student;
  }

  public Course getCourse() {
    return course;
  }

  public Map<String, Float> getMark() {
    return mark;
  }

  public String getGroupName() {
    return groupName;
  }

  public void setMark(Map<String, Float> mark) {
    this.mark = mark;
  }

  public String toString() {
    return (
      student.getName() + ", " + course.getCourseName() + ", " + groupName
    );
  }

  public static void main(String[] args) {
    try {
      HashMap<String, Integer> map = new HashMap<String, Integer>();
      Course c1 = new Course("CZ2001", CourseType.LEC);
      map.put("_LEC", 50);
      c1.addGroups(map);
      HashMap<String, String[]> weightage = new HashMap<String, String[]>();
      weightage.put("exam", new String[] { "60%", "false", "" });
      weightage.put("coursework", new String[] { "40%", "true", "" });
      weightage.put(
        "assigments",
        new String[] { "70%", "false", "coursework" }
      );
      weightage.put(
        "attendance",
        new String[] { "30%", "false", "coursework" }
      );
      c1.setWeightage(weightage);

      HashMap<String, Float> markIn = new HashMap<String, Float>();
      markIn.put("exam", 80.0f);
      markIn.put("assigments", 70.0f);
      markIn.put("attendance", 90.0f);

      Record rec = new Record(new Student("Alice", "ACC", "AY1718 S1", "U1723456A"),c1,"SSP1",markIn);
      System.out.println(rec.calculateAverage());
    } catch(Exception e) {
      System.out.println(e.getMessage());
    }
  }

}

