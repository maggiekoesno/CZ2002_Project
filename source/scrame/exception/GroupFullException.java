package scrame.exception;

public class GroupFullException
  extends Exception {

  public GroupFullException(String courseName, String groupName) {
    super();
    System.out.println(
      "Sorry, no vacancy left for group " + groupName + " on course " + courseName + "."
    );
  }

}

