package it.logostech.wristbandproject.app;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import it.logostech.wristbandproject.app.deamons.PaymentAuthDaemon;
import it.logostech.wristbandproject.app.deamons.PaymentGateDaemon;
import it.logostech.wristbandproject.app.model.payment.PaymentDetails;
import it.logostech.wristbandproject.app.nfc.TagReaderCallback;

public class CardReaderActivity extends Activity {

    // TAG string for debug purpose
    private static final String TAG = CardReaderActivity.class.getSimpleName();
    // this callback is called while activity is in foreground
    private TagReaderCallback readerCallback = null;
    // this is the (default) application id for Nfc communications
    private String aid = "F0010203040506";
    private Thread gateDaemonThread = null;
    private Thread authDaemonThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // default stuff
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_reader);

        // ---------- RESET BUTTON ----------
        Button resetButton = (Button) findViewById(R.id.card_reader_reset_button_id);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                PaymentGateDaemon.GATE_DAEMON.reset();
                TextView latestTagView = (TextView) findViewById(R.id.cardReaderLastTagText);
                latestTagView.setText("");

            }
        });

        // create reader callback for NFC (registration will take place during
        // onResume and de-registration during onPause)
        this.readerCallback = new TagReaderCallback(aid);
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


        // Retrieve the current device id and set it on the PaymentGateDaemon
        // TODO Change properly once the ID policy is defined
        PaymentGateDaemon.deviceNfcId = "GATE";
        PaymentAuthDaemon.deviceNfcId = "AUTH";

        // create AUTH and GATE Daemon threads
        this.authDaemonThread = new Thread(PaymentAuthDaemon.AUTH_DAEMON, PaymentAuthDaemon.TAG);
        this.gateDaemonThread = new Thread(PaymentGateDaemon.GATE_DAEMON, PaymentGateDaemon.TAG);
        // link GATE as callback of AUTH
        PaymentAuthDaemon.AUTH_DAEMON.setCallbackDaemon(PaymentGateDaemon.GATE_DAEMON);

        // TODO Here payment details are filled and passed to the daemon
        PaymentGateDaemon.GATE_DAEMON.setPayDetails(
                PaymentDetails.fromProperties("TID", PaymentGateDaemon.deviceNfcId," WEAR",
                        100,PaymentDetails.PURCHASE_TYPE_GENERIC));

        Handler handler = (new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg != null && msg.obj != null) {
                    TextView latestTagView = (TextView) findViewById(R.id.cardReaderLastTagText);
                    latestTagView.setText((String) msg.obj);
                }
            }
        });


        // enables and sets the foreground reader callback
        // this flags are recommended for communication with Android HCE (see
        // API guide "NFC Basics" and enableReaderMode method on API Reference)
        this.readerCallback.setCardReaderHandler(handler);
        int flags = NfcAdapter.FLAG_READER_NFC_A | NfcAdapter.FLAG_READER_SKIP_NDEF_CHECK;
        NfcAdapter.getDefaultAdapter(this).enableReaderMode(this, this.readerCallback, flags, null);

        // start all daemons
        this.authDaemonThread.start();
        this.gateDaemonThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // disable foreground reader callback (not sure if needed!)
        NfcAdapter.getDefaultAdapter(this).disableForegroundDispatch(this);
        // interrupt and destroy daemons
        this.gateDaemonThread.interrupt();
        this.authDaemonThread.interrupt();
        this.gateDaemonThread = null;
        this.authDaemonThread = null;
    }
}
