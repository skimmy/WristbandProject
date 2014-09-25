package it.logostech.wristbandproject.app.deamons;

import android.nfc.tech.IsoDep;

import java.io.IOException;

import it.logostech.wristbandproject.app.model.TagModel;
import it.logostech.wristbandproject.app.model.payment.PaymentProtocolGate;

/**
 * This class should be used as a daemon where
 * <p/>
 * Created by Michele Schimd on 22/09/2014.
 */
public class PaymentGateDaemon {

    // this is the device ID, if it null something went wrong during the
    // initialization of the Nfc part which is supposed to be performed in the
    // onResume of the CardReaderActivity because PaymentGate will not be active
    // if that activity is not in foreground.
    public static String deviceNfcId = null;

    // this is the model of the currently connected tag "device"
    private static TagModel currentTag = null;

    // the payment protocol associated with the current tag
    PaymentProtocolGate payProtocol = null;

    /**
     * This method is called when a tag is discovered (it can be either a new
     * one, or one already known). Since the communication on NFC channels are
     * usually <i>half duplex</i>, this method automatically ignores a tag when
     * there is one already in process. The method also indicated whether the
     * passed tag has been ignored (return value <code>false</code>) or it has
     * been set as the new current tag (return value <code>false</code>).
     *
     * @param tag    the discovered tag
     * @param isoDep
     * @return <code>true</code> if the passed tag is the new current tag and
     * <code>false</code> otherwise
     */
    public static boolean tagDiscovered(TagModel tag, IsoDep isoDep) {
        // Check if the Id corresponds with the id that is actually in an active
        // NFC communication (if any).
        if (currentTag != null && currentTag.equals(tag)) {
            // communication continues...
            // it is hard to decide what to do here!!
        } else {
            // new tag...
            if (currentTag == null) {
                currentTag = tag;
                simpleNfcCommunication(tag, isoDep);
                // ...mismatch
            } else {
                // there already exist a tag so we discard the current one but
                // before we close the Iso-Dep connection to the discarded tag
                try {
                    isoDep.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
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
            return true;
        }
        return false;
    }

    /**
     * Performs a NFC communication payment protocol with the tag described by
     * the passed parameter.
     *
     * @param tagModel the tag identifying the NFC counterpart
     */
    private static void simpleNfcCommunication(TagModel tagModel, IsoDep isoDep) {
        // We have here a connected Iso-Dep channel

    }
}
