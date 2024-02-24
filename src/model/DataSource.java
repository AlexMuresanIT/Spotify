package model;

import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class DataSource {

    public static final String CONN = "org.sqlite.JDBC";
    public static final String DB_NAME="music.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:C:\\SCOALA\\JavaCourse\\Spotify\\"+DB_NAME;
    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID="_id";
    public static final String COLUMN_ALBUM_NAME="name";
    public static final String COLUMN_ALBUM_ARTIST="artist";
    public static final int INDEX_ALBUM_ID=1;
    public static final int INDEX_ALBUM_NAME=2;
    public static final int INDEX_ALBUM_ARTIST=3;
    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID="_id";
    public static final String COLUMN_ARTIST_NAME="name";
    public static final int INDEX_ARTIST_ID=1;
    public static final int INDEX_ARTIST_NAME=2;
    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONGS_ID="_id";
    public static final String COLUMN_SONGS_TRACK="track";
    public static final String COLUMN_SONGS_TITLE="title";
    public static final String COLUMN_SONGS_ALBUM="album";
    public static final int INDEX_SONGS_ID=1;
    public static final int INDEX_SONGS_TRACK=2;
    public static final int INDEX_SONGS_TITLE=3;
    public static final int INDEX_SONGS_ALBUM=4;

    public static final int ORDER_BY_NONE =1;
    public static final int ORDER_BY_ASC =2;
    public static final int ORDER_BY_DESC =3;

    public static final String QUERY_ARTISTS_BY_NAME_START=
            "SELECT "+TABLE_ARTISTS+'.'+COLUMN_ARTIST_NAME+", "+TABLE_ALBUMS+'.'+COLUMN_ALBUM_NAME+
                    ", "+TABLE_SONGS+'.'+COLUMN_SONGS_TRACK+","+TABLE_SONGS+'.'+COLUMN_SONGS_TITLE+
                    " FROM "+TABLE_SONGS+" INNER JOIN "+TABLE_ALBUMS+" ON "+TABLE_SONGS+'.'+COLUMN_SONGS_ALBUM+
                    " = "+TABLE_ALBUMS+'.'+COLUMN_ALBUM_ID+" INNER JOIN "+TABLE_ARTISTS+" ON "+
                    TABLE_ALBUMS+'.'+COLUMN_ALBUM_ARTIST+" = "+TABLE_ARTISTS+'.'+COLUMN_ARTIST_ID+
                    " WHERE "+TABLE_SONGS+'.'+COLUMN_SONGS_TITLE+"=?";

    public static final String CREATE_VIEW_ARTISTS =
            "SELECT "+TABLE_ARTISTS+'.'+COLUMN_ARTIST_NAME+", "+TABLE_ALBUMS+'.'+COLUMN_ALBUM_NAME+
                    ", "+TABLE_SONGS+'.'+COLUMN_SONGS_TRACK+","+TABLE_SONGS+'.'+COLUMN_SONGS_TITLE+
                    " FROM "+TABLE_SONGS+" INNER JOIN "+TABLE_ALBUMS+" ON "+TABLE_SONGS+'.'+COLUMN_SONGS_ALBUM+
                    " = "+TABLE_ALBUMS+'.'+COLUMN_ALBUM_ID+" INNER JOIN "+TABLE_ARTISTS+" ON "+
                    TABLE_ALBUMS+'.'+COLUMN_ALBUM_ARTIST+" = "+TABLE_ARTISTS+'.'+COLUMN_ARTIST_ID+
                    " ORDER BY "+TABLE_ARTISTS+'.'+COLUMN_ARTIST_NAME+", "+TABLE_ALBUMS+'.'+COLUMN_ALBUM_NAME+
                    ", "+TABLE_SONGS+'.'+COLUMN_SONGS_TITLE;

    public static final String INSERT_ARTIST = "INSERT INTO "+TABLE_ARTISTS+
            "("+COLUMN_ARTIST_NAME+") VALUES(?)";
    public static final String INSERT_ALBUM = "INSERT INTO "+TABLE_ALBUMS+
            "("+COLUMN_ALBUM_NAME+", "+COLUMN_ALBUM_ARTIST+") VALUES(?, ?)";
    public static final String INSERT_SONGS = "INSERT INTO "+TABLE_SONGS+
            "("+COLUMN_SONGS_TRACK+", "+COLUMN_SONGS_TITLE+", "+COLUMN_SONGS_ALBUM+
            ") VALUES(?, ?, ?)";
    public static final String QUERY_ARTIST = "SELECT "+COLUMN_ARTIST_ID+" FROM "+
            TABLE_ARTISTS+" WHERE "+COLUMN_ARTIST_NAME+"= ?";
    public static final String QUERY_ALBUM = "SELECT "+COLUMN_ALBUM_ID+" FROM "+
            TABLE_ALBUMS+" WHERE "+COLUMN_ALBUM_NAME+"= ?";
    public static final String QUERY_SONG = "SELECT "+TABLE_SONGS+'.'+COLUMN_SONGS_ALBUM+" FROM "+TABLE_SONGS+ " WHERE "
            +TABLE_SONGS+'.'+COLUMN_SONGS_TITLE+"= ?";
    public static final String DELETE_SONG = "DELETE FROM "+TABLE_SONGS+" WHERE "
            +TABLE_SONGS+'.'+COLUMN_SONGS_TITLE+"= ?";
    public static final String QUERY_ALBUM_ARTIST_ID = "SELECT "+TABLE_ALBUMS+'.'+COLUMN_ALBUM_ARTIST
            +" FROM "+TABLE_ALBUMS+" WHERE "+TABLE_ALBUMS+'.'+COLUMN_ALBUM_ID+"= ?";

    public static final String DELETE_ALBUM = "DELETE FROM "+TABLE_ALBUMS+" WHERE "
            +TABLE_ALBUMS+'.'+COLUMN_ALBUM_ID+"=?";
    public static final String QUERY_ARTIST_BY_ID = "SELECT * FROM "+TABLE_ARTISTS
            +" WHERE "+TABLE_ARTISTS+'.'+COLUMN_ARTIST_ID+"=?";
    public static final String DELETE_ARTIST = "DELETE FROM "+TABLE_ARTISTS+" WHERE "
            +TABLE_ARTISTS+'.'+COLUMN_ARTIST_ID+"=?";

    public static final String SEE_SONGS_FROM_AN_ARTIST =
            "SELECT "+TABLE_SONGS+'.'+COLUMN_SONGS_TITLE+", "
            +" FROM "+TABLE_SONGS+" INNER JOIN "+TABLE_ALBUMS
            +" ON "+TABLE_SONGS+'.'+COLUMN_SONGS_ALBUM
            +" = "+TABLE_ALBUMS+"."+COLUMN_ALBUM_ID
            +" INNER JOIN "+TABLE_ARTISTS+" ON "+TABLE_ALBUMS+"."+COLUMN_ALBUM_ARTIST
            +" = "+TABLE_ARTISTS+'.'+COLUMN_ARTIST_ID+" WHERE "
                    +TABLE_ARTISTS+'.'+COLUMN_ARTIST_NAME+"=?";

    private Connection conn;
    private PreparedStatement querySongByArtist;
    public PreparedStatement insertIntoArtists;
    public PreparedStatement insertIntoAlbums;
    private PreparedStatement insertIntoSongs;
    private PreparedStatement deleteSong;
    private PreparedStatement queryArtist;
    private PreparedStatement queryAlbum;
    private PreparedStatement querySong;
    private PreparedStatement queryAlbumByArtist;
    private PreparedStatement deleteAlbum;
    private PreparedStatement queryArtistByID;
    private PreparedStatement deleteArtist;
    private PreparedStatement songsFromArtist;


    public boolean openDB(){

        try{
            Class.forName(CONN);
            conn = DriverManager.getConnection(CONNECTION_STRING);
            querySongByArtist= conn.prepareStatement(QUERY_ARTISTS_BY_NAME_START);
            insertIntoArtists= conn.prepareStatement(INSERT_ARTIST,Statement.RETURN_GENERATED_KEYS);
            insertIntoAlbums= conn.prepareStatement(INSERT_ALBUM,Statement.RETURN_GENERATED_KEYS);
            insertIntoSongs= conn.prepareStatement(INSERT_SONGS);
            queryArtist= conn.prepareStatement(QUERY_ARTIST);
            queryAlbum= conn.prepareStatement(QUERY_ALBUM);
            querySong= conn.prepareStatement(QUERY_SONG);
            deleteSong= conn.prepareStatement(DELETE_SONG);
            queryAlbumByArtist=conn.prepareStatement(QUERY_ALBUM_ARTIST_ID);
            deleteAlbum= conn.prepareStatement(DELETE_ALBUM);
            queryArtistByID= conn.prepareStatement(QUERY_ARTIST_BY_ID);
            deleteArtist=conn.prepareStatement(DELETE_ARTIST);
            songsFromArtist= conn.prepareStatement(SEE_SONGS_FROM_AN_ARTIST);
            return true;
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Could not connect to the DB..."+e.getMessage());
            return false;
        }
    }
    private static final String SONGS_ALBUM= "SELECT "+TABLE_SONGS+'.'+COLUMN_SONGS_TITLE
            +", "+TABLE_ALBUMS+'.'+COLUMN_ALBUM_NAME+" FROM "+TABLE_SONGS+" INNER JOIN "
            +TABLE_ALBUMS+" ON "+TABLE_SONGS+'.'+COLUMN_SONGS_ALBUM+" = "
            +TABLE_ALBUMS+'.'+COLUMN_ALBUM_ID+" ORDER BY "+TABLE_ALBUMS+'.'+COLUMN_ALBUM_NAME
            +", "+TABLE_SONGS+'.'+COLUMN_SONGS_TITLE;
    public List<SongAlbum> readSongsAndAlbums(){

        try{

            Statement statement = conn.createStatement();
            ResultSet resultSet = statement.executeQuery(SONGS_ALBUM);

            List<SongAlbum> songs = new ArrayList<>();

            while(resultSet.next()){
                SongAlbum song = new SongAlbum();
                song.setName_song(resultSet.getString(1));
                song.setName_album(resultSet.getString(2));
                songs.add(song);
            }

            return songs;

        }catch (SQLException e){
            System.out.println("Could not retrieve data..."+e.getMessage());
            return null;
        }
    }
    public boolean closeDB(){
        try{
            conn.close();
            if(insertIntoArtists!=null)
                insertIntoArtists.close();
            if(insertIntoAlbums!=null)
                insertIntoAlbums.close();
            if(insertIntoSongs!=null)
                insertIntoSongs.close();
            if(queryArtist!=null)
                queryArtist.close();
            if(queryAlbum!=null)
                queryAlbum.close();
            return true;
        }catch (SQLException e) {
            System.out.println("Could not close the connection..."+e.getMessage());
            return false;
        }
    }
    public List<SongsFromArtist> songsFromOneArtist(String name){

        try{
            songsFromArtist.setString(1,name);
            ResultSet resultSet = songsFromArtist.executeQuery();

            List<SongsFromArtist> songs = new ArrayList<>();

            while(resultSet.next()){
                SongsFromArtist song = new SongsFromArtist();
                song.setSong_name(resultSet.getString(1));
                songs.add(song);
            }

            return songs;

        }catch (SQLException e){
            System.out.println("Could not retrieve data..."+e.getMessage());
            return null;
        }
    }

    public List<SingleSong> readSongs(){

        StringBuilder sb = new StringBuilder("SELECT "+TABLE_SONGS+'.'+COLUMN_SONGS_TITLE+" FROM "+TABLE_SONGS);
        Statement statement = null;
        ResultSet resultSet = null;

       try{
            statement = conn.createStatement();
            resultSet=statement.executeQuery(sb.toString());

            List<SingleSong> songs = new ArrayList<>();

            while(resultSet.next()){
                SingleSong song = new SingleSong();
                song.setName_song(resultSet.getString(1));
                songs.add(song);
            }
           return songs;

       }catch (SQLException e){
           System.out.println("Could not read the songs..."+e.getMessage());
           return null;
       }

    }

    public List<Album> readAlbums(){

        StringBuilder sb = new StringBuilder("SELECT * FROM "+TABLE_ALBUMS);
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            statement = conn.createStatement();
            resultSet=statement.executeQuery(sb.toString());

            List<Album> albums = new ArrayList<>();

            while(resultSet.next()){
                Album album = new Album();
                album.setId(resultSet.getInt(1));
                album.setName(resultSet.getString(2));
                album.setArtist_id(resultSet.getInt(3));
                albums.add(album);
            }
            return albums;

        }catch (SQLException e){
            System.out.println("Could not read the albums..."+e.getMessage());
            return null;
        }

    }

    public List<Artist> readArtists(){

        StringBuilder sb = new StringBuilder("SELECT * FROM "+TABLE_ARTISTS);
        Statement statement = null;
        ResultSet resultSet = null;

        try{
            statement= conn.createStatement();
            resultSet = statement.executeQuery(sb.toString());

            List<Artist> artists = new ArrayList<>();

            while(resultSet.next()){
                Artist artist = new Artist();
                artist.setId(resultSet.getInt(1));
                artist.setName(resultSet.getString(2));
                artists.add(artist);
            }
            return artists;

        }catch (SQLException e){
            System.out.println("Could not read the artists..."+e.getMessage());
            return null;
        }
    }

    public void updateSong(String oldName, String newName){

        StringBuilder sb = new StringBuilder("UPDATE "+TABLE_SONGS+" SET "+COLUMN_SONGS_TITLE+ "='");
        sb.append(newName);
        sb.append("'");
        sb.append(" WHERE "+COLUMN_SONGS_TITLE+"='");
        sb.append(oldName);
        sb.append("'");
        System.out.println(sb.toString());
        Statement statement = null;

        try{
            statement = conn.createStatement();
            statement.execute(sb.toString());
        }catch (SQLException e){
            System.out.println("Could not update the song..."+e.getMessage());
        }
    }

    public void updateAlbum(String oldName, String newName){

        StringBuilder sb = new StringBuilder("UPDATE "+TABLE_ALBUMS+" SET "+COLUMN_ALBUM_NAME+ "='");
        sb.append(newName);
        sb.append("'");
        sb.append(" WHERE "+COLUMN_ALBUM_NAME+"='");
        sb.append(oldName);
        sb.append("'");
        System.out.println(sb.toString());
        Statement statement = null;

        try{
            statement = conn.createStatement();
            statement.execute(sb.toString());
        }catch (SQLException e){
            System.out.println("Could not update the album..."+e.getMessage());
        }
    }

    public void updateArist(String oldName, String newName){

        StringBuilder sb = new StringBuilder("UPDATE "+TABLE_ARTISTS+" SET "+COLUMN_ARTIST_NAME+ "='");
        sb.append(newName);
        sb.append("'");
        sb.append(" WHERE "+COLUMN_ARTIST_NAME+"='");
        sb.append(oldName);
        sb.append("'");
        System.out.println(sb.toString());
        Statement statement = null;

        try{
            statement = conn.createStatement();
            statement.execute(sb.toString());
        }catch (SQLException e){
            System.out.println("Could not update the artist..."+e.getMessage());
        }
    }

    public void schemaCommand(String table_name){

        System.out.println("--------------------");
        System.out.println("table: "+table_name);
        StringBuilder sb = new StringBuilder("SELECT * FROM "+table_name);

        try(Statement statement = conn.createStatement();
        ResultSet result = statement.executeQuery(sb.toString())){

            ResultSetMetaData set = result.getMetaData();
            int ctCounter = set.getColumnCount();
            for(int i=1;i<=ctCounter;i++){
                System.out.printf("%d column: %s type: %s%n",i,set.getColumnName(i),set.getColumnTypeName(i));
            }
            System.out.println("--------------------");
        }catch (SQLException e){
            System.out.println("Could not execute query....");
        }
    }
    public List<SongArtist> retrieveDataFromSong(String name){


        Statement statement = null;
        ResultSet resultSet=null;
        try{
            querySong.setString(1,name);
            resultSet=querySong.executeQuery();
            List<SongArtist> list = new ArrayList<>();
            while(resultSet.next()){
                SongArtist songArtist = new SongArtist();
                songArtist.setArtist_name(resultSet.getString(1));
                songArtist.setAlbum_name(resultSet.getString(2));
                songArtist.setTrack(resultSet.getInt(3));
                list.add(songArtist);
            }
            return list;
        }catch (SQLException e){
            System.out.println("Could not execute query....");
            return null;
        }finally {
            try{
                if(resultSet!=null)
                    resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(statement!=null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public List<String>  retrieveAlbumsFromSpecificArtist(String artist_name, int sortOrder){
        //SELECT albums.name FROM albums INNER JOIN artists ON albums.artist = artists._id WHERE artists.name='Pink Floyd'
        //                                                                                 ORDER BY albums.name COLLATE NOCASE ASC;

        StringBuilder sb = new StringBuilder("SELECT ");
        sb.append(TABLE_ALBUMS);
        sb.append('.');
        sb.append(COLUMN_ALBUM_NAME);
        sb.append(" FROM ");
        sb.append(TABLE_ALBUMS);
        sb.append(" INNER JOIN ");
        sb.append(TABLE_ARTISTS);
        sb.append(" ON ");
        sb.append(TABLE_ALBUMS);
        sb.append('.');
        sb.append(COLUMN_ALBUM_ARTIST);
        sb.append(" = ");
        sb.append(TABLE_ARTISTS);
        sb.append('.');
        sb.append(COLUMN_ARTIST_ID);
        sb.append(" WHERE ");
        sb.append(TABLE_ARTISTS);
        sb.append('.');
        sb.append(COLUMN_ARTIST_NAME+"=\"");
        sb.append(artist_name+"\"");

        if(sortOrder!=ORDER_BY_NONE){
            sb.append(" ORDER BY ");
            sb.append(TABLE_ALBUMS);
            sb.append('.');
            sb.append(COLUMN_ALBUM_NAME);
            sb.append(" COLLATE NOCASE ");
            if(sortOrder==ORDER_BY_DESC)
                sb.append("DESC");
            else sb.append("ASC");
        }
        //System.out.println("SQL: "+sb.toString());

        Statement statement = null;
        ResultSet resultSet = null;
        try{
            statement = conn.createStatement();
            resultSet = statement.executeQuery(sb.toString());
            List<String> albums = new ArrayList<>();
            while(resultSet.next()){
                albums.add(resultSet.getString(1));
            }
            return albums;
        } catch (SQLException e) {
            System.out.println("Could not execute query...");
            return null;
        }finally {
            try{
                if(resultSet!=null)
                    resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            if(statement!=null) {
                try {
                    statement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public int getCount(String table){

        StringBuilder sb = new StringBuilder("SELECT COUNT(*) AS count FROM "+table);

        //System.out.println("Table name: "+table);
        try(Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sb.toString())){

            int count = resultSet.getInt("count");
            //System.out.printf("Count = %d%n",count);
            return count;

        }catch (SQLException e){
            System.out.println("Could not execute query..."+e.getMessage());
            return 0;
        }
    }

    public boolean createViewWithArtists(String name){

        //CREATE VIEW IF NOT EXISTS artists_list AS
        //SELECT artists.name, albums.name, songs.track, songs.title FROM songs
        //INNER JOIN artists ON songs.album = albums._id
        //INNER JOIN artists ON albums.artist = artists._id ORDER BY artists.name, albums.name, songs.track;

        StringBuilder sb = new StringBuilder("CREATE VIEW IF NOT EXISTS "+name+" AS ");
        sb.append(CREATE_VIEW_ARTISTS);

        try(Statement statement = conn.createStatement()){

            statement.execute(sb.toString());
            System.out.println("View created successfully");
            return true;

        }catch (SQLException e){
            System.out.println("View cannot be created...");
            e.printStackTrace();
            return false;
        }
    }

    public static final String QUERY_SONG_ALBUM_ARTIST=
            "SELECT "+TABLE_SONGS+'.'+COLUMN_SONGS_TITLE+", "+TABLE_ALBUMS+'.'+COLUMN_ALBUM_NAME+
                    ", "+TABLE_ARTISTS+'.'+COLUMN_ARTIST_NAME+" FROM "+TABLE_SONGS+" INNER JOIN "+
                    TABLE_ALBUMS+" ON "+TABLE_SONGS+'.'+COLUMN_SONGS_ALBUM+"="+TABLE_ALBUMS+'.'+COLUMN_ALBUM_ID+
                    " INNER JOIN "+TABLE_ARTISTS+" ON "+TABLE_ALBUMS+'.'+COLUMN_ALBUM_ARTIST+"="+TABLE_ARTISTS+"."+
                    COLUMN_ARTIST_ID+" ORDER BY "+TABLE_ALBUMS+'.'+COLUMN_ALBUM_NAME+", "+
                    TABLE_SONGS+'.'+COLUMN_SONGS_TITLE+", "+TABLE_ARTISTS+'.'+COLUMN_ARTIST_NAME;

    public List<SongAlbumArtist> songAlbumArtist(){

        /*SELECT songs.title, albums.name, artists.name FROM songs
        INNER JOIN albums ON songs.album = albums._id
        INNER JOIN artists ON albums.artist=artists._id ORDER BY albums.name,songs.title, artists.name;*/
        StringBuilder sb = new StringBuilder(QUERY_SONG_ALBUM_ARTIST);

        //System.out.println(sb.toString());
        try(Statement statement = conn.createStatement();
        ResultSet resultSet = statement.executeQuery(sb.toString())){

            List<SongAlbumArtist> list = new ArrayList<>();
            while(resultSet.next()){

                SongAlbumArtist saa = new SongAlbumArtist();
                saa.setSong_title(resultSet.getString(1));
                saa.setAlbum_name(resultSet.getString(2));
                saa.setArtist_name(resultSet.getString(3));
                list.add(saa);
            }
            return list;

        }catch (SQLException e){
            System.out.println("Could not execute query...");
            e.printStackTrace();
            return null;
        }

    }

    private int insertArtist(String name) throws SQLException{

        queryArtist.setString(1,name);
        ResultSet resultSet = queryArtist.executeQuery();
        if(resultSet.next()){
            // if artist is in the table
            return resultSet.getInt(1);
        }else{
            //if artist is not in the table
            insertIntoArtists.setString(1,name);
            int affectedRows = insertIntoArtists.executeUpdate();// returns the number of rows affected by the sql code

            if(affectedRows!=1){
                throw new SQLException("Could not insert artist...");
            }

            ResultSet generatedKeys = insertIntoArtists.getGeneratedKeys(); // insert the artist and a key is generated
            if(generatedKeys.next()){
                return generatedKeys.getInt(1);
            }else{
                throw new SQLException("Couldn't get _id for artist...");
            }
        }

    }

    private int insertAlbum(String name, int artist_ID) throws SQLException{

        //SELECT * FROM artists WHERE name='';
        queryAlbum.setString(1,name);
        ResultSet resultSet = queryAlbum.executeQuery();
        if(resultSet.next()){
            // if album is in the table
            return resultSet.getInt(1);
        }else{
            //if album is not in the table
            insertIntoAlbums.setString(1,name);
            insertIntoAlbums.setInt(2,artist_ID);
            int affectedRows = insertIntoAlbums.executeUpdate();// returns the number of rows affected by the sql code

            if(affectedRows!=1){
                throw new SQLException("Could not insert album...");
            }

            ResultSet generatedKeys = insertIntoAlbums.getGeneratedKeys(); // insert the artist and a key is generated
            if(generatedKeys.next()){
                return generatedKeys.getInt(1);
            }else{
                throw new SQLException("Couldn't get _id for album...");
            }
        }

    }

    public void insertSong(String title, String artist, String album, int track) {

        try{
            conn.setAutoCommit(false);

            int artist_id = insertArtist(artist);
            int album_id = insertAlbum(album,artist_id);
            insertIntoSongs.setInt(1,track);
            insertIntoSongs.setString(2,title);
            insertIntoSongs.setInt(3,album_id);

           int affectedRows = insertIntoSongs.executeUpdate();
           if(affectedRows==1){
               System.out.println("Song added to the playlist.");
               conn.commit();
           }else{
               throw new SQLException("Song insert failed...");
           }

        }catch (SQLException e){
            System.out.println("Insert song failed..."+e.getMessage());
            try{
                System.out.println("Performing rollback");
                conn.rollback();
            }catch (SQLException e2){
                System.out.println("Could not rollback..."+e2.getMessage());
            }
        }finally {
            System.out.println("Resetting default commit behavior");
            try {
                conn.setAutoCommit(true);
            } catch (SQLException e3) {
                System.out.println("Couls not reset auto-commit");
            }
        }
    }

    private int deleteSong(String name){

        try {
            querySong.setString(1, name);
            ResultSet resultSet = querySong.executeQuery();
            if (resultSet.next()) {
                int album_id= resultSet.getInt(1);
                //song exists
                System.out.println("Song deleted successfully.");
                deleteSong.setString(1, name);
                deleteSong.execute();
                return album_id;
            } else {
                System.out.println("This song is not in the table...");
                return -1;
            }
        }catch (SQLException e){
            System.out.println("There was an error..."+e.getMessage());
            return -1;
        }
    }

    private int deleteAlbum(String name){

        try {
            int album_ID = deleteSong(name);
            if(album_ID!=-1){
                queryAlbumByArtist.setInt(1, album_ID);
                ResultSet resultSet = queryAlbumByArtist.executeQuery();
                if(resultSet.next()){
                    int artist_id = resultSet.getInt(1);
                    //deleting the album
                    System.out.println("Album deleted succesfully");
                    deleteAlbum.setInt(1,album_ID);
                    deleteAlbum.execute();
                    return artist_id;
                }else{
                    System.out.println("Album is not in the table.");
                    return -1;
                }
            }else{
                System.out.println("Could not delete the song so nor the album.");
                return -1;
            }
        }catch (SQLException e){
            System.out.println("There was an error..."+e.getMessage());
            return -1;
        }
    }

    public void deleteSongWithArtist(String name){
        try{
            conn.setAutoCommit(false);
            int artist_id = deleteAlbum(name);
            if(artist_id!=-1){
                queryArtistByID.setInt(1,artist_id);
                ResultSet resultSet = queryArtistByID.executeQuery();
                if(resultSet.next()){
                    System.out.println("Artist deleted successfully");
                    deleteArtist.setInt(1,artist_id);
                    deleteArtist.execute();
                    conn.commit();
                }else{
                    System.out.println("There is no artist with this ID.");
                }
            }else{
                System.out.println("Could not delete the song the album nor the artist.");
            }
        }catch (SQLException e){
            System.out.println("Insert song failed..."+e.getMessage());
            try{
                System.out.println("Performing rollback");
                conn.rollback();
            }catch (SQLException e2){
                System.out.println("Could not rollback..."+e.getMessage());
            }
        }finally {
            try {
                conn.setAutoCommit(true);
            }catch (SQLException e3){
                System.out.println("Could not reset auto-commit");
            }
        }
    }

}
