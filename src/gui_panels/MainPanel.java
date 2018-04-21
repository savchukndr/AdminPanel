package gui_panels;

import javax.swing.*;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.ActionEvent;

/**
 * Created by Andrii Savchuk on 21.04.2018.
 * All rights are reserved.
 * If you will have any questions, please
 * contact via email (savchukndr@gmail.com)
 */
public class MainPanel extends JPanel{

    private static final long serialVersionUID = 4L;

    /**
     * Create the panel.
     */
    public MainPanel() {
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
        JButton buttonStartServer = new JButton("Start Server");
        buttonStartServer.addActionListener(this::startActionPerformed);
        JButton buttonStopServer = new JButton("Stop Server");
        buttonStopServer.addActionListener(this::stopActionPerformed);

        // Text
        JTextArea textArea = new JTextArea(20, 40);
        textArea.setEditable(false);

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
//        if (testField.getText().equals("")){
//            JOptionPane.showMessageDialog(null, "The field is empty!!!\n Do not forget to input amount of raws!");
//        }else {
//            if (!testField.getText().matches("^\\d+$")){
//                JOptionPane.showMessageDialog(null, "Wrong symbols! (Not Integer)");
//            }else {
//                amountOfRaws = Integer.parseInt(testField.getText());
//                (t = new aTask()).execute();
//                buttonCount.setEnabled(false);
//                buttonCencel.setEnabled(true);
//                k = 1;
//                if (resPGexist) {
//                    resPGFrame.dispose();
//                    resPGexist = false;
//                }
//                if (resRDExist) {
//                    resRDFrame.dispose();
//                    resPGexist = false;
//                }
//                if (resRDPGexist) {
//                    resRDPGFrame.dispose();
//                    resRDPGexist = false;
//                }
//            }
//        }
    }

    private void stopActionPerformed(ActionEvent e){
//        buttonCount.setEnabled(true);
//        buttonCencel.setEnabled(false);
//        t.cancel(true);
//        labelDownload.setText("Downloading is interrupted!");
    }
}
