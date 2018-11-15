package scrame.exception;

/**
 * The exception when you are doing something that is not permitted
 * for that type of course.
 */
public class IllegalCourseTypeException extends Exception {

  /**
   * The exception message.
   */
  private String message;
  
  /**
   * Constructor of the exception.
   * 
   * @param message the message to be shown later
   */
  public IllegalCourseTypeException(String message) {
    super();
    this.message = message;
  }
  
  /**
   * Getter to the exception message.
   * 
   * @return the message as a string
   */
  public String getMessage() {
    return message;
  }
}
