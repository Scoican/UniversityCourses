package Repository.Mark;

import Domain.Mark;
import Repository.AbstractRepositories.FileRepository;
import Validator.Validator;

public class MarkFileRepository extends FileRepository<String, Mark> {
    /**
     * Constructor
     * @param fileName
     * name of the file
     * @param validator
     * Validator for Mark
     */
    public MarkFileRepository(String fileName, Validator<Mark> validator) {
        super(fileName, validator);
    }

    /**
     * Method that creates an Mark out of a string from the file
     * @param line
     * Mark to be created
     * @return the new Mark
     */
    @Override
    protected Mark createEntity(String line) {
        String[] parts=line.split("/");
        String[] idParts=parts[0].split("-");
        return new Mark(idParts[0],idParts[1],Float.parseFloat(parts[1]),parts[2]);
    }

}
