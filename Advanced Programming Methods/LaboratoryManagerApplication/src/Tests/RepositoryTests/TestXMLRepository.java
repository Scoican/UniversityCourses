package Tests.RepositoryTests;

import Domain.Student;
import Repository.AbstractRepositories.CrudRepository;
import Repository.Student.StudentXMLRepository;
import Validator.StudentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class TestXMLRepository {
    private CrudRepository<String, Student> repoXML =new StudentXMLRepository("./src/Tests/TestingData/Students.xml", new StudentException());
    private Student s1=new Student("9999","Claudia","asd@gmail.com","Cineva","227");
    private Student s2=new Student("1001","Elena","asd@gmail.com","Cineva","227");

    /**
     * Testing methods for XMLRepository
     */
    @Test
    void save() {
        repoXML.delete("9999");
        assertNull(repoXML.save(s1));
        repoXML.delete("9999");
        repoXML.save(s2);
        assertEquals(s2, repoXML.save(s2));
        repoXML.delete(s2.getID());
        try{
            repoXML.save(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Entity must be not null!", e.getMessage());
        }
    }

    @Test
    void findOne() {
        try{
            repoXML.findOne(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("ID must be not null!", e.getMessage());
        }
        assertNull(repoXML.findOne("1010"));
        repoXML.save(s1);
        assertEquals(s1, repoXML.findOne("9999"));
    }

    @Test
    void findAll() {
        int size=0;
        for(Student s: repoXML.findAll()) size++;
        assertEquals(4,size);
    }

    @Test
    void delete() {
        try{
            repoXML.delete(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("ID must be not null!", e.getMessage());
        }
        repoXML.save(s1);
        assertNull(repoXML.delete("1234"));
        assertEquals(s1, repoXML.delete("9999"));
    }

    @Test
    void update() {
        repoXML.save(s1);
        try{
            repoXML.update(null);
        }
        catch (IllegalArgumentException e){
            assertEquals("Entity must be not null!", e.getMessage());
        }
        assertNull(repoXML.update(s1));
        Student s=new Student("1011","Elena","asd@gmail.com","Somebody","223");

        assertEquals(s, repoXML.update(s));
    }
}
