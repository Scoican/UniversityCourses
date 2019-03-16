package Service;

import Domain.Mark;
import Domain.Project;
import Domain.Student;
import Repository.AbstractRepositories.CrudRepository;
import Repository.Mark.MarkXMLRepository;
import Repository.Project.ProjectXMLRepository;
import Repository.Student.StudentXMLRepository;
import Utils.*;
import Validator.MarkException;
import Validator.ProjectException;
import Validator.StudentException;
import Validator.ValidatorException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Class that uses the CRUD operations on Students and Projects
 */
public class Service implements Observable<StudentChangeEvent> {
    private String fileNameStudent;
    private String fileNameProject;
    private String fileNameMark;
    private CrudRepository<String, Student> students;
    private CrudRepository<String, Project> projects;
    private CrudRepository<String, Mark> marks;
    private List<Observer<StudentChangeEvent>> observers=new ArrayList<>();

    /**
     * Constructor
     */
    public Service(String fileNameStudent,String fileNameProject,String fileNameMark) {
        this.fileNameStudent=fileNameStudent;
        this.fileNameProject=fileNameProject;
        this.fileNameMark=fileNameMark;
        this.students = new StudentXMLRepository(fileNameStudent,new StudentException());
        this.projects = new ProjectXMLRepository(fileNameProject,new ProjectException());
        this.marks    = new MarkXMLRepository(fileNameMark,new MarkException());
    }

    /**
     * Method which adds a new student
     * @param id-string
     * id must not be null
     * @param name-string
     * @param email-string
     * @param teacher-string
     * @param group-string
     * @return null- if the given Student is saved
     * otherwise returns the Student (id already exists)
     * @throws ValidatorException
     * if the Student is not valid
     * @throws IllegalArgumentException
     * if the given Student is null. *
     */
    public Student addStudent(String id,String name,String email,String teacher,String group){
        Student student = students.save(new Student(id,name,email,teacher,group));
        if(student == null){
            notifyObservers(new StudentChangeEvent(ChangeEventType.ADD, student));
        }
        return student;
    }


    /**
     * removes the Student with the specified id
     * @param id
     * id must be not null
     * @return the removed Student or null if there is no Student with the given id
     * @throws IllegalArgumentException
     * if the given id is null.
     */
    public Student deleteStudent(String id){
        Student student =students.delete(id);
        if(student != null){
            notifyObservers(new StudentChangeEvent(ChangeEventType.DELETE, student));
        }
        return student;
    }

    /**
     * Method that modifies the arguments of a given Student
     * @param id-string
     * id must not be null
     * @param name-string
     * @param email-string
     * @param teacher-string
     * @param group-string
     * @return null - if the Student is updated,
     * otherwise returns the Student - (e.g id does not exist).
     * @throws IllegalArgumentException
     * if the given Student is null.
     * @throws ValidatorException
     * if the Student is not valid.
     */
    public Boolean updateStudent(String id, String name, String email, String teacher,String group){
        boolean updated=false;
        Student s= students.findOne(id);
        if(s==null){
            return false;
        }
        if(name.equals(""))
            name=s.getName();
        else updated=true;

        if(email.equals(""))
            email=s.getEmail();
        else updated=true;

        if(teacher.equals(""))
            teacher=s.getTeacher();
        else updated=true;

        if(group.equals(""))
            group=s.getGroup();
        else updated=true;
        Student student=students.update(new Student(id,name,email,teacher,group));
        if(student!=null){
            notifyObservers(new StudentChangeEvent(ChangeEventType.UPDATE, student));
        }
        return updated;
    }

    /**
     *
     * @param id -the id of the Student to be returned
     * id must not be null
     * @return the Student with the specified id
     * or null - if there is no Student with the given id
     * @throws IllegalArgumentException
     * if id is null.
     */
    public Student findStudent(String id){
        return students.findOne(id);
    }

    /**
     * @return all Students
     */
    public Iterable<Student> getStudents(){
        return students.findAll();
    }

    /**
     * Method that adds a new project
     * @param id-string
     * id must not be null
     * @param description-string
     * @param deadLine-Integer
     * @param currentWeek-Integer
     * @param startWeek-Integer
     * @return null- if the given Project is saved
     * otherwise returns the Project (id already exists)
     * @throws ValidatorException
     * if the Project is not valid
     * @throws IllegalArgumentException
     * if the given Project is null. *
     */
    public Project addProject(String id,String description,Integer deadLine,Integer currentWeek,Integer startWeek){
        return projects.save(new Project(id,description,deadLine,currentWeek,startWeek));
    }

    /**
     *
     * @param id -the id of the Project to be returned
     * id must not be null
     * @return the Project with the specified id
     * or null - if there is no Project with the given id
     * @throws IllegalArgumentException
     * if id is null.
     */
    public Project findProject(String id){
        return projects.findOne(id);
    }

    /**
     * Method that returns the number of Projects
     * @return the number of projects
     */
    public AtomicInteger sizeProject(){
        AtomicInteger size= new AtomicInteger();
        Iterable<Project> list= getProjects();
        list.forEach(x-> size.getAndIncrement());
        return size;
    }

    /**
     * Method that returns the number of Students
     * @return the number of Students
     */
    public AtomicInteger sizeStudent(){
        AtomicInteger size= new AtomicInteger();
        Iterable<Student> list= getStudents();
        list.forEach(x-> size.getAndIncrement());
        return size;
    }

    /**
     * @return all Projects
     */
    public Iterable<Project> getProjects(){
        return projects.findAll();
    }

    /**
     * removes the Project with the specified id
     * @param id
     * id must be not null
     * @return the removed Project or null if there is no Project with the given id
     * @throws IllegalArgumentException
     * if the given id is null.
     */
    public Project deleteProject(String id){
        return projects.delete(id);
    }

    /**
     * Method that extends the deadline of a project with a given number of weeks
     * @param id
     * id must not be null
     * @param nrOfWeeks-number of weeks to extend with
     * @return true if the extension was successful
     *         false otherwise
     */
    public boolean extendDeadLine(String id,Integer nrOfWeeks){
        Project project=projects.findOne(id);
        if(project!=null) {
            if (project.getCurrentWeek() <= project.getDeadLine()) {
                if (project.getDeadLine() + nrOfWeeks <= 14) {
                    project.setDeadLine(project.getDeadLine() + nrOfWeeks);
                    projects.update(project);
                    return true;
                } else return false;
            }
            return false;
        }
        return false;
    }

    /**
     * Method that calculates the maximum mark of a Student at the Project with the matching ID
     * @param projectId
     * projectId must not be null
     * @return the maximum mark
     */
    public Float maxMark(String projectId){
        float value=10f;
        Project project=projects.findOne(projectId);
        int diff=project.getCurrentWeek()-project.getDeadLine();
        if(diff<=2&&diff>=0){
            value=value-diff*2.5f;
        }
        if(diff>2){
            value=1f;
        }
        return value;
    }

    /**
     * Method that checks if the IDs of the student and project exist
     * @param studentId
     * studentId must not be null
     * @param projectId
     * projectId must not be null
     * @return  true if the IDs are found
     *          false otherwise
     */
    public boolean checkMarkId(String studentId,String projectId){
        boolean check=true;
        if(students.findOne(studentId)==null){
            check=false;
        }
        if(projects.findOne(projectId)==null){
            check=false;
        }
        return check;
    }

    /**
     * Method that creates and writes a file for a Student at a specific project
     * @param mark-the Mark received by the Student
     */
    private void writeFileMark(Mark mark){

        String[] split=mark.getID().split("-");
        Student student=students.findOne(split[0]);
        Project project=projects.findOne(split[1]);
        String path = "./src/Service/"+student.getName()+".txt";
        File file = new File(path);
        //file.getParentFile().mkdir();
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(path, true))) {
            bw.write("Project ID:"+project.getID());
            bw.newLine();
            bw.write("Mark:"+mark.getValue());
            bw.newLine();
            bw.write("Finished in week number:"+project.getCurrentWeek());
            bw.newLine();
            bw.write("Deadline:"+project.getDeadLine());
            bw.newLine();
            bw.write("Feedback:"+mark.getFeedback());
            bw.newLine();
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * saves the entity with the specified id
     * @param studentId
     * studentId must not be null
     * @param projectId
     * projectId must be not null
     * @param value
     * the value of the mark
     * @param feedback
     * the feedback the mark
     * @return null- if the given entity is saved
     * otherwise returns the entity (id already exists)
     * @throws ValidatorException
     * if the entity is not valid
     * @throws IllegalArgumentException
     * if the given id is null.
     */
    public Mark addMark(String studentId,String projectId,Float value,String feedback){
        Mark mark=new Mark(studentId,projectId,value, feedback);
        if(marks.save(mark)==null){
            writeFileMark(mark);
            return null;
        }else{
            return mark;
        }
    }

    @Override
    public void addObserver(Observer<StudentChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<StudentChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(StudentChangeEvent studentChangeEvent) {
        observers.forEach(x->x.update(studentChangeEvent));
    }

}
