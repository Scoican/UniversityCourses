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
import java.util.List;

public class ClientObjectWorker implements Runnable, IClient, IObserver {
    private IServer server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    private String username;

    public ClientObjectWorker(IServer server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try {
            output = new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input = new ObjectInputStream(connection.getInputStream());
            connected = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (connected) {
            try {
                Object request = input.readObject();
                Object response = handleRequest((Request) request);
                if (response != null) {
                    sendResponse((Response) response);
                }
            } catch (IOException | BasketException | ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error " + e);
        }
    }

    private Response handleRequest(Request request) throws BasketException {
        if (request instanceof LoginRequest) {
            System.out.println("Login request ...");
            LoginRequest logReq = (LoginRequest) request;
            UserDTO userDTO = logReq.getUser();
            User user = DTOUtils.getFromDTO(userDTO);
            try {
                server.login(user, this);
                username = user.getId();
                return new OkResponse();
            } catch (BasketException e) {
                connected = false;
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof LogoutRequest) {
            System.out.println("Logout request");
            LogoutRequest logReq = (LogoutRequest) request;
            UserDTO userDTO = logReq.getUser();
            User user = DTOUtils.getFromDTO(userDTO);
            try {
                server.logout(user);
                connected = false;
                return new OkResponse();
            } catch (BasketException e) {
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof GetAllEventRequest) {
            List<Event> list = (List<Event>) server.getEvents();
            EventDTO[] listDTO = new EventDTO[list.size()];
            for (Event event : list) {
                EventDTO eventDTO = DTOUtils.getDTO(event);
                listDTO[list.indexOf(event)] = eventDTO;
            }
            return new GetAllEventResponse(listDTO);
        }
        if (request instanceof GetEventRequest) {
            Integer idEvent = ((GetEventRequest) request).getId();
            List<Event> list = (List<Event>) server.getEvents();
            for (Event event : list) {
                if (event.getId().equals(idEvent)) {
                    return new GetEventResponse(event);
                }
            }
        }
        if (request instanceof GetTicketRequest) {
            Integer idTicket = ((GetTicketRequest) request).getId();
            List<Ticket> list = (List<Ticket>) server.getTickets();
            for (Ticket ticket : list) {
                if (ticket.getId().equals(idTicket)) {
                    return new GetTicketResponse(ticket);
                }
            }
        }

        if (request instanceof AddEventRequest) {
            AddEventRequest saveSignUpRequest = (AddEventRequest) request;
            EventDTO eventDTO = saveSignUpRequest.getEventDTO();
            Event event = DTOUtils.getFromDTO(eventDTO);
            try {
                server.addEvent(event.getId(), event.getGameName(), event.getGamePrice(), event.getFreeSeats());
                return new OkResponse();
            } catch (BasketException e) {
                connected = false;
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof DeleteRequest) {
            System.out.println("Delete request ...");
            DeleteRequest deleteRequest = (DeleteRequest) request;
            Integer id = deleteRequest.getId();
            try {
                server.deleteEvent(id);
                return new OkResponse();
            } catch (BasketException e) {
                connected = false;
                return new ErrorResponse(e.getMessage());
            }
        }

        if (request instanceof UpdateEventRequest) {
            System.out.println("Update request ...");
            UpdateEventRequest updateEventRequest = (UpdateEventRequest) request;
            Integer id = updateEventRequest.getEventDTO().getId();
            Event event = DTOUtils.getFromDTO(updateEventRequest.getEventDTO());
            try {
                server.updateEvent(id, event.getGameName(), event.getGamePrice(), event.getFreeSeats());
                return new OkResponse();
            } catch (BasketException e) {
                connected = false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof GetAllTicketsRequest) {
            List<Ticket> list = (List<Ticket>) server.getTickets();
            TicketDTO[] listDTO = new TicketDTO[list.size()];
            for (Ticket ticket : list) {
                TicketDTO ticketDTO = DTOUtils.getDTO(ticket);
                listDTO[list.indexOf(ticket)] = ticketDTO;
            }
            return new GetAllTicketsResponse(listDTO);
        }
        if (request instanceof AddTicketRequest) {
            AddTicketRequest addTicketRequest = (AddTicketRequest) request;
            TicketDTO ticketDTO = addTicketRequest.getTicketDTO();
            Ticket ticket = DTOUtils.getFromDTO(ticketDTO);
            try {
                server.addTicket(ticket.getId_game(), ticket.getReservedSeats(), ticket.getClientName());
                return new OkResponse();
            } catch (BasketException e) {
                connected = false;
                return new ErrorResponse(e.getMessage());
            }
        }
        if (request instanceof DeleteTicketRequest) {
            System.out.println("Delete ticket request ...");
            DeleteTicketRequest deleteTicketRequest = (DeleteTicketRequest) request;
            Integer id = deleteTicketRequest.getId();
            try {
                server.deleteTicket(id);
                return new OkResponse();
            } catch (BasketException e) {
                connected = false;
                return new ErrorResponse(e.getMessage());
            }
        }
        return null;
    }

    @Override
    public void increaseNumberOfParticipants(Event event) throws BasketException {
        System.out.println("Update number of participants");
        try {
            sendResponse(new IncreaseNumberOfParticipantsResponse(event));
        } catch (IOException e) {
            throw new BasketException("Sending error " + e);
        }
    }

    private void sendResponse(Response response) throws IOException {
        System.out.println("Sending response " + response.toString());
        output.writeObject(response);
        output.flush();
    }

    @Override
    public void update(List<Event> events) {
        try {
            sendResponse(new NotifyResponse(events));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
