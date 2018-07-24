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
            String sql = "SELECT id_chain, title FROM chain";
            resultSet = stmt.executeQuery(sql);
        } catch (SQLException ignored) {
        }
        return resultSet;
    }

    public ResultSet selectStore(String idChain){
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT id_chain,id_store,title FROM store WHERE id_chain=" + Integer.parseInt(idChain);
            resultSet = stmt.executeQuery(sql);
        } catch (SQLException ignored) {
        }
        return resultSet;
    }

    public ResultSet selectStoreID(String titleChain){
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT id_store FROM store WHERE title='" + titleChain + "'";
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
            String sql = "SELECT id_product,id_product_type,title FROM product WHERE id_product_type=" + Integer.parseInt(idProductTypeSearch);
            resultSet = stmt.executeQuery(sql);
        } catch (SQLException ignored) {
        }
        return resultSet;
    }

    public ResultSet selectProductID(String titleProduct){
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT id_product FROM product WHERE title='" + titleProduct + "'";
            resultSet = stmt.executeQuery(sql);
        } catch (SQLException ignored) {
        }
        return resultSet;
    }

    public void insertAgreement(int id_store){
        try {
            stmt = conn.createStatement();
            String sql = "INSERT INTO agreement(id_store) VALUES(" + id_store + ");";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet selectAgreementID(int id_store){
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT id_agreement FROM agreement WHERE id_store=" + id_store;
            resultSet = stmt.executeQuery(sql);
        } catch (SQLException ignored) {
        }
        return resultSet;
    }

    public void insertAgreementData(String title, int productCount, int productShelfPosition, int productId, int agreementId){
        try {
            stmt = conn.createStatement();
            String sql = "INSERT INTO agreement_data( " +
                    "title," +
                    "product_count, " +
                    "product_shelf_position," +
                    " id_product," +
                    " id_agreement) VALUES(" +
                    "'" + title + "', " +
                    "" + productCount + ", " +
                    "" + productShelfPosition + ", " +
                    "" + productId + ", " +
                    "" + agreementId + ");";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
