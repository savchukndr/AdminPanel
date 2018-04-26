package utils;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by Andrii Savchuk on 26.04.2018.
 * All rights are reserved.
 * If you will have any cuastion, please
 * contact via email (savchukndr@gmail.com)
 */
public class RedisServer{

    private JTextArea outputDestination = null;

    public RedisServer(JTextArea outputDestination){
        this.outputDestination = outputDestination;
    }

    public void start() throws IOException, InterruptedException {
        RedisServerThread redisServer = new RedisServerThread();
        Thread thread = new Thread(redisServer);
        thread.start();
//        thread.join();
    }

//    public synchronized void stop(){
//        if (thread == null) return;
//        thread = null;
//    }

    private synchronized void UpdateServerStatusWindow(String message, JTextArea destination){
        try
        {
            destination.append(message + "\n");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "There was an error updating the server     message output window in the TCP Listner!");
            JOptionPane.showMessageDialog(null, e);

        }

    }

    class RedisServerThread implements Runnable{
        @Override
        public void run(){
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
//            Process p = builder.start();
//            BufferedReader r = new BufferedReader(new InputStreamReader(p.getInputStream()));
//            String line;
//            while (true) {
//                line = r.readLine();
//                if (line == null) { break; }
//                System.out.println(line);
//            }
        }
    }
}