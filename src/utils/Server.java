package utils;

import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Andrii Savchuk on 22.04.2018.
 * All rights are reserved.
 * If you will have any questions, please
 * contact via email (savchukndr@gmail.com)
 */
public class Server {
    private int port;
    private Socket clientSocket = null;
    private ServerSocket serverSocket;
    private Thread thread;
    private JTextArea outputDestination = null;
    private boolean running = true;
    private int x = 4;

    /**
     * Constructor
     * @param outputDestination - textArea object
     */
    public Server(JTextArea outputDestination) {
        this.port = 1994;
        this.outputDestination = outputDestination;
    }

    /**
     * This method starts serwer
     * @throws IOException - throws socket exceptions
     * @throws InterruptedException - throws multithreading exceptions
     */
    public void start() throws IOException, InterruptedException {
        SocketThread socket = new SocketThread();
        thread = new Thread(socket);
        thread.start();
        thread.join();
        System.out.println(x + 2);
    }

    /**
     * This method updates textArea object in MainPanel.java file
     * @param message - message that will be displayed in textArea object
     * @param destination - textArea object
     */
    private synchronized void UpdateServerStatusWindow(String message, JTextArea destination) {
        try {
            destination.append(message + "\n");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "There was an error updating the server     message output window in the TCP Listner!");
            JOptionPane.showMessageDialog(null, e);

        }

    }

    /**
     * This method stops server.
     */
    public synchronized void stop() {
        if (thread == null) return;

        running = false;
        if (serverSocket != null) {
            try {
                serverSocket.close();
            } catch (IOException e) {
                System.out.println(e.toString());
            }
        }

        thread = null;
    }

    /**
     * Create thread making server-client communications
     * and stops socket listening when server stops.
     */
    class SocketThread implements Runnable {
        @Override
        public void run() {
            running = true;
            while (running) {
                UpdateServerStatusWindow("Waiting for message", outputDestination);
                try {
                    serverSocket = new ServerSocket(port);
                    clientSocket = serverSocket.accept();
                    UpdateServerStatusWindow("Message received.", outputDestination);
                    DataInputStream inStream = new DataInputStream(clientSocket.getInputStream());
                    int length = inStream.readInt();
                    String s = "";// read length of incoming message
                    if (length > 0) {
                        byte[] message = new byte[length];
                        inStream.readFully(message, 0, message.length); // read the message
                        s = new String(message);
                    }
                    try {
                        BufferedWriter out = new BufferedWriter(new FileWriter("C:\\test\\test.txt"));
                        try {
                            JSONObject jsonObj = new JSONObject(s);
                            if (!jsonObj.getString("image").equals("")) {
                                out.write(jsonObj.getString("image"));
                                byte[] decodedString = new sun.misc.BASE64Decoder().decodeBuffer(jsonObj.getString("image"));
                                File of = new File("C:\\test\\yourFile.jpeg");
                                FileOutputStream osf = new FileOutputStream(of);
                                osf.write(decodedString);
                                osf.flush();
                            }
                            UpdateServerStatusWindow("login: " + jsonObj.getString("login"), outputDestination);
                            UpdateServerStatusWindow("name: " + jsonObj.getString("name"), outputDestination);
                            UpdateServerStatusWindow("localization: " + jsonObj.getString("localiztion"), outputDestination);
                            if (jsonObj.getString("image").equals("")) {
                                UpdateServerStatusWindow("commente: no comment", outputDestination);
                            } else {
                                UpdateServerStatusWindow("comment: " + jsonObj.getString("comment"), outputDestination);
                            }
                            if (jsonObj.getString("image").equals("")) {
                                UpdateServerStatusWindow("image: no image", outputDestination);
                            } else {
                                UpdateServerStatusWindow("photo path: C:\\test\\yourFile.jpeg", outputDestination);
                            }
                        } catch (JSONException e) {
                            UpdateServerStatusWindow(e.toString(), outputDestination);
                        }
                        out.close();
                    } catch (IOException e) {
                        UpdateServerStatusWindow(e.toString(), outputDestination);
                    }
                } catch (IOException e) {
                    UpdateServerStatusWindow("Server stopped.", outputDestination);
                }
                UpdateServerStatusWindow("", outputDestination);
                try {
                    serverSocket.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}