package gui_tables;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import dialog_frames.*;
import gui.MainFrame;
import gui_panels.EmployeePanel;
import main.Main;
import redis.clients.jedis.Jedis;
import utils.RedisUtils;

public class EmployeeTablePanel extends JPanel {

    private JTable table;
    private Jedis jedis;

    //CONSTRUCTOR
    public EmployeeTablePanel() {
        setLayout(new BorderLayout());
        JPanel panelTop = new JPanel(); // EventQueue.invokeLater(() -> panelTop = new JPanel());
        panelTop.setLayout(new GridBagLayout());
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new GridBagLayout());

        JButton buttonAddEmployee = new JButton("Add Employee");
        buttonAddEmployee.addActionListener(this::addActionPerformed);
        JButton buttonDeleteEmployee = new JButton("Delete Employee");
        buttonDeleteEmployee.addActionListener(this::deleteActionPerformed);

        table = new JTable();

        JScrollPane scrollTable = new JScrollPane(table);
        scrollTable.setViewportView(table);

        panelTop.add(scrollTable);
        panelBottom.add(buttonDeleteEmployee);
        panelBottom.add(buttonAddEmployee);
//        panelBottom.add(buttonDeleteEmployee);

        jedis = new Jedis("localhost");
        RedisUtils mapRedisObject = new RedisUtils();
        List<String> keyList = mapRedisObject.getKeyList();


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
        model.addColumn("Employee ID");
        model.addColumn("Name");
        model.addColumn("Surname");
        model.addColumn("Login");
        model.addColumn("Password");

        //THE ROW
        for (int i = 0; i <= keyList.size() - 1; i++) {
            Map<String, String> mapOfValues = mapRedisObject.getKeyMap(keyList.get(i));
            model.addRow(new Object[0]);
            model.setValueAt(false, i, 0);
            model.setValueAt(keyList.get(i), i, 1);
            model.setValueAt(mapOfValues.get("name"), i, 2);
            model.setValueAt(mapOfValues.get("surname"), i, 3);
            model.setValueAt(mapOfValues.get("login"), i, 4);
            model.setValueAt(mapOfValues.get("password"), i, 5);
        }

        //ADD BUTTON TO FORM
        buttonDeleteEmployee.setBounds(20, 30, 130, 30);
        add(panelTop, BorderLayout.CENTER);
        add(panelBottom, BorderLayout.SOUTH);
    }

    private void deleteActionPerformed(ActionEvent e) {
        for (int i = 0; i < table.getRowCount(); i++) {
            Boolean checked = Boolean.valueOf(table.getValueAt(i, 0).toString());
            String employeeId = table.getValueAt(i, 1).toString();

            if (checked) {
                jedis.del(employeeId);
                JOptionPane.showMessageDialog(null, "Deleted: " + employeeId);
            }
        }
        MainFrame mainFrame = Main.getMainFrame();
        EmployeePanel employeePanel = mainFrame.getEmployeePanel();
        employeePanel.remove(this);
        employeePanel.revalidate();
        employeePanel.repaint();
        employeePanel.add(new EmployeeTablePanel());
    }

    private void addActionPerformed(ActionEvent e) {
        AddEmployeeFrame dialogFrame = new AddEmployeeFrame(this);
        dialogFrame.show();
    }
}