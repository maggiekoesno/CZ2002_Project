package scrame.exception;

/**
 * Exception thrown when the total weightage component does not
 * tally up correctly.
 */
public class IllegalWeightageException extends Exception {
  /**
   * The exception message.
   */
  private String message;

  /**
   * The constructor of the exception.
   * @param message the message to be shown later
   */
  public IllegalWeightageException(String message) {
    super();
    this.message = message;
  }

  /**
   * Getter to the exception message.
   * @return the message as a string
   */
  public String getMessage() {
    return message;
  }
}
