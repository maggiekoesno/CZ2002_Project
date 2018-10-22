public class EnumTest {
  private CourseType courseType;

  public EnumTest(CourseType courseType) {
    this.courseType = courseType;
  }

  public void showDescription() {
    switch (courseType) {
    case LEC:
      System.out.println("This course has lectures only.");
      break;

    case TUT:
      System.out.println("This course has lectures and tutorials.");
      break;

    case LAB:
      System.out.println("This course has lectures, tutorials and labs.");
      break;
    }
  }

  public static void main(String[] args) {
    EnumTest firstCourse = new EnumTest(CourseType.LEC);
    firstCourse.showDescription();
    EnumTest secondCourse = new EnumTest(CourseType.TUT);
    secondCourse.showDescription();
    EnumTest thirdCourse = new EnumTest(CourseType.LAB);
    thirdCourse.showDescription();
  }
}