package Domain;

import java.util.Objects;

public class Ticket {
    private Integer id;
    private Integer event;
    private String buyer;
    private Double price;

    public Ticket(Integer id, Integer event, String buyer, Double price) {
        this.id = id;
        this.event = event;
        this.buyer = buyer;
        this.price = price;
    }

    public Ticket(Integer event, String buyer, Double price) {
        this.event = event;
        this.buyer = buyer;
        this.price = price;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getEvent() {
        return event;
    }

    public void setEvent(Integer event) {
        this.event = event;
    }

    public String getBuyer() {
        return buyer;
    }

    public void setBuyer(String buyer) {
        this.buyer = buyer;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) &&
                Objects.equals(event, ticket.event) &&
                Objects.equals(buyer, ticket.buyer) &&
                Objects.equals(price, ticket.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", event=" + event +
                ", buyer='" + buyer + '\'' +
                ", price=" + price +
                '}';
    }
}
