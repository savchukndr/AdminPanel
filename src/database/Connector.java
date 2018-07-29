package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by Andrii Savchuk on 27.05.2018.
 * All rights are reserved.
 * If you will have any cuastion, please
 * contact via email (savchukndr@gmail.com)
 */
public class Connector {
    private Connection conn = null;

    void connect() {
        try {
            String JDBC_DRIVER = "org.postgresql.Driver";
            Class.forName(JDBC_DRIVER);
            String db = "dyplomDB";
            String DB_URL = "jdbc:postgresql://localhost:5432/" + db;
            String USER = "savchukndr";
            String PASS = "savchukao22";
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public void disconnect() {
        try {
            if (conn != null)
                conn.close();
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
        }
    }

    Connection getConn() {
        return conn;
    }
}
