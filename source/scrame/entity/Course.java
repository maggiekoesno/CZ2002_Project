package scrame.entity;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

import scrame.exception.GroupFullException;
import scrame.exception.IllegalCourseTypeException;
import scrame.exception.LectureFullException;
import scrame.helper.CourseType;

public class Course implements Serializable {
  private static final long serialVersionUID = 6720965137323151182L;
  public static final int WEIGHT = 0;
  public static final int HAS_CHILD = 1;
  public static final int PARENT = 2;

  private String courseName;
  private CourseType courseType;
  private HashMap<String, Integer> tutLabGroups;
  private HashMap<String, String[]> weightage;

  /**
   * Constructor for Course class.
   * 
   * @param courseName name of course
   * @param courseType type of course (lec; lec and tut; lec, tut and lab)
   * @param vacancies vacancies of type HashMap
   * @param weightage weightage of course components
   */
  public Course(String courseName, CourseType courseType, HashMap<String, Integer> vacancies,
      HashMap<String, String[]> weightage) {
    this.courseName = courseName;
    this.courseType = courseType;
    this.tutLabGroups = new HashMap<String, Integer>();
    addGroups(vacancies);
    setWeightage(weightage);
  }

  /**
   * Initialize course by registering groups.
   * 
   * For CourseType.TUT and CourseType.LAB courses, need to check if the lecture vacancy is
   * consistent with the total vacancy of tutorial/lab groups.
   * 
   * The continuation of the function is dependent according to the type of the course. If
   * the course has lectures only, then only register the lecture vacancy. Else, register
   * each group along with their vacancies.
   * 
   * @param vacancies key-value pair of group name and number of vacancy
   */
  public void addGroups(HashMap<String, Integer> vacancies) throws IllegalArgumentException {
    if (courseType == CourseType.LEC) {
      if (!vacancies.containsKey("_LEC")) {
        throw new IllegalArgumentException("Please provide a vacancy number for lectures.");
      }

      tutLabGroups.put("_LEC", vacancies.get("_LEC"));
    } else {
      int tempLectureVacancy = -1;
      int totalVacancy = 0;

      for (Map.Entry<String, Integer> entry : vacancies.entrySet()) {
        if (entry.getKey() == "_LEC") {
          tempLectureVacancy = entry.getValue();
          continue;
        }

        totalVacancy += entry.getValue();
      }

      // Test if lecture vacancy is the total vacancy of all tutorial/lab groups.
      if (totalVacancy != tempLectureVacancy) {
        throw new IllegalArgumentException("Invalid vacancy argument.");
      }

      for (Map.Entry<String, Integer> entry : vacancies.entrySet()) {
        tutLabGroups.put(entry.getKey(), entry.getValue());
      }
    }
  }

  /**
   * Register based on group names. This function is only for courses of type CourseType.TUT and
   * CourseType.LAB.
   * 
   * If registration is successful, decrement the vacancy for that particular group.
   * 
   * @param groupName group name to be registered at
   */
  public void register(String groupName) throws IllegalCourseTypeException, GroupFullException {
    if (courseType == CourseType.LEC) {
      throw new IllegalCourseTypeException(
        "You should call register() instead, since course " + courseName +
        " does not have any tutorial/lab groups."
      );
    }

    int groupVacancy = tutLabGroups.get(groupName);

    if (groupVacancy == 0) {
      throw new GroupFullException(courseName, groupName);
    }
    
    tutLabGroups.put(groupName, --groupVacancy);

    int lectureVacancy = tutLabGroups.get("_LEC");
    tutLabGroups.put("_LEC", --lectureVacancy);
  }

  /**
   * Register on courses only of type CourseType.LEC. If registration is successful, decrement
   * the lecture vacancy.
   */
  public void register() throws IllegalCourseTypeException, LectureFullException {
    if (courseType != CourseType.LEC) {
      throw new IllegalCourseTypeException(
        "To register on " + courseName + ", you must register based on your tutorial/lab group."
      );
    }

    int lectureVacancy = tutLabGroups.get("_LEC");
    if (lectureVacancy == 0) {
      throw new LectureFullException(courseName);
    }
    
    tutLabGroups.put("_LEC", --lectureVacancy);
  }

  /**
   * Check group vacancy. Only applicable on courses only of type CourseType.TUT and
   * CourseType.LAB.
   * 
   * @param groupName group name to check
   * @return group vacancy
   */
  public int checkGroupVacancy(String groupName) throws IllegalCourseTypeException {
    if (courseType == CourseType.LEC) {
      throw new IllegalCourseTypeException(
        "Course " + courseName + " does not have any tutorial/lab group."
      );
    }

    return tutLabGroups.get(groupName);
  }

  /**
   * Print all groups on the course. 
   */
  public void printAllGroups() {
    if (courseType == CourseType.LEC) {
      System.out.println(
        "Oops, it appears that " + courseName + " doesn't have any groups."
      );
      return;
    }
    
    String groupName;
    int vacancy;
    
    for (Map.Entry<String, Integer> entry : tutLabGroups.entrySet()) {
      groupName = entry.getKey();
      vacancy = entry.getValue();

      if (groupName.equals("_LEC")) {
        continue;
      }

      System.out.println(groupName + ": " + vacancy);
    }
  }

  /**
   * Setter method for weightage.
   * 
   * @param weightage weightage to be inserted
   */
  public void setWeightage(HashMap<String, String[]> weightage) throws IllegalArgumentException {
    if (!validateWeightage(weightage)) {
      throw new IllegalArgumentException("Illegal weightage argument.");
    }

    this.weightage = weightage;
  }

  /**
   * Validate weightage.
   * 
   * @param weightage weightage in HashMap
   * @return true if weightage is validated, else false
   */
  private boolean validateWeightage(HashMap<String, String[]> weightage) {
    return validateWeightage(weightage, "");
  }

  /**
   * Overloaded method to validate weightage.
   * 
   * @return true if weightage is validated, else false
   */
  private boolean validateWeightage(HashMap<String, String[]> weightage, String check) {
    int total = 0;
    boolean flag = true;

    // Check if component's weights sum up to 100%.
    for (Map.Entry<String, String[]> entry : weightage.entrySet()) {
      String component = entry.getKey();
      String[] info = entry.getValue();

      if (info[PARENT].equals(check)) {
        if (info[HAS_CHILD].equals("true")) {
          flag = flag && validateWeightage(weightage, component);
        }

        String w = info[WEIGHT];
        total += Integer.parseInt(w.substring(0, w.length() - 1));
      }
    }
    return (total == 100 && flag);
  }

  /**
   * Getter method for course name.
   * 
   * @return course name
   */
  public String getCourseName() {
    return courseName;
  }

  /**
   * Getter method for course type.
   * 
   * @return course type
   */
  public CourseType getCourseType() {
    return courseType;
  }

  /**
   * Getter method for lecture vacancy.
   * 
   * @return lecture vacancy
   */
  public int getLectureVacancy() {
    return tutLabGroups.get("_LEC");
  }

  /**
   * Getter method for tutorial/lab groups.
   * 
   * @return HashMap of tutorial/lab groups
   */
  public HashMap<String, Integer> getTutLabGroups() {
    return tutLabGroups;
  }

  /**
   * Getter method for weightage.
   * 
   * @return weightage
   */
  public HashMap<String, String[]> getWeightage() {
    return weightage;
  }
}
