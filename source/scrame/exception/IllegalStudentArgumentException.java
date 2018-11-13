package scrame.exception;

public class IllegalStudentArgumentException extends Exception {

  private String message;
  
  public IllegalStudentArgumentException(String message) {
    super();
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}