import model.*;

import java.io.*;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<SingleSong> songs = new ArrayList<>();
    private static List<Album> albums = new ArrayList<>();
    private static List<Artist> artists = new ArrayList<>();
    private static List<SongAlbum> songsAlbums = new ArrayList<>();
    private static List<SongAlbumArtist> songsAlbumsArtists = new ArrayList<>();
    private static List<String> albumsFromAnArtist = new ArrayList<>();

    public static void main(String[] args) {

        DataSource db = new DataSource();
        if(!db.openDB()) {
            System.out.println("Could not open the DB.");
        }else {
            System.out.println("DB opened successfully");
        }

        choices(db);
        //db.insertSong("Sirens","Travis Scott","UTOPIA",14);
        //db.updateAlbum("UTOPIA","ASTROWORLD");
        //db.deleteSongWithArtist("Sirens");

        db.closeDB();
    }

    private static void choices(DataSource db){

        readInstructions();
        String nameSong,nameArtist,nameAlbum;
        int track_nr;
        boolean cont = true;
        while(cont==true){
            System.out.println("Select your choice:");
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice){
                case 1: // add a song to the playlist
                    System.out.println("Name of the song:");
                    nameSong=scanner.nextLine();
                    System.out.println("Name of the album:");
                    nameArtist=scanner.nextLine();
                    System.out.println("Name of the artist:");
                    nameAlbum=scanner.nextLine();
                    System.out.println("Track of the song");
                    track_nr=Integer.parseInt(scanner.nextLine());
                    db.insertSong(nameSong,nameArtist,nameAlbum,track_nr);
                    break;
                case 2: //remove a song from the playlist
                    System.out.println("Name of the song");
                    nameSong=scanner.nextLine();
                    db.deleteSongWithArtist(nameSong);
                    break;
                case 3: //update a song
                    System.out.println("Enter the name of the song you want to replace");
                    String oldName=scanner.nextLine();
                    System.out.println("Enter the new name of the song");
                    nameSong=scanner.nextLine();
                    db.updateSong(oldName,nameSong);
                    break;
                case 4: //update an album
                    System.out.println("Enter the name of the album you want to replace");
                    oldName=scanner.nextLine();
                    System.out.println("Enter the new name of the album");
                    nameSong=scanner.nextLine();
                    db.updateAlbum(oldName,nameSong);
                    break;
                case 5: // update an arist
                    System.out.println("Enter the name of the artist you want to replace");
                    oldName=scanner.nextLine();
                    System.out.println("Enter the new name of the artist");
                    nameSong=scanner.nextLine();
                    db.updateArist(oldName,nameSong);
                    break;
                case 6: // read the songs
                    songs=db.readSongs();
                    readSongs(songs);
                    break;
                case 7: // read the albums
                    songsAlbums=db.readSongsAndAlbums();
                    readSongsAndAlbum(songsAlbums);
                    break;
                case 8: // read the artists
                    artists=db.readArtists();
                    readArtistss(artists);
                    break;
                case 9: // see your discography
                    songsAlbumsArtists=db.songAlbumArtist();
                    readSongsAlbumArtist(songsAlbumsArtists);
                    break;
                case 10: // all albums from a specific artist
                    System.out.println("Enter the artist.");
                    nameArtist=scanner.nextLine();
                    albumsFromAnArtist=db.retrieveAlbumsFromSpecificArtist(nameArtist,DataSource.ORDER_BY_ASC);
                    readAlbumsFromAnArtist(albumsFromAnArtist,nameArtist);
                    break;
                case 0:
                    cont=false;
                    break;
            }
        }
    }

    private static void readAlbumsFromAnArtist(List<String> list, String name){

        try(PrintWriter writer = new PrintWriter(new FileWriter("albums_from_an_artist.txt"))){
            writer.write("Albums from "+name);
            writer.println();
            for(var index:list){
                writer.println(index);
            }
        }catch (IOException e){
            System.out.println("Could not write the data..."+e.getMessage());
        }
    }

    private static void readSongsAlbumArtist(List<SongAlbumArtist> list){

        try(PrintWriter writer = new PrintWriter(new FileWriter("discography.txt"))){
            writer.write("All songs with albums");
            writer.println();
            for(var index:list){
                writer.println(index);
            }
            System.out.println("Your discography is added to the file.");
        }catch (IOException e){
            System.out.println("Could not write the songs..."+e.getMessage());
        }
    }

    private static void readArtistss(List<Artist> artists){

        try(PrintWriter writer = new PrintWriter(new FileWriter("artists.txt"))){
            writer.write("All songs with albums");
            writer.println();
            for(var artist:artists){
                writer.println(artist);
            }
            System.out.println("Songs and albums added to the file.");
        }catch (IOException e){
            System.out.println("Could not write the songs..."+e.getMessage());
        }
    }

    private static void readSongsAndAlbum(List<SongAlbum> songs){

        try(PrintWriter writer = new PrintWriter(new FileWriter("All_songs_and_all_albums.txt"))){
            writer.write("All songs with albums");
            writer.println();
            for(var song:songs){
                writer.println(song);
            }
            System.out.println("Songs and albums added to the file.");
        }catch (IOException e){
            System.out.println("Could not write the songs..."+e.getMessage());
        }
    }

    private static void readSongs(List<SingleSong> songs){

        try(PrintWriter writer = new PrintWriter(new FileWriter("Your_songs.txt"))){
                writer.write("Liked songs");
                writer.println();
                for(var song:songs){
                    writer.println(song);
                }
                System.out.println("Songs added to the file.");
        }catch (IOException e){
            System.out.println("Could not write the songs..."+e.getMessage());
        }
    }

    private static void readInstructions(){

        try(BufferedReader reader = new BufferedReader(new FileReader("src/instructions.txt"))){
            String text = reader.readLine();
            while(text!=null){
                System.out.println(text);
                text= reader.readLine();
            }
        }catch (IOException e){
            System.out.println("Could not read the data..."+e.getMessage());
        }
    }
}