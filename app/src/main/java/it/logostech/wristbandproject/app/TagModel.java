package it.logostech.wristbandproject.app;

import java.math.BigInteger;

/**
 * Created by michele.schimd on 04/07/2014.
 */
public class TagModel {
    private byte[] id;

    private String name = "";

    public TagModel(byte[] id) {
        this.id = id;
    }

    public TagModel(byte[] id, String name) {
        this.id = id;
        this.name = name;
    }

    public byte[] getId() {
        return id;
    }

    public void setId(byte[] id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStringId() {
        String idString  = (new BigInteger(this.id).toString());;
        return idString;
    }
}
