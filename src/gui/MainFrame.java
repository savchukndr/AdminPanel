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
public class MainFrame {

    private JFrame frame;
    private JPanel panel;
    private ActionListener actionListener;
    private EmployeePanel employeePanel;
    private StorePanel storePanel;
    private ChainPanel chainPanel;
    private AgreementPanel agreementPanel;
    private ReportPanel reportPanel;
    private MainPanel mainPanel;

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

    public MainFrame(){
        actionListener = e -> {
            String command = e.getActionCommand();
            switch (command){
                case "Main":
                    setCurrentPanel(mainPanel);
                    break;
                case "Employees":
                    boolean redisIsStarted = MainPanel.isRedisIsStarted();
                    if (redisIsStarted){
                    employeePanel = new EmployeePanel();
                    setCurrentPanel(employeePanel);
                    }else{
                        JOptionPane.showMessageDialog(null, "Start Server before check Employee Panel.");
                    }
                    break;
                case "Chains":
//                    boolean redisIsStarted = MainPanel.isRedisIsStarted();
//                    if (redisIsStarted){
                    chainPanel = new ChainPanel();
                    setCurrentPanel(chainPanel);
//                    }else{
//                        JOptionPane.showMessageDialog(null, "Start Server before check Employee Panel.");
//                    }
                    break;
                case "Stores":
//                    boolean redisIsStarted = MainPanel.isRedisIsStarted();
//                    if (redisIsStarted){
                        storePanel = new StorePanel();
                        setCurrentPanel(storePanel);
//                    }else{
//                        JOptionPane.showMessageDialog(null, "Start Server before check Employee Panel.");
//                    }
                    break;
                case "Reports":
//                    boolean redisIsStarted = MainPanel.isRedisIsStarted();
//                    if (redisIsStarted){
                    reportPanel = new ReportPanel();
                    setCurrentPanel(reportPanel);
//                    }else{
//                        JOptionPane.showMessageDialog(null, "Start Server before check Employee Panel.");
//                    }
                    break;
                case "Agreements":
//                    boolean redisIsStarted = MainPanel.isRedisIsStarted();
//                    if (redisIsStarted){
                    agreementPanel = new AgreementPanel();
                    setCurrentPanel(agreementPanel);
//                    }else{
//                        JOptionPane.showMessageDialog(null, "Start Server before check Employee Panel.");
//                    }
                    break;
                case "Log out":
                    Main.logout();
                    break;
                default:
                    System.out.println("Unknown action.");
                    break;
            }
        };
        initialize();
    }

    /**
     * Initialize the frame contents.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 700, 560);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton buttonMain = new JButton("Main");
//        buttonMain.setBackground(SystemColor.menu);
        buttonMain.setBounds(10, 10, 100, 20);
        buttonMain.addActionListener(actionListener);
//        buttonMain.setEnabled(false);
        frame.getContentPane().add(buttonMain);

        JButton buttonEmployees = new JButton("Employees");
//        buttonMain.setBackground(SystemColor.menu);
        buttonEmployees.setBounds(105, 10, 100, 20);
        buttonEmployees.addActionListener(actionListener);
        frame.getContentPane().add(buttonEmployees);

        JButton buttonAgreements = new JButton("Agreements");
//        buttonMain.setBackground(SystemColor.menu);
        buttonAgreements.setBounds(200, 10, 110, 20);
        buttonAgreements.addActionListener(actionListener);
        frame.getContentPane().add(buttonAgreements);

        JButton buttonReports = new JButton("Reports");
//        buttonMain.setBackground(SystemColor.menu);
        buttonReports.setBounds(295, 10, 100, 20);
        buttonReports.addActionListener(actionListener);
        frame.getContentPane().add(buttonReports);

        JButton buttonChains = new JButton("Chains");
//        buttonMain.setBackground(SystemColor.menu);
        buttonChains.setBounds(295, 10, 100, 20);
        buttonChains.addActionListener(actionListener);
//        frame.getContentPane().add(buttonChains);

        JButton buttonStores = new JButton("Stores");
//        buttonMain.setBackground(SystemColor.menu);
        buttonStores.setBounds(390, 10, 100, 20);
        buttonStores.addActionListener(actionListener);
//        frame.getContentPane().add(buttonStores);

        panel = new JPanel();
        panel.setBounds(0, 72, 784, 489);
        panel.setLayout(null);
        try {
            mainPanel = new MainPanel();
            setCurrentPanel(mainPanel);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JButton logoutBtn = new JButton("Log out");
        logoutBtn.setBounds(580, 9, 100, 20);
        logoutBtn.addActionListener(actionListener);
        frame.getContentPane().add(logoutBtn);
    }

    private <T extends JPanel> void setCurrentPanel(T t){
        frame.getContentPane().remove(panel);
        panel = t;
        panel.setBounds(0, 45, 684, 489);
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
