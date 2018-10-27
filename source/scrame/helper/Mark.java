package helper;

import java.util.ArrayList;

public class Mark {
  private Map<String, Float> mark;

  public Mark(ArrayList<String> component) {
    // For every pair of component and score, insert to mark.
    for (int i = 0; i < component.size(); i++) {
      this.mark.put(component.get(i), -1.0f);
    }
  }
}