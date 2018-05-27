package gui_tables;

import java.awt.*;
import java.awt.event.ActionEvent;

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

        //THE ROW
        for(int i=0;i<=20;i++)
        {
            model.addRow(new Object[0]);
            model.setValueAt(false,i,0);
            model.setValueAt("ID" + i, i, 1);
            model.setValueAt("Chain store", i, 2);
        }

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

            //DISPLAY
            if(checked)
            {
                JOptionPane.showMessageDialog(null, "Deleted: " + i);
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