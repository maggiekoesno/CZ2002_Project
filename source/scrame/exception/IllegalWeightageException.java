package scrame.exception;

public class IllegalWeightageException extends Exception {

  private String message;
  
  public IllegalWeightageException(String message) {
    super();
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}
