package Repository;

import Domain.Event;
import Utils.JdbcUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.sound.midi.Soundbank;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class EventRepository implements IRepository<Integer, Event> {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public EventRepository(Properties props){
        logger.info("Initializing EventRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Events")){
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB Event Size " + e);
        }
        return 0;
    }

    @Override
    public void save(Event entity) {
        logger.traceEntry("saving event{}",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Events values (?,?,?)")) {
            preStmt.setInt(1,entity.getId());
            preStmt.setString(2,entity.getName());
            preStmt.setInt(3,entity.getSeats());
            int result = preStmt.executeUpdate();
        }
        catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB Event Save "+e);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting event with {}",integer);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Events where id=?")){
            preStmt.setInt(1,integer);
            int result = preStmt.executeUpdate();
        }catch(SQLException e){
            logger.error(e);
            System.out.println("Error DB Event Delete "+e);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, Event entity) {
        logger.traceEntry("updating event with {}",integer);
        Connection con = dbUtils.getConnection();
        Event event = findOne(integer);
        if(event==null){
            return;
        }
        Integer newId=entity.getId();
        String newName=entity.getName();
        Integer newSeats=entity.getSeats();

        try(PreparedStatement preStmt=con.prepareStatement("update Events"+
                " set id = ?,name = ?,seats = ? "+
                " where id = ? ")){
            preStmt.setInt(4,integer);
            preStmt.setInt(1,newId);
            preStmt.setString(2,newName);
            preStmt.setInt(3,newSeats);
            int result=preStmt.executeUpdate();
        }catch(SQLException e) {
            logger.error(e);
            System.out.println("Error DB Event Update " + e);
        }
        logger.traceExit();
    }

    @Override
    public Event findOne(Integer integer) {
        logger.traceEntry("finding event with id {}",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Events where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    int id= result.getInt("id");
                    String name=result.getString("name");
                    int seats=result.getInt("seats");
                    Event event = new Event(id,name,seats);
                    logger.traceExit(event);
                    return event;
                }
            }
        }catch(SQLException e) {
            logger.error(e);
            System.out.println("Error DB Event FindOne " + e);
        }
        logger.traceExit("No event found with id{}",integer);
        return null;
    }

    @Override
    public Iterable<Event> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<Event> events=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from events")){
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    int id= result.getInt("id");
                    String name=result.getString("name");
                    int seats=result.getInt("seats");
                    Event event = new Event(id,name,seats);
                    events.add(event);
                }
            }
        }catch(SQLException e) {
            logger.error(e);
            System.out.println("Error DB Event FindAll " + e);
        }
        logger.traceExit(events);
        return events;
    }
}
