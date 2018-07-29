package utils;

import javax.swing.*;
import java.io.IOException;

/**
 * Created by Andrii Savchuk on 26.04.2018.
 * All rights are reserved.
 * If you will have any questions, please
 * contact via email (savchukndr@gmail.com)
 */
public class RedisServer {

    private JTextArea outputDestination = null;

    public RedisServer(JTextArea outputDestination) {
        this.outputDestination = outputDestination;
    }

    public void start() throws IOException, InterruptedException {
        RedisServerThread redisServer = new RedisServerThread();
        Thread thread = new Thread(redisServer);
        thread.start();
    }

    private synchronized void UpdateServerStatusWindow(String message, JTextArea destination) {
        try {
            destination.append(message + "\n");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was an error updating the server     message output window in the TCP Listner!");
            JOptionPane.showMessageDialog(null, e);

        }

    }

    class RedisServerThread implements Runnable {
        @Override
        public void run() {
            try {
                execCommand();
            } catch (IOException e) {
                UpdateServerStatusWindow(e.toString(), outputDestination);
            }
        }

        void execCommand() throws IOException {
            ProcessBuilder builder = new ProcessBuilder(
                    "cmd.exe", "/c", "cd \"C:\\Program Files\\Redis\" && redis-server");
            builder.redirectErrorStream(true);
            builder.start();
        }
    }
}