package Interfaces;

import Domain.Event;
import Domain.User;
import Exceptions.BasketException;
import Service.EventService;
import Service.TicketService;
import Service.UserService;
import Utils.EventChangeEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;


public class LoginController extends AbstractViewController implements IClient {

    @FXML
    TextField usernameText;
    @FXML
    PasswordField passwordText;
    @FXML
    Button loginBtn;

    private IServer server;

    private UserService userService;
    private TicketService ticketService;
    private EventService eventService;

    public void setService(UserService userService,TicketService ticketService,EventService eventService){
        this.userService = userService;
        this.ticketService = ticketService;
        this.eventService = eventService;
    }

    public void setService(IServer server){
        this.server = server;
    }

    @FXML
    public void handlerLoginButton(ActionEvent actionEvent) {
        try{
            String user=usernameText.getText();
            String pass=passwordText.getText();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/MainPageView.fxml"));
            try{
                Parent root = loader.load();
                Stage stage = new Stage();
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                User newUser=new User(user,pass);
                MainPageController mainPageController=loader.getController();
                server.login(newUser, mainPageController);
                mainPageController.setService(server,newUser);
                stage.setTitle(newUser.getId());
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException |BasketException e1) {
                showErrorMessage(e1.getMessage());
            }}catch (NullPointerException ex){
            showErrorMessage("Username si parola incorecte");
        }
    }

    @Override
    public void increaseNumberOfParticipants(Event event) throws BasketException {

    }


}
