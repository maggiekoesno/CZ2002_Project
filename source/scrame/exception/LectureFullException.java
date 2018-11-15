package scrame.exception;

/**
 * The exception thrown when there is an attempt to register
 * to an already full course.
 */
public class LectureFullException extends Exception {

  /**
   * The exception message.
   */
  private String message;
  
  /**
   * The constructor of the exception.
   * @param courseName the name of the Course in problem
   */
  public LectureFullException(String courseName) {
    super();
    this.message = "Sorry, no more vacancy for " + courseName;
  }

  /**
   * Getter to the exception message.
   * @return the message as a string
   */
  public String getMessage() {
    return message;
  }
}
