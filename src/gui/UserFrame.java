package gui;

import javax.swing.*;

import gui_panels.*;
import gui_panels.MainPanel;
import main.Main;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by Andrii Savchuk on 21.04.2018.
 * All rights are reserved.
 * If you will have any cuastion, please
 * contact via email (savchukndr@gmail.com)
 */
public class UserFrame {

    private JFrame frame;
    private JPanel panel;
    private ActionListener actionListener;
    private EmployeePanel employeePanel;
    private StorePanel storePanel;
    private ChainPanel chainPanel;
    private AgreementPanel agreementPanel;
    private ReportPanel reportPanel;
    private MainPanel mainPanel;
    private SearchPanel searchPanel;

    public EmployeePanel getEmployeePanel() {
        return employeePanel;
    }

    public StorePanel getStorePanel() {
        return storePanel;
    }

    public ChainPanel getChainPanel() {
        return chainPanel;
    }

    public AgreementPanel getAgrementPanel() {
        return agreementPanel;
    }

    public ReportPanel getReportPanel() {
        return reportPanel;
    }

    public SearchPanel getSearchPanel() {
        return searchPanel;
    }

    public UserFrame(){

        initialize();
    }

    /**
     * Initialize the frame contents.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(200, 200, 700, 560);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);
        frame.setTitle("User Application");

        panel = new JPanel();
        panel.setBounds(0, 10, 784, 489);
        panel.setLayout(null);
        searchPanel = new SearchPanel();
        setCurrentPanel(searchPanel);
    }

    private <T extends JPanel> void setCurrentPanel(T t){
        panel = t;
        panel.setBounds(0, 10, 684, 489);
//		panel.setLayout(null);
        frame.getContentPane().add(panel);
        //panel.setLayout(null);
        panel.setVisible(true);
        frame.repaint();
        frame.getContentPane().add(panel);
        frame.revalidate();
    }

    /**
     * Show MainFrame
     */
    public void show() {
        frame.setVisible(true);
    }

    /**
     * Dispose MainFrame
     */
    public void dispose() {
        frame.dispose();
    }
}
