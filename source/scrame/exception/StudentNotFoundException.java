package scrame.exception;

public class StudentNotFoundException extends Exception {

  private String message;

  public StudentNotFoundException() {
    super();
    this.message = "Oops, student is not registered yet!";
  }

  public String getMessage() {
    return message;
  }
}

