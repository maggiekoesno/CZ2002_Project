package scrame.exception;

public class GroupFullException extends Exception {

  private String message;

  public GroupFullException(String courseName, String groupName) {
    super();
    this.message = "Sorry, no vacancy left for group " +
                    groupName + " on course " + courseName + ".";
  }

  public String getMessage() {
    return message;
  }
}
