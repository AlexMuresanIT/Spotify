package model;

public class Song {

    private int id;
    private int track;
    private String name;
    private int album_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAlbum_id() {
        return album_id;
    }

    public void setAlbum_id(int album_id) {
        this.album_id = album_id;
    }

    @Override
    public String toString() {
        return "ID: %-4d | Track: %-2d | Title: %-60s | Album: %-4d".formatted(id,track,name,album_id);
    }
}
