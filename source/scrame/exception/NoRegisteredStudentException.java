package scrame.exception;

/**
 * Exception when there is no student registered to a particular course.
 */
public class NoRegisteredStudentException extends Exception {

  /**
   * The exception message.
   */
  private String message;

  /**
   * The constructor of the exception.
   * @param courseName the name of the Course in problem
   */
  public NoRegisteredStudentException(String courseName) {
    super();
    this.message = (
      "Oops, there are no students registered to course " + courseName + "!"
    );
  }

  /**
   * Getter to the exception message.
   * @return the message as a string
   */
  public String getMessage() {
    return message;
  }
}
