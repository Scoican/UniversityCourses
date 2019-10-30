package Interfaces;

import javafx.scene.control.Alert;

import java.io.Serializable;

public class AbstractViewController implements Serializable {
    public static void showErrorMessage(String msg){
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Error");
        message.setContentText(msg);
        message.showAndWait();

    }

    public static void showMessage(Alert.AlertType type, String header, String msg){
        Alert message = new Alert(type);
        message.setHeaderText(header);
        message.setContentText(msg);
        message.showAndWait();
    }
}
