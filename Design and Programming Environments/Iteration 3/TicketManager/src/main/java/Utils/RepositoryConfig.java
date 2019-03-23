package Utils;

import Repository.EventRepository;
import Repository.TicketRepository;
import Repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.io.IOException;
import java.util.Properties;

@Configuration
public class RepositoryConfig {
    @Bean
    @Primary
    public Properties jdbcProps(){
        Properties repoProps=new Properties();
        try {
            repoProps.load(getClass().getResourceAsStream("/bd.properties"));
            System.out.println("Properties set. ");
            repoProps.list(System.out);
        } catch (IOException e) {
            System.out.println("Cannot find bd.config "+e);

        }
        return  repoProps;
    }

    @Bean(name="EventRepository")
    public EventRepository createRepositoryEvent(Properties jdbcProps){
        return new EventRepository(jdbcProps);
    }

    @Bean(name="TicketRepository")
    public TicketRepository createRepositoryTicket(Properties jdbcProps){
        return new TicketRepository(jdbcProps);
    }

    @Bean(name="UserRepository")
    public UserRepository createRepositoryUser(Properties jdbcProps){
        return new UserRepository(jdbcProps);
    }
}
