package GUI;

import Domain.Student;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class StudentView {
    private StudentController ctrl;

    private BorderPane borderPane;
    private TableView<Student> tableView = new TableView<>();

    TextField textFieldID = new TextField();
    TextField textFieldName = new TextField();
    TextField textFieldGroup = new TextField();
    TextField textFieldEmail = new TextField();
    TextField textFieldTeacher = new TextField();


    public StudentView(StudentController ctrl) {
        this.ctrl = ctrl;
        initView();
    }

    public BorderPane getView(){return borderPane;}

    private void initView() {
        borderPane=new BorderPane();

        borderPane.setTop(initTop());
        borderPane.setLeft(initLeft());
        borderPane.setCenter(initCenter());

        Label copyright = new Label("@Hertos");
        AnchorPane anchorPane = new AnchorPane(copyright);
        AnchorPane.setBottomAnchor(copyright,0d);
        borderPane.setBottom(anchorPane);
    }

    private AnchorPane initCenter() {
        AnchorPane centerAnchorPane=new AnchorPane();
        GridPane gridPane = createGridPane();

        centerAnchorPane.getChildren().add(gridPane);
        AnchorPane.setLeftAnchor(gridPane,20d);
        
        HBox buttonsGroup = createButtons();

        AnchorPane.setBottomAnchor(buttonsGroup,50d);
        AnchorPane.setLeftAnchor(buttonsGroup,75d);
        centerAnchorPane.getChildren().add(buttonsGroup);
        
        return centerAnchorPane;
    }

    private HBox createButtons() {
        Button buttonAdd = new Button("Add");
        Button buttonUpdate = new Button("Update");
        Button buttonDelete = new Button("Delete");

        HBox hBox = new HBox(5,buttonAdd,buttonUpdate,buttonDelete);

        buttonAdd.setOnAction(ctrl::handleAddStudent);
        buttonUpdate.setOnAction(ctrl::handleUpdateStudent);
        buttonDelete.setOnAction(ctrl::handleDeleteStudent);

        return hBox;
    }

    private GridPane createGridPane() {
        GridPane gridPaneStudent = new GridPane();
        gridPaneStudent.setHgap(5);
        gridPaneStudent.setVgap(5);

        Label labelID = createLabel("ID:");
        Label labelName = createLabel("Name:");
        Label labelGroup = createLabel("Group:");
        Label labelEmail = createLabel("Email:");
        Label labelTeacher = createLabel("Teacher:");


        gridPaneStudent.add(labelID, 0, 0);
        gridPaneStudent.add(labelName, 0, 1);
        gridPaneStudent.add(labelEmail, 0, 2);
        gridPaneStudent.add(labelTeacher, 0, 3);
        gridPaneStudent.add(labelGroup, 0, 4);

        gridPaneStudent.add(textFieldID, 1,0);
        gridPaneStudent.add(textFieldName, 1,1);
        gridPaneStudent.add(textFieldEmail, 1,2);
        gridPaneStudent.add(textFieldTeacher, 1,3);
        gridPaneStudent.add(textFieldGroup, 1,4);

        ColumnConstraints c1 = new ColumnConstraints();
        c1.setPrefWidth(60d);

        ColumnConstraints c2 = new ColumnConstraints();
        c2.setPrefWidth(200d);

        gridPaneStudent.getColumnConstraints().addAll(c1,c2);
        return gridPaneStudent;
    }

    private Label createLabel(String s) {
        Label l = new Label(s);
        l.setFont(new Font(15));
        l.setTextFill(Color.BLACK);

        return l;
    }

    private AnchorPane initLeft() {
        AnchorPane leftAnchorPane=new AnchorPane();
        tableView = createStudentTable();
        leftAnchorPane.getChildren().add(tableView);
        AnchorPane.setLeftAnchor(tableView,20d);

        return leftAnchorPane;
    }

    private TableView<Student> createStudentTable() {
        TableColumn<Student,String> nameColumn = new TableColumn<>("Name");
        TableColumn<Student,String> emailColumn = new TableColumn<>("Email");
        TableColumn<Student,String> teacherColumn = new TableColumn<>("Teacher");
        TableColumn<Student,String> groupColumn = new TableColumn<>("Group");

        tableView.getColumns().addAll(nameColumn,emailColumn,teacherColumn,groupColumn);

        nameColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("email"));
        teacherColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("teacher"));
        groupColumn.setCellValueFactory(new PropertyValueFactory<Student,String>("group"));

        tableView.setItems(ctrl.getModel());

        tableView.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue) ->{
                    ctrl.showStudentDetails(newValue);
                }
        );

        return tableView;
    }

    private AnchorPane initTop() {
        AnchorPane topAnchorPane=new AnchorPane();

        Label titleLabel=new Label("Student Manager");
        topAnchorPane.getChildren().add(titleLabel);
        AnchorPane.setTopAnchor(titleLabel,20d);
        AnchorPane.setRightAnchor(titleLabel,260d);
        titleLabel.setFont(new Font(30));
        return topAnchorPane;
    }
}
