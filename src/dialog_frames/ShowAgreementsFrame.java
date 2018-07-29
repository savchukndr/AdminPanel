package dialog_frames;

import database.AgreementDbTable;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Andrii Savchuk on 26.04.2018.
 * All rights are reserved.
 * If you will have any questions, please
 * contact via email (savchukndr@gmail.com)
 */
public class ShowAgreementsFrame extends JFrame{

    public ShowAgreementsFrame(String agreementId){

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        setTitle("Agreements");
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



        //Text Labels
        JLabel agreementTitleTextLabel = new JLabel();
        JLabel chainStoreTextLabel = new JLabel();
        JLabel storeTextLabel = new JLabel();
        JLabel productTypeTextLabel = new JLabel();
        JLabel productTextLabel = new JLabel();
        JLabel productCountTextLabel = new JLabel();
        JLabel shelfPositionTextLabel = new JLabel();

        // Read from data base
        AgreementDbTable agreementDbTable = new AgreementDbTable();
        try {
            ResultSet resultSet = agreementDbTable.selectAgreementDataByID(agreementId);
            while(resultSet.next()){
                agreementTitleTextLabel.setText(resultSet.getString("agreement_title"));
                chainStoreTextLabel.setText(resultSet.getString("chain_title"));
                storeTextLabel.setText(resultSet.getString("store_title"));
                productTypeTextLabel.setText(resultSet.getString("product_type_title"));
                productTextLabel.setText(resultSet.getString("product_title"));
                productCountTextLabel.setText(resultSet.getString("product_count"));
                shelfPositionTextLabel.setText(resultSet.getString("shelf_position"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        //Buttons
        JButton buttonClose = new JButton("Close");
        buttonClose.addActionListener(this::closeActionPerformed);
        buttonClose.setPreferredSize( new Dimension( 30, 20 ) );

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

        add(agreementTitleTextLabel, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(chainStoreTextLabel, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(storeTextLabel, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(productTypeTextLabel, new GridBagConstraints(1, 3, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(productTextLabel, new GridBagConstraints(1, 4, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(productCountTextLabel, new GridBagConstraints(1, 5, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(shelfPositionTextLabel, new GridBagConstraints(1, 6, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));

        panelLeft.add(buttonClose, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));

        add(panelLeft, new GridBagConstraints(1, 7, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));

        setVisible(true);
        pack();
    }

    private void closeActionPerformed(ActionEvent e){
        this.dispose();
    }
}
