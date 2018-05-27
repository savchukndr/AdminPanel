package dialog_frames;

import database.ChainDbTable;
import main.Main;
import gui.MainFrame;
import gui_panels.ChainPanel;
import gui_tables.ChainTablePanel;

import org.mindrot.jbcrypt.BCrypt;
import redis.clients.jedis.Jedis;
import utils.AESCrypt;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.List;


/**
 * Created by Andrii Savchuk on 26.04.2018.
 * All rights are reserved.
 * If you will have any questions, please
 * contact via email (savchukndr@gmail.com)
 */
public class AddChainFrame extends JFrame{
    private JTextField chainTextField;
    private JLabel chainLabel;
    private ChainTablePanel chainTablePanel;
    private ChainDbTable chainDbTable;

    public AddChainFrame(ChainTablePanel chainTablePanel){
        this.chainTablePanel = chainTablePanel;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new GridBagLayout());

        //Labels
        chainLabel = new JLabel();
        chainLabel.setText("Chain:");

        //Text fields
        chainTextField = new JTextField();
        chainTextField.setPreferredSize( new Dimension( 200, 20) );

        //Buttons
        JButton buttonAdd = new JButton("Add");
        buttonAdd.addActionListener(this::addActionPerformed);
        buttonAdd.setPreferredSize( new Dimension( 30, 20 ) );
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(this::cancelActionPerformed);
        buttonCancel.setPreferredSize( new Dimension( 30, 20 ) );

        add(chainLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));

        add(chainTextField, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));

        panelLeft.add(buttonAdd, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        panelLeft.add(buttonCancel, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));

        add(panelLeft, new GridBagConstraints(1, 4, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));

        setVisible(true);
        pack();
    }

    private void addActionPerformed(ActionEvent e){
        if (chainTextField.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Some fields are empty!!!");
        }else {
            //Inserting value into chain table
            chainDbTable = new ChainDbTable();
            chainDbTable.createTable();
            ResultSet resultSet = chainDbTable.selectAll();
            ArrayList<String> queryList = new ArrayList<>();
            try {
                while(resultSet.next()){
                    queryList.add(resultSet.getString("name").trim().toLowerCase());
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            if(!queryList.contains(chainTextField.getText().toLowerCase())) {
                chainDbTable.insert(chainTextField.getText().toLowerCase());
                MainFrame mainFrame = Main.getMainFrame();
                ChainPanel chainPanel = mainFrame.getChainPanel();
                chainPanel.remove(chainTablePanel);
                chainPanel.revalidate();
                chainPanel.repaint();
                chainPanel.add(new ChainTablePanel());
                this.dispose();
            }else {
                JOptionPane.showMessageDialog(null, "Chain store already exist!");
                chainTextField.setText("");
            }
        }
    }

    private void cancelActionPerformed(ActionEvent e){
        this.dispose();
    }
}
