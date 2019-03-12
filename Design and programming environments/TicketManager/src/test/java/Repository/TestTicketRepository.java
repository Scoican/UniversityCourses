package Repository;

import Domain.Ticket;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class TestTicketRepository {
    @Test
    public void testTicketRepository(){
        Properties prop=new Properties();

        try {
            prop.load(new FileReader("C:\\Users\\Scoican\\Desktop\\Facultate\\Medii de proiectare si programare\\Laborator\\TicketManager\\bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        IRepository<Integer, Ticket> ticketRepo=new TicketRepository(prop);
        ticketRepo.save(new Ticket(102,102,"User1",200d));
        ticketRepo.save(new Ticket(103,103,"User2",200d));
        ticketRepo.save(new Ticket(104,104,"User3",200d));
        assert(ticketRepo.findOne(102).getBuyer().equals("User1"));
        assert(ticketRepo.findOne(103).getBuyer().equals("User2"));
        assert(ticketRepo.findOne(104).getBuyer().equals("User3"));
        assert(ticketRepo.size()==3);
        ticketRepo.update(102,new Ticket(140,104,"User190",200d));
        assert(ticketRepo.findOne(140).getBuyer().equals("User190"));
        ticketRepo.delete(104);
        ticketRepo.delete(103);
        ticketRepo.delete(140);

        assert(ticketRepo.size()==0);
    }
}
