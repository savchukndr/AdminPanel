package gui_tables;

import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.ReportDbTable;

public class ReportTablePanel extends JPanel{

    //CONSTRUCTOR
    public ReportTablePanel(){
        setLayout(new BorderLayout());
        JPanel panelTop = new JPanel();
        panelTop.setLayout(new GridBagLayout());

        JTable table = new JTable();

        JScrollPane scrollTable=new JScrollPane(table);
        scrollTable.setViewportView(table);

        panelTop.add(scrollTable);

        //THE MODEL OF OUR TABLE
        DefaultTableModel model=new DefaultTableModel()
        {
            public Class<?> getColumnClass(int column)
            {
                switch(column)
                {
                    case 0:
                        return String.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;

                    default:
                        return String.class;
                }
            }
        };

        table.setModel(model);
        model.addColumn("ID");
        model.addColumn("Date");
        model.addColumn("Agreement title");

        ReportDbTable reportDbTable = new ReportDbTable();
        try {
            ResultSet resultSet = reportDbTable.selectReport();
            HashMap<String, ArrayList<String>> reportMap = new HashMap<>();
            while(resultSet.next()){
                ArrayList<String> reportList = new ArrayList<>();
                reportList.add(resultSet.getString("date_result"));
                reportList.add(resultSet.getString("agreement_title"));
                reportMap.put(resultSet.getString("id_result"), reportList);
            }
            int count = 0;
            for (String key: reportMap.keySet()) {
                model.addRow(new Object[0]);
                model.setValueAt(key, count, 0);
                model.setValueAt(reportMap.get(key).get(0), count, 1);
                model.setValueAt(reportMap.get(key).get(1), count, 2);
                count++;
            }
        } catch (NullPointerException | SQLException ignored) {}

        add(panelTop, BorderLayout.CENTER);
    }
}