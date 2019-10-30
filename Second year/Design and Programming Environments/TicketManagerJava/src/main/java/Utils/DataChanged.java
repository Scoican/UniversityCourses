package Utils;

public class DataChanged implements EventUtils {
    private EventType type;

    public DataChanged(EventType type) {
        this.type = type;
    }
}
