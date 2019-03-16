package GUI;

import Domain.Project;
import Service.ProjectService;
import Utils.ChangeEventType;
import Utils.Observer;
import Utils.ProjectChangeEvent;
import Validator.ValidatorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;


public class FXMLProjectController implements Observer<ProjectChangeEvent> {

    private ProjectService service;
    private ObservableList<Project> model = FXCollections.observableArrayList();

    @FXML
    TableView tableView;
    @FXML
    TableColumn<Project,String> numberColumn;
    @FXML
    TableColumn<Project,String> descriptionColumn;
    @FXML
    TableColumn<Project,String> deadlineColumn;
    @FXML
    TableColumn<Project,String> weekNumberColumn;
    @FXML
    TableColumn<Project,String> startWeekNumberColumn;
    @FXML
    TextField numberTextField;
    @FXML
    TextArea descriptionTextArea;
    @FXML
    TextField deadlineTextField;
    @FXML
    TextField weekNumberTextField;
    @FXML
    TextField startWeekNumberTextField;

    public void setProjectService(ProjectService projectService){
        this.service = projectService;
        this.service.addObserver(this);
        initModel();
    }

    private void initModel() {
        Iterable<Project> projects = service.getProjects();
        List<Project> projectList = StreamSupport.stream(projects.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(projectList);
    }

    @FXML
    public void initialize() {
        numberColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("ID"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("description"));
        deadlineColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("deadLine"));
        weekNumberColumn.setCellValueFactory(new PropertyValueFactory<Project, String>("currentWeek"));
        startWeekNumberColumn.setCellValueFactory(new PropertyValueFactory<Project,String>("startWeek"));
        tableView.setItems(model);
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    showProjectDetails((Project) newValue);
                    numberTextField.setEditable(false);
                }
        );
    }

    private void showProjectDetails(Project value) {
        if (value == null){
            numberTextField.setText("");
            descriptionTextArea.setText("");
            deadlineTextField.setText("");
            weekNumberTextField.setText("");
            startWeekNumberTextField.setText("");
        }
        else{
            numberTextField.setText(value.getID());
            descriptionTextArea.setText(value.getDescription());
            deadlineTextField.setText(String.valueOf(value.getDeadLine()));
            weekNumberTextField.setText(String.valueOf(value.getCurrentWeek()));
            startWeekNumberTextField.setText(String.valueOf(value.getStartWeek()));
        }
    }

    public void handleAddProject(ActionEvent actionEvent){

        try{
            Project hw = extractProject();
            Project response = service.addProject(hw.getID(), hw.getDescription(), hw.getDeadLine(), hw.getCurrentWeek(),hw.getStartWeek());
            if (response == null){
                showMessage(Alert.AlertType.INFORMATION, "Salvare realizata cu succes", "Tema a fost adaugata");
                update(new ProjectChangeEvent(ChangeEventType.ADD,response));
                showProjectDetails(null);
            }
            else{
                showErrorMessage("Exista deja o tema cu acest numar");
            }
        }catch (ValidatorException e){
            showErrorMessage(e.getMessage());
        }catch (NumberFormatException e){
            showErrorMessage("Please insert valid numbers for the week fields");
        }
    }

    private Project extractProject() {
        String s;
        String id = numberTextField.getText();
        String description = descriptionTextArea.getText();
        Integer deadline = Integer.valueOf(deadlineTextField.getText());
        Integer weekNumber = Integer.valueOf(weekNumberTextField.getText());
        Integer startWeek = Integer.valueOf(startWeekNumberTextField.getText());

        return new Project(id, description, deadline, weekNumber,startWeek);
    }

    @Override
    public void update(ProjectChangeEvent projectChangeEvent) {
        initModel();
    }

    private void showErrorMessage(String text) {
        Alert error= new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Eroare");
        error.setContentText(text);
        error.showAndWait();
    }

    private void showMessage(Alert.AlertType type, String header, String text) {
        Alert alert = new Alert(type);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public void handleExtendDeadline(ActionEvent actionEvent) {
        String s;
        try{
            String id = numberTextField.getText();
            Integer deadline = Integer.valueOf(deadlineTextField.getText());
            Boolean response = service.extendDeadLine(id, deadline);
            if (response ==true){
                showMessage(Alert.AlertType.INFORMATION, "Project Extended", "Project Extended");
                update(new ProjectChangeEvent(ChangeEventType.UPDATE,service.findProject(id)));
                showProjectDetails(null);
            }
            else{
                showErrorMessage("Tema nu se poate extinde");
            }
        }catch (ValidatorException e){
            showErrorMessage(e.getMessage());
        }catch(NumberFormatException e){
            showErrorMessage("Please insert a valid number for deadline extension");
        }
    }
    public void handleClearFields(ActionEvent actionEvent){
        showProjectDetails(null);
        numberTextField.setEditable(true);
    }
}
