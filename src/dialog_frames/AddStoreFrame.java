package dialog_frames;

import database.ChainDbTable;
import database.StoreDbTable;
import main.Main;
import gui.MainFrame;
import gui_panels.StorePanel;
import gui_tables.StoreTablePanel;

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
public class AddStoreFrame extends JFrame{
    private JComboBox<String> chainList;
    private JTextField storeTextField;
    private JLabel chainLabel;
    private JLabel storeLabel;
    private StoreTablePanel storeTablePanel;
    private ChainDbTable chainDbTable;
    private StoreDbTable storeDbTable;

    public AddStoreFrame(StoreTablePanel storeTablePanel){
        this.storeTablePanel = storeTablePanel;
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new GridBagLayout());

        //Labels
        chainLabel = new JLabel();
        chainLabel.setText("Chain Store:");
        storeLabel = new JLabel();
        storeLabel.setText("Store:");

        //ComboBox
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
        String[] chainTiteles = queryList.toArray(new String[0]);
        chainList = new JComboBox<>(chainTiteles);
        chainList.setPreferredSize( new Dimension( 200, 20) );

        //Text fields
        storeTextField = new JTextField();

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
        add(storeLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));

        add(chainList, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(storeTextField, new GridBagConstraints(1, 1, 1, 1, 1, 1,
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
        if (storeTextField.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Some fields are empty!!!");
        }else {
            storeDbTable = new StoreDbTable();
            storeDbTable.createTable();
            String chainNameSelected = (String) chainList.getSelectedItem();
            storeDbTable.insert(chainNameSelected, storeTextField.getText());
            MainFrame mainFrame = Main.getMainFrame();
            StorePanel storePanel = mainFrame.getStorePanel();
            storePanel.remove(storeTablePanel);
            storePanel.revalidate();
            storePanel.repaint();
            storePanel.add(new StoreTablePanel());
            this.dispose();
        }
    }

    private void cancelActionPerformed(ActionEvent e){
        this.dispose();
    }
}
