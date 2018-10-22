import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Record {
  public static final int WEIGHT = 0;
  public static final int HAS_CHILD = 1;
  public static final int PARENT = 2;

  private Map<String, Map<String, Mark>> record;

  public Record() {
      record = new HashMap<String, HashMap<String, Mark>>();
  }
  
  public ArrayList<String> listLeafComponents(int courseId) {
    ArrayList<String> leafComponents = new ArrayList<String>();
    CourseManager cm = new CourseManager();
    Course c = cm.getCourse(courseId);
    Map<String, String[]> weightage = c.getWeightage();

    for (Map.Entry<String, String[]> entry : weightage.entrySet()) { 
      if (entry.getValue()[HAS_CHILD].equals("false")) {
        leafComponents.add(entry.getKey());
      }
    }

    return leafComponents;
  }

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