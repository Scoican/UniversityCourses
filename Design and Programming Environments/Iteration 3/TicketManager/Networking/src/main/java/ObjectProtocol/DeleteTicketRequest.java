package ObjectProtocol;

public class DeleteTicketRequest implements Request{
    Integer id;

    public Integer getId() {
        return id;
    }

    public DeleteTicketRequest(Integer id) {
        this.id = id;
    }
}
