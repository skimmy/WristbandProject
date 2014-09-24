package it.logostech.wristbandproject.app.nfc;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.util.Objects;

import it.logostech.wristbandproject.app.deamons.PaymentSystemDaemon;
import it.logostech.wristbandproject.app.model.TagModel;
import it.logostech.wristbandproject.app.util.TypeUtil;

/**
 * This is the callback invoked while the CardReader activity is in
 * foreground, it also receives <i>tag discovered</i> notifications
 * when tha activity is woke up by the android dispatching system.
 * <p/>
 * Created by Michele Schimd on 22/09/2014.de
 */
public class TagReaderCallback implements NfcAdapter.ReaderCallback {

    // this is the preffered way to communicate with the card reader activity
    private Handler cardReaderHandler = null;

    // Debug purpse TAG string.
    // WARNING: this TAG has nothing to do with the NFC tag concept
    private static final String TAG = TagReaderCallback.class.getSimpleName();


    // This method is called when the tag dispatcher routes a tag discovered
    // message while the current activity is in foreground.
    // However it seems that, even when the activity is resumed by the system
    // because of the a tag discovered, than this callback is called
    @Override
    public void onTagDiscovered(Tag tag) {

        // get the human readable ID, put it in the log and on the UI
        String discoveredTagId = TypeUtil.byteArrayToHexString(tag.getId());
        Log.v(TAG, "onTagDiscovered (" + discoveredTagId + ")");
        if (this.cardReaderHandler != null) {
            Message msg = new Message();
            msg.obj = discoveredTagId;
            this.cardReaderHandler.sendMessage(msg);
        }

        // from now on the software should only use TagModel object, unless
        // specifically required differently.
        TagModel tagModel = new TagModel(tag);

        // At this point we simply send the tag to the PaymentSystemDaemon so
        // that it can be processed.
        PaymentSystemDaemon.tagDiscovered(tagModel);
    }

    public void setCardReaderHandler(Handler cardReaderHandler) {
        this.cardReaderHandler = cardReaderHandler;
    }
}
