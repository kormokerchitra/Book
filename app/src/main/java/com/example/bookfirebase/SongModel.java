package com.example.bookfirebase;

public class SongModel {

    String id;
    String song_name;
    String song_pdf;
    String song_category;

    SongModel(String id, String song_name, String song_pdf, String song_category) {
        this.id = id;
        this.song_name = song_name;
        this.song_pdf = song_pdf;
        this.song_category = song_category;
    }

    public String getId() {
        return id;
    }

    public String getSong_name() {
        return song_name;
    }

    public String getSong_pdf() {
        return song_pdf;
    }

    public String getSong_category() {
        return song_category;
    }
}
