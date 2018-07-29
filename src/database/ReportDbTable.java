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

    public ReportDbTable(){
        Connector connect = new Connector();
        connect.connect();
        conn = connect.getConn();
    }

    public ResultSet selectReport(){
        ResultSet resultSet = null;
        try {
            Statement stmt = conn.createStatement();
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

    public ResultSet selectSearch(){
        ResultSet resultSet = null;
        try {
            Statement stmt = conn.createStatement();
            String sql = "SELECT res.id_result AS id_result,\n" +
                    "        res.product_count AS product_count,\n" +
                    "        res.is_product_on_exposition AS exposition,\n" +
                    "        res.product_localization AS localization,\n" +
                    "        res.is_product_visible AS product_visibility,\n" +
                    "        res.is_distributor_visible AS distributor_visibility,\n" +
                    "        res.date_result AS date_result,\n" +
                    "        res.image_proc_estimation AS estimation,\n" +
                    "        res.id_image AS image_id,\n" +
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
