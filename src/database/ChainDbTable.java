package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Andrii Savchuk on 27.05.2018.
 * All rights are reserved.
 * If you will have any cuastion, please
 * contact via email (savchukndr@gmail.com)
 */
public class ChainDbTable {
    private Connection conn;
    private Statement stmt;
    private Connector connect;

    public ChainDbTable(){
        connect = new Connector();
        connect.connect();
        conn = connect.getConn();
    }

    public Connection getConn() {
        return conn;
    }

    public void createTable(){
        try {
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS chain(id SERIAL, name CHAR(50) PRIMARY KEY NOT NULL);";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String chainName){
        try {
            stmt = conn.createStatement();
            String sql = "INSERT INTO chain(name) VALUES('" + chainName + "');";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet selectAll(){
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM public.chain;";
            resultSet = stmt.executeQuery(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }
}
