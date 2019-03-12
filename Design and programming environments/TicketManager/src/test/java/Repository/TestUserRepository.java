package Repository;

import Domain.User;
import org.junit.Test;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class TestUserRepository {

    @Test
    public void testUserRepository(){
        Properties prop=new Properties();

        try {
            prop.load(new FileReader("C:\\Users\\Scoican\\Desktop\\Facultate\\Medii de proiectare si programare\\Laborator\\TicketManager\\bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        IRepository<Integer, User> userRepo=new UserRepository(prop);
        userRepo.save(new User(102,"User1","FMI123131"));
        userRepo.save(new User(103,"User2","FMI123131"));
        userRepo.save(new User(104,"User3","FMI123131"));
        assert(userRepo.findOne(102).getUsername().equals("User1"));
        assert(userRepo.findOne(103).getUsername().equals("User2"));
        assert(userRepo.findOne(104).getUsername().equals("User3"));
        assert(userRepo.size()==3);
        userRepo.update(102,new User(130,"User190","asdasdasda"));
        assert(userRepo.findOne(130).getUsername().equals("User190"));
        userRepo.delete(130);
        userRepo.delete(103);
        userRepo.delete(104);

        assert(userRepo.size()==0);
    }
}
