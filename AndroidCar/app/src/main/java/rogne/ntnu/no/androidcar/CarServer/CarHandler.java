/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rogne.ntnu.no.androidcar.CarServer;

import java.io.*;
import java.net.*;

/**
 *
 * @author Face
 */
public class CarHandler extends Thread {
    private Socket conn;
    public CarHandler(Socket conn)
    {
        this.conn = conn;

    }
    @Override
    public void run() {
        String line , input = "";

        try
        {
            //get socket writing and reading streams
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            PrintStream out = new PrintStream(conn.getOutputStream());

            //Send welcome message to client
            out.println("Hello:");

            //Now start reading input from client
            while(((line = in.readLine()) != null) && !(line.toLowerCase().equals("quit")))
            {
                //reply with the same message, adding some text
                out.println("I got : " + line);
                System.out.println("I got :" + line);
            }

            //client disconnected, so close socket
            conn.close();
        }

        catch (IOException e)
        {
            System.out.println("IOException on socket : " + e);
            e.printStackTrace();
        }
    }
}
