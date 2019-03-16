package GUI;

import Service.MarkService;
import Service.ProjectService;
import Service.StudentService;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController {

    StudentService studService=new StudentService("./src/Data/Students.xml");
    ProjectService projectService=new ProjectService("./src/Data/Projects.xml");
    MarkService markService=new MarkService("./src/Data/Students.xml","./src/Data/Projects.xml","./src/Data/Marks.xml");

    public void handleMark(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/MarkManagment.fxml"));
            BorderPane rootLayout = loader.load();
            FXMLMarkController gradeController = loader.getController();
            gradeController.setService(studService, projectService,markService);

            Stage stage = new Stage();
            stage.setTitle("Mark CRUD Operations");
            stage.setScene(new Scene(rootLayout));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleProject(ActionEvent actionEvent){
        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/ProjectView.fxml"));
            BorderPane rootLayout = loader.load();
            FXMLProjectController projectController = loader.getController();
            projectController.setProjectService(projectService);

            Stage stage = new Stage();
            stage.setTitle("Project CRUD Operations");
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void handleStudent(ActionEvent actionEvent) {
        try {

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/StudentView.fxml"));
            BorderPane rootLayout = loader.load();
            FXMLStudentController studentController = loader.getController();
            studentController.setService(studService);

            Stage stage = new Stage();
            stage.setTitle("Student CRUD Operations");
            Scene scene = new Scene(rootLayout);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
