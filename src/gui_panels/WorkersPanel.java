package gui_panels;

import javax.swing.JPanel;
import javax.swing.JLabel;

/**
 * Created by Andrii Savchuk on 21.04.2018.
 * All rights are reserved.
 * If you will have any cuastion, please
 * contact via email (savchukndr@gmail.com)
 */
public class WorkersPanel extends JPanel{

    private static final long serialVersionUID = 4L;

    /**
     * Create the panel.
     */
    public WorkersPanel() {
        setLayout(null);

        JLabel lblHelloFromAdmin = new JLabel("Hello from workers panel");
        lblHelloFromAdmin.setBounds(50, 100, 150, 20);
        add(lblHelloFromAdmin);

    }
}