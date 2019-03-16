package UserInterface;

import Domain.Project;
import Domain.Student;
import Service.MarkService;
import Service.ProjectService;
import Service.StudentService;
import Validator.ValidatorException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * User Interface class
 */
public class UI {
    private StudentService studService;
    private ProjectService projectService;
    private MarkService markService;
    private BufferedReader br=new BufferedReader(new InputStreamReader(System.in));

    /**
     * Constructor
     */
    public UI() {
        this.studService=new StudentService("./src/Data/Students.xml");
        this.projectService=new ProjectService("./src/Data/Projects.xml");
        this.markService=new MarkService("./src/Data/Students.xml","./src/Data/Projects.xml","./src/Data/Marks.xml");

    }

    /**
     * Method that reads data and adds a new Student
     */
    private void uiAddStudent(){
        String id;
        String name;
        String email;
        String teacher;
        String group;
        try {
            System.out.print("ID:");
            id = br.readLine();
            System.out.print("Name:");
            name = br.readLine();
            System.out.print("Email:");
            email = br.readLine();
            System.out.print("Teacher:");
            teacher = br.readLine();
            System.out.print("Group:");
            group = br.readLine();
           // Optional<Student> optional= Optional.ofNullable(service.addStudent(id, name, email, teacher, group));
           // optional.ifPresent(value->{System.out.println("Student already exists!");});
            if(studService.addStudent(id,name,email,teacher,group)!=null){
                System.out.println("Student already exists!");
            }
            else{
                System.out.println("Student added successfully");
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch(ValidatorException err){
            System.out.println(err.getMessage());
        }
    }
    /**
     * Method that reads data and adds a new Project
     */
    private void uiAddProject(){
        String id;
        String description;
        String deadline;
        String currentWeek;
        String startWeek;
        try {
            System.out.print("ID:");
            id = br.readLine();
            System.out.print("Description:");
            description = br.readLine();
            System.out.print("Deadline:");
            deadline = br.readLine();
            System.out.print("Current week:");
            currentWeek = br.readLine();
            System.out.print("Start week:");
            startWeek = br.readLine();
            if(projectService.addProject(id,description,Integer.parseInt(deadline),Integer.parseInt(currentWeek),Integer.parseInt(startWeek))!=null){
                System.out.println("Project already exists!");
            }
            else{
                System.out.println("Project added successfully");
            }
        }catch (IOException e){
            e.printStackTrace();
        }catch(ValidatorException err){
            System.out.println(err.getMessage());
        }catch(NumberFormatException err){
            System.out.println("Please insert a number!");
        }
    }

    /**
     * Method that reads an ID and removes the Student with that ID
     */
    private void uiDeleteStudent(){
        String id;
        try{
            System.out.print("ID:");
            id=br.readLine();
            if(studService.deleteStudent(id)==null){
                System.out.println("Student not existing");
            }
            else{
                System.out.println("Student removed successfully");
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch(ValidatorException err){
            System.out.println(err.getMessage());
        }
    }

    /**
     * Method that reads an ID and removes the Project with that ID
     */
    private void uiDeleteProject(){
        String id;
        try{
            System.out.print("ID:");
            id=br.readLine();
            if(projectService.deleteProject(id)==null){
                System.out.println("Project not existing");
            }
            else{
                System.out.println("Project removed successfully");
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch(ValidatorException err){
            System.out.println(err.getMessage());
        }
    }

    /**
     * Method that reads data and updates a student with the new data
     */
    private void uiUpdateStudent(){
        String id;
        String name;
        String email;
        String teacher;
        String group;
        try {
            System.out.print("ID:");
            id = br.readLine();
            System.out.print("Name:");
            name = br.readLine();
            System.out.print("Email:");
            email = br.readLine();
            System.out.print("Teacher:");
            teacher = br.readLine();
            System.out.print("Group:");
            group = br.readLine();
            if(!studService.updateStudent(id, name, email, teacher, group)) {
                System.out.println("Student update failed");
            }else{
                System.out.println("Student update successfully");
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch(ValidatorException err){
            System.out.println(err.getMessage());
        }
    }

    /**
     * Method that extends the deadline of a project
     */
    private void uiExtendDeadline(){
        String id;
        String newDeadline;
        try{
            System.out.print("ID:");
            id=br.readLine();
            System.out.print("Number of weeks for extension:");
            newDeadline=br.readLine();
            if(!projectService.extendDeadLine(id, Integer.parseInt(newDeadline))){
                System.out.print("Failed to extend deadline!");
            }else{
                System.out.println("Extended deadline!");
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch(ValidatorException err){
            System.out.println(err.getMessage());
        }catch(NumberFormatException err){
            System.out.println("Please insert a number!");
        }
    }

    /**
     * Method that prints a list of all Students
     */
    private void uiPrintStudents(){
        Iterable<Student> list= studService.getStudents();
        list.forEach(x-> System.out.println(x.toString()));
        if(studService.sizeStudent().get()==0){
            System.out.println("There are no students at the moment");
        }
    }

    /**
     * Method that prints a list of all Project
     */
    private void uiPrintProjects(){
        Iterable<Project> list= projectService.getProjects();
        list.forEach(x-> System.out.println(x.toString()));
        if(projectService.sizeProject().get()==0){
            System.out.println("There are no projects at the moment");
        }
    }

    /**
     * Method that reads data and adds a new Mark
     */
    private void uiAddMark(){
        String studentId;
        String projectId;
        String value;
        String feedback;
        try {
            System.out.print("Student Id:");
            studentId = br.readLine();
            System.out.print("Project Id:");
            projectId = br.readLine();
            if(!markService.checkMarkId(studentId, projectId)){
                System.out.println("Invalid student or project");
                return;
            }
            System.out.println("Maximum mark for this student at the project is:"+ markService.maxMark(projectId).toString());
            System.out.print("Mark:");
            value = br.readLine();
            System.out.print("Feedback:");
            feedback=br.readLine();
            if(markService.addMark(studentId,projectId,Float.parseFloat(value),feedback)!=null||Float.parseFloat(value)> markService.maxMark(projectId)){
                System.out.println("Mark assignment failed");
            }else {
                System.out.println("Mark assignment successfully");
            }
        }catch(IOException e){
            e.printStackTrace();
        }catch(ValidatorException err){
            System.out.println(err.getMessage());
        }catch(NumberFormatException err){
            System.out.println("Please insert a number!");
        }
    }

    /**
     * Method that prints all the commands available for the menu
     */
    private void printMenu(){
        System.out.println("Welcome!");
        System.out.println("1.Add a new student");
        System.out.println("2.Add a new project");
        System.out.println("3.Remove a student");
        System.out.println("4.Remove a project");
        System.out.println("5.Update a student");
        System.out.println("6.Extend deadline on a project");
        System.out.println("7.Print all students");
        System.out.println("8.Print all projects");
        System.out.println("9.Give a mark to a student");
        System.out.println("0.End program");
    }

    /**
     * Method which reads a number from the console and calls a specific command
     */
    public void run(){
        boolean running = true;
        printMenu();
        while(running){
            String cmd;
            System.out.print("Please insert a command:");
            try {
                cmd=br.readLine();
            } catch (IOException e) {
                System.out.println("Invalid command!");
                continue;
            }
            switch(cmd){
                case "0":
                    System.out.println("Program ended");
                    running=false;
                    break;
                case "1":
                    uiAddStudent();
                    break;
                case "2":
                    uiAddProject();
                    break;
                case "3":
                    uiDeleteStudent();
                    break;
                case "4":
                    uiDeleteProject();
                    break;
                case "5":
                    uiUpdateStudent();
                    break;
                case "6":
                    uiExtendDeadline();
                    break;
                case "7":
                    uiPrintStudents();
                    break;
                case "8":
                    uiPrintProjects();
                    break;
                case "9":
                    uiAddMark();
                    break;
                default:
                    System.out.println("Invalid command!");
                    break;
            }
        }
    }
}
