package Tests.ValidatorTests;

import Domain.Mark;
import Validator.MarkException;
import Validator.ValidatorException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class TestMarkException {

    /**
     * Method that tests the class MarkException
     */
    @Test
    void testMarkException(){
        MarkException exc=new MarkException();
        Mark m1=new Mark("1","1",-1f,"asd");
        Mark m2=new Mark("2","2",null,"asd");
        Mark m3=new Mark("3","3",10f,"");


        boolean err=false;
        try{
            exc.validate(m1);
        }catch (ValidatorException e){
            assertEquals("Wrong info:Value;",e.getMessage());
            err=true;
        }
        assertTrue(err);
        err=false;
        try{
            exc.validate(m2);
        }catch (ValidatorException e){
            assertEquals("Wrong info:Value;",e.getMessage());
            err=true;
        }
        assertTrue(err);
        err=false;
        try{
            exc.validate(m3);
        }catch (ValidatorException e){
            assertEquals("Wrong info:Feedback;",e.getMessage());
            err=true;
        }
        assertTrue(err);



    }
}
