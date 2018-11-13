package scrame.exception;

public class CourseNotFoundException extends Exception {

  private String message;

  public CourseNotFoundException(String courseName) {
    super();
    this.message = "Oops, it seems that the course " + courseName +
                   " has not been registered to the system yet!";
  }

  public String getMessage() {
    return message;
  }
}
