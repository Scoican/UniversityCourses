package ObjectProtocol;

public class DeleteRequest implements Request {
    public DeleteRequest(Integer id) {
        this.id = id;
    }

    private Integer id;

    public Integer getId() {
        return id;
    }
}
