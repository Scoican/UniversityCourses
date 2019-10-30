package Repository;

import ModelDomain.Event;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class EventRepository implements IRepository<Integer, Event> {
    private Connection connection;

    public EventRepository() {
        connection = DatabaseConnection.getConnection();
    }

    @Override
    public int size() {

        try (PreparedStatement preStmt = connection.prepareStatement("select count(*) as [SIZE] from Events")) {
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB ModelDomain.Event Size " + e);
        }
        return 0;
    }

    @Override
    public void save(Event entity) {
        try (PreparedStatement preStmt = connection.prepareStatement("insert into Events values (?,?,?,?)")) {
            preStmt.setString(1, entity.getGameName());
            preStmt.setDouble(2, entity.getGamePrice());
            preStmt.setInt(3, entity.getFreeSeats());
            preStmt.setString(4, entity.getGameState().toString());
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error DB ModelDomain.Event Save " + e);
        }
    }

    @Override
    public void delete(Integer integer) {
        try (PreparedStatement preStmt = connection.prepareStatement("delete from Events where id=?")) {
            preStmt.setInt(1, integer);
            int result = preStmt.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Error DB ModelDomain.Event Delete " + e);
        }
    }

    @Override
    public void update(Integer integer, Event entity) {
        try (PreparedStatement preStmt = connection.prepareStatement("update Events set game_name=? ,game_price=?, free_seats=? ,game_state=? where id=?")) {
            preStmt.setString(1, entity.getGameName());
            preStmt.setDouble(2, entity.getGamePrice());
            preStmt.setInt(3, entity.getFreeSeats());
            preStmt.setString(4, entity.getGameState().toString());
            preStmt.setInt(5, integer);
            int result = preStmt.executeUpdate();

        } catch (SQLException ex) {
            System.out.println("Error DB " + ex);
        }
    }

    @Override
    public Event findOne(Integer integer) {
        try (PreparedStatement preStmt = connection.prepareStatement("select * from Events where id=?")) {
            preStmt.setInt(1, integer);
            try (ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                    int id = result.getInt("id");
                    String gameName = result.getString("game_name");
                    double price = result.getDouble("game_price");
                    int seats = result.getInt("free_seats");
                    Event event = new Event(id, gameName, price, seats);
                    return event;
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB ModelDomain.Event FindOne " + e);
        }
        return null;
    }

    @Override
    public Iterable<Event> findAll() {
        List<Event> events = new ArrayList<>();
        try (PreparedStatement preStmt = connection.prepareStatement("select * from events")) {
            try (ResultSet result = preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String gameName = result.getString("game_name");
                    double price = result.getDouble("game_price");
                    int seats = result.getInt("free_seats");
                    Event event = new Event(id, gameName, price, seats);
                    events.add(event);
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB ModelDomain.Event FindAll " + e);
        }
        return events;
    }
}
