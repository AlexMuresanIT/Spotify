package model;

public class SongAlbumArtist {

    private String song_title;
    private String album_name;
    private String artist_name;

    public String getSong_title() {
        return song_title;
    }

    public void setSong_title(String song_title) {
        this.song_title = song_title;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    @Override
    public String toString() {
        return "%-60s : %-40s : %-40s".formatted(song_title,album_name,artist_name);
    }
}
