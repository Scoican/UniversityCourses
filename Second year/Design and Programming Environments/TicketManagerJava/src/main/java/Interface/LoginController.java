package Interface;

import Domain.User;
import Service.EventService;
import Service.TicketService;
import Service.UserService;
import javafx.event.ActionEvent;
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

import javax.xml.bind.ValidationException;
import java.io.IOException;

import static Utils.GUIUtils.showErrorMessage;

public class LoginController {

    @FXML
    TextField usernameText;
    @FXML
    PasswordField passwordText;
    @FXML
    Button loginBtn;

    private UserService userService;
    private TicketService ticketService;
    private EventService eventService;

    public void setService(UserService userService,TicketService ticketService,EventService eventService){
        this.userService = userService;
        this.ticketService = ticketService;
        this.eventService = eventService;
    }

    @FXML
    public void handlerLoginButton(ActionEvent e){

        try{

            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/MainPageView.fxml"));
            AnchorPane root = (AnchorPane)fxmlLoader.load();
            root.setId("pane");
            MainPageController controller = fxmlLoader.getController();
            controller.setService(userService,ticketService,eventService);

            String username = usernameText.getText();
            String password = passwordText.getText();
            try {
                User user = userService.login(username, password);
                if (user != null) {
                    Stage stage = new Stage();
                    stage.setTitle(user.getId());
                    Scene scene=new Scene(root);
                    scene.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());
                    stage.setScene(scene);
                    stage.show();
                    Stage currentScene = (Stage) usernameText.getScene().getWindow();
                    currentScene.close();

                }
            }
            catch (ValidationException msg){
                usernameText.setText("");
                passwordText.setText("");
                showErrorMessage(msg.getMessage());
            }

        }
        catch (IOException e1){
            showErrorMessage(e1.getMessage());
        }
    }

}
