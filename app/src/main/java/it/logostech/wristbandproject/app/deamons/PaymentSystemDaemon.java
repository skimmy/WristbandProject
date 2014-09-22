package it.logostech.wristbandproject.app.deamons;

import android.nfc.tech.IsoDep;

import it.logostech.wristbandproject.app.model.TagModel;

/**
 * This class should be used as a daemon where
 * <p/>
 * Created by Michele Schimd on 22/09/2014.
 */
public class PaymentSystemDaemon {

    private static TagModel currentTag = null;

    /**
     * This method is called when a tag is discovered (it can be either a new
     * one, or one already known). Since the communication on NFC channels are
     * usually <i>half duplex</i>, this method automatically ignores a tag when
     * there is one already in process. The method also indicated whether the
     * passed tag has been ignored (return value <code>false</code>) or it has
     * been set as the new current tag (return value <code>false</code>).
     *
     * @param tag the discovered tag
     * @return <code>true</code> if the passed tag is the new current tag and
     * <code>false</code> otherwise
     *
     */
    public static boolean tagDiscovered(TagModel tag) {
        // Check if the Id corresponds with the id that is actually in an active
        // NFC communication (if any).
        if (currentTag != null && currentTag.equals(tag)) {
            //
        } else {
            // new tag...
            if (currentTag == null) {
                currentTag = tag;
                simpleNfcCommunication(currentTag);
               // ...mismatch
            } else {
                // there already exist a tag so we discard the current one
                return false;
            }
        }
        return true;
    }

    /**
     * Resets the current tag if and only if the actual current tag has the same
     * id as the passed one.
     *
     * @param tag the tag to be reset
     * @return <code>true</code> if and only if the current tag has been reset
     */
    public static boolean discardTag(TagModel tag) {
        if (currentTag.equals(tag)) {
            currentTag = null;
            return  true;
        }
        return false;
    }

    /**
     * Performs a NFC communication payment protocol with the tag described by
     * the passed parameter.
     *
     * @param tagModel the tag identifying the NFC counterpart
     */
    private static void simpleNfcCommunication(TagModel tagModel) {
        // try to set up a communication channel
        if (tagModel.getNfcTag() == null) {
            throw new IllegalArgumentException("Nfc communication needs low level Tag object, found null");
        }
        IsoDep isoDep = IsoDep.get(tagModel.getNfcTag());

        if (isoDep != null) {

        } else {
            // TODO: Do something when the channel can not be established
        }
    }
}
