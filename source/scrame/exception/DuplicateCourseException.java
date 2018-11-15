package scrame.exception;

/**
 * The exception thrown when you try to register the same course try
 */
public class DuplicateCourseException extends Exception {

  /**
   * The message of the exception
   */
  private String message;
  
  /**
   * The constructor for the exception
   * 
   * @param courseName  The name of the course as string
   */
  public DuplicateCourseException(String courseName) {
    super();
    this.message = courseName + " is already registered.";
  }

  /**
   * The getter to the exception message
   * @return the message as a string
   */
  public String getMessage() {
    return message;
  }
}
