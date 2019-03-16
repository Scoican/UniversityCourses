package Tests.RepositoryTests;

import Domain.Student;
import Repository.AbstractRepositories.CrudRepository;
import Repository.AbstractRepositories.InMemoryRepository;
import Validator.StudentException;
import Validator.ValidatorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class TestInMemoryRepository {

    /**
     * Testing Methods for InMemoryRepository
     */
    @Test
    void testFindOne() {
        CrudRepository<String,Student> studRepo = new InMemoryRepository<String,Student>(new StudentException());
        Student s1= new Student("1","George","george@gmail.com","Prof","222");
        try{
            studRepo.findOne(null);
            assert(true);
        }catch(IllegalArgumentException err){
            assertEquals("ID must be not null!",err.getMessage());
        }
        try{
            studRepo.findOne("");
            assert(true);
        }catch(IllegalArgumentException err){
            assertEquals("ID must be not null!",err.getMessage());
        }
        assertNull(studRepo.findOne("1"));
        studRepo.save(s1);
        assertEquals(s1,studRepo.findOne("1"));
    }

    @Test
    void testSave(){
        CrudRepository<String,Student> studRepo = new InMemoryRepository<String,Student>(new StudentException());
        Student s1= new Student("1","George","george@gmail.com","Prof","222");
        Student s2= new Student("","George","george@gmail.com","Prof","222");
        Student s3= new Student("1","George","george@gmail.com","Prof","222");
        try{
            studRepo.save(null);
            assert(true);
        }catch(IllegalArgumentException err){
            assertEquals("Entity must be not null!",err.getMessage());
        }
        try{
            studRepo.save(s2);
            assert(true);
        }catch(ValidatorException err){
            assertEquals("Date gresite:ID;",err.getMessage());
        }
        studRepo.save(s1);
        assertEquals(s1.getName(),studRepo.findOne("1").getName());
        studRepo.save(s3);
        assertEquals("George",studRepo.findOne("1").getName());
    }

    @Test
    void testDelete() {
        CrudRepository<String,Student> studRepo = new InMemoryRepository<String,Student>(new StudentException());
        Student s1= new Student("1","George","george@gmail.com","Prof","222");
        try{
            studRepo.delete(null);
            assert(true);
        }catch(IllegalArgumentException err){
            assertEquals("ID must be not null!",err.getMessage());
        }
        try{
            studRepo.delete("");
            assert(true);
        }catch(IllegalArgumentException err){
            assertEquals("ID must be not null!",err.getMessage());
        }
        studRepo.save(s1);
        assertEquals(s1,studRepo.delete("1"));
    }

    @Test
    void testUpdate() {
        CrudRepository<String,Student> studRepo = new InMemoryRepository<String,Student>(new StudentException());
        Student s1= new Student("1","George","george@gmail.com","Prof","222");
        Student s2= new Student("","George","george@gmail.com","Prof","222");
        Student s3= new Student("1","George","george@gmail.com","Prof","222");
        try{
            studRepo.update(null);
            assert(true);
        }catch(IllegalArgumentException err){
            assertEquals("Entity must be not null!",err.getMessage());
        }
        try{
            studRepo.update(s2);
            assert(true);
        }catch(ValidatorException err){
            assertEquals("Date gresite:ID;",err.getMessage());
        }
        assertEquals(s1,studRepo.update(s1));
        studRepo.save(s1);
        assertNull(studRepo.update(s3));
    }
}
