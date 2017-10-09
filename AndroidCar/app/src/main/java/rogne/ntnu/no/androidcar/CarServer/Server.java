
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rogne.ntnu.no.androidcar.CarServer;

import java.io.*;
import java.net.*;
import rogne.ntnu.no.androidcar.CarServer.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Face
 */
public class Server extends Thread {

    private ServerSocket serverSocket = null;
    private int port;

    public Server(int port) {
        this.port = port;
    }

    /**
     *
     */
    @Override
    public void run() {
        System.out.println("hello from: " +  Thread.currentThread());
        int tries = 3;
        while (tries > 0 && !startServer(tries)) {
            tries--;
        }
        boolean running = true;
        while (running) {
            listenForConnection();

        }
    }

    private  boolean startServer(int tries) {
        try {
            serverSocket = new ServerSocket(port, 10);
            System.out.println("Server started on port: " + port);
            return true;
        } catch (IOException ex) {
            int attempts = 4 - tries;
            System.out.println("Attempt " + attempts);
            System.out.println("Could not start server on port: " + port + ". Reason: " + ex.getMessage());
            return false;
        }
    }

    private void listenForConnection() {
        Socket conn = null;
        try {
            conn = serverSocket.accept();
            System.out.println("Connection recived from " + conn.getInetAddress().getHostName() + " : " + conn.getPort());
            new CarHandler(conn).start();
        } catch (IOException ex) {
            System.out.println("Could not accept connection. Reason: " + ex.getMessage());
        }
    }
}
