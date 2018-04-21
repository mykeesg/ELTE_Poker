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
    
    public static void logMessage(String s) {
        ERR.println(s);
    }
}
