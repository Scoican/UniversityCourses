package Interface;

import Domain.Event;
import Domain.EventDTO;
import Domain.GameState;
import Domain.Ticket;
import Service.EventService;
import Service.TicketService;
import Service.UserService;
import Utils.EventChangeEvent;
import Utils.EventType;
import Utils.Observer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainPageController extends AbstractViewController implements Observer<EventChangeEvent> {

    private UserService userService;
    private TicketService ticketService;
    private EventService eventService;
    private ObservableList<EventDTO> model = FXCollections.observableArrayList();

    @FXML
    TableView<EventDTO> tabel;
    @FXML
    TableColumn<EventDTO, String> eventCol;
    @FXML
    TableColumn<EventDTO, String> seatsCol;
    @FXML
    TableColumn<EventDTO, String> priceCol;
    @FXML
    TableColumn<EventDTO,String> statusCol;
    @FXML
    TextField eventText;
    @FXML
    TextField clientText;
    @FXML
    Label priceLabel;
    @FXML
    Spinner<Integer> nrSpinner;
    @FXML
    Button buyButton;
    @FXML
    Button clearButton;
    @FXML
    Button logoutButton;


    public void setService(UserService userService, TicketService ticketService, EventService eventService) {
        this.userService = userService;
        this.ticketService = ticketService;
        this.eventService = eventService;
        this.eventService.addObserver(this);
        initModel();
        initSpinner();
    }

    private void initModel() {

        model.setAll(getEventDTO());
    }

    private List<EventDTO> getEventDTO() {
        return StreamSupport.stream(eventService.findAll().spliterator(), false)
                .map(m -> new EventDTO(m.getGameName(), m.getFreeSeats(), m.getGamePrice(), m.getId())).collect(Collectors.toList());
    }


    @FXML
    public void handlerLogout(ActionEvent e) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/LoginView.fxml"));
        Parent root = fxmlLoader.load();
        root.setId("pane");
        LoginController controller = fxmlLoader.getController();
        controller.setService(userService, ticketService, eventService);
        Stage stage = new Stage();
        Scene scene=new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("/style.css").toExternalForm());
        stage.setScene(scene);
        stage.show();
        Stage thatStage = (Stage) eventText.getScene().getWindow();
        thatStage.close();
    }


    @FXML
    public void handlerClear(ActionEvent e) {
        showEvent(null);
    }

    @FXML
    public void handlerBuy(ActionEvent e) {

        Ticket ticket = extractTicket();
        Event event = eventService.findOne(ticket.getId_game());
        try {
            if (event.getGameState() == GameState.SOLD_OUT)
                throw new ValidationException("This event is SOLD OUT");
            else {
                ticketService.save(ticket.getId_game(), ticket.getReservedSeats(), ticket.getPrice(), ticket.getClientName());
                showMessage(Alert.AlertType.INFORMATION, "Succes! ", "Tickets purchased!");
                showEvent(null);
                update(new EventChangeEvent(EventType.UPDATE,event));
            }
        } catch (ValidationException ex) {
            showErrorMessage(ex.getMessage());
        }
    }

    public void initSpinner() {
        nrSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                Integer id = Integer.valueOf(eventText.getText().split(":")[0]);
                Event event = eventService.findOne(id);
                priceLabel.setText((event.getGamePrice() * t1) + " lei");
            }
        });
    }

    private void showEvent(EventDTO m) {
        if (m == null) {
            eventText.setText("");
            clientText.setText("");
            //initSpinner();
            priceLabel.setText("0 lei");
        } else {
            eventText.setText(m.getId() + ":" + m.getEventName());
        }
    }

    private Ticket extractTicket() {
        Integer id_game = Integer.valueOf(eventText.getText().split(":")[0]);
        String name = clientText.getText();
        Integer nr = nrSpinner.getValue();
        Double price = Double.valueOf(priceLabel.getText().split(" ")[0]);

        return new Ticket(id_game, nr, name, price);
    }


    @FXML
    private void initialize() {

        eventCol.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        seatsCol.setCellValueFactory(new PropertyValueFactory<>("freeSeats"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("gameState"));

        tabel.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> showEvent(newValue));

        tabel.setItems(model);
    }

    @Override
    public void update(EventChangeEvent eventChangeEvent) {
        initModel();
        initSpinner();
    }
}