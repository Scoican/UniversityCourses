package ObjectProtocol;

import DTOPackage.TicketDTO;

public class GetAllTicketsResponse implements Response {
    private TicketDTO[] ticketDTO;

    public GetAllTicketsResponse(TicketDTO[] ticketDTO) {
        this.ticketDTO = ticketDTO;
    }

    public TicketDTO[] getTicketDTO() {
        return ticketDTO;
    }
}
