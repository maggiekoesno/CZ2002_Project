public class FacultyMember {
  private String name;
  private String id;
  private String faculty;
  private boolean isCourseCoordinator;

  public FacultyMember(String name, String id, String faculty, boolean isCourseCoordinator) {
    this.name = name;
    this.id = id;
    this.faculty = faculty;
    this.isCourseCoordinator = isCourseCoordinator;
  }

  public FacultyMember(String name, String id, String faculty) {
    this.name = name;
    this.id = id;
    this.faculty = faculty;
    this.isCourseCoordinator = false;
  }
  // Debug purposes
  // public static void main(String[] args){
  //   FacultyMember test = new FacultyMember("Elbert", "U1720411A", "CSC", true);
  //   FacultyMember test2 = new FacultyMember("Jason", "U1231244B", "MAS");
  //   System.out.println(test.name + test.id + test.faculty + test.isCourseCoordinator);
  //   System.out.println(test2.name + test2.id + test2.faculty + test2.isCourseCoordinator);
  // }
  public String getName() {
    return this.name;
  }

  public String getId() {
    return this.id;
  }

  public String getFaculty() {
    return this.faculty;
  }

  public void setId(String id) {
    this.id = id;
  }

  public boolean getIsCourseCoordinator() {
    return this.isCourseCoordinator;
  }

  public void setFaculty(String faculty) {
    this.faculty = faculty;
  }

  public void setIsCourseCoodinator(boolean isCourseCoordinator) {
    this.isCourseCoordinator = isCourseCoordinator;
  }

  public void setName(String name) {
    this.name = name;
  }

}