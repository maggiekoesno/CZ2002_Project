package scrame.exception;

public class NoRegisteredStudentException extends Exception {

  private String message;

  public NoRegisteredStudentException(String courseName) {
    super();
    this.message = (
      "Oops, there are no students registered to course " + courseName + "!"
    );
  }

  public String getMessage() {
    return message;
  }
}
