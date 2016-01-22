package com.karaokeCopacabana;

/**
 * Created by  on 05.12.15.
 */
public class PlaylistEntry {
    private User user;
    private Song song;

    public PlaylistEntry(User user, Song song ){
        this.user = user;
        this.song = song;
    }
    public Song getSong() {
        return this.song;
    }
    public User getUser() {
        return this.user;
    }

}
