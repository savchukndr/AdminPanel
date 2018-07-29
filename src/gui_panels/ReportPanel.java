package gui_panels;

import javax.swing.*;

import gui_tables.*;

/**
 * Created by Andrii Savchuk on 21.04.2018.
 * All rights are reserved.
 * If you will have any questions, please
 * contact via email (savchukndr@gmail.com)
 */
public class ReportPanel extends JPanel {

    private static final long serialVersionUID = 4L;

    /**
     * Create the panel.
     */
    public ReportPanel() {
        ReportTablePanel reportTablePanel = new ReportTablePanel();
        add(reportTablePanel);
    }
}