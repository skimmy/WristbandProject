package it.logostech.wristbandproject.app.nfc;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.util.Log;

import it.logostech.wristbandproject.app.deamons.PaymentSystemDaemon;
import it.logostech.wristbandproject.app.model.TagModel;
import it.logostech.wristbandproject.app.util.TypeUtil;

/**
 * This is the callback invoked while the CardReader activity is in
 * foreground, it also receives <i>tag discovered</i> notifications
 * when tha activity is woke up by the android dispatching system.
 *
 * Created by Michele Schimd on 22/09/2014.
 */
public class TagReaderCallback implements NfcAdapter.ReaderCallback {

    // Debug purpse TAG string.
    // WARNING: this TAG has nothing to do with the NFC tag concept
    private static final String TAG = TagReaderCallback.class.getSimpleName();

    // This method is called when the tag dispatcher routes a tag discovered
    // message while the current activity is in foreground.
    // However it seems that, even when the activity is resumed by the system
    // because of the a tag discovered, than this callback is called
    @Override
    public void onTagDiscovered(Tag tag) {
        Log.v(TAG, "onTagDiscovered (" +
                TypeUtil.byteArrayToHexString(tag.getId()) + ")");

        // from now on the software should only use TagModel object, unless
        // specifically required differently.
        TagModel tagModel = new TagModel(tag);

        // At this point we simply send the tag to the PaymentSystemDaemon so
        // that it can be processed.
        PaymentSystemDaemon.tagDiscovered(tagModel);
    }
}
