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
import javafx.scene.control.Alert;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class StudentController implements Observer<StudentChangeEvent> {
    private StudentService service;
    private ObservableList<Student> model;
    private StudentView view;

    public StudentController(StudentService service) {
        this.service=service;
        List<Student> list = StreamSupport.stream(service.getStudents().spliterator(),false)
                .collect(Collectors.toList());
        model = FXCollections.observableArrayList(list);
    }

    public void setView(StudentView view) { this.view = view; }

    public ObservableList<Student> getModel(){
        return model;
    }

    public void showStudentDetails(Student value) {
        if(value == null){
            view.textFieldID.setText("");
            view.textFieldName.setText("");
            view.textFieldEmail.setText("");
            view.textFieldTeacher.setText("");
            view.textFieldGroup.setText("");
        }
        else{
            view.textFieldID.setText(value.getID());
            view.textFieldName.setText(value.getName());
            view.textFieldEmail.setText(value.getEmail());
            view.textFieldTeacher.setText(value.getTeacher());
            view.textFieldGroup.setText(value.getGroup());
        }
    }

    public void handleAddStudent(ActionEvent actionEvent) {
        Student s = extractStudent();
        try{
            Student entity=service.addStudent(s.getID(),s.getName(),s.getEmail(),s.getTeacher(),s.getGroup());
            if(entity==null){
                showMessage("Saving done","Student saved");
                update(new StudentChangeEvent(ChangeEventType.ADD,s));
                showStudentDetails(null);
            }
            else{
                showErrorMessage("Student already existing");
            }
        }catch (ValidatorException e){
            showErrorMessage(e.getMessage());
        }
    }


    public void handleUpdateStudent(ActionEvent actionEvent) {
        Student s=extractStudent();
        try{
            Boolean condition=service.updateStudent(s.getID(),s.getName(),s.getEmail(),s.getTeacher(),s.getGroup());
            if(condition){
                showMessage("Update done","Student info updated");
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

    public void handleDeleteStudent(ActionEvent actionEvent) {
        String idStudent=view.textFieldID.getText();
        try{
            Student entity=service.deleteStudent(idStudent);
            if(entity !=null){
                showMessage("Delete done","Student deleted");
                update(new StudentChangeEvent(ChangeEventType.DELETE,service.findStudent(idStudent)));
                showStudentDetails(null);
            }
            else{
                showErrorMessage("Student not found");
            }
        }catch (ValidatorException e){
            showErrorMessage(e.getMessage());
        }
    }


    private void showErrorMessage(String text) {
        Alert error= new Alert(Alert.AlertType.ERROR);
        error.setHeaderText("Eroare");
        error.setContentText(text);
        error.showAndWait();
    }

    private void showMessage(String header, String text) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText(header);
        alert.setContentText(text);
        alert.showAndWait();
    }

    private Student extractStudent() {
        String id = view.textFieldID.getText();
        String name = view.textFieldName.getText();
        String group = view.textFieldGroup.getText();
        String email = view.textFieldEmail.getText();
        String teacher = view.textFieldTeacher.getText();

        return new Student(id,name,email,teacher,group);
    }

    @Override
    public void update(StudentChangeEvent studentChangeEvent) {
        model.setAll(StreamSupport.stream(service.getStudents().spliterator(),false)
                .collect(Collectors.toList()));
    }
}
