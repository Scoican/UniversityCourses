
import Interface.LoginController;
import Repository.EventRepository;
import Repository.TicketRepository;
import Repository.UserRepository;
import Service.EventService;
import Service.TicketService;
import Service.UserService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.CycleMethod;
import javafx.stage.Stage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class Main extends Application {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        /*
        logger.traceEntry();
        ApplicationContext context=new AnnotationConfigApplicationContext(RepositoryConfig.class);
        //ApplicationContext context=new ClassPathXmlApplicationContext("classpath:RepositoryApp.xml");
        EventRepository eventRepository=context.getBean(EventRepository.class);
        System.out.println("Dim:" +eventRepository.size());
        System.out.println("Hello!");

        logger.traceExit();
        */
        launch(args);

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(ClassLoader.getSystemResource("LoginView.fxml"));
        AnchorPane root = new AnchorPane();
        root = loader.load();
        root.setId("pane");
        LoginController loginController = loader.getController();
        Properties prop = new Properties();

        try {
            prop.load(new FileReader("C:\\Users\\Scoican\\Desktop\\Facultate\\Medii de proiectare si programare\\Laborator\\TicketManager\\src\\main\\resources\\bd.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        EventRepository eventRepository= new EventRepository(prop);
        TicketRepository ticketRepository=new TicketRepository(prop);
        UserRepository userRepository=new UserRepository(prop);

        UserService userService=new UserService(userRepository);
        TicketService ticketService=new TicketService(ticketRepository,eventRepository);
        EventService eventService=new EventService(eventRepository);

        loginController.setService(userService,ticketService,eventService);


        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("style.css").toExternalForm());
        primaryStage.setScene(scene);
        primaryStage.setTitle("Login");
        primaryStage.show();
    }

    /*
    private void init(Stage primaryStage) throws IOException {

        Properties prop=new Properties();

        try {
            prop.load(new FileReader("C:\\Users\\Scoican\\Desktop\\Facultate\\Medii de proiectare si programare\\Laborator\\TicketManager\\src\\main\\resources\\bd.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }


        EventRepository eventRepository= new EventRepository(prop);
        TicketRepository ticketRepository=new TicketRepository(prop);
        UserRepository userRepository=new UserRepository(prop);

        UserService userService=new UserService(userRepository);
        TicketService ticketService=new TicketService(ticketRepository,eventRepository);
        EventService eventService=new EventService(eventRepository);

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/LoginView.fxml"));
        AnchorPane rootLayout = loader.load();
        Scene mainMenuScene = new Scene(rootLayout);

        LoginController loginController=loader.getController();
        loginController.setService(userService,ticketService,eventService);

        primaryStage.setScene(mainMenuScene);
        primaryStage.show();
    }*/
}
