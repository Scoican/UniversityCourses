package GUI;

import Domain.Mark;
import Domain.MarkDTO;
import Domain.Project;
import Domain.Student;
import Service.MarkService;
import Service.ProjectService;
import Service.StudentService;
import Utils.MarkChangeEvent;
import Utils.Observer;
import Validator.ValidatorException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.event.ActionEvent;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FXMLMarkController implements Observer<MarkChangeEvent> {

    private StudentService studentService;
    private ProjectService projectService;
    private MarkService markService;

    private ObservableList<MarkDTO> model = FXCollections.observableArrayList();
    @FXML
    private ComboBox<String> comboBoxProject;
    @FXML
    public ComboBox<String> comboBoxStudent;
    @FXML
    private TextField textFieldMark;
    @FXML
    private ComboBox<String> comboBoxProjectFilter;
    @FXML
    public ComboBox<String> comboBoxStudentFilter;
    @FXML
    private ComboBox<String> comboBoxGroupFilter;
    @FXML
    private TextArea textAreaFeedback;
    @FXML
    private Button addButton;
    @FXML
    private ComboBox<Integer> comboBoxPresentationWeek;
    @FXML
    private Button clearButton;


    @FXML
    private TableView<MarkDTO> tableView;
    @FXML
    private TableColumn<Mark, String> studentColumn;
    @FXML
    private TableColumn<Mark, String> projectColumn;
    @FXML
    private TableColumn<Mark, String> markColumn;
    @FXML
    private TableColumn<Mark, LocalDate> dateColumn;

    public void setService(StudentService studentService,ProjectService projectService,MarkService markService){
        this.studentService=studentService;
        this.projectService=projectService;
        this.markService=markService;
        this.markService.addObserver(this);
        initModel();
        initComboBoxes();

    }
    @FXML
    public void handleClearButton(ActionEvent actionEvent){
        comboBoxGroupFilter.setValue(null);
        comboBoxStudentFilter.setValue(null);
        comboBoxProjectFilter.setValue(null);
        comboBoxPresentationWeek.setValue(null);
        comboBoxStudent.setValue(null);
        comboBoxProject.setValue(null);
        this.initModel();
        tableView.setItems(model);
    }

    private void initComboBox() {
        List<String> studentList = StreamSupport.stream(studentService.getStudents().spliterator(),false).
                map(student -> "ID: "+student.getID()+" Name: "+student.getName()).collect(Collectors.toList());
        ObservableList<String> studentData=FXCollections.observableArrayList(studentList);

        comboBoxStudent.setItems(studentData);
        comboBoxStudentFilter.setItems(studentData);
        new AutoCompleteComboBoxListener<>(comboBoxStudent);
        new AutoCompleteComboBoxListener<>(comboBoxStudentFilter);
    }

    @FXML
    private void initialize(){
        studentColumn.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        projectColumn.setCellValueFactory(new PropertyValueFactory<>("projectNumber"));
        markColumn.setCellValueFactory(new PropertyValueFactory<>("value"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<>("date"));


        textFieldMark.textProperty().addListener(o->handleFeedback());
        comboBoxProjectFilter.valueProperty().addListener(o->handleFilter());
        comboBoxStudentFilter.valueProperty().addListener(o->handleFilter());
        comboBoxGroupFilter.valueProperty().addListener(o->handleFilter());

        tableView.setItems(model);
    }



    private void handleFilter() {
        Predicate<Mark> pProject=n->
                this.comboBoxProjectFilter.getSelectionModel().getSelectedItem()==null||
                        n.getProjectId().equals(this.comboBoxProjectFilter.getSelectionModel().getSelectedItem().split(" ")[1]);
        Predicate<Mark> pStudent=n->
                this.comboBoxStudentFilter.getSelectionModel().getSelectedItem()==null||
                        n.getStudentId().equals(this.comboBoxStudentFilter.getSelectionModel().getSelectedItem().split(" ")[1]);
        Predicate<Mark> pGroup=n->
                this.comboBoxGroupFilter.getSelectionModel().getSelectedItem()==null||
                        studentService.findStudent(n.getStudentId()).getGroup().equals(this.comboBoxGroupFilter.getSelectionModel().getSelectedItem());
        Iterable<Mark> marks=markService.getMarks();
        List<MarkDTO> marksList=StreamSupport.stream(marks.spliterator(),false)
                .filter(pProject.and(pStudent).and(pGroup))
                .map(n-> new MarkDTO(studentService.findStudent(n.getStudentId()).getName(),
                        n.getProjectId(),
                        n.getValue(),LocalDate.now()))
                .collect(Collectors.toList());
        model.setAll(marksList);
    }

    private void handleFeedback() {
        Float penalty = 10f - markService.maxMark(comboBoxProject.getValue().split(" ")[1]);
        textAreaFeedback.setText("NOTA A FOST DIMINUTATA CU " + penalty + " PUNCTE.");
    }


    private void initModel() {
        model.setAll(getMarkDTO());
    }

    private List<MarkDTO> getMarkDTO() {
        return StreamSupport.stream(markService.getMarks().spliterator(),false)
                .map(n-> new MarkDTO(studentService.findStudent(n.getStudentId()).getName(),
                        n.getProjectId(),
                        n.getValue(),LocalDate.now()))
                .collect(Collectors.toList());

    }
    @Override
    public void update(MarkChangeEvent markChangeEvent) {
        initModel();
        initComboBoxes();
    }

    private void initComboBoxes() {
        List<String> list=StreamSupport.stream(projectService.getProjects().spliterator(),false)
                .map(project -> "ID: "+project.getID()+" Name: "+project.getDescription())
                .collect(Collectors.toList());
        ObservableList<String> data=FXCollections.observableArrayList(list);
        this.comboBoxProject.setItems(data);
        this.comboBoxProjectFilter.setItems(data);

        List<String> expiringProjects=StreamSupport.stream(projectService.getProjects().spliterator(),false)
                .filter(project ->(project.getCurrentWeek()-project.getDeadLine())>=2)
                .map(p->p.getID()+" Name: "+p.getDescription())
                .collect(Collectors.toList());
        try{
            this.comboBoxProject.getSelectionModel().select(list.indexOf(expiringProjects.get(0)));
        }catch(IndexOutOfBoundsException e){

        }
        ObservableList<String> dataFilter = FXCollections.observableArrayList("221", "222", "223", "224", "225", "226", "227");
        ObservableList<Integer> weekInfo=FXCollections.observableArrayList(1,2,3,4,5,6,7,8,9,10,11,12,13,14);
        this.comboBoxGroupFilter.setItems(dataFilter);
        this.comboBoxPresentationWeek.setItems(weekInfo);
        initComboBox();
    }

    public void handleAddMark(ActionEvent actionEvent){
        try{
            Mark mark=extractMark();
            Student student=studentService.findStudent(mark.getStudentId());
            Project project=projectService.findProject(mark.getProjectId());
            Float penalty=markService.maxMark(mark.getProjectId())-mark.getValue();
            Alert alert=new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmation");
            alert.setHeaderText("Do you wish to the next mark?");
            alert.setContentText
                            ("Student: "+student.getName()+'\n'+
                            "Project number: "+project.getID()+"\n"+
                            "Penalty: "+penalty+"\n"+
                            "Final mark: "+mark.getValue()
                                    );
            Optional<ButtonType> result=alert.showAndWait();
            if(result.get()==ButtonType.OK){
                Mark entity=markService.addMark(mark.getStudentId(),mark.getProjectId(),mark.getValue(),mark.getFeedback());
                if(entity==null){
                    showMessage(Alert.AlertType.INFORMATION,"Mark added","Mark added with success");
                }else{
                    showErrorMessage("Failed adding");
                }
            }
        }catch (ValidatorException e){
            showErrorMessage(e.getMessage());
        }catch(NullPointerException e){
            showErrorMessage("Please insert valid data for the mark");
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

    private Mark extractMark() {
        String idProject=comboBoxProject.getValue().split(" ")[1];
        String studentName=comboBoxStudent.getValue();
        if(studentName==null){
            return null;
        }
        String value=textFieldMark.getText();
        if(value==null){
            return null;
        }
        String idStudent=studentName.split(" ")[1];
        String feedback=textAreaFeedback.getText();
        return new Mark(idStudent,idProject,Float.parseFloat(value),feedback);
    }
}
