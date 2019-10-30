package ObjectProtocol;

import ModelDomain.Ticket;

public class GetTicketResponse implements Response{
    private Ticket ticket;
    protected GetTicketResponse(Ticket ticket){
        this.ticket=ticket;
    }
    public Ticket getTicket(){
        return ticket;
    }
}
