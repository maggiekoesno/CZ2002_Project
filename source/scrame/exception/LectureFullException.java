package exception;

public class LectureFullException extends Exception {
  public LectureFullException(String courseName) {
    super();
    System.out.println("Sorry, no more vacancy for " + courseName);
  }
}