package com.karaokeCopacabana;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.HashMap;

/**
 * Created by on 05.12.15.
 */
public class PlaylistHandler implements HttpHandler {
    private Playlist playlist;
    private Collection collection;
    private HashMap<String, User> users = new HashMap<String, User>();
    private int currentVolume;

    public PlaylistHandler (Playlist playlist, Collection collection) {
        this.playlist = playlist;
        this.collection = collection;
        //set initial volume

        try {
            this.setVolume(50);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void handle(HttpExchange httpExchange) throws IOException {
        Headers responseHeaders = httpExchange.getResponseHeaders();
        responseHeaders.set("Access-Control-Allow-Origin", "*");
        String command = httpExchange.getRequestURI().getQuery();
        System.out.println(command);
        if (command.contains("addSong")) {
            this.handleAdd(httpExchange);
        }
        if (command.contains("deleteSong")) {
            this.handleDelete(httpExchange);
        }
        if (command.contains("stop")) {
            this.handleStop(httpExchange);
        }
        if (command.contains("volumeUp")) {
            this.handleVolumeUp(httpExchange);
        }
        if (command.contains("volumeDown")) {
            this.handleVolumeDown(httpExchange);
        }


    }

    private void handleAdd(HttpExchange httpExchange) throws IOException {


        User user = this.getUser(httpExchange.getRemoteAddress());


        int id = Integer.valueOf(httpExchange.getRequestURI().getQuery().split("=")[1]);

        Song song = this.collection.get(id);
        this.playlist.add(song, user);


        String response = "song " + song.getTitle() + " added!";

        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
    private void handleDelete(HttpExchange httpExchange) {

    }
    private void handleStop(HttpExchange httpExchange) throws IOException {
        User user = this.getUser(httpExchange.getRemoteAddress());
        String response;
        if (this.playlist.getCurrentlyPlaying() == null) {
            response = "not playing anything at the moment";
        } else if (this.playlist.getCurrentlyPlaying().getUser() == user) {
            this.playlist.abort();
            response = "current song stopped!";
        } else {
            response = "not qualified to stop current song. Sit back and enjoy";
        }
        System.out.println(response);


        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }

    private void handleVolumeUp(HttpExchange httpExchange) throws IOException {

        int newVolume = Math.min(100, this.currentVolume+10);
        this.setVolume(newVolume);
        String response = "Volume increased";
        System.out.println(response);


        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();

    }
    private void handleVolumeDown(HttpExchange httpExchange) throws IOException {

        int newVolume = Math.max(0, this.currentVolume - 10);
        this.setVolume(newVolume);
        String response = "Volume lowered";
        System.out.println(response);


        httpExchange.sendResponseHeaders(200, response.length());
        OutputStream os = httpExchange.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }


    private void setVolume(int Volume) throws IOException {
        String[] command = new String[4];
        command[0] ="amixer";
        command[1] ="sset";
        command[2] ="Master";
        command[3] =Volume+"%";
        Runtime.getRuntime().exec(command);

        this.currentVolume=Volume;

    }
    private User getUser(InetSocketAddress address) {
        String ip = address.toString().split(":")[0];
        if (!this.users.containsKey(ip)) {
            this.users.put(ip, new User());
        }
        return this.users.get(ip);

    }
}
