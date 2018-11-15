package scrame.exception;

/**
 * The exception thrown when you try to register to a group
 * within a class that does not exist
 */
public class GroupNotFoundException extends Exception {
  /**
   * The message of the exception
   */
  private String message;

  /**
   * Constructor for the exception
   * 
   * @param groupName   The name of the groupname to be registered
   * @param courseName  The name of the course to be registered
   */
  public GroupNotFoundException(String groupName, String courseName) {
    super();
    this.message = (
      "Oops, it seems that there is no group " + groupName + " on course " +
       courseName + "!"
    );
  }
  
  /**
   * The getter to the exception message
   * 
   * @return the message as a string
   */
  public String getMessage() {
    return message;
  }
}
