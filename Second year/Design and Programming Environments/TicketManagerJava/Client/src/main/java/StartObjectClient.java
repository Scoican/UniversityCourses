import Interfaces.IServer;
import Interfaces.LoginController;
import Interfaces.MainPageController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class StartObjectClient extends Application {

    private static int defaultPort=55555;
    private static String defaultServer="localhost";

    private static IServer server;

    /*public static void main(String[] args){
        Properties clientProps=new Properties();
        try{
            clientProps.load(StartObjectClient.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set.");
            clientProps.list(System.out);
        }catch(IOException e){
            System.err.println("Cannot find chatclient.properties "+e);
            return;
        }
        String serverIP=clientProps.getProperty("server.host",defaultServer);
        int serverPort=defaultPort;
        try{
            serverPort=Integer.parseInt(clientProps.getProperty("server.port"));
        }catch (NumberFormatException ex){
            System.err.println("Wrong port number "+ex.getMessage());
            System.out.println("Using default port: "+defaultPort);
        }
        System.out.println("Using server IP "+serverIP);
        System.out.println("Using server port "+serverPort);
        server=new ServerObjectProxy(serverIP,serverPort);
        //ApplicationContext factory = new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        //IServer server=(IServer) factory.getBean("chatService");

       // launch(args);
    }*/

    @Override
    public void start(Stage stage) throws Exception {
        ApplicationContext factory=new ClassPathXmlApplicationContext("classpath:spring-client.xml");
        IServer server= (IServer) factory.getBean("chatService");

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemResource("LoginView.fxml"));
        Parent root = loader.load();

        LoginController loginController=loader.getController();
        loginController.setService(server);

        FXMLLoader matchLoader=new FXMLLoader( getClass().getClassLoader().getResource("MainPageView.fxml"));
        Parent matchRoot = matchLoader.load();

        MainPageController mainPageController=(MainPageController) matchLoader.getController();
        mainPageController.setServer(server);

        loginController.setController(mainPageController);
        loginController.setParent(matchRoot);

        stage.setTitle("Login");
        stage.setScene(new Scene(root));
        stage.show();
    }
}
