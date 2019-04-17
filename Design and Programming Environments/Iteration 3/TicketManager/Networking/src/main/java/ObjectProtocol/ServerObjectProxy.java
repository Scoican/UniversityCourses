package ObjectProtocol;

import DTOPackage.DTOUtils;
import DTOPackage.EventDTO;
import DTOPackage.TicketDTO;
import DTOPackage.UserDTO;
import Domain.Event;
import Domain.Ticket;
import Domain.User;
import Exceptions.BasketException;
import Interfaces.IClient;
import Interfaces.IObserver;
import Interfaces.IServer;
import Utils.EventChangeEvent;
import Utils.Observer;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServerObjectProxy implements IServer {
    private String host;
    private int port;
    private IObserver client;
    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;
    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;

    public ServerObjectProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<>();
    }

    @Override
    public void login(User user, IObserver client) throws BasketException {
        initializeConnection();
        UserDTO userDTO= DTOUtils.getDTO(user);
        sendRequest(new LoginRequest(userDTO));
        Response response=readResponse();
        if(response instanceof  OkResponse){
            this.client=client;
            return;
        }
        if(response instanceof ErrorResponse){
            ErrorResponse err=(ErrorResponse)response;
            closeConnection();
            throw new BasketException(err.getMessage());
        }
    }


    @Override
    public void logout(User user)throws BasketException{
        UserDTO officialUserDTO = DTOUtils.getDTO(user);
        sendRequest(new LogoutRequest(officialUserDTO));
        Response response = readResponse();
        closeConnection();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            throw new BasketException(err.getMessage());
        }

    }

    @Override
    public Iterable<Event> getEvents() throws BasketException {
        sendRequest(new GetAllEventRequest());
        Response response=readResponse();
        if(response instanceof GetAllEventResponse){
            List<Event> rez=new ArrayList<>();
            for(EventDTO eventDTO:((GetAllEventResponse)response).getEvents()){
                rez.add(DTOUtils.getFromDTO(eventDTO));
            }
            return rez;
        }
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new BasketException(err.getMessage());

        }
        return null;
    }

    public Iterable<Ticket> getTickets() throws BasketException{
        sendRequest(new GetAllTicketsRequest());
        Response response=readResponse();
        if(response instanceof GetAllTicketsResponse){
            List<Ticket> rez=new ArrayList<>();
            for(TicketDTO ticketDTO:((GetAllTicketsResponse)response).getTicketDTO()){
                rez.add(DTOUtils.getFromDTO(ticketDTO));
            }
            return rez;
        }

        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new BasketException(err.getMessage());

        }
        return null;
    }

    @Override
    public void addEvent(Integer id,String gameName,Double gamePrice,Integer freeSeats) throws BasketException {
        EventDTO eventDTO=new EventDTO(id,gameName,gamePrice,freeSeats);
        sendRequest(new AddEventRequest(eventDTO));
        Response response=readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new BasketException(err.getMessage());
        }
    }

    public void updateEvent(Integer id,String gameName,Double gamePrice,Integer freeSeats) throws BasketException {
        EventDTO eventDto=new EventDTO(id,gameName,gamePrice,freeSeats);
        sendRequest(new UpdateEventRequest(eventDto));
        Response response=readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new BasketException(err.getMessage());
        }
    }

    @Override
    public void addTicket(Integer idEvent,Integer reservedSeats,String clientName) throws BasketException {
        TicketDTO ticketDTO=new TicketDTO(0,idEvent,reservedSeats,0d,clientName);
        sendRequest(new AddTicketRequest(ticketDTO));
        Response response=readResponse();
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new BasketException(err.getMessage());
        } }

    @Override
    public void deleteTicket(Integer id) throws BasketException {
        sendRequest(new DeleteTicketRequest(id));
        Response response=readResponse();
        if(response instanceof OkResponse){
            System.out.println("Operatiune realizata cu succes");
        }
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new BasketException(err.getMessage());
        }
    }

    @Override
    public Ticket getTicket(Integer id) throws BasketException {
        sendRequest(new GetTicketRequest(id));
        Response response=readResponse();
        if(response instanceof GetTicketResponse){
            Ticket rez=((GetTicketResponse) response).getTicket();
            return rez;
        }
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new BasketException(err.getMessage());
        }
        return null;
    }

    @Override
    public Event getEvent(Integer id) throws BasketException {
        sendRequest(new GetEventRequest(id));
        Response response=readResponse();
        if(response instanceof GetEventResponse){
            return ((GetEventResponse) response).getEvent();
        }
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new BasketException(err.getMessage());
        }
        return null;
    }

    @Override
    public void deleteEvent(Integer id) throws BasketException {

        sendRequest(new DeleteRequest(id));
        Response response=readResponse();
        if(response instanceof OkResponse){
            System.out.println("Operatiune realizata cu succes");
        }
        if(response instanceof ErrorResponse){
            ErrorResponse err = (ErrorResponse) response;
            closeConnection();
            throw new BasketException(err.getMessage());
        }
    }

    private void initializeConnection() throws BasketException{
        try {
            connection = new Socket(host, port);
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input =  new ObjectInputStream(connection.getInputStream());
            finished = false;
            startReader();
        }catch (IOException e){
            e.printStackTrace();
        }

    }

    private void startReader(){
        Thread tw = new Thread(new ReaderThread());
        tw.start();
    }

    private class ReaderThread implements Runnable{
        public void run(){
            while (!finished){
                try {
                    Object response = input.readObject();
                    System.out.println("response received "+response);
                    if(response instanceof UpdateResponse){
                        handleUpdate((UpdateResponse)response);
                    }else {
                        try {
                            qresponses.put((Response)response);
                        }catch (InterruptedException e){
                            e.printStackTrace();
                        }
                    }
                }catch (IOException | ClassNotFoundException e){
                    System.out.println("Reading error " + e);
                }
            }
        }
    }

    private void handleUpdate(UpdateResponse response){
        if(response instanceof NotifyResponse){
            NotifyResponse notifyResponse=(NotifyResponse)response;
            List<Event> events=notifyResponse.getEvents();
            client.update(events);
            System.out.println("handleNotifyProxy ok");
        }
    }

    private void sendRequest(Request request)throws BasketException{
        try {
            output.writeObject(request);
            output.flush();
        }catch (IOException e){
            throw new BasketException("Error sending object "+ e);
        }
    }

    private Response readResponse() throws BasketException{
        Response response = null;
        try {
            response = qresponses.take();
        }catch (InterruptedException| NullPointerException e){
            e.printStackTrace();
        }
        return response;
    }

    private void closeConnection(){
        finished = true;
        try {
            input.close();
            output.close();
            connection.close();
            client = null;
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
