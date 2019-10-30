package Domain;

import java.util.Objects;

public class EventDTO {
    private String eventName;
    private Integer freeSeats;
    private Double price;
    private GameState gameState;
    private Integer id;

    public EventDTO(String eventName, Integer freeSeats, Double price, Integer id) {
        this.eventName = eventName;
        this.freeSeats = freeSeats;
        this.price = price;
        this.id = id;
        if(this.freeSeats>0){
            this.gameState=GameState.AVAILABLE;
        }else{
            this.gameState=GameState.SOLD_OUT;
        }
    }

    public String getEventName() {
        return eventName;
    }

    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    public Integer getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(Integer freeSeats) {
        this.freeSeats = freeSeats;
        if(this.freeSeats>0){
            this.gameState=GameState.AVAILABLE;
        }else{
            this.gameState=GameState.SOLD_OUT;
        }
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EventDTO eventDTO = (EventDTO) o;
        return Objects.equals(id, eventDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventName, freeSeats, price, gameState, id);
    }

    @Override
    public String toString() {
        return "EventDTO{" +
                "eventName='" + eventName + '\'' +
                ", freeSeats=" + freeSeats +
                ", price=" + price +
                ", gameState=" + gameState +
                ", id=" + id +
                '}';
    }
}
