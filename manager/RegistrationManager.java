import java.util.Scanner;
import java.util.HashMap;
public class RegistrationManager {
    
    private Record record;
    public RegistrationManager() {
        record = new Record();
    }

    public void registerStudentCourse() {
        Scanner sc = new Scanner(System.in);

        System.out.print("Please input Matric:");
        String s = sc.next();
        //check apakah student sudah keregister di Hashmap , ini cukup aneh but let it be
        StudentManager sManager = new StudentManager();
        sManager.loadFromTextFile();
        if(!sManager.isStudentInList(s)){
            System.out.println("Student is not registered, Add the student first.");
            return;
        }

        System.out.print("Please input Course ID:");
        int c = sc.nextInt();
        CourseManager cManager = new CourseManager();
        cManager.loadFromTextFile();
        if(!cManager.isCourseInList(c)) {
            System.out.println("Course is not registered, Add the course first.");
            return;
        }

        //display all the groups of specific courseid
        
        Course courseFound = cManager.getCourse(c);
        courseFound.printAllGroups();

        System.out.println("Which group do you want to register in ?");
        String group = sc.next();
        //register
        courseFound.register(group);
        
        //need to create record of studentId  -> ( courseObject ! i think, mark)
    }
}