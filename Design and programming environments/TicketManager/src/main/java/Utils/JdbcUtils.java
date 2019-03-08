package Utils;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class JdbcUtils {
    private Properties jdbcProps;
    private static final Logger logger= LogManager.getLogger();
    public JdbcUtils(Properties props){
        jdbcProps=props;
    }
    private Connection instance=null;


    private Connection getNewConnection(){
        Connection con=null;
        try {
            String driver="org.sqlite.JDBC";
            Class.forName("org.sqlite.JDBC");
            logger.info("Loaded driver ...{}",driver);
            con= DriverManager.getConnection("jdbc:sqlite:C:/Users/Scoican/Desktop/Facultate/Medii de proiectare si programare/Laborator/TicketManager/TicketManager.db");
        } catch (ClassNotFoundException e) {
            logger.error(e);
            System.out.println("Error loading driver "+e);
        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error getting connection "+e);
        }
        return con;
    }

    public Connection getConnection(){
        logger.traceEntry();
        try {
            if (instance==null || instance.isClosed())
                instance=getNewConnection();

        } catch (SQLException e) {
            logger.error(e);
            System.out.println("Error DB "+e);
        }
        logger.traceExit(instance);
        return instance;
    }
}