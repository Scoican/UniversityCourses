package Interfaces;

import Exceptions.BasketException;
import ModelDomain.*;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.xml.bind.ValidationException;
import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class MainPageController extends UnicastRemoteObject implements IClient, Initializable, IObserver, Serializable {

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
    TableColumn<EventDTO, String> statusCol;
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

    private IServer server;
    private User currentUser;

    public MainPageController() throws RemoteException {
    }

    public void setServer(IServer server) {
        this.server = server;
    }

    public void setService(IServer server, User currentUser) throws BasketException {
        this.server = server;
        this.currentUser = currentUser;
        initModel();
        initSpinner();

    }

    private void initModel() {
        model.setAll(getEventDTO());
    }

    private List<EventDTO> getEventDTO() {
        try {
            return StreamSupport.stream(server.getEvents().spliterator(), false)
                    .map(m -> new EventDTO(m.getGameName(), m.getFreeSeats(), m.getGamePrice(), m.getId())).collect(Collectors.toList());
        } catch (BasketException e) {
            e.printStackTrace();
            return null;
        }
    }

    @FXML
    public void handlerLogout(ActionEvent e) throws IOException, BasketException {

        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/LoginView.fxml"));
        Parent root = fxmlLoader.load();
        ((Node)(e.getSource())).getScene().getWindow().hide();
        LoginController controller = fxmlLoader.getController();
        controller.setService(server);
        server.logout(currentUser);
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    public void handlerClear(ActionEvent e) {
        showEvent(null);
    }

    @FXML
    public void handlerBuy(ActionEvent e) {
        Ticket ticket = extractTicket();
        EventDTO eventDTO = tabel.getSelectionModel().getSelectedItem();

        try {
            if (eventDTO.getGameState() == GameState.SOLD_OUT)
                throw new ValidationException("This event is SOLD OUT");
            else {
                server.addTicket(ticket.getId_game(), ticket.getReservedSeats(), ticket.getClientName());
                AbstractViewController.showMessage(Alert.AlertType.INFORMATION, "Success! ", "Tickets purchased!");
                showEvent(null);
                update((List<Event>) server.getEvents());
            }
        } catch (ValidationException | BasketException ex) {
            AbstractViewController.showErrorMessage(ex.getMessage());
        }

    }

    public void initSpinner() {
        nrSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                Double price = tabel.getSelectionModel().getSelectedItem().getPrice();
                priceLabel.setText((price * t1) + " lei");
            }
        });

    }

    private void showEvent(EventDTO m) {
        if (m == null) {
            eventText.setText("");
            clientText.setText("");
            //initSpinner();
            Platform.runLater(new Runnable() {
                @Override
                public void run() {
                    priceLabel.setText("0 lei");
                }
            });

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
    public void increaseNumberOfParticipants(Event event) throws BasketException {

    }

    @Override
    public void update(List<Event> events) {
        model.setAll(events.stream()
                .map(m -> new EventDTO(m.getGameName(), m.getFreeSeats(), m.getGamePrice(), m.getId())).collect(Collectors.toList()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventCol.setCellValueFactory(new PropertyValueFactory<>("eventName"));
        seatsCol.setCellValueFactory(new PropertyValueFactory<>("freeSeats"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("gameState"));

        tabel.getSelectionModel().selectedItemProperty().addListener(
                (observable,oldValue,newValue)->{
                    showEvent(newValue);
                }
        );

        tabel.setItems(model);

        tabel.setRowFactory(row -> new TableRow<EventDTO>(){
            @Override
            public void updateItem(EventDTO item, boolean empty){
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setStyle("");
                } else {
                    if (item.getGameState() == GameState.SOLD_OUT) {
                        for(int i=0; i<getChildren().size();i++){
                            ((Labeled) getChildren().get(i)).setTextFill(Color.RED);
                        }
                    }
                }
            }
        });
    }
}