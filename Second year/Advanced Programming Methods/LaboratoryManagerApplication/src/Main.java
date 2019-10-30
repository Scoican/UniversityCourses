import GUI.StudentController;
import GUI.StudentView;
import Service.StudentService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

/*
public class Main {

    public static void main(String[] args) {
        UI ui=new UI();
        ui.run();
    }
}*/

public class Main extends Application {
    //@Override
   public void start2(Stage stage) throws Exception {
        StudentService service = new StudentService("./src/Data/Students.xml");
        StudentController ctrl=new StudentController(service);
        StudentView view=new StudentView(ctrl);
        ctrl.setView(view);

        stage.setResizable(false);
        stage.setTitle("Manager");
        stage.setScene(new Scene(view.getView(),650,500));
        stage.show();

    }
    public void start(Stage primaryStage) throws Exception {

        try {
            //Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/main.fxml"));
            //URL
            BorderPane rootLayout = (BorderPane) loader.load();

            Scene scene = new Scene(rootLayout);
            primaryStage.setScene(scene);
            primaryStage.show();

        }catch (IOException e){
            e.printStackTrace();
        }


    }

    public static void main(String[] args) {
        launch(args);
    }
}