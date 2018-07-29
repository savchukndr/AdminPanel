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
public class ReportDbTable {
    private Connection conn;
    private Statement stmt;
    private Connector connect;

    public ReportDbTable(){
        connect = new Connector();
        connect.connect();
        conn = connect.getConn();
    }

    public Connection getConn() {
        return conn;
    }

    public ResultSet selectReport(){
        ResultSet resultSet = null;
        try {
            stmt = conn.createStatement();
            String sql = "SELECT res.id_result AS id_result,\n" +
                    "        res.date_result AS date_result,\n" +
                    "        agree_data.title AS agreement_title\n" +
                    "FROM image_processing_result res,\n" +
                    "      agreement_data agree_data\n" +
                    "WHERE res.id_agreement_data = agree_data.id_agreement_data;";
            resultSet = stmt.executeQuery(sql);
        } catch (SQLException ignored) {
        }
        return resultSet;
    }

}
