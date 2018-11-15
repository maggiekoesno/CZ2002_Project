package scrame.exception;

/**
 * The exception thrown when you try to register the same course.
 */
public class DuplicateCourseException extends Exception {

  /**
   * The message of the exception.
   */
  private String message;
  
  /**
   * Constructor for the exception.
   * 
   * @param courseName  course name
   */
  public DuplicateCourseException(String courseName) {
    super();
    this.message = courseName + " is already registered.";
  }

  /**
   * Getter function to the exception message.
   * 
   * @return the message as a string
   */
  public String getMessage() {
    return message;
  }
}
