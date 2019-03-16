package GUI;

import Domain.Student;
import Service.StudentService;
import Utils.ChangeEventType;
import Utils.Observer;
import Utils.StudentChangeEvent;
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

public class FXMLStudentController implements Observer<StudentChangeEvent> {

    private Service.StudentService service;
    private ObservableList<Student> model = FXCollections.observableArrayList();

    @FXML
    TableView tableView;
    @FXML
    TableColumn<Student,String> nameColumn;
    @FXML
    TableColumn<Student,String> groupColumn;
    @FXML
    TableColumn<Student,String> emailColumn;
    @FXML
    TableColumn<Student,String> teacherColumn;
    @FXML
    Button clearButton;
    @FXML
    TextField textFieldID;
    @FXML
    TextField textFieldName;
    @FXML
    TextField textFieldGroup;
    @FXML
    TextField textFieldEmail;
    @FXML
    TextField textFieldTeacher;

    public void setService(StudentService service){
        this.service = service;
        this.service.addObserver(this);
        initModel();
    }



    @FXML
    public void handleClearButton(ActionEvent actionEvent){
        showStudentDetails(null);
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("name"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("group"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("email"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<Student, String>("teacher"));
        tableView.setItems(model);
        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable, oldValue, newValue) -> {
                    showStudentDetails((Student) newValue);
                    textFieldID.setEditable(false);
                }
        );
    }

    private void initModel() {
        Iterable<Student> students = service.getStudents();
        List<Student> studentsList = StreamSupport.stream(students.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(studentsList);
    }

    @Override
    public void update(StudentChangeEvent studentChangeEvent) {
        initModel();
    }


    public void showStudentDetails(Student value){
        if (value == null){
            textFieldID.setText("");
            textFieldName.setText("");
            textFieldGroup.setText("");
            textFieldEmail.setText("");
            textFieldTeacher.setText("");
        }
        else{
            textFieldID.setText(value.getID());
            textFieldName.setText(value.getName());
            textFieldGroup.setText(value.getGroup());
            textFieldEmail.setText(value.getEmail());
            textFieldTeacher.setText(value.getTeacher());
        }
    }

    public void handleDeleteStudent(ActionEvent actionEvent){
        String idStudent = textFieldID.getText();
        try{
            Student response = service.deleteStudent(idStudent);
            if (response != null){
                showMessage(Alert.AlertType.INFORMATION, "Stergere realizata cu succes", "Studentul a fost sters");
                showStudentDetails(null);
            }
            else{
                showErrorMessage("Studentul nu a fost gasit");
            }
        }catch(ValidatorException e){
            showErrorMessage(e.getMessage());
        }
    }

    public void handleAddStudent(ActionEvent actionEvent){
        Student s = extractStudent();
        try{
            Student response = service.addStudent(s.getID(), s.getName(), s.getEmail(),s.getTeacher(),s.getGroup());
            if (response == null){
                showMessage(Alert.AlertType.INFORMATION, "Salvare realizata cu succes", "Studentul a fost adaugat");
                update(new StudentChangeEvent(ChangeEventType.ADD,s));
                showStudentDetails(null);
            }
            else{
                showErrorMessage("Exista deja un student cu acest id");
            }
        }catch(ValidatorException e){
            showErrorMessage(e.getMessage());
        }
    }

    public void handleUpdateStudent(ActionEvent actionEvent){
        Student s=extractStudent();
        try{
            Boolean check=service.updateStudent(s.getID(),s.getName(),s.getEmail(),s.getTeacher(),s.getGroup());
            if(check){
                showMessage(Alert.AlertType.INFORMATION,"Update done","Student info updated");
                update(new StudentChangeEvent(ChangeEventType.UPDATE,s));
                showStudentDetails(null);
            }
            else{
                showErrorMessage("Student update failed");
            }
        }catch(ValidatorException e){
            showErrorMessage(e.getMessage());
        }
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

    private Student extractStudent() {
        String id = textFieldID.getText();
        String name = textFieldName.getText();
        String group = textFieldGroup.getText();
        String email = textFieldEmail.getText();
        String teacher = textFieldTeacher.getText();

        return new Student(id, name,email, teacher,group);
    }

}
