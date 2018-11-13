package scrame.entity;

import java.io.Serializable;

public class FacultyMember implements Serializable {
  private String name;
  private String id;
  private String faculty;
  private boolean isCourseCoordinator;

  /**
   * Constructor for FacultyMember.
   * 
   * @param name     name of the faculty member
   * @param id       id of the faculty member
   * @param faculty  faculty of the faculty member
   * 
   */
  public FacultyMember(String name, String id, String faculty) {
    this.name = name;
    this.id = id;
    this.faculty = faculty;
    this.isCourseCoordinator = false;
  }

  /**
   * Overloaded constructor for FacultyMember.
   * 
   * @param name                 name of the faculty member
   * @param id                   id of the faculty member
   * @param faculty              faculty of the faculty member
   * @param isCourseCoordinator  true if faculty member is course coordinator, false otherwise
   */
  public FacultyMember(String name, String id, String faculty, boolean isCourseCoordinator) {
    this.name = name;
    this.id = id;
    this.faculty = faculty;
    this.isCourseCoordinator = isCourseCoordinator;
  }

  /**
   * Getter method for faculty member name
   */
  public String getName() {
    return this.name;
  }

  /**
   * Getter method for faculty Id
   */
  public String getId() {
    return this.id;
  }

  /**
   * Getter method for faculty name
   */
  public String getFaculty() {
    return this.faculty;
  }

  /**
   * Setter method for faculty id
   */
  public void setId(String id) {
    this.id = id;
  }

  /**
   * Check whether faculty member is the course coordinator
   */
  public boolean getIsCourseCoordinator() {
    return this.isCourseCoordinator;
  }

  /**
   * Setter method for faculty name
   */
  public void setFaculty(String faculty) {
    this.faculty = faculty;
  }

  /**
   * Setter method for coursecoordinator
   */
  public void setIsCourseCoodinator(boolean isCourseCoordinator) {
    this.isCourseCoordinator = isCourseCoordinator;
  }

  /**
   * Setter method for faculty member name
   */
  public void setName(String name) {
    this.name = name;
  }
}
