package Client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class StartTextClient {
    public static void main(String[] args) {
        try(Socket client=new Socket("localhost",55555)) {
            System.out.println("Conectat la server");
            try(PrintWriter writer=new PrintWriter(client.getOutputStream());
                BufferedReader br=new BufferedReader(new InputStreamReader(client.getInputStream()))) {

                String username = "Hertos";
                writer.println(username);
                String parola="123456";
                writer.println(parola);
                writer.flush();

                String response = br.readLine();
                if(response.equals("1"))
                    System.out.println(">> Parola este corecta");
                else System.out.println(">> Parola este gresita");
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
}
