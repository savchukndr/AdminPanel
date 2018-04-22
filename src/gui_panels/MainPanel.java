package gui_panels;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.net.ServerSocket;

import utils.*;

/**
 * Created by Andrii Savchuk on 21.04.2018.
 * All rights are reserved.
 * If you will have any questions, please
 * contact via email (savchukndr@gmail.com)
 */
public class MainPanel extends JPanel{

    private JTextArea textArea;
    private JButton buttonStartServer;
    private JButton buttonStopServer;
    private aTask task;
    private Server server;

    private static final long serialVersionUID = 4L;

    /**
     * Create the panel.
     */
    public MainPanel() throws IOException {
        // Set MainPanel Layout
        setLayout(new BorderLayout());

        // Panels
        JPanel panelTop = new JPanel();
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new GridBagLayout());
        JPanel panelRight = new JPanel();
        panelRight.setLayout(new GridBagLayout());

        // Lables
        JLabel labelTabName = new JLabel("Main Page");
        panelTop.add(labelTabName);
        JLabel labelListen = new JLabel();
        labelListen.setText("Press \"Start Server\" to start Listening.");

        // Buttons
        buttonStartServer = new JButton("Start Server");
        buttonStartServer.addActionListener(this::startActionPerformed);
        buttonStartServer.setEnabled(true);
        buttonStopServer = new JButton("Stop Server");
        buttonStopServer.addActionListener(this::stopActionPerformed);
        buttonStopServer.setEnabled(false);

        // Text
        textArea = new JTextArea();
        textArea.setEditable(false);
        textArea.setRows(20);

        // Adding scroll to main Text Area
        JScrollPane scrollMainPanel = new JScrollPane (textArea,
                JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        DefaultCaret caret = (DefaultCaret) textArea.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);

        panelLeft.add(buttonStartServer, new GridBagConstraints(0, 5, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        panelLeft.add(buttonStopServer, new GridBagConstraints(0, 6, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0,2,350,2), 2, 2));


        // Adding scroll with TextArea to panelRight
        panelRight.add(scrollMainPanel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,0,2), 2, 2));
        panelRight.add(labelListen, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(0,2,100,2), 2, 2));

        // Add to MainPanel layaout
        add(panelLeft, BorderLayout.WEST);
        add(panelRight, BorderLayout.CENTER);
        add(panelTop, BorderLayout.NORTH);
    }

    private void startActionPerformed(ActionEvent e){
        buttonStartServer.setEnabled(false);
        buttonStopServer.setEnabled(true);
        (task = new aTask()).execute();
    }

    private void stopActionPerformed(ActionEvent e){
        buttonStartServer.setEnabled(true);
        buttonStopServer.setEnabled(false);
        task.done();
    }

    private class aTask extends SwingWorker<Void, String>{

        @Override
        protected Void doInBackground() throws Exception {
            textArea.setText("");
            textArea.setText("Server started.\n");
            server = new Server(textArea);
            try {
                server.start();
            }catch (IOException e){
                System.out.println(e.toString());
            }
            return null;
        }

        @Override
        protected void done(){
            server.stop();
        }
    }
}
