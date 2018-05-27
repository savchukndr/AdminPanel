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
            String sql = "CREATE TABLE IF NOT EXISTS chain(" +
                    "id SERIAL, " +
                    "name TEXT PRIMARY KEY NOT NULL);";
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
            String sql = "SELECT * FROM chain;";
            resultSet = stmt.executeQuery(sql);
        } catch (SQLException ignored) {
        }
        return resultSet;
    }

    public void deleteRow(String chainName){
        //TODO: delete from chain and all rows from store tables
        try {
            stmt = conn.createStatement();
            String sql = "DELETE FROM store WHERE name_chain = '" + chainName + "';";
            stmt.executeUpdate(sql);
            String sql1 = "DELETE FROM chain WHERE name = '" + chainName + "';";
            stmt.executeUpdate(sql1);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
