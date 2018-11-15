package scrame.exception;

/**
 * The exeption when the sum of the group vacancies does not
 * tally up correctly to the course total vacancy.
 */
public class IllegalVacancyException extends Exception {
  
  /**
   * The exception message
   */
  private String message;
  
  /**
   * The constructor of the exception.
   * @param message the message to be shown later
   */
  public IllegalVacancyException(String message) {
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