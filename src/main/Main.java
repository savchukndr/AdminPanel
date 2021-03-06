package main;

import gui.MainFrame;
import gui.UserFrame;

/**
 * Created by Andrii Savchuk on 21.04.2018.
 * All rights are reserved.
 * If you will have any cuastion, please
 * contact via email (savchukndr@gmail.com)
 */
public class Main {

    private static MainFrame mainFrame;


    public static MainFrame getMainFrame() {
        return mainFrame;
    }

    public static void main(String args[]) {
        Main.goToMainFrame();
        Main.goToUserFrame();
    }

    private static void goToMainFrame() {
        mainFrame = new MainFrame();
        mainFrame.show();
    }

    private static void goToUserFrame() {
        UserFrame userFrame = new UserFrame();
        userFrame.show();
    }

    public static void logout() {
        mainFrame.dispose();
        mainFrame = null;
    }
}
