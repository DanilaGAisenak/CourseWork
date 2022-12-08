package client;

//import org.w3c.dom.ls.LSOutput;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.util.Scanner;

public class Client {
    private boolean enter = false;
    private ResultSet rs = null;

    public static void main(String[] args) {
        //DataInputStream is = null;
        //DataOutputStream os = null;
        Socket sock = null;
        int choice = 0;
        BufferedReader sc = new BufferedReader(new InputStreamReader(System.in));
        try{
            sock = new Socket("localhost", 1024);
            ObjectOutputStream oos = new ObjectOutputStream(sock.getOutputStream());
            ObjectInputStream ois = new ObjectInputStream(sock.getInputStream());
            SignIn first = new SignIn(sock, oos, ois);
            first.setVisible(true);

        } catch (UnknownHostException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
