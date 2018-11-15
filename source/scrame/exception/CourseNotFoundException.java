package scrame.exception;

/**
 * Exception when you try to find a course based on the
 * coursename, but it's not registered yet.
 */
public class CourseNotFoundException extends Exception {

  /**
   * The message to be thrown.
   */
  private String message;

  /**
   * Constructor for the exception.
   * 
   * @param courseName the name of the course as string
   */
  public CourseNotFoundException(String courseName) {
    super();
    this.message = "Oops, course " + courseName +
                   " has not been registered yet!";
  }

  /**
   * Getter function for message.
   * 
   * @return the string of message
   */
  public String getMessage() {
    return message;
  }
}
