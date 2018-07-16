package gui_tables;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//import database.ReportDbTable;
import dialog_frames.*;
import gui.MainFrame;
import gui_panels.ReportPanel;
import main.Main;

public class ReportTablePanel extends JPanel{

    private JTable table;
    //    private ReportDbTable reportDbTable;
    private HashMap<String, String> reportMap;

    //CONSTRUCTOR
    public ReportTablePanel(){
        setLayout(new BorderLayout());
        JPanel panelTop = new JPanel(); // EventQueue.invokeLater(() -> panelTop = new JPanel());
        panelTop.setLayout(new GridBagLayout());

        table = new JTable();

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
                    case 3:
                        return String.class;
                    case 4:
                        return String.class;
                    case 5:
                        return String.class;
                    case 6:
                        return String.class;

                    default:
                        return String.class;
                }
            }
        };

        //ASSIGN THE MODEL TO TABLE
        table.setModel(model);
        model.addColumn("ID Result");
        model.addColumn("On shelf");
        model.addColumn("Localization");
        model.addColumn("Visability");
        model.addColumn("Date");
        model.addColumn("Estimation");
        model.addColumn("Agreement");

        //TODO: read from data base

        model.addRow(new Object[0]);
        model.setValueAt(1, 0,0);
        model.setValueAt("True", 0,1);
        model.setValueAt("Biedronka 1", 0,2);
        model.setValueAt("True", 0,3);
        model.setValueAt("2018/07/12 11:57:33", 0,4);
        model.setValueAt("Good", 0,5);
        model.setValueAt("Agreement 1", 0,6);

        model.addRow(new Object[0]);
        model.setValueAt(2, 1,0);
        model.setValueAt("True", 1,1);
        model.setValueAt("Zabka 1", 1,2);
        model.setValueAt("False", 1,3);
        model.setValueAt("2018/07/12 12:10:28", 1,4);
        model.setValueAt("Bad", 1,5);
        model.setValueAt("Agreement 2", 1,6);

        model.addRow(new Object[0]);
        model.setValueAt(3, 2,0);
        model.setValueAt("False", 2,1);
        model.setValueAt("Biedronka 2", 2,2);
        model.setValueAt("False", 2,3);
        model.setValueAt("2018/07/12 13:40:07", 2,4);
        model.setValueAt("Bad", 2,5);
        model.setValueAt("Agreement 2", 2,6);

//        for(int i=0; i<3; i++){
//            model.addRow(new Object[0]);
//            model.setValueAt(false, i,0);
//            model.setValueAt(i+1, i,1);
//            model.setValueAt("Ageement" + String.valueOf(i+1), i,2);
//        }

        add(panelTop, BorderLayout.CENTER);

        //TODO: read from data base
//        reportDbTable = new ReportDbTable();
//        try {
//            ResultSet resultSet = reportDbTable.selectAll();
//            reportMap = new HashMap<>();
//            while(resultSet.next()){
//                reportMap.put(resultSet.getString("id"), resultSet.getString("name").trim());
//            }
//            //THE ROW
//            int count = 0;
//            for (String key: reportMap.keySet()) {
//                model.addRow(new Object[0]);
//                model.setValueAt(false,count,0);
//                model.setValueAt(key, count, 1);
//                model.setValueAt(reportMap.get(key), count, 2);
//                count++;
//            }
//        } catch (NullPointerException | SQLException ignored) {}
    }
}