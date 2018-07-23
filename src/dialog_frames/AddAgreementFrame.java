package dialog_frames;

//import database.AgreementDbTable;
import database.AgreementDbTable;
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
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
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
    private AgreementDbTable agreementDbTable;
    private JComboBox chainList, storeList, productTypeList;
    private DefaultComboBoxModel modelStore, modelProduct;

    private String[] generateList (ResultSet selectMethod, String title){
        ResultSet resultSet = selectMethod;
        ArrayList<String> queryList = new ArrayList<>();
        try {
            while(resultSet.next()){
                queryList.add(resultSet.getString(title).trim().toLowerCase());
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return queryList.toArray(new String[0]);
    }

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

        //ComboBox
        agreementDbTable = new AgreementDbTable();
        chainList = new JComboBox<>(generateList(agreementDbTable.selectChain(), "name"));
//        chainList.setSelectedIndex(0);
        String selectedChain = (String) chainList.getSelectedItem();
        modelStore = new DefaultComboBoxModel(generateList(agreementDbTable.selectStore(selectedChain), "store"));
        storeList = new JComboBox<>(modelStore);
        chainList.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                storeList.removeAllItems();
                modelStore.removeAllElements();
                String a = chainList.getSelectedItem().toString();
                String[] s = generateList(agreementDbTable.selectStore(a), "store");
                storeList = new JComboBox<>(generateList(agreementDbTable.selectStore(a), "store"));
                for (String x: s) {
                    modelStore.addElement(x);
                }
            }
        });

        //////////
        ResultSet resultSet = agreementDbTable.selectProductType();
        HashMap<String, String> productMap = new HashMap<>();
        try {
            while(resultSet.next()){
                productMap.put(resultSet.getString("id_product_type"), resultSet.getString("title").trim());
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        String[] simpleArray = productMap.values().toArray(new String[0]);
        productTypeList = new JComboBox<>(simpleArray);
        ///////////

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
        add(chainList, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(storeList, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(productTypeList, new GridBagConstraints(1, 3, 1, 1, 1, 1,
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
        System.out.println((String) chainList.getSelectedItem()); //Chain into data base
        System.out.println((String) modelStore.getSelectedItem()); //Store into data base
    }

    private void cancelActionPerformed(ActionEvent e){
        this.dispose();
    }
}
