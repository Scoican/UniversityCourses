package Server;

import Repository.UserRepository;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public abstract class AbstractServer {
    private int port;
    private ServerSocket server=null;
    private UserRepository repo;
    public AbstractServer( int port){
        this.port=port;
    }
    public void setData(UserRepository repo){
        this.repo=repo;
    }
    public UserRepository getRepo(){
        return this.repo;
    }
    public void start() throws ServerException {
        try{
            server=new ServerSocket(port);
            while(true){
                System.out.println("Astept conexiuni ...");
                Socket client=server.accept();
                System.out.println("Client conectat ...");
                processRequest(client);
            }
        } catch (IOException e) {
            throw new ServerException("Starting server errror ",e);
        }finally {
            try{
                server.close();
            } catch (IOException e) {
                throw new ServerException("Closing server error ", e);
            }
        }
    }

    protected abstract  void processRequest(Socket client);
    public void stop() throws ServerException{
        try{
            server.close();
        } catch (IOException e) {
            throw new ServerException("Closing server error ", e);
        }
    }
}
