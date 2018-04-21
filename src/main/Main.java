package main;

import gui.MainFrame;

/**
 * Created by Andrii Savchuk on 21.04.2018.
 * All rights are reserved.
 * If you will have any cuastion, please
 * contact via email (savchukndr@gmail.com)
 */
public class Main {

    private static MainFrame mainFrame;


    public static void main(String args[]){
        Main.goToMainFrame();
    }

    public static void goToMainFrame(){
        mainFrame = new MainFrame();
        mainFrame.show();
    }

    public static void logout() {
        mainFrame.dispose();
        mainFrame = null;
    }
}
