package scrame.tester;

import org.junit.Test;
import org.junit.Assert;

import scrame.entity.Student;

public class StudentTest {

  @Test
  public void testAssertions() {
    try {
      Student[] students = new Student[]{
        new Student("Alice", "ACC", "AY1718 S1", "U1723456A"),
        new Student("bob", "bus", "ay1718 s1", "u1734567b"),
        new Student("eve", "ene", "ay1617 s2", "u1645678e")
      };
  
      Assert.assertEquals("Alice", students[0].getName());
      Assert.assertEquals("BUS", students[1].getMajor());
      Assert.assertEquals("AY1617 S2", students[2].getEnroll());
    } catch (IllegalArgumentException e) {
      System.out.println(e.getMessage());
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}