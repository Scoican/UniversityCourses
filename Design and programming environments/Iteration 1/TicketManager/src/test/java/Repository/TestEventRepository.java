package Repository;

import Domain.Event;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TestEventRepository {
    @Test
    public void testTicketRepository(){
        Properties prop=new Properties();

        try {
            prop.load(new FileReader("C:\\Users\\Scoican\\Desktop\\Facultate\\GitHub\\UniversityCourses\\Design and programming environments\\TicketManager\\bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        IRepository<Integer, Event> eventRepo=new EventRepository(prop);
        eventRepo.save(new Event(102,"name1",100));
        eventRepo.save(new Event(103,"name2",100));
        eventRepo.save(new Event(104,"name3",100));
        assert(eventRepo.findOne(102).getName().equals("name1"));
        assert(eventRepo.findOne(103).getName().equals("name2"));
        assert(eventRepo.findOne(104).getName().equals("name3"));
        assert(eventRepo.size()==3);
        eventRepo.update(102,new Event(140,"User190",200));
        assert(eventRepo.findOne(140).getName().equals("User190"));
        eventRepo.delete(104);
        eventRepo.delete(103);
        eventRepo.delete(140);

        assert(eventRepo.size()==0);
    }
}
