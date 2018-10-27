package scrame.tester;

import org.junit.Test;
import org.junit.Assert;

import scrame.entity.FacultyMember;

public class FacultyMemberTest {

  @Test
  public void testAssertions() {
    FacultyMember[] members = new FacultyMember[]{
      new FacultyMember("Elbert", "U1720411A", "CSC", true),
      new FacultyMember("Jason", "U1231244B", "MAS")
    };

    Assert.assertEquals("Elbert", members[0].getName());
    Assert.assertEquals(true, members[0].getIsCourseCoordinator());
    Assert.assertEquals(false, members[1].getIsCourseCoordinator());
  }
}
