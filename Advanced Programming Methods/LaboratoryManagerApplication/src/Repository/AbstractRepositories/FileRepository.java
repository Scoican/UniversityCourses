package Repository.AbstractRepositories;

import Domain.HasID;
import Validator.Validator;

import java.io.*;

public abstract class FileRepository<ID, E extends HasID<ID>> extends InMemoryRepository<ID,E> {
    private String fileName;

    /**
     * Constructor
     * @param fileName
     * name of the file in use
     * @param validator
     * validator for the entities of the file
     */
    public FileRepository(String fileName, Validator<E> validator) {
        super(validator);
        this.fileName=fileName;
        this.loadData();

    }

    /**
     * Method that reads and loads all the data from a file,creating entities
     */
    private void loadData(){
        try (BufferedReader br = new BufferedReader(new FileReader(this.fileName))) {
            String line;
            while((line=br.readLine())!=null){
                E e = createEntity(line);
                super.save(e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that writes an Entity in the file in use
     * @param entity
     * entity to write
     */
    private void saveEntity(E entity){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileName, true))) {
            bw.write(entity.toFile());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     *
     * @param entity
     * entity must be not null
     * @return null- if the given entity is saved
     * otherwise returns the entity (id already exists)
     * @throws IllegalArgumentException
     * if the given entity is null. *
     */
    @Override
    public E save(E entity) {
        E aux=super.save(entity);
        if(aux==null) {
            saveEntity(entity);
        }
        return aux;
    }

    /**
     * Abstract method that creates an entity out of a string from the file
     * @param line
     * entity to be created
     * @return the new Entity
     */
    protected abstract E createEntity(String line);

    /**
     * removes the entity with the specified id
     * @param id
     * id must be not null
     * @return the removed entity or null if there is no entity with the given id
     * @throws IllegalArgumentException
     * if the given id is null.
     */
    @Override
    public E delete(ID id) {
        E temp=super.delete(id);
        if(temp!=null) {
            writeAll();
        }
        return temp;
    }

    /**
     *
     * @param entity
     * entity must not be null
     * @return null - if the entity is updated,
     * otherwise returns the entity - (e.g id does not exist).
     * @throws IllegalArgumentException
     * if the given entity is null.
     */
    @Override
    public E update(E entity) {
        E temp=super.update(entity);
        if(temp==null){
            writeAll();
            return null;
        }
        return entity;
    }

    /**
     * Method that writes an entity to the file
     * @param entity
     * Entity to be put into the file
     */
    private void writeToFile(E entity){
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(this.fileName,true))) {
            bw.write(entity.toFile());
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method that writes all entities to the file
     */
    private void writeAll(){
        try (BufferedWriter ignored = new BufferedWriter(new FileWriter(this.fileName))) {
            Iterable<E> values=entities.values();
            values.forEach(this::writeToFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
