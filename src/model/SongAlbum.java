package model;

public class SongAlbum {

    private String name_song;
    private String name_album;

    public String getName_song() {
        return name_song;
    }

    public void setName_song(String name_song) {
        this.name_song = name_song;
    }

    public String getName_album() {
        return name_album;
    }

    public void setName_album(String name_album) {
        this.name_album = name_album;
    }

    @Override
    public String toString() {
        return "%-50s | %-20s".formatted(name_song,name_album);
    }
}
