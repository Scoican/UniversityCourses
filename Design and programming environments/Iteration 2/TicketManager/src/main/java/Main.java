import Domain.Event;
import Domain.Ticket;
import Domain.User;
import Repository.EventRepository;
import Repository.IRepository;
import Repository.TicketRepository;
import Repository.UserRepository;
import Utils.RepositoryConfig;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {

        logger.traceEntry();
        ApplicationContext context=new AnnotationConfigApplicationContext(RepositoryConfig.class);
        //ApplicationContext context=new ClassPathXmlApplicationContext("classpath:RepositoryApp.xml");
        EventRepository eventRepository=context.getBean(EventRepository.class);
        System.out.println("Dim:" +eventRepository.size());
        System.out.println("Hello!");

        logger.traceExit();


    }
}
