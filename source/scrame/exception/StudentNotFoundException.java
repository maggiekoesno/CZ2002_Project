package scrame.exception;

/**
 * Exception thrown when you try to find a non existing student
 */
public class StudentNotFoundException extends Exception {

  /**
   * The exception message
   */
  private String message;

  /**
   * The constructor of the exception
   */
  public StudentNotFoundException() {
    super();
    this.message = "Oops, student is not registered yet!";
  }

  /**
   * Getter to the exception message
   * @return the message as a string
   */
  public String getMessage() {
    return message;
  }
}

