/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cllient;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

/**
 *
 * @author iron2414
 */
public class Client {
     private static final int PORT = 444;
     private static final String HOST = "localhost";
     private Socket socket;
     private Scanner input;
     private PrintWriter output;

    public Client(String name) throws IOException {
        socket = new Socket(HOST,PORT);
        input = new Scanner(socket.getInputStream());
        output = new PrintWriter(socket.getOutputStream());
        output.println(name);
        output.flush();
        System.out.println("Socket connected");
    }

    public static void main(String[] args) {
        try {
            Client client = new Client("Test name");
            while(true) {
                //TODO do Poker with the server
            }
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    
}
