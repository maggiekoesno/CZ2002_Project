package scrame.exception;

public class IllegalCourseTypeException
  extends Exception {

  private String message;
  
  public IllegalCourseTypeException(String message) {
    super();
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}

