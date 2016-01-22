package com.karaokeCopacabana;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.concurrent.ArrayBlockingQueue;

import static java.lang.Thread.sleep;


/**
 * Created by on 05.12.15.
 */
public class Playlist implements Runnable {
    private ArrayBlockingQueue<PlaylistEntry> queue = new ArrayBlockingQueue<PlaylistEntry>(1000);
    private Boolean playbackActive = false;
    private Process currentPlayback;
    private PlaylistEntry currentlyPlaying;

    public void add(Song song, User user) {
        //only one song per user at a given time
        for (PlaylistEntry entry : this.queue) {
            if (entry.getUser() == user) {
                user.addToBacklog(song);
                return;
            }
        }

        this.queue.add(new PlaylistEntry(user, song));
    }

    public void run() {
        while (true) {
            if (!this.playbackActive && this.queue.peek() != null) {
                this.playNextSong();
            } else {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }


        }
    }

    private void playNextSong() {
        this.playbackActive = true;
        this.currentlyPlaying = this.queue.poll();
        //set next song for user in queue
        Song nextForUser = this.currentlyPlaying.getUser().getNextSong();
        if (nextForUser != null) {
            this.queue.add(new PlaylistEntry(this.currentlyPlaying.getUser(), nextForUser));
        }
        String[] command = new String[4];
        switch (this.currentlyPlaying.getSong().getType()) {
            case 1 : command[0] = "pykar";
                     break;
            case 2 : command[0] = "pycdg";
                break;
            case 3 : command[0] = "pympg";
                break;

        }
        command[1] = "-f";
        command[2] = "--zoom=full";
        command[3] = this.currentlyPlaying.getSong().getPath();
        //System.out.println("command: "+ command);


        StringBuffer output = new StringBuffer();

        try {
            this.currentPlayback = Runtime.getRuntime().exec(command);
            this.currentPlayback.waitFor();
            BufferedReader reader =
                    new BufferedReader(new InputStreamReader(this.currentPlayback.getInputStream()));

            String line = "";
            while ((line = reader.readLine())!= null) {
                output.append(line + "\n");
            }
            System.out.println("song " + this.currentlyPlaying.getSong().getTitle() + " finished.");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            this.currentPlayback = null;
            this.currentlyPlaying = null;
            this.playbackActive = false;
        }



    }
    public void abort() {
        if (this.currentPlayback != null) {
            this.currentPlayback.destroy();
        }
    }
    public PlaylistEntry getCurrentlyPlaying() {
        return this.currentlyPlaying;
    }

}
