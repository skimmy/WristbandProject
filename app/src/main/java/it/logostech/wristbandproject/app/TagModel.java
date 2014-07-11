package it.logostech.wristbandproject.app;

/**
 * Created by michele.schimd on 04/07/2014.
 */
public class TagModel {
    private byte[] id;

    public TagModel(byte[] id) {
        this.id = id;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }
}
