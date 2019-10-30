package Repository;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnection {

    public static Connection connection;
    public static Properties properties = new Properties();

    public static Connection getConnection() {
        try {
            properties.load(new FileInputStream("C:\\Users\\Scoican\\Desktop\\Facultate\\Medii de proiectare si programare\\Laborator\\TicketManager\\src\\main\\resources\\bd.properties"));
            Class.forName(properties.getProperty("mtasks.jdbc.driver"));
            connection = DriverManager.getConnection(properties.getProperty("mtasks.jdbc.url"));
            return connection;
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
            return null;
        }
    }
}