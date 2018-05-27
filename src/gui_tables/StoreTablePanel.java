package gui_tables;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.StoreDbTable;
import dialog_frames.*;
import gui.MainFrame;
import gui_panels.StorePanel;
import main.Main;

public class StoreTablePanel extends JPanel{

    private JTable table;
    private AddStoreFrame dialogFrame;
    private StoreDbTable storeDbTable;
    private HashMap<String, List<String>> storeMap;

    //CONSTRUCTOR
    public StoreTablePanel(){
        setLayout(new BorderLayout());
        JPanel panelTop = new JPanel(); // EventQueue.invokeLater(() -> panelTop = new JPanel());
        panelTop.setLayout(new GridBagLayout());
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new GridBagLayout());

        JButton buttonAddStore = new JButton("Add Store");
        buttonAddStore.addActionListener(this::addActionPerformed);
        JButton buttonDeleteStore = new JButton("Delete Store");
        buttonDeleteStore.addActionListener(this::deleteActionPerformed);

        table = new JTable();

        JScrollPane scrollTable=new JScrollPane(table);
        scrollTable.setViewportView(table);

        panelTop.add(scrollTable);
        panelBottom.add(buttonDeleteStore);
        panelBottom.add(buttonAddStore);


        //THE MODEL OF OUR TABLE
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

                    default:
                        return String.class;
                }
            }
        };

        //ASSIGN THE MODEL TO TABLE
        table.setModel(model);
        model.addColumn("Select");
        model.addColumn("Store ID");
        model.addColumn("Store Chain");
        model.addColumn("Store");

        storeDbTable = new StoreDbTable();
        try{
            ResultSet resultSet = storeDbTable.selectAll();
            storeMap = new HashMap<>();
            while(resultSet.next()){
                List<String> stringList = new ArrayList<>();
                stringList.add(resultSet.getString("name_chain").trim());
                stringList.add(resultSet.getString("store").trim());
                storeMap.put(resultSet.getString("id_store"), stringList);
            }

            int count = 0;
            for (String key: storeMap.keySet()) {
                model.addRow(new Object[0]);
                List<String> temp = storeMap.get(key);
                model.setValueAt(false,count,0);
                model.setValueAt(key, count, 1);
                model.setValueAt(temp.get(0), count, 2);
                model.setValueAt(temp.get(1), count, 3);
                count++;
            }
        } catch (NullPointerException | SQLException e) {
            e.printStackTrace();
        }

        //ADD BUTTON TO FORM
        buttonDeleteStore.setBounds(20,30,130,30);
        add(panelTop, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }

    private void deleteActionPerformed(ActionEvent e){
        // TODO Auto-generated method stub
        for(int i=0;i<table.getRowCount();i++)
        {
            Boolean checked=Boolean.valueOf(table.getValueAt(i, 0).toString());
            String chainId = table.getValueAt(i, 1).toString();
            //DISPLAY
            if(checked)
            {
                storeDbTable.deleteRow(chainId);
                JOptionPane.showMessageDialog(null, "Deleted: " + i);
            }
        }

        MainFrame mainFrame = Main.getMainFrame();
        StorePanel storePanel = mainFrame.getStorePanel();
        storePanel.remove(this);
        storePanel.revalidate();
        storePanel.repaint();
        storePanel.add(new StoreTablePanel());
    }

    private void addActionPerformed(ActionEvent e){
        dialogFrame = new AddStoreFrame(this);
        dialogFrame.show();
    }
}