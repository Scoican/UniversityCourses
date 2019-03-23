package Interface;

import Domain.Event;
import Domain.Ticket;
import Domain.User;
import Service.EventService;
import Service.TicketService;
import Service.UserService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class UserController {


    @FXML
    TableView tableView;
    @FXML
    TableColumn<Event, String> nameColumn;
    @FXML
    TableColumn<Ticket, String> priceColumn;
    @FXML
    TableColumn<Event, String> seatsColumn;
    @FXML
    Button sellButton;
    @FXML
    Button searchButton;
    @FXML
    TextField buyerTextField;
    @FXML
    TextField seatsTextField;



    private UserService userService;
    private TicketService ticketService;
    private EventService eventService;
    private User user;
    private Scene loginScene;
    private Stage primaryStage;
    private ObservableList<Event> model = FXCollections.observableArrayList();

    private void initModel() {
        Iterable<Event> events = eventService.findAll();
        List<Event> eventList = StreamSupport.stream(events.spliterator(), false)
                .collect(Collectors.toList());
        model.setAll(eventList);
    }

    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("name"));
        seatsColumn.setCellValueFactory(new PropertyValueFactory<Event, String>("seats"));
        tableView.setItems(model);

    }

    @FXML
    public void handleSearch(){

        List<Event> eventList = StreamSupport.stream(eventService.findAll().spliterator(), false)
                .collect(Collectors.toList());
        eventList.sort(Comparator.comparingInt(Event::getSeats)
                .reversed());
        model.setAll(eventList);
        tableView.setItems(model);
    }

    @FXML
    public void handleSell(){
        String buyer="";
        String seats="";
        if (buyerTextField.getText() != null) {
             buyer = buyerTextField.getText().trim();
        }
        if(seatsTextField.getText()!=null){
            seats = seatsTextField.getText().trim();
        }
        Event event= (Event) tableView.getSelectionModel().getSelectedItem();
        event.setSeats(event.getSeats()-Integer.parseInt(seats));
        eventService.update(event.getId(),event.getName(),event.getSeats());
        initModel();
    }

    public void setLoginScene(Scene loginScene) {
        this.loginScene=loginScene;
    }

    public void setPrimaryStage(Stage primaryStage) {
        this.primaryStage=primaryStage;
    }

    public void setData(UserService userService, TicketService ticketService, EventService eventService, User user) {
        this.userService=userService;
        this.ticketService=ticketService;
        this.eventService=eventService;
        this.user=user;
        initModel();
    }

}
