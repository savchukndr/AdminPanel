package gui_panels;

import javax.swing.*;

import gui_tables.*;

/**
 * Created by Andrii Savchuk on 21.04.2018.
 * All rights are reserved.
 * If you will have any questions, please
 * contact via email (savchukndr@gmail.com)
 */
public class EmployeePanel extends JPanel{

    private static final long serialVersionUID = 4L;
    private JPanel panelTop;
    private EmployeeTablePanel employeeTablePanel;

    public EmployeeTablePanel getEmployeeTablePanel() {
        return employeeTablePanel;
    }

    /**
     * Create the panel.

     */
    public EmployeePanel() {
        employeeTablePanel = new EmployeeTablePanel();
        add(employeeTablePanel);
//        // Set MainPanel Layout
//        setLayout(new BorderLayout());
//
//        // Panels
//        JPanel panelTopLabel = new JPanel();
//        panelTop = new JPanel(); // EventQueue.invokeLater(() -> panelTop = new JPanel());
//        panelTop.setLayout(new GridBagLayout());
//        JPanel panelBottom = new JPanel();
//        panelBottom.setLayout(new GridBagLayout());
//
//        // Lables
//        JLabel labelTabName = new JLabel("Employee Page");
//        panelTopLabel.add(labelTabName);
////        JLabel labelListen = new JLabel();
////        labelListen.setText("Press \"Start Server\" to start Listening.");
//
//        EmployeeTablePanel table = new EmployeeTablePanel();
//
//        JScrollPane scrollTable=new JScrollPane(table);
//        scrollTable.setBounds(70,80,600,200);
//        scrollTable.setViewportView(table);
//        panelTop.add(scrollTable);
//
////        EventQueue.invokeLater(() -> {
////            //INITIALIZE JFRAME FORM
////            EmployeeTablePanel table=new EmployeeTablePanel();
////            setVisible(true);
////        });
//
////        EventQueue.invokeLater(() -> {
////                    final EmployeeTablePanel table = new EmployeeTablePanel();
////                    scroll.setViewportView(table);
////                });
//
//
//
//
//
////        // Buttons
////        JButton buttonAddEmployee = new JButton("Add Employee");
////        buttonAddEmployee.addActionListener(this::addActionPerformed);
////        JButton buttonDeleteEmployee = new JButton("Delete Employee");
////        buttonDeleteEmployee.addActionListener(this::delteActionPerformed);
//
//        // Text
////        JTextArea textArea = new JTextArea(20, 40);
////        textArea.setEditable(false);
////
////        // Adding scroll to main Text Area
////        JScrollPane scrollEmployeePanel = new JScrollPane (textArea,
////                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
////        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
////        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
//
////        panelLeft.add(buttonStartServer, new GridBagConstraints(0, 5, 1, 1, 1, 1,
////                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
////                new Insets(2,2,2,2), 2, 2));
////        panelLeft.add(buttonStopServer, new GridBagConstraints(0, 6, 1, 1, 1, 1,
////                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
////                new Insets(0,2,350,2), 2, 2));
////
////
////        // Adding scroll with TextArea to panelRight
////        panelRight.add(scroll, new GridBagConstraints(0, 0, 1, 1, 1, 1,
////                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
////                new Insets(2,2,0,2), 2, 2));
////        panelRight.add(labelListen, new GridBagConstraints(0, 2, 1, 1, 1, 1,
////                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
////                new Insets(0,2,100,2), 2, 2));
////
////        // Add to MainPanel layaout
////        add(panelLeft, BorderLayout.WEST);
////        add(panelRight, BorderLayout.CENTER);
//        add(panelTopLabel, BorderLayout.NORTH);
//        add(panelTop, BorderLayout.CENTER);
    }
}