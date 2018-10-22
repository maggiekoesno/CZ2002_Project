import java.io.FileReader;
import java.io.BufferedReader;
import java.nio.file.Files;
import java.util.HashSet;
import java.util.Scanner;

public class CourseManager {
    private HashSet<Course> courseList;

    private static String fileName = "courses.txt"; // The name of the file to open.

    public Course getCourse(int courseId) {
        Iterator value = courseList.iterator();
        Course courseFound = null;
        while (value.hashNext()) {
            if (value.next().getCourseId() == courseId) {
                courseFound = tmp;
                break;
            }
        }       
        return courseFound;                                                        
    }

    public void loadFromTextFile() {
      String line = null;
  
      try {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(fileName));
  
        while ((line = bufferedReader.readLine()) != null) {
          line.split(",");
          courseList.add(new Course(line[0], courseTypeToEnum(line[1])));
        }
  
        bufferedReader.close();
      } catch (FileNotFoundException ex) {
        System.out.println("Unable to open file '" + fileName + "'");
      } catch (IOException ex) {
        System.out.println("Error reading file '" + fileName + "'");
      }
    }
  
    public void addCourse() {
      Scanner sc = new Scanner(System.in);

      System.out.print("Enter new course name: ");
      String courseName = sc.next();
      System.out.print("Enter course type: ");
      String tempCourseType = sc.next();
  
      CourseType courseType = courseTypeToEnum(tempCourseType);
      Course course = new Course(courseName, courseType);
      courseList.add(course);
  
      String myText = "\n" + courseName + "," + courseType;
  
      try {
        Files.write(Paths.get(filename), myText.getBytes(), StandardOpenOption.APPEND);
        System.out.println("Course added successfully.");
      } catch (IOException e) {
        System.out.println("Unable to write new student into file.");
      }
    }
    
    private CourseType courseTypeToEnum(String tempCourseType) {
      CourseType courseType;      
      
      switch (tempCourseType.toUpperCase()) {
        case "LEC":
          courseType = CourseType.LEC;
          break;

        case "TUT":
          courseType = CourseType.TUT;
          break;

        case "LAB":
          courseType = CourseType.LAB;
          break;

        default:
          throw new IllegalCourseTypeException("Course type input is invalid.");
      }

      return courseType;
    }

    public boolean isCourseInList(int courseId) {
        Iterator value = courseList.iterator();
        Course courseFound = null;
        while (value.hashNext()) {
            Course tmp = value.next();
            if (tmp.getCourseId() == courseId) {
                courseFound = tmp;
                break;
            }
        }
        return courseFound.getCourseId() == courseId;
    }

    public void checkVacancy() {
        Scanner sc = new Scanner(System.in);
        Iterator value = courseList.iterator();

        System.out.print("Enter course ID: ");
        int courseID = sc.nextInt();

        if (!courseList.contains(courseID)) {
            System.out.println("Course ID invalid!");
            return;
        }

        System.out.println();
        System.out.println("++++++++++++++++++++++++++++++");
        System.out.println("++++++ Course Vacancies ++++++");
        System.out.println("++++++++++++++++++++++++++++++");

        while (value.hashNext()) {
            Course tmp = value.next();
            int vacancy;

            if (tmp.getCourseId() == courseId) {
                CourseType type = tmp.getCourseType();
                switch(type){
                    case "LEC":
                        System.out.println("++ Course Name ++++ Vacancy ++");
                        System.out.println("++++++++++++++++++++++++++++++");
                        System.out.print("++   " + tmp.getCourseName() + "    ++++   ");
                        System.out.printf("%3d", tmp.checkLectureVacancy());
                        System.out.println("   ++");
                        System.out.println("++++++++++++++++++++++++++++++");
                        break;
                    case "TUT":
                    case "LAB":
                        HashMap<String,Integer> groups = tmp.getTutLabGroups();

                        System.out.println("++ Group Name +++++ Vacancy ++");
                        System.out.println("++++++++++++++++++++++++++++++");

                        for (Map.Entry<String, Integer> entry : groups.entrySet()) {
                            System.out.print("++    " + entry.getKey() + "     +++++    ");
                            System.out.printf("%2d", entry.getValue());
                            System.out.println("   ++");     
                        }
                        
                        System.out.println("++++++++++++++++++++++++++++++");
                        break;
                }
                break;
            }
        }
    }
}