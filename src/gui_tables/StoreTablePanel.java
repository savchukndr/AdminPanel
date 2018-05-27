package gui_tables;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dialog_frames.*;
import gui.MainFrame;
import gui_panels.StorePanel;
import main.Main;

public class StoreTablePanel extends JPanel{

    private JTable table;
    private AddStoreFrame dialogFrame;

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
                    case 4:
                        return String.class;
                    case 5:
                        return String.class;

                    default:
                        return String.class;
                }
            }
        };

        //ASSIGN THE MODEL TO TABLE
        table.setModel(model);
        model.addColumn("Select");
        model.addColumn("Chain Store ID");
        model.addColumn("Chain Store");
        model.addColumn("Store");

        //THE ROW
        for(int i=0;i<=20;i++)
        {
            model.addRow(new Object[0]);
            model.setValueAt(false,i,0);
            model.setValueAt("ID" + i, i, 1);
            model.setValueAt("Chain name", i, 2);
            model.setValueAt("Store", i, 3);
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

            //DISPLAY
            if(checked)
            {
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