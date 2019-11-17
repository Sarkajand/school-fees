package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static final String DB_NAME = "musicForUI.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\Java\\databases\\" + DB_NAME;

    public static final String TABLE_ALBUMS = "albums";
    public static final String COLUMN_ALBUM_ID = "_id";
    public static final String COLUMN_ALBUM_NAME = "name";
    public static final String COLUMN_ALBUM_ARTIST = "artist";
    public static final int INDEX_ALBUM_ID = 1;
    public static final int INDEX_ALBUM_NAME = 2;
    public static final int INDEX_ALBUM_ARTIST = 3;

    public static final String TABLE_ARTISTS = "artists";
    public static final String COLUMN_ARTIST_ID = "_id";
    public static final String COLUMN_ARTIST_NAME = "name";
    public static final int INDEX_ARTIST_ID = 1;
    public static final int INDEX_ARTIST_NAME = 2;

    public static final String TABLE_SONGS = "songs";
    public static final String COLUMN_SONG_ID = "_id";
    public static final String COLUMN_SONG_TRACK = "track";
    public static final String COLUMN_SONG_TITLE = "title";
    public static final String COLUMN_SONG_ALBUM = "album";
    public static final int INDEX_SONG_ID = 1;
    public static final int INDEX_SONG_TRACK = 2;
    public static final int INDEX_SONG_TITLE = 3;
    public static final int INDEX_SONG_ALBUM = 4;

    public static final int ORDER_BY_NONE = 1;
    public static final int ORDER_BY_ASC = 2;
    public static final int ORDER_BY_DESC = 3;


    public static final String INSERT_ARTIST =
            "INSERT INTO " + TABLE_ARTISTS + " (" + COLUMN_ARTIST_NAME + ") VALUES (?)";
    public static final String INSERT_ALBUM =
            "INSERT INTO " + TABLE_ALBUMS + " (" + COLUMN_ALBUM_NAME + ", " + COLUMN_ALBUM_ARTIST + ") VALUES (?, ?)";
    public static final String INSERT_SONG =
            "INSERT INTO " + TABLE_SONGS + " (" + COLUMN_SONG_TRACK + ", " + COLUMN_SONG_TITLE + ", " + COLUMN_SONG_ALBUM +
                    ") VALUES (?, ?, ?)";
    public static final String QUERY_ARTIST =
            "SELECT " + COLUMN_ARTIST_ID + " FROM " + TABLE_ARTISTS + " WHERE " + COLUMN_ARTIST_NAME +
                    " = ?";
    public static final String QUERY_ALBUM =
            "SELECT " + COLUMN_ALBUM_ID + " FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_NAME +
                    " = ?";

    public static final String QUERY_ALBUMS_BY_ARTIST_ID = "SELECT * FROM " + TABLE_ALBUMS +
            " WHERE " + COLUMN_ALBUM_ARTIST + " = ? ORDER BY " + COLUMN_ALBUM_NAME + " COLLATE NOCASE";

    public static final String UPDATE_ARTIST_NAME =
            "UPDATE " + TABLE_ARTISTS + " SET " + COLUMN_ARTIST_NAME + " = ? WHERE " + COLUMN_ARTIST_ID + " = ?";

    public static final String QUERY_SONGS_BY_ALBUM_ID =
            "SELECT * FROM " + TABLE_SONGS +
                    " WHERE " + COLUMN_SONG_ALBUM + " = ?";

    public static final String QUERY_ALBUM_BY_ID =
            "SELECT * FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_ID + " = ?";

    public static final String DELETE_SONG =
            "DELETE FROM " + TABLE_SONGS + " WHERE " + COLUMN_SONG_ID + " = ?";

    public static final String DELETE_ALBUM =
            "DELETE FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_ID + " = ?";

    public static final String DELETE_ARTIST =
            "DELETE FROM " + TABLE_ARTISTS + " WHERE " + COLUMN_ARTIST_ID + " = ?";
    //    this create singleton class
    private static DataSource instance = new DataSource();
    private Connection conn;
    private PreparedStatement insertIntoArtists;
    private PreparedStatement insertIntoAlbums;
    private PreparedStatement insertIntoSongs;
    private PreparedStatement queryArtist;
    private PreparedStatement queryAlbum;
    private PreparedStatement queryAlbumsByArtistId;
    private PreparedStatement updateArtistName;
    private PreparedStatement querySongsByAlbumId;
    private PreparedStatement queryAlbumById;
    private PreparedStatement deleteSong;
    private PreparedStatement deleteAlbum;
    private PreparedStatement deleteArtist;

    private DataSource() {

    }

    public static DataSource getInstance() {
        return instance;
    }
//    I have only one instance of class, constructor is private, no other class can call it
//    I call methods from singleton class in format
//          DataSource.getInstance().methodName();

    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);

            insertIntoArtists = conn.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS);
            insertIntoAlbums = conn.prepareStatement(INSERT_ALBUM, Statement.RETURN_GENERATED_KEYS);
            insertIntoSongs = conn.prepareStatement(INSERT_SONG);
            queryArtist = conn.prepareStatement(QUERY_ARTIST);
            queryAlbum = conn.prepareStatement(QUERY_ALBUM);
            queryAlbumsByArtistId = conn.prepareStatement(QUERY_ALBUMS_BY_ARTIST_ID);
            updateArtistName = conn.prepareStatement(UPDATE_ARTIST_NAME);
            querySongsByAlbumId = conn.prepareStatement(QUERY_SONGS_BY_ALBUM_ID);
            queryAlbumById = conn.prepareStatement(QUERY_ALBUM_BY_ID);
            deleteSong = conn.prepareStatement(DELETE_SONG);
            deleteAlbum = conn.prepareStatement(DELETE_ALBUM);
            deleteArtist = conn.prepareStatement(DELETE_ARTIST);

            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
            if (insertIntoArtists != null) {
                insertIntoArtists.close();
            }
            if (insertIntoAlbums != null) {
                insertIntoAlbums.close();
            }
            if (insertIntoSongs != null) {
                insertIntoSongs.close();
            }
            if (queryArtist != null) {
                queryArtist.close();
            }
            if (queryAlbum != null) {
                queryAlbum.close();
            }
            if (queryAlbumsByArtistId != null) {
                queryAlbumsByArtistId.close();
            }
            if (updateArtistName != null) {
                updateArtistName.close();
            }
            if (querySongsByAlbumId != null) {
                querySongsByAlbumId.close();
            }
            if (queryAlbumById != null) {
                queryAlbumById.close();
            }
            if (deleteSong != null) {
                deleteSong.close();
            }
            if (deleteAlbum != null) {
                deleteAlbum.close();
            }
            if (deleteArtist != null) {
                deleteArtist.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    public List<Artist> queryArtists(int sortOrder) {

        StringBuilder sb = new StringBuilder("SELECT * FROM ");
        sb.append(TABLE_ARTISTS);
        if (sortOrder != ORDER_BY_NONE) {
            sb.append(" ORDER BY ");
            sb.append(COLUMN_ARTIST_NAME);
            sb.append(" COLLATE NOCASE ");
            if (sortOrder == ORDER_BY_DESC) {
                sb.append("DESC");
            } else {
                sb.append("ASC");
            }
        }

        try (Statement statement = conn.createStatement();
             ResultSet results = statement.executeQuery(sb.toString())) {

            List<Artist> artists = new ArrayList<>();
            while (results.next()) {
//     this is only for testing progressBar
//                try{
//                    Thread.sleep(20);
//                } catch (InterruptedException e) {
//                    System.out.println("Interrupted: " + e.getMessage());
//                }
                Artist artist = new Artist();
                artist.setId(results.getInt(INDEX_ARTIST_ID));
                artist.setName(results.getString(INDEX_ARTIST_NAME));
                artists.add(artist);
            }
            return artists;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Album> queryAlbumForArtistId(int id) {
        try {
            queryAlbumsByArtistId.setInt(1, id);
            ResultSet results = queryAlbumsByArtistId.executeQuery();

            List<Album> albums = new ArrayList<>();
            while (results.next()) {
                Album album = new Album();
                album.setId(results.getInt(1));
                album.setName(results.getString(2));
                album.setArtistId(id);
                albums.add(album);
            }
            return albums;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    public List<Song> querySongsForAlbumId(int id) {
        try {
            querySongsByAlbumId.setInt(1, id);
            ResultSet results = querySongsByAlbumId.executeQuery();

            List<Song> songs = new ArrayList<>();
            while (results.next()) {
                Song song = new Song();
                song.setTitle(results.getString(INDEX_SONG_TITLE));
                song.setTrack(results.getInt(INDEX_SONG_TRACK));
                song.setAlbumId(id);
                song.setId(results.getInt(INDEX_SONG_ID));
                songs.add(song);
            }
            return songs;
        } catch (SQLException e) {
            System.out.println("Query failed: " + e.getMessage());
            return null;
        }
    }

    private int insertArtist(String name) throws SQLException {
        queryArtist.setString(1, name);
        ResultSet results = queryArtist.executeQuery();
        if (results.next()) {
            return results.getInt(1);
        } else {
            insertIntoArtists.setString(1, name);
            int affectedRows = insertIntoArtists.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert artist!");
            }
            ResultSet generatedKeys = insertIntoArtists.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id for artist");
            }
        }
    }

    private int insertAlbum(String name, int artistId) throws SQLException {
        queryAlbum.setString(1, name);
        ResultSet results = queryAlbum.executeQuery();
        if (results.next()) {
            return results.getInt(1);
        } else {
            insertIntoAlbums.setString(1, name);
            insertIntoAlbums.setInt(2, artistId);
            int affectedRows = insertIntoAlbums.executeUpdate();

            if (affectedRows != 1) {
                throw new SQLException("Couldn't insert album!");
            }
            ResultSet generatedKeys = insertIntoAlbums.getGeneratedKeys();
            if (generatedKeys.next()) {
                return generatedKeys.getInt(1);
            } else {
                throw new SQLException("Couldn't get _id for album");
            }
        }
    }

    public boolean insertSong(NewSong newSong) {

        String title = newSong.getTitle();
        String artist = newSong.getArtist();
        String album = newSong.getAlbum();
        int track = newSong.getTrack();

        try {
            conn.setAutoCommit(false);

            int artistId = insertArtist(artist);
            int albumID = insertAlbum(album, artistId);
            insertIntoSongs.setInt(1, track);
            insertIntoSongs.setString(2, title);
            insertIntoSongs.setInt(3, albumID);
            int affectedRows = insertIntoSongs.executeUpdate();

            if (affectedRows == 1) {
                conn.commit();
                return true;
            } else {
                throw new SQLException("The song insert failed");
            }

        } catch (Exception e) {
            System.out.println("Insert song exception: " + e.getMessage());
            try {
                System.out.println("Performing rollback");
                conn.rollback();
                return false;
            } catch (SQLException e2) {
                System.out.println("oh boy! this is really bad" + e2.getMessage());
                return false;
            }
        } finally {
            try {
                System.out.println("Resetting default commit behaviour");
                conn.setAutoCommit(true);
            } catch (SQLException e3) {
                System.out.println("couldn't reset auto-commit!" + e3.getMessage());
            }
        }
    }

    public boolean updateArtistName(int id, String newName) {
        try {
            updateArtistName.setString(1, newName);
            updateArtistName.setInt(2, id);
            int affectedRecords = updateArtistName.executeUpdate();

            return affectedRecords == 1;

        } catch (SQLException e) {
            System.out.println("updating artist failed: " + e.getMessage());
            return false;
        }
    }

    public boolean deleteSong(Song song) {
        try {
            int albumId = song.getAlbumId();
            int songId = song.getId();
            deleteSong.setInt(1, songId);
            deleteSong.execute();
            querySongsByAlbumId.setInt(1, albumId);
            ResultSet results = querySongsByAlbumId.executeQuery();
            if (!results.next()) {
                queryAlbumById.setInt(1, albumId);
                ResultSet resultAlbum = queryAlbumById.executeQuery();
                int artistId = resultAlbum.getInt(INDEX_ALBUM_ARTIST);
                deleteAlbum.setInt(1, albumId);
                deleteAlbum.execute();
                queryAlbumsByArtistId.setInt(1, artistId);
                ResultSet resultArtist = queryAlbumsByArtistId.executeQuery();
                if (!resultArtist.next()) {
                    deleteArtist.setInt(1, artistId);
                    deleteArtist.execute();
                }
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}

