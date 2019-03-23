package Service;
import Domain.User;
import Repository.UserRepository;
import Utils.DataChanged;
import Utils.Observable;
import Utils.Observer;

import java.util.ArrayList;
import java.util.List;

public class UserService implements Observable<DataChanged> {

    private List<Observer<DataChanged>> observers=new ArrayList<>();
    private UserRepository userRepository;


    public UserService(UserRepository userRepository){
        this.userRepository=userRepository;
    }

    public void save(String username,String password){
        User user=new User(username,password);
        userRepository.save(user);
    }

    public void delete(Integer integer){
        userRepository.delete(integer);
    }

    public void update(Integer integer,String username,String password){
        User user=new User(username,password);
        userRepository.update(integer,user);
    }

    public User findOne(String username){
        return userRepository.findOne(username);
    }

    public Iterable<User> findAll(){
        return userRepository.findAll();
    }
    @Override
    public void addObserver(Observer<DataChanged> e) {
        observers.add(e);
    }

    @Override
    public void removeObserver(Observer<DataChanged> e) {
        observers.remove(e);
    }

    @Override
    public void notifyObservers(DataChanged t) {
        observers.forEach(o->o.update(t));
    }

    public boolean checkPassword(String username, String password) {
        User user = userRepository.findOne(username);

        if (user!=null) {
            String userPassword=user.getPassword();
            return userPassword.equals(password);
        }
        return false;
    }
}
