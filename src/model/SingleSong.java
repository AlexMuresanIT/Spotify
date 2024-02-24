package model;

public class SingleSong {

    private String name_song;

    public String getName_song() {
        return name_song;
    }

    public void setName_song(String name_song) {
        this.name_song = name_song;
    }

    @Override
    public String toString() {
        return "%-50s".formatted(name_song);
    }
}
