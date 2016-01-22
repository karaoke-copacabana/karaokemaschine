package com.karaokeCopacabana;

import java.util.concurrent.ArrayBlockingQueue;

/**
 * Created by  on 05.12.15.
 */
public class User {
    private String name;
    private String id;
    private String ip;
    //holds backlog for user
    private ArrayBlockingQueue<Song> backlog = new ArrayBlockingQueue<Song>(100);

    public String getName() {
        return this.name;
    }
    public String getId() {
        return this.id;
    }
    public String getIp() {
        return this.ip;
    }

    public void addToBacklog(Song song) {
        this.backlog.add(song);
    }

    public Song getNextSong() {
        return this.backlog.poll();
    }

}
