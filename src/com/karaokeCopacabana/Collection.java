package com.karaokeCopacabana;

import com.oracle.javafx.jmx.json.*
        ;

import java.util.ArrayList;
import java.io.File;

/**
 * Created by on 05.12.15.
 */
public class Collection {
    private ArrayList<Song> songs = new ArrayList<Song>(10000);
    private String list = "";


    public void load(String location) {
        File directory = new File(location);
        for (File file : directory.listFiles()) {
            if (file.isFile()) {
                try {
                    this.songs.add(new Song(file));
                } catch (Exception e) {
                    //e.printStackTrace();
                }
            } else if (file.isDirectory()) {
                this.load(file.getAbsolutePath());
            }
        }


    }
    public Song get(int id) {
        return this.songs.get(id);
    }

    public void init() {
        int i=0;

        for(Song song: this.songs) {
            this.list += "<li ><a class=\"song\" href=\"http://karaoke:8001/playlist?addSong=" + i + "\">" + song.getTitle() + "</a></li>\n";
            i++;
        }
    }

    public String getAll() {

        return this.list;

    }
}
