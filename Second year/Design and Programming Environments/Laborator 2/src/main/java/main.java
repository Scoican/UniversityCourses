import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

class Main {
    public static void main(String[] args) {
        Properties prop=new Properties();

        try {
            prop.load(new FileReader("C:\\Users\\Scoican\\Desktop\\Facultate\\Medii de proiectare si programare\\Laborator\\Tema2LaboratorMPP\\bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Repository<Integer,User> repo=new UserRepository(prop);
        System.out.println("Inainte de adaugare");
        for(User s:repo.findAll()){
            System.out.println(s.getUsername());
        }
        repo.save(new User(100,"Alex","FMI123131"));
        System.out.println("Dupa adaugare");
        for(User s:repo.findAll()){
            System.out.println(s.getUsername());
        }
        repo.delete(100);
        repo.delete(6);
    }
}
