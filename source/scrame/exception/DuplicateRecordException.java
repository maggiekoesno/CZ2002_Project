package scrame.exception;

public class DuplicateRecordException extends Exception {

  private String message;
  
  public DuplicateRecordException(String studentName, String courseName) {
    super();
    this.message = studentName + " is already registered on course " + courseName + ".";
  }

  public String getMessage() {
    return message;
  }
}
