package application.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/* Singleton pattern, ensuring that only 1 db connection is created and used*/
public class DbConnection {
    private Connection dbLink;
    private static DbConnection instance;

    private DbConnection() {
        System.out.println("Try to connect db");
        createConnection();
    }

    private void createConnection() {
        String url = "jdbc:sqlite:F:/dictionary.db";

        try {
            Class.forName("org.sqlite.JDBC");
            this.dbLink = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    public static DbConnection getInstance() {
        if (instance == null) {
            instance = new DbConnection();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (dbLink == null || dbLink.isClosed()) {
                createConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return dbLink;
    }
//    private Connection dbLink;
//    public Connection getDBConnection(){
//        System.out.println("Try to connect to the db");
//        String url = "jdbc:sqlite:D:/dictionary.db";
//
//        try {
//            Class.forName("org.sqlite.JDBC");
//            Connection connection = DriverManager.getConnection(url);
//            System.out.println("Connected successfully.");
//            return connection;
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//
//        return null;
//    }
}
