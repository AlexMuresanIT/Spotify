package model;

public class SongArtist {

    private String artist_name;
    private String album_name;
    private int track;

    public String getArtist_name() {
        return artist_name;
    }

    public void setArtist_name(String artist_name) {
        this.artist_name = artist_name;
    }

    public String getAlbum_name() {
        return album_name;
    }

    public void setAlbum_name(String album_name) {
        this.album_name = album_name;
    }

    public int getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    @Override
    public String toString() {
        return "SongArtist{" +
                "artist_name='" + artist_name + '\'' +
                ", album_name='" + album_name + '\'' +
                ", track=" + track +
                '}';
    }
}
