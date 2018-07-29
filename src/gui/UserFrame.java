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

    public UserFrame() {

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
        SearchPanel searchPanel = new SearchPanel();
        setCurrentPanel(searchPanel);
    }

    private <T extends JPanel> void setCurrentPanel(T t) {
        panel = t;
        panel.setBounds(0, 10, 684, 489);
        frame.getContentPane().add(panel);
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
}
