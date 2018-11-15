package scrame.exception;

/**
 * Exception thrown when you try to register to a group in a course that
 * has already full.
 */
public class GroupFullException extends Exception {

  /**
   * The exception message.
   */
  private String message;

  /**
   * Consructor for the exception.
   * 
   * @param courseName  The course name to which the registration is being made
   * @param groupName   The group name of the course which is full
   */
  public GroupFullException(String courseName, String groupName) {
    super();
    this.message = "Sorry, no vacancy left for group " +
                    groupName + " on course " + courseName + ".";
  }

  /**
   * Getter of the exception message.
   * 
   * @return the message as a string
   */
  public String getMessage() {
    return message;
  }
}
