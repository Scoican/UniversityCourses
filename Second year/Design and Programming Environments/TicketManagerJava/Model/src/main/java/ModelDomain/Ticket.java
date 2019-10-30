package ModelDomain;

import java.io.Serializable;
import java.util.Objects;

public class Ticket implements IHasID<Integer>, Serializable {
    private Integer id;
    private Integer id_game;
    private Integer reservedSeats;
    private String clientName;
    private Double price;

    public Ticket(Integer id, Integer id_game, Integer reservedSeats, Double price, String clientName) {
        this.id = id;
        this.id_game = id_game;
        this.reservedSeats = reservedSeats;
        this.clientName = clientName;
        this.price = price;
    }

    public Ticket(Integer id_game, Integer reservedSeats, String clientName, Double price) {
        this.id_game = id_game;
        this.reservedSeats = reservedSeats;
        this.clientName = clientName;
        this.price = price;
    }

    public Integer getId_game() {
        return id_game;
    }

    public void setId_game(Integer id_game) {
        this.id_game = id_game;
    }

    public Integer getReservedSeats() {
        return reservedSeats;
    }

    public void setReservedSeats(Integer reservedSeats) {
        this.reservedSeats = reservedSeats;
    }

    public String getClientName() {
        return clientName;
    }

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public Integer getId() {
        return id;
    }

    @Override
    public void setId(Integer integer) {
        id = integer;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public String toString() {
        return "ModelDomain.Ticket{" +
                "id=" + id +
                ", id_game=" + id_game +
                ", reservedSeats=" + reservedSeats +
                ", clientName='" + clientName + '\'' +
                ", price=" + price +
                '}';
    }
}
