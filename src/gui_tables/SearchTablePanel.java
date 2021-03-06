package gui_tables;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import database.ReportDbTable;
import dialog_frames.ShowImageFrame;

/**
 * Created by Andrii Savchuk on 21.04.2018.
 * All rights are reserved.
 * If you will have any questions, please
 * contact via email (savchukndr@gmail.com)
 */
public class SearchTablePanel extends JPanel {

    private static final long serialVersionUID = 4L;
    private JTable table;
    private JPanel panelTop, panelRight, panelBottom;
    private JTextField searchTextField;
    private JLabel labelListen;
    private JButton buttonSearch, buttonShowImage, buttonDelete;

    /**
     * Create the panel.
     */
    public SearchTablePanel(){
        // Set MainPanel Layout
        setLayout(new BorderLayout());

        // Panels
        panelTop = new JPanel();
        panelRight = new JPanel();
        panelRight.setLayout(new GridBagLayout());
        panelBottom = new JPanel();
        panelBottom.setLayout(new GridBagLayout());

        // Labels
//        JLabel labelTabName = new JLabel("Main Page");
//        panelTop.add(labelTabName);
        labelListen = new JLabel();
        labelListen.setText("Press \"Search\" to start searching:");

        // Buttons
        buttonSearch = new JButton("Search");
        buttonSearch.addActionListener(this::searchActionPerformed);
        buttonSearch.setEnabled(true);
        buttonShowImage = new JButton("Show Image");
        buttonShowImage.setBounds(20,30,130,30);
        buttonShowImage.addActionListener(this::showActionPerformed);
        buttonDelete = new JButton("Delete");
//        buttonDelete.addActionListener(this::deleteActionPerformed);

        //Text fields
        searchTextField = new JTextField();
        searchTextField.setPreferredSize( new Dimension( 200, 27) );

        // Adding scroll to main Text Area
        table = new JTable();

        JScrollPane scrollTable=new JScrollPane(table);
        scrollTable.setViewportView(table);

        add(panelTop, BorderLayout.CENTER);


        panelTop.add(labelListen, new GridBagConstraints(0, 5, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 2, 100, 2), 2, 2));
        panelTop.add(searchTextField, new GridBagConstraints(1, 5, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0, 2, 350, 2), 2, 2));
        panelTop.add(buttonSearch, new GridBagConstraints(2, 5, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 2, 2), 2, 2));


        // Adding scroll with TextArea to panelRight
        panelRight.add(scrollTable, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 0, 2), 2, 2));
        panelBottom.add(buttonShowImage, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 0, 2), 2, 2));
        panelBottom.add(buttonDelete, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2, 2, 350, 2), 2, 2));


        // Add to MainPanel layaout
        add(panelTop, BorderLayout.NORTH);
        add(panelRight, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.WEST);
    }

    private void showTableSerach(String str){
        DefaultTableModel model=new DefaultTableModel()
        {
            public Class<?> getColumnClass(int column)
            {
                switch(column)
                {
                    case 0:
                        return Boolean.class;
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
                    case 7:
                        return String.class;
                    case 8:
                        return String.class;

                    default:
                        return String.class;
                }
            }
        };

        table.setModel(model);
        model.addColumn("Select");
        model.addColumn("Estimation");
        model.addColumn("Date");
        model.addColumn("Amount");
        model.addColumn("Exposition");
        model.addColumn("Visibility product");
        model.addColumn("Visibility distributor");
        model.addColumn("Localization");
        model.addColumn("Agreement title");
        model.addColumn("Image ID");

        ReportDbTable reportDbTable = new ReportDbTable();
        try {
            ResultSet resultSet = reportDbTable.selectByStoreSearch(str);
            if (!resultSet.isBeforeFirst() ) {
                resultSet = reportDbTable.selectByEstimation(str);
                if (!resultSet.isBeforeFirst()){
                    resultSet = reportDbTable.selectByAgreement(str);
                    if (!resultSet.isBeforeFirst()){
                        resultSet = reportDbTable.selectByDate(str);
                    }
                }
            }
            HashMap<String, ArrayList<String>> searchMap = new HashMap<>();
            while(resultSet.next()){
                ArrayList<String> reportList = new ArrayList<>();
                reportList.add(resultSet.getString("estimation"));
                reportList.add(resultSet.getString("date_result"));
                reportList.add(resultSet.getString("product_count"));
                reportList.add(resultSet.getString("exposition"));
                reportList.add(resultSet.getString("product_visibility"));
                reportList.add(resultSet.getString("distributor_visibility"));
                reportList.add(resultSet.getString("localization"));
                reportList.add(resultSet.getString("agreement_title"));
                reportList.add(resultSet.getString("image_id"));
                searchMap.put(resultSet.getString("id_result"), reportList);
            }
            int count = 0;
            for (String key: searchMap.keySet()) {
                model.addRow(new Object[0]);
                model.setValueAt(false, count, 0);
                model.setValueAt(searchMap.get(key).get(0), count, 1);
                model.setValueAt(searchMap.get(key).get(1), count, 2);
                model.setValueAt(searchMap.get(key).get(2), count, 3);
                model.setValueAt(searchMap.get(key).get(3), count, 4);
                model.setValueAt(searchMap.get(key).get(4), count, 5);
                model.setValueAt(searchMap.get(key).get(5), count, 6);
                model.setValueAt(searchMap.get(key).get(6), count, 7);
                model.setValueAt(searchMap.get(key).get(7), count, 8);
                model.setValueAt(searchMap.get(key).get(8), count, 9);
                count++;
            }
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }


    }

    private void searchActionPerformed(ActionEvent e) {
        if (searchTextField.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Search field is empty.");
        } else {
            showTableSerach(searchTextField.getText());
        }
    }

    private void showActionPerformed(ActionEvent e) {
        HashSet<Boolean> checkSet = new HashSet<>();
        for (int i = 0; i < table.getRowCount(); i++) {
            Boolean checked = Boolean.valueOf(table.getValueAt(i, 0).toString());
            String image_id = table.getValueAt(i, 9).toString();

            //DISPLAY
            if (checked) {
                JFrame showFrame = new ShowImageFrame(image_id);
                showFrame.show();
            } else {
                checkSet.add(checked);
            }
        }

    }

}
