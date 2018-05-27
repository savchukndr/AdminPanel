package gui_tables;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import database.ChainDbTable;
import dialog_frames.*;
import gui.MainFrame;
import gui_panels.ChainPanel;
import main.Main;

public class ChainTablePanel extends JPanel{

    private JTable table;
    private AddChainFrame dialogFrame;
    private ChainDbTable chainDbTable;
    private HashMap<String, String> chainMap;

    //CONSTRUCTOR
    public ChainTablePanel(){
        setLayout(new BorderLayout());
        JPanel panelTop = new JPanel(); // EventQueue.invokeLater(() -> panelTop = new JPanel());
        panelTop.setLayout(new GridBagLayout());
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new GridBagLayout());

        JButton buttonAddChain = new JButton("Add Chain");
        buttonAddChain.addActionListener(this::addActionPerformed);
        JButton buttonDeleteChain = new JButton("Delete Chain");
        buttonDeleteChain.addActionListener(this::deleteActionPerformed);

        table = new JTable();

        JScrollPane scrollTable=new JScrollPane(table);
        scrollTable.setViewportView(table);

        panelTop.add(scrollTable);
        panelBottom.add(buttonDeleteChain);
        panelBottom.add(buttonAddChain);

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

                    default:
                        return String.class;
                }
            }
        };

        //ASSIGN THE MODEL TO TABLE
        table.setModel(model);
        model.addColumn("Select");
        model.addColumn("ChainID");
        model.addColumn("Chain");

        chainDbTable = new ChainDbTable();
        try {
            ResultSet resultSet = chainDbTable.selectAll();
            chainMap = new HashMap<>();
            while(resultSet.next()){
                chainMap.put(resultSet.getString("id"), resultSet.getString("name").trim());
            }
            //THE ROW
            int count = 0;
            for (String key: chainMap.keySet()) {
                model.addRow(new Object[0]);
                model.setValueAt(false,count,0);
                model.setValueAt(key, count, 1);
                model.setValueAt(chainMap.get(key), count, 2);
                count++;
            }
        } catch (NullPointerException | SQLException ignored) {}

        //ADD BUTTON TO FORM
        buttonDeleteChain.setBounds(20,30,130,30);
        add(panelTop, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }

    private void deleteActionPerformed(ActionEvent e){
        // TODO Auto-generated method stub
        for(int i=0;i<table.getRowCount();i++)
        {
            Boolean checked=Boolean.valueOf(table.getValueAt(i, 0).toString());
            String chainId = table.getValueAt(i, 2).toString();

            //DISPLAY
            if(checked)
            {
                chainDbTable.deleteRow(chainId);
                JOptionPane.showMessageDialog(null, "Deleted: " + chainId);
            }
        }

        MainFrame mainFrame = Main.getMainFrame();
        ChainPanel chainPanel = mainFrame.getChainPanel();
        chainPanel.remove(this);
        chainPanel.revalidate();
        chainPanel.repaint();
        chainPanel.add(new ChainTablePanel());
    }

    private void addActionPerformed(ActionEvent e){
        dialogFrame = new AddChainFrame(this);
        dialogFrame.show();
    }
}