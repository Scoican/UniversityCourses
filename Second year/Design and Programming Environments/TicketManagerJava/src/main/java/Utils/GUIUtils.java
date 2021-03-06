package Utils;

import javafx.scene.control.Alert;

public class GUIUtils {
    public static void showErrorMessage(String text) {
        Alert message = new Alert(Alert.AlertType.ERROR);
        message.setTitle("Eroare");
        message.setContentText(text);
        message.getDialogPane().setStyle("" +
                "-fx-font-family: \"Trebuchet MS\", Helvetica, sans-serif;\n" +
                "-fx-text-fill: white;\n" +
//                "-fx-background-color: rgba(172,20,39,0.5);"+
                "");
        message.showAndWait();
    }

    public static void showInfoMessage(String text) {
        Alert message = new Alert(Alert.AlertType.INFORMATION);
        message.setTitle("Informare");
        message.setContentText(text);
        message.getDialogPane().setStyle("" +
                "-fx-font-family: \"Trebuchet MS\", Helvetica, sans-serif;\n" +
                "-fx-text-fill: white;\n" +
//                "-fx-background-color: rgba(172,20,39,0.5);"+
                "");
        message.showAndWait();
    }
}
