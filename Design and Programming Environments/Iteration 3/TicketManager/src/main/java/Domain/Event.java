package Domain;

import java.util.Objects;

public class Event implements IHasID<Integer>{

    private Integer id;
    private String gameName;
    private Double gamePrice;
    private Integer freeSeats;
    private GameState gameState;

    public Event(String gameName,Double gamePrice, Integer freeSeats) {
        this.gameName=gameName;
        this.gamePrice = gamePrice;
        this.freeSeats = freeSeats;
        if(this.freeSeats>0){
            gameState=GameState.AVAILABLE;
        }else{
            gameState=GameState.SOLD_OUT;
        }
    }

    public Event(Integer id,String gameName,Double gamePrice, Integer freeSeats) {
        this.id=id;
        this.gameName=gameName;
        this.gamePrice = gamePrice;
        this.freeSeats = freeSeats;
        if(this.freeSeats>0){
            gameState=GameState.AVAILABLE;
        }else{
            gameState=GameState.SOLD_OUT;
        }
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public Double getGamePrice() {
        return gamePrice;
    }

    public void setGamePrice(Double gamePrice) {
        this.gamePrice = gamePrice;
    }

    public Integer getFreeSeats() {
        return freeSeats;
    }

    public void setFreeSeats(Integer freeSeats) {
        this.freeSeats = freeSeats;
        if(this.freeSeats>0){
            this.setGameState(GameState.AVAILABLE);
        }else{
            this.setGameState(GameState.SOLD_OUT);
        }
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public Integer getId() {
        return this.id;
    }

    @Override
    public void setId(Integer integer) {
        this.id=integer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Event event = (Event) o;
        return Objects.equals(id, event.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id,gameName, gamePrice, freeSeats, gameState);
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", gameName='" + gameName + '\'' +
                ", gamePrice=" + gamePrice +
                ", freeSeats=" + freeSeats +
                ", gameState=" + gameState +
                '}';
    }
}
