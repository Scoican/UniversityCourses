package Repository;

import Domain.Event;
import Domain.User
        ;
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

public class UserRepository implements IRepository<Integer, User> {

    private JdbcUtils dbUtils;

    private static final Logger logger= LogManager.getLogger();

    public UserRepository(Properties props){
        logger.info("Initializing UserRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Users")){
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB User Size " + e);
        }
        return 0;
    }

    @Override
    public void save(User entity) {
        logger.traceEntry("saving user{}",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Users values (?,?,?)")) {
            preStmt.setInt(1,entity.getId());
            preStmt.setString(2,entity.getUsername());
            preStmt.setString(3,entity.getPassword());
            int result = preStmt.executeUpdate();
        }
        catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB User Save "+e);
        }
        logger.traceExit();
    }

    @Override
    public void delete(Integer integer) {
        logger.traceEntry("deleting user with {}",integer);
        Connection con = dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Users where id=?")){
            preStmt.setInt(1,integer);
            int result = preStmt.executeUpdate();
        }catch(SQLException e){
            logger.error(e);
            System.out.println("Error DB User Delete "+e);
        }
        logger.traceExit();
    }

    @Override
    public void update(Integer integer, User entity) {
        logger.traceEntry("updating user with {}",integer);
        Connection con = dbUtils.getConnection();
        User user=findOne(integer);
        if(user==null){
            return;
        }
        Integer newId=entity.getId();
        String newUsername=entity.getUsername();
        String newPassword=entity.getPassword();

        try(PreparedStatement preStmt=con.prepareStatement("update Users set id = ?,username = ?,password = ? where id = ? ")){
            preStmt.setInt(4,integer);
            preStmt.setInt(1,newId);
            preStmt.setString(2,newUsername);
            preStmt.setString(3,newPassword);
            int result=preStmt.executeUpdate();
        }catch(SQLException e) {
            logger.error(e);
            System.out.println("Error DB User Update " + e);
        }
        logger.traceExit();
    }

    @Override
    public User findOne(Integer integer) {
        logger.traceEntry("finding user with id {}",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Users where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    int id= result.getInt("id");
                    String username=result.getString("username");
                    String password=result.getString("password");
                    User user = new User(id,username,password);
                    logger.traceExit(user);
                    return user;
                }
            }
        }catch(SQLException e) {
            logger.error(e);
            System.out.println("Error DB User FindOne " + e);
        }
        logger.traceExit("No user found with id{}",integer);
        return null;
    }

    public User findOne(String user) {
        logger.traceEntry("finding user with username {}",user);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Users where username=?")){
            preStmt.setString(1,user);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    int id= result.getInt("id");
                    String username=result.getString("username");
                    String password=result.getString("password");
                    User u = new User(id,username,password);
                    logger.traceExit(u);
                    return u;
                }
            }
        }catch(SQLException e) {
            logger.error(e);
            System.out.println("Error DB User FindOne " + e);
        }
        logger.traceExit("No user found with username{}",user);
        return null;
    }

    @Override
    public Iterable<User> findAll() {
        logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<User> users=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Users")){
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    int id= result.getInt("id");
                    String username=result.getString("username");
                    String password=result.getString("password");
                    User user = new User(id,username,password);
                    users.add(user);
                }
            }
        }catch(SQLException e) {
            logger.error(e);
            System.out.println("Error DB User FindAll " + e);
        }
        logger.traceExit(users);
        return users;
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
