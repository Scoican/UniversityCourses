using TicketManagerCSharp.domain;

namespace Networking.DTOS
{
    public class DTOUtils
    {
        public static User getFromDTO(UserDTO user)
        {
            string username = user.Username;
            string password = user.Password;
            return new User(username, password);
        }

        public static UserDTO getDTO(User user)
        {
            string username = user.Id;
            string password = user.Password;
            return new UserDTO(username, password);
        }

        public static Event getFromDTO(EventDTO ev)
        {
            return new Event(ev.Id, ev.GameName, ev.GamePrice, ev.FreeSeats);
        }

        public static EventDTO getDTO(Event ev)
        {
            return new EventDTO(ev.Id, ev.GameName, ev.GamePrice, ev.FreeSeats);
        }

        public static Ticket getFromDTO(TicketDTO ticket)
        {
            return new Ticket(ticket.Id, ticket.IdGame, ticket.ReservedSeats, ticket.Price, ticket.ClientName);
        }

        public static TicketDTO getDTO(Ticket ticket)
        {
            return new TicketDTO(ticket.Id, ticket.IdGame, ticket.ReservedSeats, ticket.Price, ticket.ClientName);
        }
    }
}