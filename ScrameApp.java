public class ScrameApp {
  private int choice;
  public static void main(String[] args){
    StudentForm form = new StudentForm();
    
    StudentManager studentManager = new StudentManager();
    studentManager.loadFromTextFile();

    CourseManager courseManager = new CourseManager();
    courseManager.loadFromTextFile();

    choice = form.display();
    // app will direct the choice to appropriate manager, then it will handle it
    switch (choice) {
      case 1: // 1. Add a student
          //get the input student (name,major,enroll,matric) 
          // call the StudentManager.addStudent() or smth 
          //to add the student into the hashmap of student AND into the text file as well
          studentManager.addStudent(); //semua udh diurus di class studentManager mestinya
          break;

      case 2: // 2. Add a course
            //diurus oleh CourseManager.addCourse()
          //get the input course (name, courseType),
          //depending on the coursetype (LEC, TUT(LEC+TUT), LAB(LEC+TUT+LAB) ) we have to ask input for hashmap accordingly, if LEC ask hashmap filling once, if TUT twice(for lec and tut)
          //hashmap format {"Index": vacancies} e.g {"10174": 50}, ask until input -1 or something
          //TODO INPUT WEIGHTAGE
          courseManager.addCourse();
          break;

      case 3: // 3. Register student for a course (this include registering for Tutorial/Lab classes)
            // diurus oleh RegistrationManager.registerStudentCourse(), get input terus panggil record and isi
          //get the studentId(matric) and courseId, AND COURSE INDEX 

          //Format : HashMap<matric:String, HashMap< courseId:int, mark: //TODO object?hashmap?>
          //use the registrationMgr, get the Student instance from hashmap using the key Matric,
          //kalau ga ada : buat slot baru and register the course there, kalau ada pakai slot nya , tambahin course baru


      case 4: // 4. Check available slot in a class (vacancy in a class)
            // diurus oleh CourseManager.checkVacancies(parameter) or smth
          //get course by CourseId
          // I THINK : munculin all vacancy per index (show all the index)
          courseManager.checkVacancy();
          break;
      
      case 5: // 5. Print student list by lecture, tutorial or laboratory session for a course. //todo
            // diurus oleh StudentManager.getStudentList(paramter) or smth
          //GET THE COURSE ID FIRST, THEN DISPLAY THE AVAILABLE FILTER (by lec, or tut, or lab whatever available)
          //get the session index,(throws an error if its not in the session list)
          //filter 

      case 6: // 6. Enter course assessment components weightage
          // ??? wtf bro
          
      case 7: // 7. Enter coursework mark – inclusive of its components.
            // call the 
          // get the student id and course id
          // I THINK : iterate every weightage coursework and ask if want to input the mark ( bc could be he baru kelar quiz 1, and quiz 2 blm selesai terus profnya mau input2 aja cicil)
          // do u want to input Quiz 1? Yes/ no : Yes
          // Enter mark : 80 
          // Quiz 2 ? Yes/no : No

      case 8: // 8. Enter exam mark
            //easy, ask the studentId(matric), and courseId, print exam mark, handled by the StudentManager.getMark() or smth

      case 9: // 9. Print course statistics 
            //handled by the CourseManager.courseStatistics()
            //get the course id
            //check everyone corresponding to the courseId 
            // I THINK: get the mean, and standard deviation and draw bell curve 
      case 10: // 10. Print student transcript.
            //easy , get the studentId, handled by StudentManager.printTranscript()
            //print all courses and their marks, make it cute
    }
  }
}

//main application logic
