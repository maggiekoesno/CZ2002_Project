package scrame.exception;

public class GroupNotFoundException extends Exception {

  private String message;

  public GroupNotFoundException(String groupName, String courseName) {
    super();
    this.message = (
      "Oops, it seems that there is no group " + groupName + " on course " +
       courseName + "!"
    );
  }

  public String getMessage() {
    return message;
  }
}
