package Service;

import Domain.Student;
import Repository.AbstractRepositories.CrudRepository;
import Repository.Student.StudentXMLRepository;
import Utils.ChangeEventType;
import Utils.Observable;
import Utils.Observer;
import Utils.StudentChangeEvent;
import Validator.StudentException;
import Validator.ValidatorException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class StudentService implements Observable<StudentChangeEvent> {
    private String fileNameStudent;
    private CrudRepository<String, Student> students;
    private List<Observer<StudentChangeEvent>> observers=new ArrayList<>();

    public StudentService(String fileNameStudent) {
        this.fileNameStudent = fileNameStudent;
        this.students = new StudentXMLRepository(fileNameStudent, new StudentException());
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

    public AtomicInteger sizeStudent(){
        AtomicInteger size= new AtomicInteger();
        Iterable<Student> list= getStudents();
        list.forEach(x-> size.getAndIncrement());
        return size;
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
