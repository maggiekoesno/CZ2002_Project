package scrame.exception;

public class IllegalVacancyException extends Exception {

  private String message;
  
  public IllegalVacancyException(String message) {
    super();
    this.message = message;
  }

  public String getMessage() {
    return message;
  }
}