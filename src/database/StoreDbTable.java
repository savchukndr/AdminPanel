package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Andrii Savchuk on 27.05.2018.
 * All rights are reserved.
 * If you will have any cuastion, please
 * contact via email (savchukndr@gmail.com)
 */
public class StoreDbTable {
    private Connection conn;
    private Statement stmt;

    public StoreDbTable() {
        Connector connect = new Connector();
        connect.connect();
        conn = connect.getConn();
    }

    public void createTable() {
        try {
            stmt = conn.createStatement();
            String sql = "CREATE TABLE IF NOT EXISTS store(" +
                    "id_store SERIAL PRIMARY KEY, " +
                    "name_chain TEXT REFERENCES chain(name)," +
                    "store TEXT NOT NULL);";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insert(String nameChain, String store) {
        try {
            stmt = conn.createStatement();
            String sql = "INSERT INTO store(name_chain, store) VALUES('" + nameChain + "','" + store + "');";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet selectAll() {
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM store;";
            resultSet = stmt.executeQuery(sql);
        } catch (SQLException ignored) {
        }
        return resultSet;
    }

    public void deleteRow(String storeId) {
        try {
            stmt = conn.createStatement();
            String sql = "DELETE FROM store WHERE id_store = " + storeId + ";";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
