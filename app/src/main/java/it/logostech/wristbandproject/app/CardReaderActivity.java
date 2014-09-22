package it.logostech.wristbandproject.app;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import it.logostech.wristbandproject.app.nfc.TagReaderCallback;

public class CardReaderActivity extends Activity {

    // TAG string for debug purpose
    private static final String TAG = CardReaderActivity.class.getSimpleName();
    // this callback is called while activity is in foreground
    TagReaderCallback readerCallback = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // default stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_reader);

        // create reader callback for NFC (registration will take place during
        // onResume and de-registration during onPause)
        this.readerCallback = new TagReaderCallback();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.card_reader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        // Tag dispatching will eventually resume this activity, so this is a
        // good place to check what NFC subsystem has discovered.
        super.onResume();
        Log.v(TAG, "onResume");
        // Note that the information about discovered tag(s) are contained in
        // the intent used to resume the activity.
        String action = getIntent().getAction();

        // enables and sets the foreground reader callback
        // this flags are recommended for communication with Android HCE (see
        // API guide "NFC Basics" and enableReaderMode method on API Reference)
        int flags = NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
        NfcAdapter.getDefaultAdapter(this).enableReaderMode(this, this.readerCallback, flags, null);
    }

    @Override
    protected void onPause() {
        super.onPause();

        // disable foreground reader callback (not sure if needed!)
        NfcAdapter.getDefaultAdapter(this).disableForegroundDispatch(this);
    }
}
