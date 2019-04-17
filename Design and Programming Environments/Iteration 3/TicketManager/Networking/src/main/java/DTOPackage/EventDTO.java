package DTOPackage;

import Domain.GameState;

import java.io.Serializable;

public class EventDTO implements Serializable {
    private Integer id;
    private String gameName;
    private Double gamePrice;
    private Integer freeSeats;
    private GameState gameState;

    public EventDTO(Integer id,String gameName,Double gamePrice, Integer freeSeats) {
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

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }
}
