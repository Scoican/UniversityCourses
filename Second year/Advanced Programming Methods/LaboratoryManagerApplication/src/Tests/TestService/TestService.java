package Tests.TestService;


import Service.Service;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TestService {

    /**
     * Testing methods for Service
     */
    @Test
    void testUpdateStudent(){
        Service service=new Service("./src/Tests/TestingData/Students.xml","./src/Tests/TestingData/Projects.xml","./src/Tests/TestingData/Marks.xml");
        service.addStudent("1","George","george@gmail.com","Prof","222");
        assertEquals(true,service.updateStudent("1","George","george@gmail.com","Prof","222"));
        assertEquals(false,service.updateStudent("1","","","",""));
        try{
            service.updateStudent("","","","","");
            assert(false);
        }catch(IllegalArgumentException err){
            assert(true);
        }
       service.deleteStudent("1");
    }
    @Test
    void testExtendDeadLine(){
        Service service=new Service("./src/Tests/TestingData/Students.xml","./src/Tests/TestingData/Projects.xml","./src/Tests/TestingData/Marks.xml");
        try{
            service.extendDeadLine("",5);
            assert(false);
        }catch(IllegalArgumentException err){
            assert(true);
        }
        assertFalse(service.extendDeadLine("1", 5));
        service.addProject("1","desc",5,3,2);
        assertTrue(service.extendDeadLine("1",5));
        assertEquals(10, (int) service.findProject("1").getDeadLine());
        service.addProject("2","desc",14,5,2);
        assertFalse(service.extendDeadLine("2",5));
        service.deleteProject("1");
        service.deleteProject("2");

    }

    @Test
    void testMaxMark(){
        Service service=new Service("./src/Tests/TestingData/Students.xml","./src/Tests/TestingData/Projects.xml","./src/Tests/TestingData/Marks.xml");
        service.addProject("1","asd",2,5,1);
        service.addProject("2","asd",2,1,1);
        service.addProject("3","asd",2,3,1);
        assertEquals(1, (float) service.maxMark("1"));
        assertEquals(10, (float) service.maxMark("2"));
        assertEquals(7.5f, (float) service.maxMark("3"));
        service.deleteProject("1");
        service.deleteProject("2");
        service.deleteProject("3");
    }

    @Test
    void testCheckMarkId(){
        Service service=new Service("./src/Tests/TestingData/Students.xml","./src/Tests/TestingData/Projects.xml","./src/Tests/TestingData/Marks.xml");
        service.addProject("1","asd",2,5,1);
        service.addStudent("1","George","george@gmail.com","Prof","222");
        assertTrue(service.checkMarkId("1","1"));
        assertFalse(service.checkMarkId("6","1"));
        assertFalse(service.checkMarkId("1","2"));
        assertFalse(service.checkMarkId("2","2"));
        try{
            service.checkMarkId("","");
            assert(false);
        }catch(IllegalArgumentException err){
            assert(true);
        }
        service.deleteProject("1");
        service.deleteStudent("1");
    }

}
