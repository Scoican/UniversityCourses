package Tests.ValidatorTests;

import Domain.Student;
import Validator.StudentException;
import Validator.ValidatorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestStudentException {

    /**
     * Method that tests the class StudentException
     */
    @Test
    void testStudentException() {
        StudentException exc= new StudentException();
        Student s1= new Student("","George","george@gmail.com","Prof","222");
        Student s2= new Student("1","","george@gmail.com","Prof","222");
        Student s3= new Student("1","George","","Prof","222");
        Student s4= new Student("1","George","george@gmail.com","","222");
        Student s5= new Student("1","George","george@gmail.com","Prof","");
        Student s6= new Student("","","","","");
        boolean err=false;
        try{
            exc.validate(s1);
        }
        catch(ValidatorException e){
            assertEquals("Date gresite:ID;",e.getMessage());
            err=true;
        }
        assertTrue(err);
        err=false;
        try{
            exc.validate(s2);
        }
        catch(ValidatorException e){
            assertEquals("Date gresite:Name;",e.getMessage());
            err=true;
        }
        assertTrue(err);
        err=false;
        try{
            exc.validate(s3);
        }
        catch(ValidatorException e){
            assertEquals("Date gresite:Email;",e.getMessage());
            err=true;
        }
        assertTrue(err);
        err=false;
        try{
            exc.validate(s4);
        }
        catch(ValidatorException e){
            assertEquals("Date gresite:Teacher;",e.getMessage());
            err=true;
        }
        assertTrue(err);
        err=false;
        try{
            exc.validate(s5);
        }
        catch(ValidatorException e){
            assertEquals("Date gresite:Group;",e.getMessage());
            err=true;
        }
        assertTrue(err);
        err=false;
        try{
            exc.validate(s6);
        }
        catch(ValidatorException e){
            assertEquals("Date gresite:ID;Email;Name;Teacher;Group;",e.getMessage());
            err=true;
        }
        assertTrue(err);

    }

}

