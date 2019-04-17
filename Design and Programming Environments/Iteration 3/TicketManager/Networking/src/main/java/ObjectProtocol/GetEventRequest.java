package ObjectProtocol;

public class GetEventRequest implements Request{
    Integer id;

    public Integer getId() {
        return id;
    }

    public GetEventRequest(Integer id) {
        this.id = id;
    }

}
