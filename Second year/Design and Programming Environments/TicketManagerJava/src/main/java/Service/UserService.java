package Service;
import Domain.User;
import Repository.UserRepository;
import Utils.DataChanged;
import Utils.Observable;
import Utils.Observer;

import javax.xml.bind.ValidationException;
import java.util.ArrayList;
import java.util.List;

public class UserService {

    private UserRepository userRepository;


    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public void save(String username,String password){
        User user=new User(username,password);
        userRepository.save(user);
    }

    public void delete(String username){
        userRepository.delete(username);
    }

    public void update(String username,String password){
        User user=new User(username,password);
        userRepository.update(username,user);
    }

    public User findOne(String username){
        return userRepository.findOne(username);
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }

    public boolean checkPassword(String username, String password) {
        User user = userRepository.findOne(username);

        if (user!=null) {
            String userPassword=user.getPassword();
            return userPassword.equals(password);
        }
        return false;
    }

    public User login(String username,String password) throws ValidationException {
        User user = userRepository.findOne(username);
        if(user==null){
            throw new ValidationException("Username or password incorrect!");
        }else{
            return user;
        }
    }
}
