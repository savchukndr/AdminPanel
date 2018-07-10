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
public class ShowAgreementsFrame extends JFrame{
    private JLabel agreementTitleLabel,
            chainStoreLabel,
            storeLabel,
            productTypeLabel,
            productLabel,
            productCountLabel,
            shelfPositionLabel,
            agreementTitleTextLabel,
            chainStoreTextLabel,
            storeTextLabel,
            productTypeTextLabel,
            productTextLabel,
            productCountTextLabel,
            shelfPositionTextLabel;
    private AgreementTablePanel agreementTablePanel;
//    private AgreementDbTable agreementDbTable;

    public ShowAgreementsFrame(AgreementTablePanel agreementTablePanel){
        this.agreementTablePanel = agreementTablePanel;

        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
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


        //Text Labels
        //TODO: Read from data base
        agreementTitleTextLabel = new JLabel();
        agreementTitleTextLabel.setText("Agreement 1");
        chainStoreTextLabel = new JLabel();
        chainStoreTextLabel.setText("Biedronka");
        storeTextLabel = new JLabel();
        storeTextLabel.setText("Biedronka 1");
        productTypeTextLabel = new JLabel();
        productTypeTextLabel.setText("Book");
        productTextLabel = new JLabel();
        productTextLabel.setText("Java");
        productCountTextLabel = new JLabel();
        productCountTextLabel.setText("2");
        shelfPositionTextLabel = new JLabel();
        shelfPositionTextLabel.setText("3");

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
