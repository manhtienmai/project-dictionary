package application;
import java.sql.DriverManager;
import java.sql.Connection;
import java.sql.SQLException;

public class DbConnection {
    public Connection dbLink;

    public Connection getDBConnection(){
        System.out.println("Attempting to connect to the database...");
        String dbName = "datadictionary";
        String dbUser = "root";
        String dbPassword = "abc123";
        String url = "jdbc:mysql://localhost:3306/" + dbName;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection connection = DriverManager.getConnection(url, dbUser, dbPassword);
            System.out.println("Connected to database successfully.");
            return connection;
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
        } catch (SQLException e) {
            System.out.println("Connection failed. Check output console.");
            e.printStackTrace();
        }

        return null;
    }

}
