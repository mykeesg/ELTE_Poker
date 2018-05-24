/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.util.Scanner;

/**
 *
 * @author mykee
 */
public class Main {

    public static void main(String[] args) {
        String choice;
        if (args.length > 0) {
            choice = args[0];
        } else {
            System.out.println("Do you want to start a server or a client?");
            choice = new Scanner(System.in).nextLine();
        }
        if (choice.equalsIgnoreCase("server")) {
            server.Server.main(null);
        } else if (choice.equalsIgnoreCase("client")) {
            client.Client.main(null);
        } else {
            System.out.println("I have no idea what should I start.. :/");
        }
    }
}
