package com.karaokeCopacabana;
import java.io.File;

/**
 * Created by  on 05.12.15.
 */
public class Song {
    private String title;
    private String absolutePath;
    private int fileType;
    private File file;

    public Song(File file) throws Exception {

        this.file = file;
        String name = file.getName();
        String[] names = name.split("\\.");
        this.title = names[0];
        String extension = names[1].toLowerCase();
        if (extension.equals("karDEACTIVATED")) {
            this.fileType = 1;
        } else if (extension.equals("cdg")) {
            this.fileType = 2;
        //} else if (extension.equals("mp3")) {
        //    this.fileType = 3;
        } else if (extension.equals("mpeg")) {
            this.fileType = 4;
        //} else if (extension.equals("zip")) {
        //    this.fileType = 5;
        //} else if (extension.equals("mid")) {
        //    this.fileType = 6;
        }  else {
            throw new Exception("Invalid file type: " + names[1]);
        }

    }
    public String getTitle() {
        return this.title;
    }

    public Integer getType() {
        return this.fileType;
    }

    public String getPath() {
        return this.file.getAbsolutePath();
    }


}
