package scrame.boundary;

/**
 * An interface for abstraction between AdminForm and StudentForm.
 */
public interface Form {
  
  /**
   * Shows information of choice allowable.
   */
  public abstract void showInformation();

  /**
   * Shows and asks choice from user.
   * 
   * @return int of chosen choice
   */
  public abstract int display();

  /**
   * Validates whether the chosen choice is permissible.
   * 
   * @param choice from display 
   * @return       boolean whether it is permissible or not
   */
  public abstract boolean validateChoice(int choice);
}