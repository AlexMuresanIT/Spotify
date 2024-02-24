package model;

public class Album {

    private int id;
    private String name;
    private int artist_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getArtist_id() {
        return artist_id;
    }

    public void setArtist_id(int artist_id) {
        this.artist_id = artist_id;
    }

    @Override
    public String toString() {
        return "ID: %-4d | Name: %-30s | Artist: %-4d".formatted(id,name,artist_id);
    }
}
