package gui_table;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

public class EmployeeTablePanel extends JPanel{

    private JTable table;

    //CONSTRUCTOR
    public EmployeeTablePanel(){
//        EventQueue.invokeLater(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
        setLayout(new BorderLayout());
        JPanel panelTop = new JPanel(); // EventQueue.invokeLater(() -> panelTop = new JPanel());
        panelTop.setLayout(new GridBagLayout());
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new GridBagLayout());

        JButton buttonAddEmployee = new JButton("Add Employee");
        buttonAddEmployee.addActionListener(this::addActionPerformed);
        JButton buttonDeleteEmployee = new JButton("Delete Employee");
        buttonDeleteEmployee.addActionListener(this::delteActionPerformed);
        JButton btn=new JButton("Get Selected");
        btn.addActionListener(this::selectActionPerformed);

        table = new JTable();

        JScrollPane scrollTable=new JScrollPane(table);
        scrollTable.setBounds(70,80,600,200);
        scrollTable.setViewportView(table);


        panelTop.add(scrollTable);
        panelBottom.add(btn);
        panelBottom.add(buttonAddEmployee);
        panelBottom.add(buttonDeleteEmployee);
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
        model.addColumn("Name");
        model.addColumn("Login");
        model.addColumn("Password");

        //THE ROW
        for(int i=0;i<=30;i++)
        {
            model.addRow(new Object[0]);
            model.setValueAt(false,i,0);
            model.setValueAt("Name"+(i+1), i, 1);
            model.setValueAt("Login", i, 2);
            model.setValueAt("Password", i, 3);
        }

        //ADD BUTTON TO FORM
        btn.setBounds(20,30,130,30);
        add(panelTop, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }

    private void selectActionPerformed(ActionEvent e){
        // TODO Auto-generated method stub

        //GET SELECTED ROW
        for(int i=0;i<table.getRowCount();i++)
        {
            Boolean checked=Boolean.valueOf(table.getValueAt(i, 0).toString());
            String col=table.getValueAt(i, 1).toString();

            //DISPLAY
            if(checked)
            {
                JOptionPane.showMessageDialog(null, col);
            }
        }
    }

    private void addActionPerformed(ActionEvent e){
    }

    private void delteActionPerformed(ActionEvent e){
    }

}