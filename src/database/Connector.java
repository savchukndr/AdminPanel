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
    private String db = "dyplomDB";
    private String USER = "savchukndr";
    private String PASS = "savchukao22";
    private Connection conn = null;
    private String JDBC_DRIVER = "org.postgresql.Driver";

    public void connect(){
        try{
            Class.forName(JDBC_DRIVER);
            String DB_URL = "jdbc:postgresql://localhost:5432/" + db;
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

        } catch ( Exception e ) {
            System.err.println( e.getClass().getName()+": "+ e.getMessage() );
            System.exit(0);
        }
    }

    public void disconnect(){
        try{
            if(conn!=null)
                conn.close();
        }catch(SQLException sqlEx){
            sqlEx.printStackTrace();
        }
    }

    public Connection getConn() {
        return conn;
    }
}
