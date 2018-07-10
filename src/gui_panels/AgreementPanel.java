package gui_panels;

import javax.swing.*;

import gui_tables.*;

/**
 * Created by Andrii Savchuk on 21.04.2018.
 * All rights are reserved.
 * If you will have any questions, please
 * contact via email (savchukndr@gmail.com)
 */
public class AgreementPanel extends JPanel{

    private static final long serialVersionUID = 4L;
    private AgreementTablePanel agreementTablePanel;

    /**
     * Create the panel.

     */
    public AgreementPanel() {
        agreementTablePanel = new AgreementTablePanel();
        add(agreementTablePanel);
    }
}