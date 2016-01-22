package com.karaokeCopacabana;

import com.sun.net.httpserver.*;

import javax.sound.sampled.LineUnavailableException;
import java.io.IOException;
import java.net.InetSocketAddress;


public class Main {

    public static void main(String[] args) {
        try {


            SocketServer socketServer = null;
            try {
                socketServer = new SocketServer(new InetSocketAddress(8002));
            } catch (LineUnavailableException e) {
                e.printStackTrace();
            }
            new Thread(socketServer).start();





            //mic control deactivated
            //ScreamServer screamServer = new ScreamServer(8002);
            //new Thread(screamServer).start();
            HttpServer server = HttpServer.create(new InetSocketAddress(1111), 0);
            Playlist playlist = new Playlist();
            Collection collection = new Collection();
            collection.load("/home/pi/songs/");
            System.out.println("Finished loading Collection");
            collection.init();
            System.out.println("list ready");
            new Thread(playlist).start();
            server.createContext("/playlist", new PlaylistHandler(playlist,collection));
            server.createContext("/", new CollectionHandler(collection));
            server.setExecutor(null); // creates a default executor
            server.start();


        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
