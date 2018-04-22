package utils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

/**
 * Created by Andrii Savchuk on 22.04.2018.
 * All rights are reserved.
 * If you will have any questions, please
 * contact via email (savchukndr@gmail.com)
 */
public class Server {
    private static int port;
    private static Socket clientSocket = null;
    private static BufferedWriter out = null;

    public Server(int port){
         Server.port = port;
    }

    public void startServer() throws IOException {
        ServerSocket socket = new ServerSocket(port);
        System.out.println("Server started:");
        System.out.println();
        clientSocket = socket.accept();
        try{
            Server.stringParser();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public String writeMassage(String s){
        return s;
    }

    private static String getInputSteam() throws IOException {
        DataInputStream inStream = new DataInputStream(clientSocket.getInputStream());
        int length = inStream.readInt();
        String inString = "";// read length of incoming message
        if (length > 0) {
            byte[] message = new byte[length];
            inStream.readFully(message, 0, message.length); // read the message
            inString = new String(message);
        }
        return inString;
    }

    private static void stringParser() throws IOException {
        out = new BufferedWriter(new FileWriter("C:\\test\\test.txt"));
        try {
            String inString = "";
            try{
                inString = Server.getInputSteam();
            }catch (IOException e){
                e.printStackTrace();
                Server.stopServer();
            }
            JSONObject jsonObj = new JSONObject(inString);
            if (!jsonObj.getString("image").equals("")) {
                out.write(jsonObj.getString("image"));
                byte[] decodedString = new sun.misc.BASE64Decoder().decodeBuffer(jsonObj.getString("image"));
                File of = new File("C:\\test\\yourFile.jpeg");
                FileOutputStream osf = new FileOutputStream(of);
                osf.write(decodedString);
                osf.flush();
            }
            System.out.println("login: " + jsonObj.getString("login"));
            System.out.println("name: " + jsonObj.getString("name"));
            System.out.println("localization: " + jsonObj.getString("localiztion"));
            if (jsonObj.getString("image").equals("")){
                System.out.println("commente: no comment");
            }else {
                System.out.println("comment: " + jsonObj.getString("comment"));
            }
            if (jsonObj.getString("image").equals("")){
                System.out.println("image: no image");
            }else {
                System.out.println("photo path: C:\\test\\yourFile.jpeg");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
            Server.stopServer();
    }

    private static void stopServer() throws IOException {
        out.close();
    }
}
