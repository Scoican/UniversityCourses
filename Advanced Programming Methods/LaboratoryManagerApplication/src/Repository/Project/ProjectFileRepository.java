package Repository.Project;

import Domain.Project;
import Repository.AbstractRepositories.FileRepository;
import Validator.Validator;

public class ProjectFileRepository extends FileRepository<String, Project> {
    /**
     * Constructor
     * @param fileName
     * name of the file
     * @param validator
     * Validator for Project
     */
    public ProjectFileRepository(String fileName, Validator<Project> validator) {
        super(fileName, validator);
    }

    /**
     * Method that creates an Project out of a string from the file
     * @param line
     * Project to be created
     * @return the new Project
     */
    @Override
    protected Project createEntity(String line) {
        String[] parts=line.split("/");
        return new Project(parts[0],parts[1],Integer.parseInt(parts[2]),Integer.parseInt(parts[3]),Integer.parseInt(parts[4]));
    }
}
