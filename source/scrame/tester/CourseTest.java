package scrame.tester;

import java.util.Map;
import java.util.HashMap;

import org.junit.Test;
import org.junit.Assert;

import scrame.entity.Course;
import scrame.entity.CourseType;

public class CourseTest {

  @Test
  public void testAssertions() {
    try {
      // https://www.tutorialspoint.com/junit/junit_using_assertion.htm
      Course c1 = new Course("CZ2001", CourseType.LEC);
      Course c2 = new Course("CZ2002", CourseType.TUT);
      Course c3 = new Course("CZ2003", CourseType.LAB);
      Course c4 = new Course("CZ2004", CourseType.TUT);

      HashMap<String, Integer> map = new HashMap<String, Integer>();

      map.put("_LEC", 50);
      c1.addGroups(map);
      map.put("SSP1", 30);
      map.put("BCG2", 20);
      c2.addGroups(map);
      c3.addGroups(map);

      Assert.assertEquals("CZ2001", c1.getCourseName());
      Assert.assertEquals(3, c3.getCourseId());

      c2.register("SSP1");    
      Assert.assertEquals(29, c2.checkGroupVacancy("SSP1"));
      Assert.assertEquals(49, c2.checkLectureVacancy());
      c1.register();    
      Assert.assertEquals(49, c1.checkLectureVacancy());
      
      HashMap<String, Integer> map2 = new HashMap<String, Integer>();
      map2.put("_LEC", 50);
      map2.put("SSP1", 30);
      // System.out.println("The following line should give IllegalArgumentException");      
      // c4.addGroups(map2);

      c2.printAllGroups();

      HashMap<String, String[]> weightage = new HashMap<String, String[]>();
      
      weightage.put("exam", new String[]{"60%", "false", ""});
      weightage.put("coursework", new String[]{"40%", "true", ""});
      weightage.put("assigments", new String[]{"70%", "false", "coursework"});
      weightage.put("attendance", new String[]{"30%", "false", "coursework"});

      c1.setWeightage(weightage);
      // assertEquals(true, c1.validateWeightage(weightage));
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}