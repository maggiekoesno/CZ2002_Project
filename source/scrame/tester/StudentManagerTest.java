package scrame.tester;

import java.util.HashSet;

import org.junit.Assert;
import org.junit.Test;

import scrame.manager.StudentManager;

public class StudentManagerTest {

  @Test
  public void testAssertions() {
    try {
      StudentManager sm = new StudentManager();

      sm.addStudent();
    } catch(Exception e) {
      e.printStackTrace();
    }
  }

}

