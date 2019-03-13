package Repository;

import Domain.Event;
import Domain.Ticket;
import Utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


import java.nio.DoubleBuffer;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class TicketRepository implements IRepository<Integer, Ticket> {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public TicketRepository(Properties props){
        logger.info("Initializing TicketRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Tickets")){
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB Ticket Size " + e);
        }
        return 0;
    }

    @Override
    public void save(Ticket entity) {
        logger.traceEntry("saving ticket{}",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Tickets values (?,?,?,?)")) {
            preStmt.setInt(1,entity.getId());
            preStmt.setString(2,entity.getBuyer());
            preStmt.setDouble(3,entity.getPrice());
            preStmt.setInt(4,entity.getEvent());
            int result = preStmt.executeUpdate();
        }
        catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB Ticket Save "+e);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting ticket with {}",integer);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Tickets where id=?")){
            preStmt.setInt(1,integer);
            int result = preStmt.executeUpdate();
        }catch(SQLException e){
            logger.error(e);
            System.out.println("Error DB Ticket Delete "+e);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Ticket entity) {
        logger.traceEntry("updating ticket with {}",integer);
        Connection con = dbUtils.getConnection();
        Ticket ticket = findOne(integer);
        if(ticket==null){
            return;
        }
        Integer newId=entity.getId();
        Integer newEvent=entity.getEvent();
        String newBuyer=entity.getBuyer();
        Double newPrice=entity.getPrice();

        try(PreparedStatement preStmt=con.prepareStatement("update Tickets"+
                " set id = ?,event = ?,buyer = ?,price = ? "+
                " where id = ? ")){
            preStmt.setInt(5,integer);
            preStmt.setInt(1,newId);
            preStmt.setInt(2,newEvent);
            preStmt.setString(3,newBuyer);
            preStmt.setDouble(4,newPrice);
            int result=preStmt.executeUpdate();
        }catch(SQLException e) {
            logger.error(e);
            System.out.println("Error DB Ticket Update " + e);
        }
        logger.traceExit();
    }

    @Override
    public Ticket findOne(Integer integer) {
        logger.traceEntry("finding ticket with id {}",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Tickets where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    int id= result.getInt("id");
                    int event = result.getInt("event");
                    String buyer=result.getString("buyer");
                    double price=result.getDouble("price");
                    Ticket ticket = new Ticket(id,event,buyer,price);
                    logger.traceExit(ticket);
                    return ticket;
                }
            }
        }catch(SQLException e) {
            logger.error(e);
            System.out.println("Error DB Ticket FindOne " + e);
        }
        logger.traceExit("No ticket found with id{}",integer);
        return null;
    }

    @Override
    public Iterable<Ticket> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Ticket> tickets=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Tickets")){
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    int id= result.getInt("id");
                    int event = result.getInt("event");
                    String buyer=result.getString("buyer");
                    double price=result.getDouble("price");
                    Ticket ticket = new Ticket(id,event,buyer,price);
                    tickets.add(ticket);
                }
            }
        }catch(SQLException e) {
            logger.error(e);
            System.out.println("Error DB Ticket FindAll " + e);
        }
        logger.traceExit(tickets);
        return tickets;
    }
}
