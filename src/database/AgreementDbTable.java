package database;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Andrii Savchuk on 23.07.2018.
 * All rights are reserved.
 * If you will have any cuastion, please
 * contact via email (savchukndr@gmail.com)
 */
public class AgreementDbTable {
    private Connection conn;
    private Statement stmt;
    private Connector connect;

    public AgreementDbTable(){
        connect = new Connector();
        connect.connect();
        conn = connect.getConn();
    }

    public Connection getConn() {
        return conn;
    }

    public ResultSet selectAll(){
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM agreement;";
            resultSet = stmt.executeQuery(sql);
        } catch (SQLException ignored) {
        }
        return resultSet;
    }

    public ResultSet selectChain(){
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT * FROM chain";
            resultSet = stmt.executeQuery(sql);
        } catch (SQLException ignored) {
        }
        return resultSet;
    }

    public ResultSet selectStore(String chainSearch){
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT store FROM store WHERE name_chain='" + chainSearch + "'";
            resultSet = stmt.executeQuery(sql);
        } catch (SQLException ignored) {
        }
        return resultSet;
    }

    public ResultSet selectProductType(){
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT id_product_type, title FROM product_type";
            resultSet = stmt.executeQuery(sql);
        } catch (SQLException ignored) {
        }
        return resultSet;
    }

    public ResultSet selectProduct(String idProductTypeSearch){
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT id_product,title FROM product WHERE id_product_type='" + idProductTypeSearch + "'";
            resultSet = stmt.executeQuery(sql);
        } catch (SQLException ignored) {
        }
        return resultSet;
    }

}
