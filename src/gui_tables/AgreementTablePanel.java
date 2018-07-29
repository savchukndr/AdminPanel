package gui_tables;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

//import database.AgreementDbTable;
import database.AgreementDbTable;
import dialog_frames.*;
import gui.MainFrame;
import gui_panels.AgreementPanel;
import main.Main;

public class AgreementTablePanel extends JPanel {

    private JTable table;
    private AgreementDbTable agreementDbTable;

    //CONSTRUCTOR
    public AgreementTablePanel() {
        setLayout(new BorderLayout());
        JPanel panelTop = new JPanel(); // EventQueue.invokeLater(() -> panelTop = new JPanel());
        panelTop.setLayout(new GridBagLayout());
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new GridBagLayout());

        JButton buttonAddAgreement = new JButton("Add Agreement");
        buttonAddAgreement.addActionListener(this::addActionPerformed);
        JButton buttonDeleteAgreement = new JButton("Delete Agreement");
        buttonDeleteAgreement.addActionListener(this::deleteActionPerformed);
        JButton buttonShowAgreement = new JButton("Show Agreement");
        buttonShowAgreement.addActionListener(this::showActionPerformed);

        table = new JTable();

        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setViewportView(table);

        panelTop.add(scrollTable);
        panelBottom.add(buttonShowAgreement);
        panelBottom.add(buttonAddAgreement);
        panelBottom.add(buttonDeleteAgreement);


        //THE MODEL OF OUR TABLE
        DefaultTableModel model = new DefaultTableModel() {
            public Class<?> getColumnClass(int column) {
                switch (column) {
                    case 0:
                        return Boolean.class;
                    case 1:
                        return String.class;
                    case 2:
                        return String.class;
                    case 3:
                        return String.class;

                    default:
                        return String.class;
                }
            }
        };

        //ASSIGN THE MODEL TO TABLE
        table.setModel(model);
        model.addColumn("Select");
        model.addColumn("ID");
        model.addColumn("Title");
        model.addColumn("Store");


        agreementDbTable = new AgreementDbTable();
        try {
            ResultSet resultSet = agreementDbTable.selectAgreementData();
            HashMap<String, ArrayList<String>> agreementMap = new HashMap<>();
            while (resultSet.next()) {
                ArrayList<String> agreementList = new ArrayList<>();
                agreementList.add(resultSet.getString("agreement_title"));
                agreementList.add(resultSet.getString("store_title"));
                agreementMap.put(resultSet.getString("agreement_id"), agreementList);
            }
            //THE ROW
            int count = 0;
            for (String key : agreementMap.keySet()) {
                model.addRow(new Object[0]);
                model.setValueAt(false, count, 0);
                model.setValueAt(key, count, 1);
                model.setValueAt(agreementMap.get(key).get(0), count, 2);
                model.setValueAt(agreementMap.get(key).get(1), count, 3);
                count++;
            }
        } catch (NullPointerException | SQLException ignored) {
        }

        //ADD BUTTON TO FORM
        buttonDeleteAgreement.setBounds(20, 30, 130, 30);
        add(panelTop, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }

    private void showActionPerformed(ActionEvent e) {
        HashSet<Boolean> checkSet = new HashSet<>();
        for (int i = 0; i < table.getRowCount(); i++) {
            Boolean checked = Boolean.valueOf(table.getValueAt(i, 0).toString());
            String agreementId = table.getValueAt(i, 1).toString();

            //DISPLAY
            if (checked) {
                ShowAgreementsFrame dialogShowFrame = new ShowAgreementsFrame(agreementId);
                dialogShowFrame.show();
            } else {
                checkSet.add(checked);
            }
        }
//        if (!checkSet.isEmpty()){
//            JOptionPane.showMessageDialog(null, "No checked agreement to show");
//        }
    }

    private void addActionPerformed(ActionEvent e) {
        AddAgreementFrame dialogAddFrame = new AddAgreementFrame(this);
        dialogAddFrame.show();
    }

    private void deleteActionPerformed(ActionEvent e) {
        for (int i = 0; i < table.getRowCount(); i++) {
            Boolean checked = Boolean.valueOf(table.getValueAt(i, 0).toString());
            String agreementId = table.getValueAt(i, 1).toString();

            if (checked) {
                agreementDbTable.deleteRow(agreementId);
                JOptionPane.showMessageDialog(null, "Deleted: " + agreementId);
            }
        }

        MainFrame mainFrame = Main.getMainFrame();
        AgreementPanel agreementPanel = mainFrame.getAgrementPanel();
        agreementPanel.remove(this);
        agreementPanel.revalidate();
        agreementPanel.repaint();
        agreementPanel.add(new AgreementTablePanel());
    }
}