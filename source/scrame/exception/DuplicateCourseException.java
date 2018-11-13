package scrame.exception;

public class DuplicateCourseException extends Exception {

  private String message;
  
  public DuplicateCourseException(String courseName) {
    super();
    this.message = courseName + " is already registered.";
  }

  public String getMessage() {
    return message;
  }
}
