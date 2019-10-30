package DTOPackage;


import ModelDomain.Event;
import ModelDomain.Ticket;
import ModelDomain.User;

public class DTOUtils {
    public static User getFromDTO(UserDTO userDTO){
        String username=userDTO.getUsername();
        String password=userDTO.getPassword();
        return new User(username,password);
    }

    public static UserDTO getDTO(User user){
        String username=user.getId();
        String password=user.getPassword();
        return new UserDTO(username,password);
    }

    public static Event getFromDTO(EventDTO eventDTO){
        Integer id=eventDTO.getId();
        String gameName=eventDTO.getGameName();
        Double gamePrice=eventDTO.getGamePrice();
        Integer freeSeats=eventDTO.getFreeSeats();
        return new Event(id,gameName,gamePrice,freeSeats);
    }

    public static EventDTO getDTO(Event event){
        Integer id=event.getId();
        String gameName=event.getGameName();
        Double gamePrice=event.getGamePrice();
        Integer freeSeats=event.getFreeSeats();
        return new EventDTO(id,gameName,gamePrice,freeSeats);
    }

    public static TicketDTO getDTO(Ticket ticket){
        Integer id=ticket.getId();
        Integer idGame=ticket.getId_game();
        Integer reservedSeats=ticket.getReservedSeats();
        Double price=ticket.getPrice();
        String clientName=ticket.getClientName();
        return new TicketDTO(id,idGame,reservedSeats,price,clientName);
    }

    public static Ticket getFromDTO(TicketDTO ticketDTO){
        Integer id=ticketDTO.getId();
        Integer idGame=ticketDTO.getIdGame();
        Integer reservedSeats=ticketDTO.getReservedSeats();
        Double price=ticketDTO.getPrice();
        String clientName=ticketDTO.getClientName();
        return new Ticket(id,idGame,reservedSeats,price,clientName);
    }

}