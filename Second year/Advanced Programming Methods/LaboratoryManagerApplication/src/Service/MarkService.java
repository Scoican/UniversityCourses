package Service;

import Domain.Mark;
import Domain.Project;
import Domain.Student;
import Repository.AbstractRepositories.CrudRepository;
import Repository.Mark.MarkXMLRepository;
import Repository.Project.ProjectXMLRepository;
import Repository.Student.StudentXMLRepository;
import Utils.ChangeEventType;
import Utils.MarkChangeEvent;
import Utils.Observable;
import Utils.Observer;
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

public class MarkService implements Observable<MarkChangeEvent> {
    private String fileNameStudent;
    private String fileNameProject;
    private String fileNameMark;
    private CrudRepository<String, Student> students;
    private CrudRepository<String, Project> projects;
    private CrudRepository<String, Mark> marks;
    private List<Observer<MarkChangeEvent>> observers=new ArrayList<>();

    public MarkService(String fileNameStudent,String fileNameProject,String fileNameMark) {
        this.fileNameStudent=fileNameStudent;
        this.fileNameProject=fileNameProject;
        this.fileNameMark=fileNameMark;
        this.students = new StudentXMLRepository(fileNameStudent,new StudentException());
        this.projects = new ProjectXMLRepository(fileNameProject,new ProjectException());
        this.marks    = new MarkXMLRepository(fileNameMark,new MarkException());
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

    public Iterable<Mark> getMarks(){
        return marks.findAll();
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
            notifyObservers(new MarkChangeEvent(ChangeEventType.ADD, mark));
            return null;
        }else{
            return mark;
        }
    }

    @Override
    public void addObserver(Observer<MarkChangeEvent> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<MarkChangeEvent> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(MarkChangeEvent gradeChangeEvent) {
        observers.forEach(x->x.update(gradeChangeEvent));
    }
}
