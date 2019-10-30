package Server;

import Domain.User;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Server extends AbstractServer {
    public Server(int port) {
        super(port);
        System.out.println("Server");
    }

    protected void processRequest(Socket client) {

        try (BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter writer=new PrintWriter(client.getOutputStream())){

            String username=br.readLine();
            String parola=br.readLine();
            User user=new User(username,parola);
            User result = new User(username,parola);
            if (result.equals(user))
                writer.println("1");
            else writer.println("0");

            writer.flush();

        } catch (IOException e) {
            System.out.println(e);
            e.printStackTrace();
        }finally {
            try {
                client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}