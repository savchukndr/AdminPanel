package dialog_frames;

//import database.AgreementDbTable;
import main.Main;
import gui.MainFrame;
import gui_panels.AgreementPanel;
import gui_tables.AgreementTablePanel;

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
public class AddAgreementFrame extends JFrame{
    private JTextField agreementTitleTextField, chainStoreTextField, storeTextField,productTypeTextField, productTextField, productCountTextField, shelfPositionTextField;
    private JLabel agreementTitleLabel, chainStoreLabel, storeLabel, productTypeLabel, productLabel, productCountLabel, shelfPositionLabel;
    private AgreementTablePanel agreementTablePanel;
//    private AgreementDbTable agreementDbTable;

    public AddAgreementFrame(AgreementTablePanel agreementTablePanel){
        this.agreementTablePanel = agreementTablePanel;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        setTitle("Add Agreement");
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new GridBagLayout());

        //Labels
        agreementTitleLabel = new JLabel();
        agreementTitleLabel.setText("Title:");
        chainStoreLabel = new JLabel();
        chainStoreLabel.setText("Chain Store:");
        storeLabel = new JLabel();
        storeLabel.setText("Store:");
        productTypeLabel = new JLabel();
        productTypeLabel.setText("Product type:");
        productLabel = new JLabel();
        productLabel.setText("Product:");
        productCountLabel = new JLabel();
        productCountLabel.setText("Count:");
        shelfPositionLabel = new JLabel();
        shelfPositionLabel.setText("Shelf Position:");


        //Text fields
        agreementTitleTextField = new JTextField();
        agreementTitleTextField.setPreferredSize( new Dimension( 200, 20) );
        chainStoreTextField = new JTextField();
        chainStoreTextField.setPreferredSize( new Dimension( 200, 20) );
        storeTextField = new JTextField();
        storeTextField.setPreferredSize( new Dimension( 200, 20) );
        productTypeTextField = new JTextField();
        productTypeTextField.setPreferredSize( new Dimension( 200, 20) );
        productTextField = new JTextField();
        productTextField.setPreferredSize( new Dimension( 200, 20) );
        productCountTextField = new JTextField();
        productCountTextField.setPreferredSize( new Dimension( 200, 20) );
        shelfPositionTextField = new JTextField();
        shelfPositionTextField.setPreferredSize( new Dimension( 200, 20) );

        //Buttons
        JButton buttonAdd = new JButton("Add");
        buttonAdd.addActionListener(this::addActionPerformed);
        buttonAdd.setPreferredSize( new Dimension( 30, 20 ) );
        JButton buttonCancel = new JButton("Cancel");
        buttonCancel.addActionListener(this::cancelActionPerformed);
        buttonCancel.setPreferredSize( new Dimension( 30, 20 ) );

        add(agreementTitleLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(chainStoreLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(storeLabel, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(productTypeLabel, new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(productLabel, new GridBagConstraints(0, 4, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(productCountLabel, new GridBagConstraints(0, 5, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(shelfPositionLabel, new GridBagConstraints(0, 6, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));

        add(agreementTitleTextField, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(chainStoreTextField, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(storeTextField, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(productTypeTextField, new GridBagConstraints(1, 3, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(productTextField, new GridBagConstraints(1, 4, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(productCountTextField, new GridBagConstraints(1, 5, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(shelfPositionTextField, new GridBagConstraints(1, 6, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));

        panelLeft.add(buttonAdd, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        panelLeft.add(buttonCancel, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));

        add(panelLeft, new GridBagConstraints(1, 7, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));

        setVisible(true);
        pack();
    }

    private void addActionPerformed(ActionEvent e){
        //TODO: insert into data base
//        if (agreementTextField.getText().equals("")){
//            JOptionPane.showMessageDialog(null, "Some fields are empty!!!");
//        }else {
//            //Inserting value into agreement table
//            agreementDbTable = new AgreementDbTable();
//            agreementDbTable.createTable();
//            ResultSet resultSet = agreementDbTable.selectAll();
//            ArrayList<String> queryList = new ArrayList<>();
//            try {
//                while(resultSet.next()){
//                    queryList.add(resultSet.getString("name").trim().toLowerCase());
//                }
//            } catch (SQLException e1) {
//                e1.printStackTrace();
//            }
//            if(!queryList.contains(agreementTextField.getText().toLowerCase())) {
//                agreementDbTable.insert(agreementTextField.getText().toLowerCase());
//                MainFrame mainFrame = Main.getMainFrame();
//                AgreementPanel agreementPanel = mainFrame.getAgreementPanel();
//                agreementPanel.remove(agreementTablePanel);
//                agreementPanel.revalidate();
//                agreementPanel.repaint();
//                agreementPanel.add(new AgreementTablePanel());
//                this.dispose();
//            }else {
//                JOptionPane.showMessageDialog(null, "Agreement already exist!");
//                agreementTextField.setText("");
//            }
//        }
    }

    private void cancelActionPerformed(ActionEvent e){
        this.dispose();
    }
}
