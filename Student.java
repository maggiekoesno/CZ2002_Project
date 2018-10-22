import java.lang.IllegalArgumentException;
import java.lang.Object;
import org.apache.commons.lang3.text.WordUtils;

public class Student {
  private String name; // Elbert Widjaja
  private String major; // Computer Science
  private String enroll; // AY1819 S1
  private String matric; // U1720411A

  public Student(String name, String major, String enroll, String matric) {
    this.name = WordUtils.capitalize(name);
    this.major = major;

    if (!enroll.matches("AY\\d{4} S[1-2]")) {
      throw new IllegalArgumentException("Oops, your enrollment seems a bit inappropriate.");
    } else {
      this.enroll = enroll;
    }

    if (!matric.matches("\\D\\d{7}\\D")) {
      throw new IllegalArgumentException("Oops, you have entered an invalid matric number.");
    } else {
      this.matric = matric;
    }
  }

  public String toString() {
    System.out.println();
  }

  public String getName() {
    return this.name;
  }

  public String getMajor() {
    return this.major;
  }

  public String getEnroll() {
    return this.enroll;
  }

  public String getMatric() {
    return this.matric;
  }

  public static void main(String[] args) {
    
  }
}