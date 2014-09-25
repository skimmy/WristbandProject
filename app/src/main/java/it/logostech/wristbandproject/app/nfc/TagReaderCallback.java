package it.logostech.wristbandproject.app.nfc;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.nfc.tech.IsoDep;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.util.Arrays;
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
    // application aid to be used during communications
    private String aid = null;

    // Debug purpse TAG string.
    // WARNING: this TAG has nothing to do with the NFC tag concept
    private static final String TAG = TagReaderCallback.class.getSimpleName();

    public TagReaderCallback(String aid) {
        this.aid = aid;
    }

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

        // start the communication protocol (this part is almost a cut-and-paste
        // from the android card reader sample)
        // TODO: should this go inside the daemon?
        IsoDep isoDep = IsoDep.get(tag);
        if (tag != null) {
            // First we need to send the card the AID
            try {
                isoDep.connect();
                Log.v(TAG, "Sending SELECT AID");
                byte[] command = NfcUtil.buildSelectApdu(this.aid);
                byte[] result = isoDep.transceive(command);
                // check if the AID has been successfully selected
                int resultLength = result.length;
                byte[] statusWord = {result[resultLength - 2], result[resultLength - 1]};
                if (Arrays.equals(NfcUtil.SELECT_OK_SW, statusWord)) {
                    Log.v(TAG, "AID " + this.aid + " selected");
                    byte[] payload = Arrays.copyOf(result, resultLength - 2);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void setCardReaderHandler(Handler cardReaderHandler) {
        this.cardReaderHandler = cardReaderHandler;
    }
}
