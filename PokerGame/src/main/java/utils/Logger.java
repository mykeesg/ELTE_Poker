/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.io.PrintStream;

/**
 *
 * @author mykee
 */
public class Logger {

    private final static PrintStream ERR = System.err;
    public static boolean LOGGING = false;

    public static void logMessage(String s) {
        if (LOGGING) {
            ERR.println(s);
        }
    }

    public static void logString(String s) {
        if (LOGGING) {
            ERR.print(s);
        }
    }
}
