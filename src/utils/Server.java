package utils;

import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.Date;

/**
 * Created by Andrii Savchuk on 22.04.2018.
 * All rights are reserved.
 * If you will have any questions, please
 * contact via email (savchukndr@gmail.com)
 */
public class Server {
    private int port;
    private Socket clientSocket = null;
    private BufferedWriter out = null;
    private JTextArea outputDestination = null;

    public Server(JTextArea outputDestination){
        this.port = 1994;
        this.outputDestination = outputDestination;
    }

    public void serverLoop() throws IOException {

        ServerSocket serverSocket = new ServerSocket(port);
//        serverSocket.setSoTimeout(100);
        while(true) {
            try {
                UpdateServerStatusWindow("Waiting for message", outputDestination);
                Socket clientSocket = serverSocket.accept();       //This is blocking. It will wait.
                UpdateServerStatusWindow("Message received.", outputDestination);
                DataInputStream inStream = new DataInputStream(clientSocket.getInputStream());
                int length = inStream.readInt();
                String s = "";// read length of incoming message
                if(length>0) {
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
                        if (jsonObj.getString("image").equals("")){
                            UpdateServerStatusWindow("commente: no comment", outputDestination);
                        }else {
                            UpdateServerStatusWindow("comment: " + jsonObj.getString("comment"), outputDestination);
                        }
                        if (jsonObj.getString("image").equals("")){
                            UpdateServerStatusWindow("image: no image", outputDestination);
                        }else {
                            UpdateServerStatusWindow("photo path: C:\\test\\yourFile.jpeg", outputDestination);
                        }
                    } catch (JSONException e) {
                        UpdateServerStatusWindow(e.toString(), outputDestination);
                    }
                    //out.close();
                }
                catch (IOException e)
                {
                    UpdateServerStatusWindow(e.toString(), outputDestination);
                }
                // the socket has received something
            } catch (SocketTimeoutException e) {
                // the socket received nothing
                UpdateServerStatusWindow(e.toString(), outputDestination);
            }
            UpdateServerStatusWindow("", outputDestination);
        }
    }


    private void UpdateServerStatusWindow(String message, JTextArea destination){
        try
        {
            destination.append(message + "\n");
        }
        catch(Exception e){
            JOptionPane.showMessageDialog(null, "There was an error updating the server     message output window in the TCP Listner!");
            JOptionPane.showMessageDialog(null, e);

        }

    }

//        public void startServer() throws IOException {
//        ServerSocket socket = new ServerSocket(port);
//        System.out.println("Server started:");
//        System.out.println();
//        clientSocket = socket.accept();
//        try{
//            Server.stringParser();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//    }

//    private static String getInputSteam() throws IOException {
//        DataInputStream inStream = new DataInputStream(clientSocket.getInputStream());
//        int length = inStream.readInt();
//        String inString = "";// read length of incoming message
//        if (length > 0) {
//            byte[] message = new byte[length];
//            inStream.readFully(message, 0, message.length); // read the message
//            inString = new String(message);
//        }
//        return inString;
//    }
//
//    private static void stringParser() throws IOException {
//        out = new BufferedWriter(new FileWriter("C:\\test\\test.txt"));
//        try {
//            String inString = "";
//            try{
//                inString = Server.getInputSteam();
//            }catch (IOException e){
//                e.printStackTrace();
//                Server.stopServer();
//            }
//            JSONObject jsonObj = new JSONObject(inString);
//            if (!jsonObj.getString("image").equals("")) {
//                out.write(jsonObj.getString("image"));
//                byte[] decodedString = new sun.misc.BASE64Decoder().decodeBuffer(jsonObj.getString("image"));
//                File of = new File("C:\\test\\yourFile.jpeg");
//                FileOutputStream osf = new FileOutputStream(of);
//                osf.write(decodedString);
//                osf.flush();
//            }
//            System.out.println("login: " + jsonObj.getString("login"));
//            System.out.println("name: " + jsonObj.getString("name"));
//            System.out.println("localization: " + jsonObj.getString("localiztion"));
//            if (jsonObj.getString("image").equals("")){
//                System.out.println("commente: no comment");
//            }else {
//                System.out.println("comment: " + jsonObj.getString("comment"));
//            }
//            if (jsonObj.getString("image").equals("")){
//                System.out.println("image: no image");
//            }else {
//                System.out.println("photo path: C:\\test\\yourFile.jpeg");
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//        Server.stopServer();
//    }

    private void stopServer() throws IOException {
        out.close();
    }
}