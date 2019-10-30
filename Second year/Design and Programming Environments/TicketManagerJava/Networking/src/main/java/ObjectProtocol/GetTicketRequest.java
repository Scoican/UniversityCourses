package ObjectProtocol;

public class GetTicketRequest implements Request{
    Integer id;

    public Integer getId() {
        return id;
    }

    public GetTicketRequest(Integer id) {
        this.id = id;
    }

}
