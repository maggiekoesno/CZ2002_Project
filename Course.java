import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

public class Course {
  public static final int WEIGHT = 0;
  public static final int HAS_CHILD = 1;
  public static final int PARENT = 2;

  private int courseId;
  private String courseName;
  private CourseType courseType;
  private int lectureVacancy;
  private HashMap<String, Integer> tutLabGroups;
  private HashMap<String, String[]> weightage;

  private static int incrementId = 1;

  /**
   * A static method to generate unique course id for every instantiated course.
   *  
   * @return generated course id
   */
  private static int getNewCourseId() {
    return incrementId++;
  }

  /**
   * Constructor for Course class.
   * 
   * @param courseName name of course
   * @param courseType type of course (lec; lec and tut; lec, tut and lab)
   */
  public Course(String courseName, CourseType courseType) {
    this.courseId = getNewCourseId();    
    this.courseName = courseName;
    this.courseType = courseType;
    this.lectureVacancy = 0;

    if (courseType != courseType.LEC) {
      this.tutLabGroups = new HashMap<String, Integer>();
    }
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
   * // addGroups({"_LEC": 50, "SSP1": 20, "BCG2": 30});
   * // addGroups({"_LEC": 50});
   * 
   * @param vacancies key-value pair of group name and number of vacancy
   */
  public void addGroups(HashMap<String, Integer> vacancies) {
    if (courseType == CourseType.TUT || courseType == CourseType.LAB) {
      int tempLectureVacancy;
      int totalVacancy = 0;

      for (Map.Entry<String, Integer> entry : vacancies.entrySet()) {
        // Get vacancy for lecture.
        if (entry.getKey() == "_LEC") {
          tempLectureVacancy = entry.getValue();
          continue;
        }

        // Get total vacancy for tutorial/lab groups.
        totalVacancy += entry.getValue();
      }

      // Test if lecture vacancy is the total vacancy of all tutorial/lab groups.
      if (totalVacancy != tempLectureVacancy) {
        throw new IllegalArgumentException("Invalid vacancy argument.");
      }

      // If so, record the group names with their respective vacancy.
      for (Map.Entry<String, Integer> entry : vacancies.entrySet()) {
        tutLabGroups.put(entry.getKey(), entry.getValue());
      }
    }

    lectureVacancy = vacancies.get("_LEC");

    // for (int i = 0; i < arrLength; i++) {
    // tutorial.put(names[i], vacancies[i]);
    // if (haslab) {
    // lab.put(names[i], vacancies[i]);
    // }
    // totalVacancy += vacancies[i];
    // }
  }


  /**
   * Register based on group names. This function is only for courses of type CourseType.TUT and
   * CourseType.LAB.
   * 
   * If registration is successful, decrement the vacancy for that particular group.
   * 
   * // register("SSP1");
   * 
   * @param groupName group name to be registered at
   */
  public void register(String groupName) {
    if (courseType == CourseType.LEC) {
      throw new IllegalCourseTypeException(
        "You should call register() instead, since course " + courseName + " does not have any tutorial/lab groups."
      );
    }
    
    int vacancy = tutLabGroups.get(groupName);
    
    if (vacancy == 0) {
      throw new GroupFullException(courseName, groupName);
    }
    
    tutLabGroups.put(groupName, vacancy - 1);

    System.out.println(
      "Congratulations! You have successfully registered to group " + groupName +
      " on course " + courseName + "!"
    );            
  }

  /**
   * Register on courses only of type CourseType.LEC. If registration is successful, decrement
   * the lecture vacancy.
   * 
   * // register();
   */
  public void register() {
    if (courseType != CourseType.LEC) {
      throw new IllegalCourseTypeException(
        "To register on " + courseName + ", you must register based on your tutorial/lab group."
      );
    }

    if (lectureVacancy == 0) {
      throw new LectureFullException();
    }
    lectureVacancy--;
    System.out.println(
      "Congratulations! You have successfully registered to course " + courseName + "!"
    );
  }

  /**
   * Check lecture vacancy. Only applicable on courses only of type CourseType.LEC.
   * 
   * @return lecture vacancy
   */
  public int checkLectureVacancy() {
    if (courseType != CourseType.LEC) {
      throw new IllegalCourseTypeException("You should check your group's vacancy instead.");
    }

    return lectureVacancy;
  }

  /**
   * Check group vacancy. Only applicable on courses only of type CourseType.TUT and
   * CourseType.LAB.
   * 
   * // checkGroupVacancy("SSP1");
   * 
   * @param groupName group name to be checked at
   * @return group vacancy
   */
  public int checkGroupVacancy(String groupName) {
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
    for (Map.Entry<String, Integer> entry : tutLabGroups.entrySet()) {
      System.out.println(entry.getKey(), entry.getValue());      
    }
  }

  /**
   * Setter method for weightage.
   * 
   * @param weightage weightage to be inserted
   */
  public void setWeightage(HashMap<String, String[]> weightage) {
    if (!validateWeightage(weightage)) {
     throw new IllegalArgumentException("Illegal weightage argument."); 
    }

    this.weightage = weightage;                
  }

  private boolean validateWeightage(HashMap<String, String[]> weightage) {
    validateWeightage(weightage, "");
  }

  /**
   * Overloaded method to validate weightage.
   * 
   * validateWeightage(
   *  {
   *    "exam": ["60%", "false", ""],
   *    "coursework": ["40%, "true", ""],
   *    "assigments": ["70%", "false", "coursework"],
   *    "attendance": ["30%", "false", "coursework"]
   *  }
   * );
   * 
   * @return true if weightage is validated, else false
   */
  private boolean validateWeightage(HashMap<String, String[]> weightage, String check) {
    // Check if component's weights sum up to 100%.
    int total = 0;
    boolean flag = true;
    for (Map.Entry<String, String[]> entry : weightage.entrySet()) { 
      if (entry.getValue()[PARENT].equals(check)) {
        if (entry.getValue()[HAS_CHILD].equals("true")) {
          flag = flag && validateWeightage(weightage, entry.getKey());
        }
        String w = entry.getValue()[WEIGHT];
        total += (int) w.substring(0, w.length() - 1);
      }
    }

    return (total == 100 || !flag);
  }

  /**
   * Getter method for course id.
   * 
   * @return course id
   */
  public int getCourseId() {
    return courseId;
  }

  /**
   * Getter method for course name.
   * 
   * @return course name
   * 
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
   * Getter method for tutorial/lab groups.
   * 
   * @return HashMap of tutorial/lab groups
   */
  public HashMap<String, Integer> getTutLabGroups() {
    return tutLabGroups;
  }

  /**
   * Getter method for weightage
   * 
   * @return weightage
   */
  public Map<String, String[]> getWeightage() {
    return weightage;
  }
}