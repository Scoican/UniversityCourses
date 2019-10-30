package Repository;

import ModelDomain.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserRepository implements IRepository<String,User> {
    private Connection connection;

    public UserRepository() {
        connection = DatabaseConnection.getConnection();
    }

    @Override
    public int size() {
        try(PreparedStatement preStmt=connection.prepareStatement("select count(*) as [SIZE] from Users")){
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    return result.getInt("SIZE");
                }
            }
        } catch (SQLException e) {
            System.out.println("Error DB ModelDomain.User Size " + e);
        }
        return 0;
    }

    @Override
    public void save(User entity) {
        try(PreparedStatement preStmt=connection.prepareStatement("insert into Users values (?,?)")) {
            preStmt.setString(1,entity.getId());
            preStmt.setString(2,entity.getPassword());
            int result = preStmt.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Error DB ModelDomain.User Save "+e);
        }
    }

    @Override
    public void delete(String s) {
        try(PreparedStatement preStmt=connection.prepareStatement("delete from Users where username=?")){
            preStmt.setString(1,s);
            int result = preStmt.executeUpdate();
        }catch(SQLException e){
            System.out.println("Error DB ModelDomain.User Delete "+e);
        }
    }

    @Override
    public void update(String s, User entity) {
        try(PreparedStatement preStmt=connection.prepareStatement("update Users set password=? where username=?")){
            preStmt.setString(1,entity.getPassword());
            preStmt.setString(2,entity.getId());
            int result=preStmt.executeUpdate();
        }catch (SQLException ex){
            System.out.println("Error DB "+ ex);
        }
    }

    @Override
    public User findOne(String s) {
        try(PreparedStatement preStmt=connection.prepareStatement("select * from Users where username=?")){
            preStmt.setString(1,s);
            try(ResultSet result = preStmt.executeQuery()){
                if(result.next()){
                    String username=result.getString("username");
                    String password=result.getString("password");
                    User user = new User(username,password);
                    return user;
                }
            }
        }catch(SQLException e) {
            System.out.println("Error DB ModelDomain.User FindOne " + e);
        }
        return null;
    }


    @Override
    public Iterable<User> findAll() {
        List<User> users=new ArrayList<>();
        try(PreparedStatement preStmt=connection.prepareStatement("select * from Users")){
            try(ResultSet result=preStmt.executeQuery()){
                while(result.next()){
                    String username=result.getString("username");
                    String password=result.getString("password");
                    User user = new User(username,password);
                    users.add(user);
                }
            }
        }catch(SQLException e) {
            System.out.println("Error DB ModelDomain.User FindAll " + e);
        }
        return users;
    }
}
