package scrame.entity;

import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;

import scrame.entity.Student;
import scrame.entity.Course;

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
      if(entry.getValue()[PARENT].equals(check)){
        if(entry.getValue()[HAS_CHILD].equals("true")){
          sum += calculateAverage(entry.getKey());
        }
        String w = entry.getValue()[WEIGHT];
        float val = (float)0.01*Integer.parseInt(w.substring(0, w.length() - 1));
        sum += mark.get(entry.getKey())*val;
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

  public static void main(String[] args){
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
            
            Student tester = new Student("Alice", "ACC", "AY1718 S1", "U1723456A");

            HashMap<String, Float> markIn = new HashMap<String, Float>();
            markIn.put("exam", (float)80);
            markIn.put("assigments", (float)70);
            markIn.put("attendance", (float)90);
            Record rec = new Record(tester, c1, markIn);
            System.out.println("Here it comes");
            float res = rec.calculateAverage();
            System.out.println(res);

        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
  }
}