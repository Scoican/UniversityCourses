import Interfaces.IServer;
import Repository.EventRepository;
import Repository.TicketRepository;
import Repository.UserRepository;
import utils.AbstractServer;
import utils.ObjectConcurrentServer;
import utils.ServerException;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class StartObjectServer {
    private static int defaultPort=55555;
    public static void main(String[] args){
        Properties serverProps=new Properties();
        try{
            serverProps.load(StartObjectServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set");
            serverProps.list(System.out);
        }catch(IOException e){
            System.err.println("Cannot find chatserver.properties"+e);
            return;
        }
        Properties prop2=new Properties();
        try {
            prop2.load(StartObjectServer.class.getResourceAsStream("bd.config"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        TicketRepository ticketRepository=new TicketRepository(prop2);
        EventRepository eventRepository=new EventRepository(prop2);
        UserRepository userRepository=new UserRepository(prop2);

        IServer serverImpl=new ServerImpl(userRepository,eventRepository,ticketRepository);
        int serverPort=defaultPort;
        try{
            serverPort=Integer.parseInt(serverProps.getProperty("concurs.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong Port Number "+ nef.getMessage());
            System.err.println("Using default port " + defaultPort);
        }
        System.out.println("Starting server on port: "+ serverPort);
        AbstractServer server = new ObjectConcurrentServer(serverPort,serverImpl);
        try {
            server.start();
        }catch (ServerException e){
            System.err.println("Error starting the server "+e.getMessage());
        }
    }
}
