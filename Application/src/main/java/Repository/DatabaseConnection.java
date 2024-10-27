package Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private Connection connection;
    private String url;

    public DatabaseConnection() {

        url = "jdbc:sqlite:db/database.db";

        try {
            connection = DriverManager.getConnection(url);
            System.out.println("Connection Successful");

        } catch (SQLException e) {

            System.out.println("Error Connecting to Database");
            e.printStackTrace();

        }

    }


    public Connection getConnection() throws SQLException {return DriverManager.getConnection(url);}


    public void closeConnection() {

        try {

            if (connection != null) {

                System.out.println("Connection Closed");

                connection.close();

            }

        } catch (SQLException e) {

            e.printStackTrace();

        }

    }
}
