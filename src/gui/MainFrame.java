package gui;

import javax.swing.*;

import gui_panels.*;
import main.Main;

import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

    public MainFrame(){
        actionListener = e -> {
            String command = e.getActionCommand();
            switch (command){
                case "Main":
                    setCurrentPanel(new MainPanel());
                    break;
                case "Employees":
                    setCurrentPanel(new EmployeePanel());
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
        frame.getContentPane().add(buttonMain);

        JButton buttonEmployees = new JButton("Employees");
//        buttonMain.setBackground(SystemColor.menu);
        buttonEmployees.setBounds(105, 10, 100, 20);
        buttonEmployees.addActionListener(actionListener);
        frame.getContentPane().add(buttonEmployees);
        panel = new JPanel();
        panel.setBounds(0, 72, 784, 489);
        panel.setLayout(null);
        setCurrentPanel(new MainPanel());

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
