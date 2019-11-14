package Automaton;

public class Transition {
    private String source;
    private String destination;
    private String value;

    public Transition(String source, String destination, String value) {
        this.source = source;
        this.destination = destination;
        this.value = value;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.source + "/" + this.destination + "/" + this.value;
    }
}
