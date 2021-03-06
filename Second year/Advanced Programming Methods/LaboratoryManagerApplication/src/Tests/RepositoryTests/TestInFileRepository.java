package Tests.RepositoryTests;

import Domain.Student;
import Repository.AbstractRepositories.CrudRepository;
import Repository.Student.StudentFileRepository;
import Validator.StudentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestInFileRepository {
    private CrudRepository<String, Student> repo=new StudentFileRepository("./src/Tests/TestingData/Students.txt", new StudentException());
    private Student s1=new Student("9999","Claudia","asd@gmail.com","Cineva","227");
    private Student s2=new Student("1001","Elena","asd@gmail.com","Cineva","227");

    /**
     * Testing methods for InFileRepository
     */
    @Test
    void save() {
        try{
            repo.save(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Entity must be not null!", e.getMessage());
        }
        repo.delete("9999");
        assertNull(repo.save(s1));
        repo.delete("9999");
        assertEquals(s2,repo.save(s2));

    }

    @Test
    void findOne() {
        try{
            repo.findOne(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("ID must be not null!", e.getMessage());
        }
        assertNull(repo.findOne("1010"));
        repo.save(s1);
        assertEquals(s1,repo.findOne("9999"));
    }

    @Test
    void findAll() {
        int size=0;
        for(Student s:repo.findAll()) size++;
        assertEquals(5,size);
    }

    @Test
    void delete() {
        try{
            repo.delete(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("ID must be not null!", e.getMessage());
        }
        repo.save(s1);
        assertNull(repo.delete("5"));
        assertEquals(s1,repo.delete("9999"));
    }

    @Test
    void update() {
        try{
            repo.update(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Entity must be not null!", e.getMessage());
        }
        repo.save(s1);
        assertNull(repo.update(s1));
        Student s3=new Student("1011","Elena","asd@gmail.com","Somebody","223");

        assertEquals(s3,repo.update(s3));
    }
}
