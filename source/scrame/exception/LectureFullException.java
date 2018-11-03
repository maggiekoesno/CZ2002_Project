package scrame.exception;

public class LectureFullException extends Exception {

  private String message;
  
  public LectureFullException(String courseName) {
    super();
    this.message = "Sorry, no more vacancy for " + courseName;
  }

  public String getMessage() {
    return message;
  }
}
