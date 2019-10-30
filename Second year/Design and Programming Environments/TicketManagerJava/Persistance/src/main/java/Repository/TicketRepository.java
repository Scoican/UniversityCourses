package Repository;

import ModelDomain.Ticket;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TicketRepository implements IRepository<Integer, Ticket> {
    private Connection connection;

    public TicketRepository() {
        connection = DatabaseConnection.getConnection();
    }

    @Override
    public int size() {
        try (PreparedStatement preStmt = connection.prepareStatement("select count(*) as [SIZE] from Tickets")) {
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB ModelDomain.Ticket Size " + e);
        }
        return 0;
    }

    @Override
    public void save(Ticket entity) {
        try (PreparedStatement preStmt = connection.prepareStatement("insert into Tickets(id_game,reserved_seats,price,client_name) values (?,?,?,?)")) {
            preStmt.setInt(1, entity.getId_game());
            preStmt.setInt(2, entity.getReservedSeats());
            preStmt.setDouble(3, entity.getPrice());
            preStmt.setString(4, entity.getClientName());
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error DB ModelDomain.Ticket Save " + e);
        }
    }

    @Override
    public void delete(Integer integer) {
        try (PreparedStatement preStmt = connection.prepareStatement("delete from Tickets where id=?")) {
            preStmt.setInt(1, integer);
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error DB ModelDomain.Ticket Delete " + e);
        }
    }

    @Override
    public void update(Integer integer, Ticket entity) {
        try (PreparedStatement preStmt = connection.prepareStatement("update Ticket set id_game=?,reserved_seats=?,price=?,client_name=?where id=?")) {
            //preStmt.setInt(1,entity.getId());
            preStmt.setInt(1, entity.getId_game());
            preStmt.setInt(2, entity.getReservedSeats());
            preStmt.setDouble(3, entity.getPrice());
            preStmt.setString(4, entity.getClientName());
            preStmt.setInt(5, integer);
            int result = preStmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
    }

    @Override
    public Ticket findOne(Integer integer) {
        try (PreparedStatement preStmt = connection.prepareStatement("select * from Tickets where id=?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    int game = result.getInt("id_game");
                    Integer reservedSeats = result.getInt("reserved_seats");
                    Double price = result.getDouble("price");
                    String clientName = result.getString("client_name");
                    Ticket ticket = new Ticket(id, game, reservedSeats, price, clientName);
                    return ticket;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB ModelDomain.Ticket FindOne " + e);
        }
        return null;
    }

    @Override
    public Iterable<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();
        try (PreparedStatement preStmt = connection.prepareStatement("select * from Tickets")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    int game = result.getInt("id_game");
                    Integer reservedSeats = result.getInt("reserved_seats");
                    Double price = result.getDouble("price");
                    String clientName = result.getString("client_name");
                    Ticket ticket = new Ticket(id, game, reservedSeats, price, clientName);
                    tickets.add(ticket);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB ModelDomain.Ticket FindAll " + e);
        }
        return tickets;
    }
}
