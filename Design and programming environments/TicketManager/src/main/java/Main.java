import Domain.Event;
import Domain.Ticket;
import Domain.User;
import Repository.EventRepository;
import Repository.IRepository;
import Repository.TicketRepository;
import Repository.UserRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        Properties prop=new Properties();

        try {
            prop.load(new FileReader("C:\\Users\\Scoican\\Desktop\\Facultate\\Medii de proiectare si programare\\Laborator\\TicketManager\\bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        IRepository<Integer, User> userRepo=new UserRepository(prop);
        IRepository<Integer, Ticket> ticketRepo=new TicketRepository(prop);
        IRepository<Integer, Event> eventRepo=new EventRepository(prop);
        System.out.println("Users before modifications:");
        for(User s:userRepo.findAll()){
            System.out.println(s.getUsername());
        }
        userRepo.save(new User(102,"User1","FMI123131"));
        userRepo.save(new User(103,"User2","FMI123131"));
        userRepo.save(new User(104,"User3","FMI123131"));
        System.out.println("Users after modifications");
        for(User s:userRepo.findAll()){
            System.out.println(s.getUsername());
        }
        userRepo.delete(102);
        userRepo.delete(103);
        userRepo.delete(104);

        System.out.println("Tickets before modifications:");
        for(Ticket s:ticketRepo.findAll()){
            System.out.println(s.getBuyer());
        }
        ticketRepo.save(new Ticket(102,101,"name1",200d));
        ticketRepo.save(new Ticket(103,102,"name2",201d));
        ticketRepo.save(new Ticket(104,103,"name3",202d));
        System.out.println("Tickets after modifications:");
        for(Ticket s:ticketRepo.findAll()){
            System.out.println(s.getBuyer());
        }
        ticketRepo.delete(102);
        ticketRepo.delete(103);
        ticketRepo.delete(104);

        System.out.println("Events before modifications:");
        for(Event s:eventRepo.findAll()){
            System.out.println(s.getName());
        }
        eventRepo.save(new Event(102,"Event1",200));
        eventRepo.save(new Event(103,"Event2",201));
        eventRepo.save(new Event(104,"Event3",202));
        System.out.println("Events after modifications:");
        for(Event s:eventRepo.findAll()){
            System.out.println(s.getName());
        }
        eventRepo.delete(102);
        eventRepo.delete(103);
        eventRepo.delete(104);

    }
}
