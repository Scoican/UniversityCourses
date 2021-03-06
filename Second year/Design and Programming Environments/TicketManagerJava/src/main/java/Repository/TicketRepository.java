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
            System.out.println("Error DB ModelDomain.Ticket Size " + e);
        }
        return 0;
    }

    @Override
    public void save(Ticket entity) {
        logger.traceEntry("saving ticket{}",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Tickets(id_game,reserved_seats,price,client_name) values (?,?,?,?)")) {
            preStmt.setInt(1,entity.getId_game());
            preStmt.setInt(2,entity.getReservedSeats());
            preStmt.setDouble(3,entity.getPrice());
            preStmt.setString(4,entity.getClientName());
            int result = preStmt.executeUpdate();
        }
        catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB ModelDomain.Ticket Save "+e);
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
            System.out.println("Error DB ModelDomain.Ticket Delete "+e);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Ticket entity) {
        logger.traceEntry("updating ModelDomain.Ticket entity with {}", integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("update Ticket set id_game=?,reserved_seats=?,price=?,client_name=?where id=?")){
            //preStmt.setInt(1,entity.getId());
            preStmt.setInt(1,entity.getId_game());
            preStmt.setInt(2,entity.getReservedSeats());
            preStmt.setDouble(3,entity.getPrice());
            preStmt.setString(4,entity.getClientName());
            preStmt.setInt(5,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            logger.error(ex);
            System.out.println("Error DB "+ ex);
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
                    int game = result.getInt("id_game");
                    Integer reservedSeats=result.getInt("reserved_seats");
                    Double price=result.getDouble("price");
                    String clientName=result.getString("client_name");
                    Ticket ticket = new Ticket(id,game,reservedSeats,price,clientName);
                    logger.traceExit(ticket);
                    return ticket;
                }
            }
        }catch(SQLException e) {
            logger.error(e);
            System.out.println("Error DB ModelDomain.Ticket FindOne " + e);
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
                    int game = result.getInt("id_game");
                    Integer reservedSeats=result.getInt("reserved_seats");
                    Double price=result.getDouble("price");
                    String clientName=result.getString("client_name");
                    Ticket ticket = new Ticket(id,game,reservedSeats,price,clientName);
                    tickets.add(ticket);
                }
            }
        }catch(SQLException e) {
            logger.error(e);
            System.out.println("Error DB ModelDomain.Ticket FindAll " + e);
        }
        logger.traceExit(tickets);
        return tickets;
    }
    public JdbcUtils getDbUtils() {
        return dbUtils;
    }

    public void setDbUtils(JdbcUtils dbUtils) {
        this.dbUtils = dbUtils;
    }

    public static Logger getLogger() {
        return logger;
    }
}
