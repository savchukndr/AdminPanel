package dialog_frames;

import main.Main;
import gui.MainFrame;
import gui_panels.EmployeePanel;
import gui_tables.EmployeeTablePanel;

import org.mindrot.jbcrypt.BCrypt;
import redis.clients.jedis.Jedis;
import utils.AESCrypt;

import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.util.*;
import java.util.List;


/**
 * Created by Andrii Savchuk on 26.04.2018.
 * All rights are reserved.
 * If you will have any questions, please
 * contact via email (savchukndr@gmail.com)
 */
public class AddEmployeeFrame extends JFrame{
    private JTextField loginTextField;
    private JTextField nameTextField;
    private JTextField surnameTextField;
    private JTextField passwordTextField;
    private JLabel loginLabel;
    private JLabel nameLabel;
    private JLabel surnameLabel;
    private JLabel passwordLabel;
    private Jedis jedis;
    private HashMap<String, String> employeeMap;
    private long emplyeeDbSize;
    private EmployeeTablePanel employeeTablePanel;

    public AddEmployeeFrame(EmployeeTablePanel employeeTablePanel){
        this.employeeTablePanel = employeeTablePanel;
        jedis = new Jedis("localhost");

        //Set AddEmployeeFrame layout
//        setSize(400,300);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());
        JPanel panelLeft = new JPanel();
        panelLeft.setLayout(new GridBagLayout());

        //Labels
        loginLabel = new JLabel();
        loginLabel.setText("Login:");
        nameLabel = new JLabel();
        nameLabel.setText("Name:");
        surnameLabel = new JLabel();
        surnameLabel.setText("Surname:");
        passwordLabel = new JLabel();
        passwordLabel.setText("Password:");

        //Text fields
        loginTextField = new JTextField();
        loginTextField.setPreferredSize( new Dimension( 200, 20) );
        nameTextField = new JTextField();
        surnameTextField = new JTextField();
        passwordTextField = new JTextField();

        //Buttons
        JButton buttonAdd = new JButton("Add");
        buttonAdd.addActionListener(this::addActionPerformed);
        buttonAdd.setPreferredSize( new Dimension( 30, 20 ) );
        JButton buttonCancel = new JButton("Cencel");
        buttonCancel.addActionListener(this::cancelActionPerformed);
        buttonCancel.setPreferredSize( new Dimension( 30, 20 ) );

        add(loginLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(nameLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(surnameLabel, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(passwordLabel, new GridBagConstraints(0, 3, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));

        add(loginTextField, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(nameTextField, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(surnameTextField, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        add(passwordTextField, new GridBagConstraints(1, 3, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));

        panelLeft.add(buttonAdd, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));
        panelLeft.add(buttonCancel, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));

        add(panelLeft, new GridBagConstraints(1, 4, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(2,2,2,2), 2, 2));

        setVisible(true);
        pack();
    }

//    private String hashPassword(String plainTextPassword){
//        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
//    }
//
//    private String checkPass(String plainPassword, String hashedPassword) {
//        if (BCrypt.checkpw(plainPassword, hashedPassword))
//            return "The password matches.";
//        else
//            return "The password does not match.";
//    }

    private void addActionPerformed(ActionEvent e){
        if (nameTextField.getText().equals("")
                || loginTextField.getText().equals("")
                || passwordTextField.getText().equals("")){
            JOptionPane.showMessageDialog(null, "Some fields are empty!!!");
        }else {
            employeeMap = new HashMap<>();
            emplyeeDbSize = jedis.dbSize();
            employeeMap.put("name", nameTextField.getText());
            employeeMap.put("surname", surnameTextField.getText());
            employeeMap.put("login", loginTextField.getText());
            try {
                employeeMap.put("password", AESCrypt.encrypt(passwordTextField.getText()));
            } catch (Exception e1) {
                e1.printStackTrace();
            }
//            employeeMap.put("password", passwordTextField.getText());
            addKeyToEmployeeMap();
            MainFrame mainFrame = Main.getMainFrame();
            EmployeePanel employeePanel = mainFrame.getEmployeePanel();
            employeePanel.remove(employeeTablePanel);
            employeePanel.revalidate();
            employeePanel.repaint();
            employeePanel.add(new EmployeeTablePanel());
            this.dispose();
        }
    }

    private void addKeyToEmployeeMap(){
        List<Integer> temp = new ArrayList<>();
        jedis = new Jedis("localhost");
        Set<String> keys = jedis.keys("employee:*");
        List<String> list = new ArrayList<>(keys);
        Collections.sort(list);
        if(list.isEmpty()){
            jedis.hmset("employee:" + String.valueOf(0), employeeMap);
        } else {
            for (String key : list) {
                temp.add(Character.getNumericValue(key.charAt(key.length() - 1)));
            }
            int i = 0;
            while (true) {
                if (!temp.contains(i)) {
                    jedis.hmset("employee:" + String.valueOf(i), employeeMap);
                    break;
                } else {
                    i++;
                }
            }
        }
    }

    private void cancelActionPerformed(ActionEvent e){
        this.dispose();
    }
}
