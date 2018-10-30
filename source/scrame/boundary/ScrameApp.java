package scrame.boundary;

import java.util.Scanner;

import scrame.boundary.AdminForm;
import scrame.boundary.StudentForm;
import scrame.manager.RecordManager;
import scrame.manager.StudentManager;
import scrame.manager.CourseManager;

public class ScrameApp {

  public static void main(String[] args) {
    StudentForm studentForm = new StudentForm();
    AdminForm adminForm = new AdminForm();
    StudentManager.loadFromFile();
    CourseManager.loadFromFile();
    RecordManager.loadFromFile();

    Scanner sc = new Scanner(System.in);

    //input admin or student
    System.out.println(
      "WELCOME TO SCRAME!, please login, input \"admin\" for admin mode, or please input your student matric "
    ); // beautify
    String username = sc.next();

    int choice;
    if (username != "admin") {
      if (!StudentManager.isStudentInList(username)) {
        System.out.println("Oops, student is not in the list, please try again.");
      } else {
        choice = studentForm.display();
      }
    } else {
      choice = adminForm.display();
    }
    // app will direct the choice to appropriate manager, then it will handle it

    switch (choice) {
      case 1:
        RecordManager.registerStudentCourse();
        break;

      case 2:
        CourseManager.checkVacancy();
        break;

      case 3:
        StudentManager.printTranscript();
        break;

      case 4:
        StudentManager.addStudent(); 
        break;

      case 5:
        CourseManager.addCourse();
        break;

      case 6:
        StudentManager.printStudentList();
        break;

      case 7:// 6. Enter course assessment components weightage
        CourseManager.setCourseWeightage();
        break;

      case 8:// 7. Enter coursework mark – inclusive of its components.
      // call the
      // get the student id and course id
      // I THINK : iterate every weightage coursework and ask if want to input the mark ( bc could be he baru kelar quiz 1, and quiz 2 blm selesai terus profnya mau input2 aja cicil)
      // do u want to input Quiz 1? Yes/ no : Yes
      // Enter mark : 80
      // Quiz 2 ? Yes/no : No
        RecordManager.setCourseworkMark();
        break;

      case 9:// 8. Enter exam mark
      //easy, ask the studentId(matric), and courseId, print exam mark, handled by the StudentManager.getMark() or smth
        RecordManager.setExamMark();
        break;

      case 10: // Print course statistics


      //input to file
    }
  }

}

