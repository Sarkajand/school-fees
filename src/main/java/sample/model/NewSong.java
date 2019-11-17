package sample.model;

public class NewSong {

    private String artist;
    private String album;
    private String title;
    private int track;

    public NewSong(String artist, String album, String title, int track) {
        this.artist = artist;
        this.album = album;
        this.title = title;
        this.track = track;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public String getTitle() {
        return title;
    }

    public int getTrack() {
        return track;
    }
}
