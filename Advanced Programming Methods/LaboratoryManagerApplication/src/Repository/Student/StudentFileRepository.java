package Repository.Student;

import Domain.Student;
import Repository.AbstractRepositories.FileRepository;
import Validator.Validator;

public class StudentFileRepository extends FileRepository<String, Student> {
    /**
     * Constructor
     * @param fileName
     * Name of the file
     * @param validator
     * Validator for Student
     */
    public StudentFileRepository(String fileName, Validator<Student> validator) {
        super(fileName, validator);
    }
    /**
     * Method that creates a Student out of a string from the file
     * @param line
     * Student to be created
     * @return the new Student
     */
    @Override
    protected Student createEntity(String line) {
        String[] parts=line.split("/");
        return new Student(parts[0],parts[1],parts[2],parts[3],parts[4]);
    }
}
