package scrame.exception;

public class CourseNotFoundException extends Exception {

  private String message;

  public CourseNotFoundException(String courseName) {
    super();
    this.message = "Oops, course " + courseName +
                   " has not been registered yet!";
  }

  public String getMessage() {
    return message;
  }
}
