package scrame.entity;

import java.io.Serializable;

/**
 * Faculty Member representing the faculties in school.
 */
public class FacultyMember implements Serializable {
  
  /**
   * The name of the faculty mamber.
   */
  private String name;
  
  /**
   * The id as a string of the faculty member.
   */
  private String id;

  /**
   * The faculty in which the faculty member is placed.
   */
  private String faculty;

  /**
   * Indicates if this faculty member is a course coordinator.
   */
  private boolean isCourseCoordinator;

  /**
   * Constructor for FacultyMember.
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
   * Getter method for faculty member name.
   * 
   * @return the string of get name
   */
  public String getName() {
    return this.name;
  }
}
