package it.logostech.wristbandproject.app.deamons;

import it.logostech.wristbandproject.app.model.TagModel;

/**
 * This class should be used as a daemon where
 * <p/>
 * Created by Michele Schimd on 22/09/2014.
 */
public class PaymentSystemDaemon {

    private static TagModel currentTag = null;

    public static void tagDiscovered(TagModel tag) {
        // Check if the Id corresponds with the id that is actually in an active
        // NFC communication (if any).
        if (currentTag != null && currentTag.equals(tag)) {
            //
        } else {
            // new tag or mismatch
            if (currentTag == null) {
                // there is no current tag so we insert the new one
                currentTag = tag;
            } else {
                // there already exist a tag so we discard the current one
                return;
            }
        }
    }
}
