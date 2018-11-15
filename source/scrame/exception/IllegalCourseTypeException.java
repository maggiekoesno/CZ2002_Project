package scrame.exception;

/**
 * The exception when you are doing something that does not permittable
 * for that type of course
 */
public class IllegalCourseTypeException extends Exception {
  /**
   * The exception message
   */
  private String message;
  
  /**
   * The constructor of the exception
   * @param message the message to be shown later
   */
  public IllegalCourseTypeException(String message) {
    super();
    this.message = message;
  }
  
  /**
   * Getter to the exception message
   * @return the message as a string
   */
  public String getMessage() {
    return message;
  }
}
