package tester;

import java.util.Map;
import java.util.HashMap;

import org.junit.Test;
import static org.junit.Assert;

import entity.Course;
import entity.CourseType;

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

      assertEquals("CZ2001", c1.getCourseName());
      assertEquals(3, c3.getCourseId());

      c2.register("SSP1");    
      assertEquals(29, c2.checkGroupVacancy("SSP1"));
      assertEquals(49, c2.checkLectureVacancy());
      c1.register();    
      assertEquals(49, c1.checkLectureVacancy());
      
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


      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // assertEquals() DOCS HELPER;

      // String str1 = new String("abc");
      // String str2 = new String("abc");
      // String str3 = null;
      // String str4 = "abc";
      // String str5 = "abc";

      // int val1 = 5;
      // int val2 = 6;

      // String[] expectedArray = {"one", "two", "three"};
      // String[] resultArray =  {"one", "two", "three"};

      // //Check that two objects are equal
      // assertEquals(str1, str2);

      // //Check that a condition is true
      // assertTrue(val1 < val2);

      // //Check that a condition is false
      // assertFalse(val1 > val2);

      // //Check that an object isn't null
      // assertNotNull(str1);

      // //Check that an object is null
      // assertNull(str3);

      // //Check if two object references point to the same object
      // assertSame(str4,str5);

      // //Check if two object references not point to the same object
      //getMesaggestr1,str3);

      // //Check whether two arrays are equal to each other.
      // assertArrayEquals(expectedArray, resultArray);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }
}