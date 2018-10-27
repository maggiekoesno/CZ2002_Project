package scrame.entity;

import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

import scrame.entity.Student;
import scrame.entity.Course;
import scrame.helper.Mark;

public class Record {
  private Student student;
  private Course course;
  private HashMap<String, Float> mark;

  public Record(Student student, Course course, HashMap<String, Float> mark)
    throws IllegalArgumentException {
    this.student = student;
    this.course = course;

    // The list of keys must be exactly the same with the keys of weightage in course,
    // whose values[1] == "false" (don't have child)

    HashSet<String> fromMark = new HashSet<String>();
    HashSet<String> fromCourse = new HashSet<String>();
    
    for (Map.Entry<String, Float> entry : mark.entrySet()) {
      fromMark.add(entry.getKey());
    }
    
    for (Map.Entry<String, String[]> entry : course.getWeightage().entrySet()) {
      if (entry.getValue()[1].equals("false")) {
        fromCourse.add(entry.getKey());
      }
    }

    if (!fromMark.containsAll(fromCourse) || !fromCourse.containsAll(fromMark)) {
      throw new IllegalArgumentException("Illegal mark set.");
    }
    
    this.mark = mark;
  }
  // TODO: public float calculateAverage();
  // TODO: public Student getStudent();
  // TODO: public Course getCourse();
  // TODO: public Map<String, Float> getMark();


















  // public ArrayList<String> listLeafComponents(int courseId) {
  //   ArrayList<String> leafComponents = new ArrayList<String>();
  //   CourseManager cm = new CourseManager();
  //   Course c = cm.getCourse(courseId);
  //   Map<String, String[]> weightage = c.getWeightage();

  //   for (Map.Entry<String, String[]> entry : weightage.entrySet()) { 
  //     if (entry.getValue()[HAS_CHILD].equals("false")) {
  //       leafComponents.add(entry.getKey());
  //     }
  //   }

  //   return leafComponents;
  // }

  // public String getStudentName() {
  //   return this.student.getName();
  // }

  // public String getMatric() {
  //   return this.student.getMatric();
  // }

  // public String getGroup() {
  //   return this.group;
  // }

  // public void setGroup(String group) {
  //   this.group = group;
  // }

  // public String getSemester() {
  //   return this.semester;
  // }

  // public String getCouse() {
  //   return this.course.getName();
  // }

}