package sample.model;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

public class Song {

    private SimpleIntegerProperty id;
    private SimpleIntegerProperty track;
    private SimpleStringProperty title;
    private SimpleIntegerProperty albumId;

    public Song() {
        this.id = new SimpleIntegerProperty();
        this.track = new SimpleIntegerProperty();
        this.title = new SimpleStringProperty();
        this.albumId = new SimpleIntegerProperty();
    }

    public int getId() {
        return id.get();
    }

    public void setId(int id) {
        this.id.set(id);
    }

    public int getTrack() {
        return track.get();
    }

    public void setTrack(int track) {
        this.track.set(track);
    }

    public String getTitle() {
        return title.get();
    }

    public void setTitle(String title) {
        this.title.set(title);
    }

    public int getAlbumId() {
        return albumId.get();
    }

    public void setAlbumId(int albumId) {
        this.albumId.set(albumId);
    }
}
