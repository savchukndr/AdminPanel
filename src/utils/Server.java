package utils;

import database.AgreementDbTable;
import org.json.JSONException;
import org.json.JSONObject;
import redis.clients.jedis.Jedis;

import javax.swing.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

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
    private BufferedWriter out = null;
    private boolean running = true;
    private String CLIENT_IP = "";
    private final int CLIENT_PORT = 1996;
    private Jedis jedis;
    private RedisUtils mapRedisObject;
    private List<String> keyList;
    private AgreementDbTable agreementDbTable;
    private String photoPath;

    public Server(JTextArea outputDestination, int port){
        this.port = port;
        this.outputDestination = outputDestination;
    }

    public void start() throws IOException, InterruptedException {
        SocketThread socket = new SocketThread();
        thread = new Thread(socket);
        thread.start();
        thread.join();
    }


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

    public synchronized void stop(){
        if (thread == null) return;
        running = false;
        if (serverSocket != null){
            try {
                serverSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
            thread = null;
    }

    private synchronized void jsonHandler(String stringMesssage){
        try {
            out = new BufferedWriter(new FileWriter("C:\\test\\test.txt"));
            try {
                JSONObject jsonObj = new JSONObject(stringMesssage);
                if (!Boolean.valueOf(jsonObj.getString("updateUserDb"))){
                    orderData(jsonObj);
                } else {
                    updateSQL(jsonObj);
                }
            } catch (JSONException e) {
                UpdateServerStatusWindow(e.toString(), outputDestination);
            }
            out.close();
        }
        catch (IOException e)
        {
            UpdateServerStatusWindow(e.toString(), outputDestination);
        }
    }

    private String runPython(String key_image, String shelf)
    {
        String[] cmd = {
                "C:\\Users\\savch\\PycharmProjects\\template-matcher\\venv\\Scripts\\python.exe",
                "C:\\Users\\savch\\PycharmProjects\\template-matcher\\main.py",
                key_image,
                shelf,
        };
        try {
            Runtime.getRuntime().exec(cmd);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Image processing started";
    }

    private synchronized void orderData(JSONObject jsonObj){
        agreementDbTable = new AgreementDbTable();
        try {
            if (!jsonObj.getString("image").equals("")) {
                out.write(jsonObj.getString("image"));
                byte[] decodedString = new sun.misc.BASE64Decoder().decodeBuffer(jsonObj.getString("image"));
                photoPath = "C:\\test\\img_to_perceed.jpeg";
                File of = new File(photoPath);
                FileOutputStream osf = new FileOutputStream(of);
                osf.write(decodedString);
                osf.flush();
            }

            //--Get agreement id from agreement_data
            ResultSet resSet;
            int agreementId = 0;
            try {
                resSet = agreementDbTable.selectAgreementIDbyTittle(jsonObj.getString("agreement"));
                while(resSet.next()){
                    agreementId = Integer.parseInt(resSet.getString("id_agreement"));
                }
            } catch (SQLException e1) {
                e1.printStackTrace();
            }

            //Get image key for Redis
            String date = jsonObj.getString("date");
            String login = jsonObj.getString("login");
            String imageId = date + "_" + login + "_" + agreementId;
            System.out.println(imageId);
            System.out.println(jsonObj.getString("shelf"));
            //----------------
            if (jsonObj.getString("image").equals("")){
                UpdateServerStatusWindow("!!!! WARNING !!!", outputDestination);
                UpdateServerStatusWindow("--- NO IMAGE ---", outputDestination);
                UpdateServerStatusWindow("!!!! WARNING !!!", outputDestination);
            }else {
                RedisUtils redisUtils = new RedisUtils();
                redisUtils.insertImageIntoDB(imageId, jsonObj.getString("image"));
                UpdateServerStatusWindow("photo path: " + photoPath, outputDestination);
            }

            //Update server window
            UpdateServerStatusWindow("Time stamp: " + jsonObj.getString("date"), outputDestination);
            UpdateServerStatusWindow("login: " + jsonObj.getString("login"), outputDestination);
            UpdateServerStatusWindow("agreement: " + jsonObj.getString("agreement"), outputDestination);
            UpdateServerStatusWindow("localization: " + jsonObj.getString("localization"), outputDestination);
            UpdateServerStatusWindow("shelf: " + jsonObj.getString("shelf"), outputDestination);

            //Run object recognition
            UpdateServerStatusWindow("Image recognition started.", outputDestination);
            runPython(imageId, jsonObj.getString("shelf"));
            //-------

//            UpdateServerStatusWindow("name: " + jsonObj.getString("name"), outputDestination);
//            UpdateServerStatusWindow("localization: " + jsonObj.getString("localization"), outputDestination);
//            if (jsonObj.getString("image").equals("")){
//                UpdateServerStatusWindow("comment: no comment", outputDestination);
//            }else {
//                UpdateServerStatusWindow("comment: " + jsonObj.getString("comment"), outputDestination);
//            }
//            UpdateServerStatusWindow("Chain Store: " + jsonObj.getString("chainStore"), outputDestination);
//            UpdateServerStatusWindow("Store: " + jsonObj.getString("store"), outputDestination);

        } catch (JSONException | IOException e) {
            UpdateServerStatusWindow(e.toString(), outputDestination);
        }
    }

    private synchronized void updateSQL(JSONObject jsonObj){
        try {
            UpdateServerStatusWindow(jsonObj.getString("greeting"), outputDestination);
            UpdateServerStatusWindow("Sending updates on device...", outputDestination);
            String jsonSTR = createRedisdbMessage().toString();
            byte[] jsonByteArr = jsonSTR.getBytes();
            try {
                Socket socket = new Socket(CLIENT_IP, CLIENT_PORT);
                DataOutputStream outStream = new DataOutputStream(socket.getOutputStream());
                outStream.writeInt(jsonByteArr.length);
                outStream.write(jsonByteArr);
                socket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            UpdateServerStatusWindow("Updates sent...", outputDestination);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private JSONObject createRedisdbMessage(){
        jedis = new Jedis("localhost");
        mapRedisObject = new RedisUtils();
        keyList = mapRedisObject.getKeyList();
        return mapRedisObject.generateJsonObject(keyList);
    }

    class SocketThread implements Runnable{
        @Override
        public void run () {
            orderDataListener();
        }

        private void orderDataListener(){
            running = true;
            while (running){
                UpdateServerStatusWindow("Waiting for message", outputDestination);
                try {
                    serverSocket = new ServerSocket(port);
                    clientSocket = serverSocket.accept();
                    CLIENT_IP = clientSocket.getInetAddress().getHostAddress();
                    UpdateServerStatusWindow("Client connected on: " + CLIENT_IP, outputDestination);
                    UpdateServerStatusWindow("Message received:", outputDestination);
                    DataInputStream inStream = new DataInputStream(clientSocket.getInputStream());
                    int length = inStream.readInt();
                    String stringMessage = "";// read length of incoming message
                    if(length>0) {
                        byte[] message = new byte[length];
                        inStream.readFully(message, 0, message.length); // read the message
                        stringMessage = new String(message);
                    }
                    jsonHandler(stringMessage);
                } catch (IOException e) {
                    UpdateServerStatusWindow("Server stopped.", outputDestination);
                }
                UpdateServerStatusWindow("", outputDestination);
                try {
                    serverSocket.close();
                    try{
                        clientSocket.close();
                    }catch (NullPointerException ignored){}
                } catch (IOException e) {
                    UpdateServerStatusWindow("Server stopped.", outputDestination);
                }
            }
        }
    }
}