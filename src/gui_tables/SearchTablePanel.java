package gui_tables;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import dialog_frames.ShowImageFrame;
import redis.clients.jedis.Jedis;
import utils.*;

/**
 * Created by Andrii Savchuk on 21.04.2018.
 * All rights are reserved.
 * If you will have any questions, please
 * contact via email (savchukndr@gmail.com)
 */
public class SearchTablePanel extends JPanel {

    private JButton buttonSearch;
    private JTextField searchTextField;
    private JTable table;
    private JFrame showFrame;

    private static final long serialVersionUID = 4L;

    /**
     * Create the panel.
     */
    public SearchTablePanel(){
        // Set MainPanel Layout
        setLayout(new BorderLayout());

        // Panels
        JPanel panelTop = new JPanel();
        JPanel panelRight = new JPanel();
        panelRight.setLayout(new GridBagLayout());
        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new GridBagLayout());

        // Labels
//        JLabel labelTabName = new JLabel("Main Page");
//        panelTop.add(labelTabName);
        JLabel labelListen = new JLabel();
        labelListen.setText("Press \"Search\" to start searching:");

        // Buttons
        buttonSearch = new JButton("Search");
        buttonSearch.addActionListener(this::searchActionPerformed);
        buttonSearch.setEnabled(true);
        JButton buttonShowImage = new JButton("Show Image");
        buttonShowImage.setBounds(20,30,130,30);
        buttonShowImage.addActionListener(this::showActionPerformed);
        JButton buttonDelete = new JButton("Delete");
//        buttonDelete.addActionListener(this::deleteActionPerformed);

        //Text fields
        searchTextField = new JTextField();
        searchTextField.setPreferredSize( new Dimension( 200, 27) );

        // Adding scroll to main Text Area

        table = new JTable();

        JScrollPane scrollTable=new JScrollPane(table);
        scrollTable.setViewportView(table);

//        panelTop.add(scrollTable);

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
                    case 6:
                        return String.class;
                    case 7:
                        return String.class;


                    default:
                        return String.class;
                }
            }
        };

        //ASSIGN THE MODEL TO TABLE
        table.setModel(model);
        model.addColumn("Select");
        model.addColumn("ID Result");
        model.addColumn("On shelf");
        model.addColumn("Localization");
        model.addColumn("Visability");
        model.addColumn("Date");
        model.addColumn("Estimation");
        model.addColumn("Agreement");

        //TODO: read from data base

        model.addRow(new Object[0]);
        model.setValueAt(false, 0,0);
        model.setValueAt(1, 0,1);
        model.setValueAt("True", 0,2);
        model.setValueAt("Biedronka 1", 0,3);
        model.setValueAt("True", 0,4);
        model.setValueAt("2018/07/12 11:57:33", 0,5);
        model.setValueAt("Good", 0,6);
        model.setValueAt("Agreement 1", 0,7);


        model.addRow(new Object[0]);
        model.setValueAt(false, 1,0);
        model.setValueAt(3, 1,1);
        model.setValueAt("False", 1,2);
        model.setValueAt("Biedronka 2", 1,3);
        model.setValueAt("False", 1,4);
        model.setValueAt("2018/07/12 13:40:07", 1,5);
        model.setValueAt("Bad", 1,6);
        model.setValueAt("Agreement 2", 1,7);

//        for(int i=0; i<3; i++){
//            model.addRow(new Object[0]);
//            model.setValueAt(false, i,0);
//            model.setValueAt(i+1, i,1);
//            model.setValueAt("Ageement" + String.valueOf(i+1), i,2);
//        }

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

    private void searchActionPerformed(ActionEvent e) {
    }

    private void showActionPerformed(ActionEvent e) {
        showFrame = new ShowImageFrame();
        showFrame.show();
    }

}
