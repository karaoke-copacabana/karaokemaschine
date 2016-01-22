package com.karaokeCopacabana;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by on 18.12.15.
 */
public class ScreamServer implements Runnable {
    private ServerSocket serverSocket;

    public ScreamServer(int port) {
        try {
            this.serverSocket = new ServerSocket(port);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                System.out.println("Waiting for connection...");
                Socket clientSocket = this.serverSocket.accept();
                DataInputStream in = new DataInputStream(clientSocket.getInputStream());


                /*System.out.println("Got one!");
                PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
                out.write("welcome");
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(clientSocket.getInputStream()));
                boolean finished = false;
                while (!finished) {
                    System.out.print(in.read());
                }*/



            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
