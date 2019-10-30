

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class UserRepository implements Repository<Integer, User>{

    private JdbcUtils dbUtils;

    //private static final Logger logger= LogManager.getLogger();

    public UserRepository(Properties props){
      //  logger.info("Initializing StudentRepository with properties: {} ",props);
        dbUtils=new JdbcUtils(props);
    }

    @Override
    public int size() {
       // logger.traceEntry();
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("select count(*) as [SIZE] from Users")) {
            try(ResultSet result = preStmt.executeQuery()) {
                if (result.next()) {
                   // logger.traceExit(result.getInt("SIZE"));
                    return result.getInt("SIZE");
                }
            }
        }catch(SQLException ex){
            //logger.error(ex);
            System.out.println("Error DB "+ex);
        }
        return 0;
    }

    @Override
    public void save(User entity) {
       // logger.traceEntry("saving student {} ",entity);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("insert into Users values (?,?,?)")){
            preStmt.setInt(1,entity.getId());
            preStmt.setString(2,entity.getUsername());
            preStmt.setString(3,entity.getPassword());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
           // logger.error(ex);
            System.out.println("Error DB "+ex);
        }
       // logger.traceExit();
    }

    @Override
    public void delete(Integer integer) {
       // logger.traceEntry("deleting student with {}",integer);
        Connection con=dbUtils.getConnection();
        try(PreparedStatement preStmt=con.prepareStatement("delete from Users where id=?")){
            preStmt.setInt(1,integer);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
         //   logger.error(ex);
            System.out.println("Error DB "+ex);
        }
       // logger.traceExit();
    }

    @Override
    public void update(Integer integer, User entity) {
      //  logger.traceEntry("updating student with {}",integer);
        Connection con=dbUtils.getConnection();
        User user=findOne(integer);
        if(user==null)
            return;
        String newUsername=entity.getUsername();
        String newPassword=entity.getPassword();

        try(PreparedStatement preStmt=con.prepareStatement("update Users " +
                "set username=?, password=?" +
                "where id=?")){
            preStmt.setInt(3,integer);
            preStmt.setString(1,newUsername);
            preStmt.setString(2,newPassword);
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
         //   logger.error(ex);
            System.out.println("Error DB "+ex);
        }
       // logger.traceExit();
    }

    @Override
    public User findOne(Integer integer) {
        //logger.traceEntry("finding student with nrMAtricol {} ",integer);
        Connection con=dbUtils.getConnection();

        try(PreparedStatement preStmt=con.prepareStatement("select * from Users where id=?")){
            preStmt.setInt(1,integer);
            try(ResultSet result=preStmt.executeQuery()) {
                if (result.next()) {
                    String username = result.getString("username");
                    String password = result.getString("password");
                    User user = new User(integer,username,password);
                  //  logger.traceExit(user);
                    return user;
                }
            }
        }catch (SQLException ex){
           // logger.error(ex);
            System.out.println("Error DB "+ex);
        }
       // logger.traceExit("No student found with id {}", integer);

        return null;
    }

    @Override
    public Iterable<User> findAll() {
        //logger.traceEntry();
        Connection con=dbUtils.getConnection();
        List<User> users=new ArrayList<>();
        try(PreparedStatement preStmt=con.prepareStatement("select * from Users")) {
            try(ResultSet result=preStmt.executeQuery()) {
                while (result.next()) {
                    int id = result.getInt("id");
                    String username = result.getString("username");
                    String password = result.getString("password");
                    User user = new User(id,username,password);
                    users.add(user);
                }
            }
        } catch (SQLException e) {
          //  logger.error(e);
            System.out.println("Error DB "+e);
        }
      //  logger.traceExit(users);
        return users;
    }
}