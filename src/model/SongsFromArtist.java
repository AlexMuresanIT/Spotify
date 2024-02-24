package model;

public class SongsFromArtist {

    private String song_name;

    public String getSong_name() {
        return song_name;
    }

    public void setSong_name(String song_name) {
        this.song_name = song_name;
    }

    @Override
    public String toString() {
        return "Song: %-20s".formatted(song_name);
    }
}
