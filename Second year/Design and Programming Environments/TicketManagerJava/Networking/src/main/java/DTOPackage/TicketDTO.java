package DTOPackage;

import java.io.Serializable;

public class TicketDTO implements Serializable {
    private Integer id;
    private Integer idGame;
    private Integer reservedSeats;
    private Double price;
    private String clientName;

    public TicketDTO(Integer id, Integer idGame, Integer reservedSeats, Double price, String clientName) {
        this.id = id;
        this.idGame = idGame;
        this.reservedSeats = reservedSeats;
        this.price = price;
        this.clientName = clientName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIdGame() {
        return idGame;
    }

    public void setIdGame(Integer idGame) {
        this.idGame = idGame;
    }

    public Integer getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(Integer reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }
}
