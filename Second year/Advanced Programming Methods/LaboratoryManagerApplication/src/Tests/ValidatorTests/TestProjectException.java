package Tests.ValidatorTests;

import Domain.Project;
import Validator.ProjectException;
import Validator.ValidatorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestProjectException {

    /**
     * Method that tests the class ProjectException
     */
    @Test
    void testProjectException() {
        ProjectException exc= new ProjectException();
        Project p1=new Project("","asd",2,1,1);
        Project p2=new Project("1","",2,1,1);
        Project p3=new Project("2","asd",0,1,-1);
        Project p4=new Project("3","asd",2,0,1);
        Project p5=new Project("4","asd",2,1,0);
        Project p6=new Project("","",0,0,0);

        boolean err=false;
        try{
            exc.validate(p1);
        }
        catch(ValidatorException e){
            assertEquals("Date gresite:ID;",e.getMessage());
            err=true;
        }
        assertTrue(err);
        err=false;
        try{
            exc.validate(p2);
        }
        catch(ValidatorException e){
            assertEquals("Date gresite:Description;",e.getMessage());
            err=true;
        }
        assertTrue(err);
        err=false;
        try{
            exc.validate(p3);
        }
        catch(ValidatorException e){
            assertEquals("Date gresite:Deadline;StartWeek;",e.getMessage());
            err=true;
        }
        assertTrue(err);
        err=false;
        try{
            exc.validate(p4);
        }
        catch(ValidatorException e){
            assertEquals("Date gresite:CurrentWeek;",e.getMessage());
            err=true;
        }
        assertTrue(err);
        err=false;
        try{
            exc.validate(p5);
        }
        catch(ValidatorException e){
            assertEquals("Date gresite:StartWeek;",e.getMessage());
            err=true;
        }
        assertTrue(err);
        err=false;
        try{
            exc.validate(p6);
        }
        catch(ValidatorException e){
            assertEquals("Date gresite:ID;Description;CurrentWeek;Deadline;StartWeek;Deadline doesn't match with start week;",e.getMessage());
            err=true;
        }
        assertTrue(err);
    }
}
