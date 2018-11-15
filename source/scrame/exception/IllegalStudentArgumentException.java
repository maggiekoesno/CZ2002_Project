package scrame.exception;

/**
 * The exception when someting wrong in the student details while registering
 */
public class IllegalStudentArgumentException extends Exception {
  /**
   * The message of the exception
   */
  private String message;
  
  /**
   * The constructor of the exception
   * @param message The message to be stored
   */
  public IllegalStudentArgumentException(String message) {
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