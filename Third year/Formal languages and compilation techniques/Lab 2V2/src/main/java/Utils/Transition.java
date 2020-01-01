package Utils;

public class Transition {

    private String source;
    private String destination;
    private String element;

    public String getSource() {
        return source;
    }

    public String getDestination() {
        return destination;
    }

    public String getElement() {
        return element;
    }

    public Transition(String source, String destination, String element){
        this.source = source;
        this.destination = destination;
        this.element = element;
    }

    @Override
    public String toString() {
        return this.source + "  " + this.destination + "  " + this.element;
    }
}
