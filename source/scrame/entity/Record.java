package scrame.entity;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

import scrame.entity.Student;
import scrame.entity.Course;
import scrame.helper.Mark;

public class Record implements Serializable {
  private Student student;
  private Course course;
  private HashMap<String, Float> mark;
  public static final int WEIGHT = 0;
  public static final int HAS_CHILD = 1;
  public static final int PARENT = 2;

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
  public float calculateAverage(){
    return calculateAverage("");
  }
  
  private float calculateAverage(String check){
    float sum=0;
    for (Map.Entry<String, String[]> entry : course.getWeightage().entrySet()){
      if(entry.getValue()[HAS_CHILD]=="false" && entry.getValue()[PARENT].equals(check)){
        String w = entry.getValue()[WEIGHT];
        float val = 0.01*Integer.parseInt(w.substring(0, w.length() - 1));
        sum += mark.get(entry.getKey())*val;
      }
      else if(entry.getValue()[HAS_CHILD]=="true"){
        sum += calculateAverage(entry.getKey());
      }
    }
    return sum;
  }
  
  public Student getStudent(){
    return student;
  }

  public Course getCourse(){
    return course;
  }
  
  public Map<String, Float> getMark(){
    return mark;
  }

}