package it.logostech.wristbandproject.app.model;

import android.nfc.Tag;

import java.math.BigInteger;
import java.util.Arrays;

import it.logostech.wristbandproject.app.util.TypeUtil;

/**
 * This is the model class for a NFC tag. It describes the content of a tag that
 * is useful to the application.
 *
 * Created by Michele Schimd on 04/07/2014.
 */
public class TagModel {

    // uniquely identify the tag within the system, it is strongly recommended
    // not to be a null reference.
    private byte[] id;
    // an (optional but recommended) name for the tag
    private String name = "";
    // in some cases it may help to keep the actual tag returned by the APIs
    private Tag nfcTag;

    /**
     * Creates a {@link it.logostech.wristbandproject.app.model.TagModel} object
     * from a {@link android.nfc.Tag} one, useful when interacting with the low
     * level NFC APIs
     *
     * @param nfcTag the <i>raw</i> NFC tag object
     */
    public TagModel(Tag nfcTag) {
        // retrieve the id and copy to the local variable
        this.id = Arrays.copyOf(nfcTag.getId(), nfcTag.getId().length);
        this.nfcTag = nfcTag;
    }

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
        return TypeUtil.byteArrayToHexString(this.id);
    }

    public  String toString() {
        return this.getStringId();
    }

    public Tag getNfcTag() {
        return nfcTag;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof  TagModel) {
            return ((TagModel) o).getStringId().equals(this.getStringId());
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.getStringId().hashCode();
    }
}
