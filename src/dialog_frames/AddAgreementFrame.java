package dialog_frames;

import database.AgreementDbTable;
import main.Main;
import gui.MainFrame;
import gui_panels.AgreementPanel;
import gui_tables.AgreementTablePanel;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.ItemEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;


/**
 * Created by Andrii Savchuk on 26.04.2018.
 * All rights are reserved.
 * If you will have any questions, please
 * contact via email (savchukndr@gmail.com)
 */
public class AddAgreementFrame extends JFrame{
    private JTextField agreementTitleTextField, productCountTextField, shelfPositionTextField;
    private AgreementTablePanel agreementTablePanel;
    private AgreementDbTable agreementDbTable;
    private JComboBox chainList, storeList, productTypeList, productList;
    private DefaultComboBoxModel modelStore, modelProduct;

    private HashMap<String, String> generateListChain (ResultSet selectMethod){
        HashMap<String, String> map = new HashMap<>();
        try {
            while(selectMethod.next()){
                map.put(selectMethod.getString("title"), selectMethod.getString("id_chain").trim());
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }
        return map;
    }

    private HashMap<String, List<String>> generateListStore (ResultSet selectMethod){
        HashMap<String, List<String>> map = new HashMap<>();
        List<String> list;
        try {
            while(selectMethod.next()){
                list = new ArrayList<>();
                list.add(selectMethod.getString("id_chain"));
                list.add(selectMethod.getString("title"));
                map.put(selectMethod.getString("id_store"), list);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return map;
    }

    private HashMap<String, String> generateListProductType (ResultSet selectMethod){
        HashMap<String, String> map = new HashMap<>();
        try {
            while (selectMethod.next()) {
                map.put(selectMethod.getString("title"), selectMethod.getString("id_product_type").trim());
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return map;
    }

    private HashMap<String, List<String>> generateListProduct (ResultSet selectMethod){
        HashMap<String, List<String>> map = new HashMap<>();
        List<String> list;
        try {
            while(selectMethod.next()){
                list = new ArrayList<>();
                list.add(selectMethod.getString("id_product_type"));
                list.add(selectMethod.getString("title"));
                map.put(selectMethod.getString("id_product"), list);
            }
        } catch (SQLException e1) {
            e1.printStackTrace();
        }

        return map;
    }

    private String[] generateArrayProduct(String selectedID){
        HashMap<String, List<String>> map = generateListProduct(agreementDbTable.selectProduct(selectedID));
        List<String> values = new ArrayList<>();
        for (List<String> x: map.values()) {
            values.add(x.get(1));
        }
        return values.toArray(new String[0]);
    }

    private String[] generateArrayStore(String selectedID){
        HashMap<String, List<String>> map = generateListStore(agreementDbTable.selectStore(selectedID));
        List<String> values = new ArrayList<>();
        for (List<String> x: map.values()) {
            values.add(x.get(1));
        }
        return values.toArray(new String[0]);
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
        JLabel agreementTitleLabel = new JLabel();
        agreementTitleLabel.setText("Title:");
        JLabel chainStoreLabel = new JLabel();
        chainStoreLabel.setText("Chain Store:");
        JLabel storeLabel = new JLabel();
        storeLabel.setText("Store:");
        JLabel productTypeLabel = new JLabel();
        productTypeLabel.setText("Product type:");
        JLabel productLabel = new JLabel();
        productLabel.setText("Product:");
        JLabel productCountLabel = new JLabel();
        productCountLabel.setText("Count:");
        JLabel shelfPositionLabel = new JLabel();
        shelfPositionLabel.setText("Shelf Position:");

        //ComboBox
        agreementDbTable = new AgreementDbTable();

        //---------------
        HashMap<String, String> chainMap = generateListChain(agreementDbTable.selectChain());
        String[] chainArray = chainMap.keySet().toArray(new String[0]);
        chainList = new JComboBox<>(chainArray);

        String selectedChain = (String) chainList.getSelectedItem();
        String[] storeArray = generateArrayStore(chainMap.get(selectedChain));

        modelStore = new DefaultComboBoxModel(storeArray);
        storeList = new JComboBox<>(modelStore);
        chainList.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                storeList.removeAllItems();
                modelStore.removeAllElements();
                String a = chainList.getSelectedItem().toString();
                String[] s = generateArrayStore(chainMap.get(a));

                storeList = new JComboBox<>(s);
                for (String x: s) {
                    modelStore.addElement(x);
                }
            }
        });
        //---------------

        //////////////////////////
        HashMap<String, String> productTypeMap = generateListProductType(agreementDbTable.selectProductType());
        String[] productTypeArray = productTypeMap.keySet().toArray(new String[0]);
        productTypeList = new JComboBox<>(productTypeArray);

        String selectedProductType = (String) productTypeList.getSelectedItem();
        String[] productArray = generateArrayProduct(productTypeMap.get(selectedProductType));

        modelProduct = new DefaultComboBoxModel(productArray);
        productList = new JComboBox<>(modelProduct);
        productTypeList.addItemListener(e -> {
            if(e.getStateChange() == ItemEvent.SELECTED) {
                productList.removeAllItems();
                modelProduct.removeAllElements();
                String a = productTypeList.getSelectedItem().toString();
                String[] s = generateArrayProduct(productTypeMap.get(a));

                productList = new JComboBox<>(s);
                for (String x: s) {
                    modelProduct.addElement(x);
                }
            }
        });
        /////////////////////////

        //Text fields
        agreementTitleTextField = new JTextField();
        agreementTitleTextField.setPreferredSize( new Dimension( 200, 20) );
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
        add(productList, new GridBagConstraints(1, 4, 1, 1, 1, 1,
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

    private void addActionPerformed(ActionEvent e) {
        if (agreementTitleTextField.getText().equals("") || productCountTextField.getText().equals("") || shelfPositionTextField.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Some fields are empty!!!");
        } else {
            System.out.println((String) chainList.getSelectedItem()); //Chain into data base
            System.out.println((String) modelStore.getSelectedItem()); //Store into data base
            System.out.println((String) productTypeList.getSelectedItem()); //Store into data base
            System.out.println((String) modelProduct.getSelectedItem()); //Store into data base

            String agreementTitle = agreementTitleTextField.getText();
            String productCount = productCountTextField.getText();
            String shelfPosition = shelfPositionTextField.getText();
            String selectedStoreTitle = (String) modelStore.getSelectedItem();
            String selectedProductTitle = (String) modelProduct.getSelectedItem();

            //-----
            ResultSet resultSet = agreementDbTable.selectAll();
            ArrayList<String> queryList = new ArrayList<>();
            try {
                while(resultSet.next()){
                    queryList.add(resultSet.getString("title").trim().toLowerCase());
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            if(!queryList.contains(agreementTitleTextField.getText().toLowerCase())) {
                int storeId = 0;
                int productId = 0;
                int agreementId = 0;
                ResultSet resSet;
                try {
                    resSet = agreementDbTable.selectStoreID(selectedStoreTitle);
                    while (resSet.next()) {
                        storeId = Integer.parseInt(resSet.getString("id_store"));
                    }

                    resSet = agreementDbTable.selectProductID(selectedProductTitle);
                    while (resSet.next()) {
                        productId = Integer.parseInt(resSet.getString("id_product"));
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                //add data into agreement
                DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
                Date date = new Date();
                String dateTime = dateFormat.format(date);
                agreementDbTable.insertAgreement(storeId);
                try {
                    resSet = agreementDbTable.selectAgreementID(storeId);
                    while (resSet.next()) {
                        agreementId = Integer.parseInt(resSet.getString("id_agreement"));
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }

                //add data into agreement_data
                agreementDbTable.insertAgreementData(agreementTitle, Integer.parseInt(productCount), Integer.parseInt(shelfPosition), productId, agreementId);
                MainFrame mainFrame = Main.getMainFrame();
                AgreementPanel agreementPanel = mainFrame.getAgrementPanel();
                agreementPanel.remove(agreementTablePanel);
                agreementPanel.revalidate();
                agreementPanel.repaint();
                agreementPanel.add(new AgreementTablePanel());
                this.dispose();
            }else {
                JOptionPane.showMessageDialog(null, "Agreement with such title already exist!");
                agreementTitleTextField.setText("");
            }
        }
    }

    private void cancelActionPerformed(ActionEvent e){
        this.dispose();
    }
}
