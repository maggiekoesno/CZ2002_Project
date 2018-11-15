package scrame.exception;

/**
 * Exception made when you try to register a student to the same course twice
 */
public class DuplicateRecordException extends Exception {

  /**
   * The message of the exception
   */
  private String message;
  
  /**
   * Constructor of the exception
   * 
   * @param studentName the name of the student wanted to be registered
   * @param courseName  the name of the course in which the student to be registered
   */
  public DuplicateRecordException(String studentName, String courseName) {
    super();
    this.message = "Oops, " + studentName + " is already registered on course " + courseName + ".";
  }

  /**
   * Getter of the message of the exception
   * 
   * @return the message as a string
   */
  public String getMessage() {
    return message;
  }
}
