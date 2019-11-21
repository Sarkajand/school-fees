package sample.model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataSource {

    public static final String DB_NAME = "schoolFees.db";
    public static final String CONNECTION_STRING = "jdbc:sqlite:D:\\Java\\MyProjects2\\" + DB_NAME;


    public static final String TABLE_CLASSES = "classes";
    public static final String COLUMN_CLASSES_ID = "_id";
    public static final String COLUMN_CLASSES_CLASS_NAME = "class_name";
    public static final String COLUMN_CLASSES_STAGE = "stage";
    public static final String COLUMN_CLASSES_STARTING_YEAR = "starting_year";
    public static final int INDEX_CLASSES_ID = 1;
    public static final int INDEX_CLASSES_NAME = 4;
    public static final int INDEX_CLASSES_STAGE = 3;
    public static final int INDEX_CLASSES_STARTING_YEAR = 2;

    public static final String TABLE_STUDENTS = "students";
    public static final String COLUMN_STUDENTS_VS = "VS";
    public static final String COLUMN_STUDENTS_LAST_NAME = "last_name";
    public static final String COLUMN_STUDENTS_FIRST_NAME = "first_name";
    public static final String COLUMN_STUDENTS_CLASS = "class";
    public static final String COLUMN_STUDENTS_FEES = "fees";
    public static final String COLUMN_STUDENTS_MOTHER_PHONE = "mother_phone";
    public static final String COLUMN_STUDENTS_FATHER_PHONE = "father_phone";
    public static final String COLUMN_STUDENTS_MOTHER_EMAIL = "mother_email";
    public static final String COLUMN_STUDENTS_FATHER_EMAIL = "father_email";
    public static final String COLUMN_STUDENTS_NOTES = "notes";
    public static final int INDEX_STUDENT_VS = 1;
    public static final int INDEX_STUDENT_LAST_NAME = 2;
    public static final int INDEX_STUDENT_FIRST_NAME = 3;
    public static final int INDEX_STUDENT_CLASS = 4;
    public static final int INDEX_STUDENT_FEES = 5;
    public static final int INDEX_STUDENT_MOTHER_PHONE = 6;
    public static final int INDEX_STUDENT_FATHER_PHONE = 7;
    public static final int INDEX_STUDENT_MOTHER_EMAIL = 8;
    public static final int INDEX_STUDENT_FATHER_EMAIL = 9;
    public static final int INDEX_STUDENT_NOTES = 10;

    public static final String TABLE_BANK_STATEMENT = "bank_statement";
    public static final String COLUMN_BANK_ID = "_id";
    public static final String COLUMN_BANK_DATE = "date";
    public static final String COLUMN_BANK_NAME = "name";
    public static final int INDEX_BANK_ID = 1;
    public static final int INDEX_BANK_DATE = 2;
    public static final int INDEX_BANK_NAME = 3;

    public static final String TABLE_TRANSACTIONS = "transactions";
    public static final String COLUMN_TRANSACTIONS_ID = "_id";
    public static final String COLUMN_TRANSACTIONS_BANK_STATEMENT = "bank_statement";
    public static final String COLUMN_TRANSACTIONS_VS = "VS";
    public static final String COLUMN_TRANSACTIONS_AMOUNT = "amount";
    public static final String COLUMN_TRANSACTIONS_PAYMENT_METHOD = "payment_method";
    public static final String COLUMN_TRANSACTIONS_NOTES = "notes";
    public static final int INDEX_TRANSACTION_ID = 1;
    public static final int INDEX_TRANSACTION_BANK_STATEMENT = 2;
    public static final int INDEX_TRANSACTION_VS = 3;
    public static final int INDEX_TRANSACTION_AMOUNT = 4;
    public static final int INDEX_TRANSACTION_PAYMENT_METHOD = 5;
    public static final int INDEX_TRANSACTION_NOTES = 6;

    public static final String VIEW_STUDENT_LIST = "students_list";
    public static final String VIEW_STUDENT_LIST_STAGE = "stage";
    public static final String VIEW_STUDENTS_LIST_CLASS = "class_name";
    public static final int INDEX_STUDENT_LIST_CLASS_NAME = 1;
    public static final int INDEX_STUDENT_LIST_LAST_NAME = 2;
    public static final int INDEX_STUDENT_LIST_FIRST_NAME = 3;
    public static final int INDEX_STUDENT_LIST_FEES = 4;
    public static final int INDEX_STUDENT_LIST_VS = 5;
    public static final int INDEX_STUDENT_LIST_MOTHER_PHONE = 6;
    public static final int INDEX_STUDENT_LIST_FATHER_PHONE = 7;
    public static final int INDEX_STUDENT_LIST_MOTHER_EMAIL = 8;
    public static final int INDEX_STUDENT_LIST_FATHER_EMAIL = 9;
    public static final int INDEX_STUDENT_LIST_NOTES = 10;
    public static final int INDEX_STUDENT_LIST_STAGE = 11;

    public static final String QUERY_STUDENTS =
            "SELECT * FROM " + VIEW_STUDENT_LIST;
    public static final String QUERY_STUDENTS_BY_SCHOOL_STAGE =
            "SELECT * FROM " + VIEW_STUDENT_LIST + " WHERE " + VIEW_STUDENT_LIST_STAGE + " = ?";
    public static final String QUERY_STUDENTS_BY_CLASS =
            "SELECT * FROM " + VIEW_STUDENT_LIST + " WHERE " + VIEW_STUDENTS_LIST_CLASS + " = ?";
    public static final String QUERY_CLASSES =
            "SELECT " + COLUMN_CLASSES_CLASS_NAME + " FROM " + TABLE_CLASSES;
    public static final String FIND_CLASS_ID_BY_CLASS_NAME =
            "SELECT " + COLUMN_CLASSES_ID + " FROM " + TABLE_CLASSES + " WHERE " + COLUMN_CLASSES_CLASS_NAME + " = ?";
    public static final String INSERT_STUDENT =
            "INSERT INTO " + TABLE_STUDENTS + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    public static final String DELETE_STUDENT =
            "DELETE FROM " + TABLE_STUDENTS + " WHERE " + COLUMN_STUDENTS_VS + " = ?";


    //
//    public static final String INSERT_ARTIST =
//            "INSERT INTO " + TABLE_ARTISTS + " (" + COLUMN_ARTIST_NAME + ") VALUES (?)";
//    public static final String INSERT_ALBUM =
//            "INSERT INTO " + TABLE_ALBUMS + " (" + COLUMN_ALBUM_NAME + ", " + COLUMN_ALBUM_ARTIST + ") VALUES (?, ?)";
//    public static final String INSERT_SONG =
//            "INSERT INTO " + TABLE_SONGS + " (" + COLUMN_SONG_TRACK + ", " + COLUMN_SONG_TITLE + ", " + COLUMN_SONG_ALBUM +
//                    ") VALUES (?, ?, ?)";
//    public static final String QUERY_ARTIST =
//            "SELECT " + COLUMN_ARTIST_ID + " FROM " + TABLE_ARTISTS + " WHERE " + COLUMN_ARTIST_NAME +
//                    " = ?";
//    public static final String QUERY_ALBUM =
//            "SELECT " + COLUMN_ALBUM_ID + " FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_NAME +
//                    " = ?";
//
//    public static final String QUERY_ALBUMS_BY_ARTIST_ID = "SELECT * FROM " + TABLE_ALBUMS +
//            " WHERE " + COLUMN_ALBUM_ARTIST + " = ? ORDER BY " + COLUMN_ALBUM_NAME + " COLLATE NOCASE";
//
//    public static final String UPDATE_ARTIST_NAME =
//            "UPDATE " + TABLE_ARTISTS + " SET " + COLUMN_ARTIST_NAME + " = ? WHERE " + COLUMN_ARTIST_ID + " = ?";
//
//    public static final String QUERY_SONGS_BY_ALBUM_ID =
//            "SELECT * FROM " + TABLE_SONGS +
//                    " WHERE " + COLUMN_SONG_ALBUM + " = ?";
//
//    public static final String QUERY_ALBUM_BY_ID =
//            "SELECT * FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_ID + " = ?";
//
//    public static final String DELETE_SONG =
//            "DELETE FROM " + TABLE_SONGS + " WHERE " + COLUMN_SONG_ID + " = ?";
//
//    public static final String DELETE_ALBUM =
//            "DELETE FROM " + TABLE_ALBUMS + " WHERE " + COLUMN_ALBUM_ID + " = ?";
//
//    public static final String DELETE_ARTIST =
//
//
    private static DataSource instance = new DataSource();
    private Connection conn;
    private PreparedStatement queryStudents;
    private PreparedStatement queryStudentsBySchoolStage;
    private PreparedStatement queryStudentsByClass;
    private PreparedStatement queryClasses;
    private PreparedStatement findClassIdByClassName;
    private PreparedStatement insertStudent;
    private PreparedStatement deleteStudent;


//    private PreparedStatement insertIntoArtists;
//    private PreparedStatement insertIntoAlbums;
//    private PreparedStatement insertIntoSongs;
//    private PreparedStatement queryArtist;
//    private PreparedStatement queryAlbum;
//    private PreparedStatement queryAlbumsByArtistId;
//    private PreparedStatement updateArtistName;
//    private PreparedStatement querySongsByAlbumId;
//    private PreparedStatement queryAlbumById;
//    private PreparedStatement deleteSong;
//    private PreparedStatement deleteAlbum;
//    private PreparedStatement deleteArtist;

    private DataSource() {

    }

    public static DataSource getInstance() {
        return instance;
    }


    public boolean open() {
        try {
            conn = DriverManager.getConnection(CONNECTION_STRING);
            queryStudents = conn.prepareStatement(QUERY_STUDENTS);
            queryStudentsBySchoolStage = conn.prepareStatement(QUERY_STUDENTS_BY_SCHOOL_STAGE);
            queryStudentsByClass = conn.prepareStatement(QUERY_STUDENTS_BY_CLASS);
            queryClasses = conn.prepareStatement(QUERY_CLASSES);
            findClassIdByClassName = conn.prepareStatement(FIND_CLASS_ID_BY_CLASS_NAME);
            insertStudent = conn.prepareStatement(INSERT_STUDENT);
            deleteStudent = conn.prepareStatement(DELETE_STUDENT);

//            insertIntoArtists = conn.prepareStatement(INSERT_ARTIST, Statement.RETURN_GENERATED_KEYS);
//            insertIntoAlbums = conn.prepareStatement(INSERT_ALBUM, Statement.RETURN_GENERATED_KEYS);
//            insertIntoSongs = conn.prepareStatement(INSERT_SONG);
//            queryArtist = conn.prepareStatement(QUERY_ARTIST);
//            queryAlbum = conn.prepareStatement(QUERY_ALBUM);
//            queryAlbumsByArtistId = conn.prepareStatement(QUERY_ALBUMS_BY_ARTIST_ID);
//            updateArtistName = conn.prepareStatement(UPDATE_ARTIST_NAME);
//            querySongsByAlbumId = conn.prepareStatement(QUERY_SONGS_BY_ALBUM_ID);
//            queryAlbumById = conn.prepareStatement(QUERY_ALBUM_BY_ID);
//            deleteSong = conn.prepareStatement(DELETE_SONG);
//            deleteAlbum = conn.prepareStatement(DELETE_ALBUM);
//            deleteArtist = conn.prepareStatement(DELETE_ARTIST);

            return true;
        } catch (SQLException e) {
            System.out.println("Couldn't connect to database: " + e.getMessage());
            return false;
        }
    }

    public void close() {
        try {
//            if (insertIntoArtists != null) {
//                insertIntoArtists.close();
//            }
//            if (insertIntoAlbums != null) {
//                insertIntoAlbums.close();
//            }
//            if (insertIntoSongs != null) {
//                insertIntoSongs.close();
//            }
//            if (queryArtist != null) {
//                queryArtist.close();
//            }
//            if (queryAlbum != null) {
//                queryAlbum.close();
//            }
//            if (queryAlbumsByArtistId != null) {
//                queryAlbumsByArtistId.close();
//            }
//            if (updateArtistName != null) {
//                updateArtistName.close();
//            }
//            if (querySongsByAlbumId != null) {
//                querySongsByAlbumId.close();
//            }
//            if (queryAlbumById != null) {
//                queryAlbumById.close();
//            }
//            if (deleteSong != null) {
//                deleteSong.close();
//            }
//            if (deleteAlbum != null) {
//                deleteAlbum.close();
//            }
//            if (deleteArtist != null) {
//                deleteArtist.close();
//            }

            if (queryStudents != null) {
                queryStudents.close();
            }
            if (queryStudentsBySchoolStage != null) {
                queryStudentsBySchoolStage.close();
            }
            if (queryStudentsByClass != null) {
                queryStudentsByClass.close();
            }
            if (findClassIdByClassName != null) {
                findClassIdByClassName.close();
            }
            if (insertStudent != null) {
                insertStudent.close();
            }
            if (deleteStudent != null) {
                deleteStudent.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (SQLException e) {
            System.out.println("Couldn't close connection: " + e.getMessage());
        }
    }

    private List<Student> setStudents(ResultSet results) {
        if (results == null) {
            System.out.println("no data for students");
            return null;
        } else {
            try {
                List<Student> students = new ArrayList<>();
                while (results.next()) {
                    Student student = new Student();
                    student.setClassName(results.getString(INDEX_STUDENT_LIST_CLASS_NAME));
                    student.setLastName(results.getString(INDEX_STUDENT_LIST_LAST_NAME));
                    student.setFirstName(results.getString(INDEX_STUDENT_LIST_FIRST_NAME));
                    student.setFees(results.getDouble(INDEX_STUDENT_LIST_FEES));
                    student.setVS(results.getInt(INDEX_STUDENT_LIST_VS));
                    student.setMotherPhone(results.getString(INDEX_STUDENT_LIST_MOTHER_PHONE));
                    student.setFatherPhone(results.getString(INDEX_STUDENT_LIST_FATHER_PHONE));
                    student.setMotherEmail(results.getString(INDEX_STUDENT_LIST_MOTHER_EMAIL));
                    student.setFatherEmail(results.getString(INDEX_STUDENT_LIST_FATHER_EMAIL));
                    student.setNotes(results.getString(INDEX_STUDENT_LIST_NOTES));
                    student.setSchoolStage(results.getString(INDEX_STUDENT_LIST_STAGE));

                    students.add(student);
                }
                return students;
            } catch (SQLException e) {
                System.out.println("Setting data for students failed: " + e.getMessage());
                e.printStackTrace();
                return null;
            }
        }
    }

    public List<Student> queryStudent() {
        try {
            ResultSet results = queryStudents.executeQuery();
            return setStudents(results);
        } catch (SQLException e) {
            System.out.println("Query students failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Student> queryStudentsBySchoolStage(String schoolStage) {
        try {
            queryStudentsBySchoolStage.setString(1, schoolStage);
            ResultSet results = queryStudentsBySchoolStage.executeQuery();
            return setStudents(results);

        } catch (SQLException e) {
            System.out.println("Query students by school stage failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<Student> queryStudentsByClass(String className) {
        try {
            queryStudentsByClass.setString(1, className);
            ResultSet results = queryStudentsByClass.executeQuery();
            return setStudents(results);
        } catch (SQLException e) {
            System.out.println("Query students by class failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public List<String> listClasses() {
        try {
            ResultSet results = queryClasses.executeQuery();
            List<String> classes = new ArrayList<>();
            while (results.next()) {
                classes.add(results.getString(1));
            }
            return classes;
        } catch (SQLException e) {
            System.out.println("Query classes failed: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    public int findClassIdByClassName(String className) {
        try {
            findClassIdByClassName.setString(1, className);
            ResultSet results = findClassIdByClassName.executeQuery();
            return results.getInt(1);
        } catch (SQLException e) {
            System.out.println("Finding class failed: " + e.getMessage());
            e.printStackTrace();
            return -1;
        }
    }

    public boolean insertStudent(Student student, int classId) {
        try {
            insertStudent.setInt(INDEX_STUDENT_VS, student.getVS());
            insertStudent.setString(INDEX_STUDENT_LAST_NAME, student.getLastName());
            insertStudent.setString(INDEX_STUDENT_FIRST_NAME, student.getFirstName());
            insertStudent.setInt(INDEX_STUDENT_CLASS, classId);
            insertStudent.setDouble(INDEX_STUDENT_FEES, student.getFees());
            insertStudent.setString(INDEX_STUDENT_MOTHER_PHONE, student.getMotherPhone());
            insertStudent.setString(INDEX_STUDENT_FATHER_PHONE, student.getFatherPhone());
            insertStudent.setString(INDEX_STUDENT_MOTHER_EMAIL, student.getMotherEmail());
            insertStudent.setString(INDEX_STUDENT_FATHER_EMAIL, student.getFatherEmail());
            insertStudent.setString(INDEX_STUDENT_NOTES, student.getNotes());

            int affectedRecords = insertStudent.executeUpdate();
            if (affectedRecords == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Inserting student failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteStudent(int VS) {
        try {
            deleteStudent.setInt(1, VS);
            int affectedRecords = deleteStudent.executeUpdate();
            if (affectedRecords == 1) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {
            System.out.println("Deleting student failed: " + e.getMessage());
            e.printStackTrace();
            return false;
        }

    }







//    public List<Artist> queryArtists(int sortOrder) {
//
//        StringBuilder sb = new StringBuilder("SELECT * FROM ");
//        sb.append(TABLE_ARTISTS);
//        if (sortOrder != ORDER_BY_NONE) {
//            sb.append(" ORDER BY ");
//            sb.append(COLUMN_ARTIST_NAME);
//            sb.append(" COLLATE NOCASE ");
//            if (sortOrder == ORDER_BY_DESC) {
//                sb.append("DESC");
//            } else {
//                sb.append("ASC");
//            }
//        }
//
//        try (Statement statement = conn.createStatement();
//             ResultSet results = statement.executeQuery(sb.toString())) {
//
//            List<Artist> artists = new ArrayList<>();
//            while (results.next()) {
////     this is only for testing progressBar
////                try{
////                    Thread.sleep(20);
////                } catch (InterruptedException e) {
////                    System.out.println("Interrupted: " + e.getMessage());
////                }
//                Artist artist = new Artist();
//                artist.setId(results.getInt(INDEX_ARTIST_ID));
//                artist.setName(results.getString(INDEX_ARTIST_NAME));
//                artists.add(artist);
//            }
//            return artists;
//        } catch (SQLException e) {
//            System.out.println("Query failed: " + e.getMessage());
//            e.printStackTrace();
//            return null;
//        }
//    }
//
//    public List<Album> queryAlbumForArtistId(int id) {
//        try {
//            queryAlbumsByArtistId.setInt(1, id);
//            ResultSet results = queryAlbumsByArtistId.executeQuery();
//
//            List<Album> albums = new ArrayList<>();
//            while (results.next()) {
//                Album album = new Album();
//                album.setId(results.getInt(1));
//                album.setName(results.getString(2));
//                album.setArtistId(id);
//                albums.add(album);
//            }
//            return albums;
//        } catch (SQLException e) {
//            System.out.println("Query failed: " + e.getMessage());
//            return null;
//        }
//    }
//
//    public List<Song> querySongsForAlbumId(int id) {
//        try {
//            querySongsByAlbumId.setInt(1, id);
//            ResultSet results = querySongsByAlbumId.executeQuery();
//
//            List<Song> songs = new ArrayList<>();
//            while (results.next()) {
//                Song song = new Song();
//                song.setTitle(results.getString(INDEX_SONG_TITLE));
//                song.setTrack(results.getInt(INDEX_SONG_TRACK));
//                song.setAlbumId(id);
//                song.setId(results.getInt(INDEX_SONG_ID));
//                songs.add(song);
//            }
//            return songs;
//        } catch (SQLException e) {
//            System.out.println("Query failed: " + e.getMessage());
//            return null;
//        }
//    }
//
//    private int insertArtist(String name) throws SQLException {
//        queryArtist.setString(1, name);
//        ResultSet results = queryArtist.executeQuery();
//        if (results.next()) {
//            return results.getInt(1);
//        } else {
//            insertIntoArtists.setString(1, name);
//            int affectedRows = insertIntoArtists.executeUpdate();
//
//            if (affectedRows != 1) {
//                throw new SQLException("Couldn't insert artist!");
//            }
//            ResultSet generatedKeys = insertIntoArtists.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                return generatedKeys.getInt(1);
//            } else {
//                throw new SQLException("Couldn't get _id for artist");
//            }
//        }
//    }
//
//    private int insertAlbum(String name, int artistId) throws SQLException {
//        queryAlbum.setString(1, name);
//        ResultSet results = queryAlbum.executeQuery();
//        if (results.next()) {
//            return results.getInt(1);
//        } else {
//            insertIntoAlbums.setString(1, name);
//            insertIntoAlbums.setInt(2, artistId);
//            int affectedRows = insertIntoAlbums.executeUpdate();
//
//            if (affectedRows != 1) {
//                throw new SQLException("Couldn't insert album!");
//            }
//            ResultSet generatedKeys = insertIntoAlbums.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                return generatedKeys.getInt(1);
//            } else {
//                throw new SQLException("Couldn't get _id for album");
//            }
//        }
//    }
//
//    public boolean insertSong(NewSong newSong) {
//
//        String title = newSong.getTitle();
//        String artist = newSong.getArtist();
//        String album = newSong.getAlbum();
//        int track = newSong.getTrack();
//
//        try {
//            conn.setAutoCommit(false);
//
//            int artistId = insertArtist(artist);
//            int albumID = insertAlbum(album, artistId);
//            insertIntoSongs.setInt(1, track);
//            insertIntoSongs.setString(2, title);
//            insertIntoSongs.setInt(3, albumID);
//            int affectedRows = insertIntoSongs.executeUpdate();
//
//            if (affectedRows == 1) {
//                conn.commit();
//                return true;
//            } else {
//                throw new SQLException("The song insert failed");
//            }
//
//        } catch (Exception e) {
//            System.out.println("Insert song exception: " + e.getMessage());
//            try {
//                System.out.println("Performing rollback");
//                conn.rollback();
//                return false;
//            } catch (SQLException e2) {
//                System.out.println("oh boy! this is really bad" + e2.getMessage());
//                return false;
//            }
//        } finally {
//            try {
//                System.out.println("Resetting default commit behaviour");
//                conn.setAutoCommit(true);
//            } catch (SQLException e3) {
//                System.out.println("couldn't reset auto-commit!" + e3.getMessage());
//            }
//        }
//    }
//
//    public boolean updateArtistName(int id, String newName) {
//        try {
//            updateArtistName.setString(1, newName);
//            updateArtistName.setInt(2, id);
//            int affectedRecords = updateArtistName.executeUpdate();
//
//            return affectedRecords == 1;
//
//        } catch (SQLException e) {
//            System.out.println("updating artist failed: " + e.getMessage());
//            return false;
//        }
//    }
//
//    public boolean deleteSong(Song song) {
//        try {
//            int albumId = song.getAlbumId();
//            int songId = song.getId();
//            deleteSong.setInt(1, songId);
//            deleteSong.execute();
//            querySongsByAlbumId.setInt(1, albumId);
//            ResultSet results = querySongsByAlbumId.executeQuery();
//            if (!results.next()) {
//                queryAlbumById.setInt(1, albumId);
//                ResultSet resultAlbum = queryAlbumById.executeQuery();
//                int artistId = resultAlbum.getInt(INDEX_ALBUM_ARTIST);
//                deleteAlbum.setInt(1, albumId);
//                deleteAlbum.execute();
//                queryAlbumsByArtistId.setInt(1, artistId);
//                ResultSet resultArtist = queryAlbumsByArtistId.executeQuery();
//                if (!resultArtist.next()) {
//                    deleteArtist.setInt(1, artistId);
//                    deleteArtist.execute();
//                }
//            }
//            return true;
//        } catch (SQLException e) {
//            e.printStackTrace();
//            return false;
//        }
//    }
}

