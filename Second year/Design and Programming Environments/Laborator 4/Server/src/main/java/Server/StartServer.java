package Server;

import Repository.UserRepository;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class StartServer {
    public static void main(String[] args) {
        Properties prop=new Properties();

        try {
            prop.load(new FileReader("C:\\Users\\Scoican\\Desktop\\Facultate\\Inteligenta artificiala\\Laborator\\tema4\\Server\\src\\main\\resources\\bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        AbstractServer server=new Server(55555);
        UserRepository repo=new UserRepository(prop);
        server.setData(repo);
        try {
            server.start();
        } catch (ServerException e) {
            System.out.println(e.getMessage());
        }
    }
}
