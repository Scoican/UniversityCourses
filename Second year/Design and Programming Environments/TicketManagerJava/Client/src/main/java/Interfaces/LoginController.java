package Interfaces;

import Exceptions.BasketException;

import ModelDomain.Event;
import ModelDomain.User;
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
import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;


public class LoginController extends UnicastRemoteObject implements IClient, Serializable {

    @FXML
    TextField usernameText;
    @FXML
    PasswordField passwordText;
    @FXML
    Button loginBtn;

    private MainPageController controller;
    private Parent parent;
    private IServer server;


    public LoginController() throws RemoteException {
    }

    public void setService(IServer server){
        this.server = server;
    }

    public void setController(MainPageController controller) {
        this.controller = controller;
    }

    public void setParent(Parent parent)
    {
        this.parent=parent;
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
                User newUser=new User(user,pass);
                MainPageController mainPageController=loader.getController();
                server.login(newUser, mainPageController);
                ((Node)(actionEvent.getSource())).getScene().getWindow().hide();
                mainPageController.setService(server,newUser);
                stage.setTitle(newUser.getId());
                stage.setScene(new Scene(root));
                stage.show();
            } catch (IOException |BasketException e1) {
                AbstractViewController.showErrorMessage(e1.getMessage());
            }}catch (NullPointerException ex){
            AbstractViewController.showErrorMessage("Username si parola incorecte");
        }
    }

    @Override
    public void increaseNumberOfParticipants(Event event) throws BasketException {

    }

}
