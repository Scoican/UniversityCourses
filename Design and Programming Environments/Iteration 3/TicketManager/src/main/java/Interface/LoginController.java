package Interface;

import Domain.User;
import Service.EventService;
import Service.TicketService;
import Service.UserService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {

    @FXML
    TextField usernameTextField;

    @FXML
    PasswordField passwordTextField;

    @FXML
    Button loginButton;

    private UserService userService;
    private TicketService ticketService;
    private EventService eventService;


    private Stage primaryStage;
    private Scene userScene;
    private Scene loginScene;

    private UserController userController;


    public void init(UserService userService,TicketService ticketService,EventService eventService, Scene loginScene){
        this.userService=userService;
        this.ticketService=ticketService;
        this.eventService=eventService;
        this.loginScene=loginScene;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage=primaryStage;
    }

    @FXML
    public void handleLogin() throws IOException {
        String username = "";
        String password = "";

        if (usernameTextField.getText() != null) {
            username = usernameTextField.getText().trim();
        }
        if(passwordTextField.getText()!=null){
            password = passwordTextField.getText().trim();
        }

        boolean valid = userService.checkPassword(username,password);
        if (valid) {
            User user=userService.findOne(username);
            initViewUser(user);
            handleUserWindow();

        }else{
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText("Wrong credentials");
            alert.setContentText("Username or password incorrect");

            alert.showAndWait();
        }
        usernameTextField.clear();
        passwordTextField.clear();

    }

    private void initViewUser(User user) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/UserView.fxml"));
        BorderPane userLayout = loader.load();
        Scene userScene = new Scene(userLayout);

        this.userController=loader.getController();

        userController.setLoginScene(loginScene);
        userController.setPrimaryStage(primaryStage);
        userController.setData(userService,ticketService,eventService,user);
        this.userScene = userScene;
    }

    private void handleUserWindow() {
        this.primaryStage.setScene(this.userScene);
    }
}
