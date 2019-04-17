package ObjectProtocol;

import DTOPackage.TicketDTO;

public class AddTicketRequest implements Request{
    private TicketDTO ticketDTO;

    public TicketDTO getTicketDTO() {
        return ticketDTO;
    }

    public AddTicketRequest(TicketDTO ticketDTO) {
        this.ticketDTO = ticketDTO;
    }
}
