package it.logostech.wristbandproject.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import it.logostech.wristbandproject.app.deamons.PaymentAuthDaemon;
import it.logostech.wristbandproject.app.deamons.PaymentWearDaemon;


public class CardEmulationActivity extends Activity {

    public static final String TAG = CardEmulationActivity.class.getSimpleName();

    private Thread wearDaemonThread = null;
    private Thread authDaemonThread = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_card_emulation);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.v(TAG, "onResume");

        // set the ID tof the PaymentWearDaemon
        // TODO Change properly once the ID policy is defined
        PaymentWearDaemon.deviceNfcId = "WEAR";
        PaymentAuthDaemon.deviceNfcId = "AUTH";

        // create daemon threads
        this.wearDaemonThread = new Thread(PaymentWearDaemon.WEAR_DAEOMN, PaymentWearDaemon.TAG);
        this.authDaemonThread = new Thread(PaymentAuthDaemon.AUTH_DAEMON, PaymentAuthDaemon.TAG);
        // link WEAR as callback of AUTH
        PaymentAuthDaemon.AUTH_DAEMON.setCallbackDaemon(PaymentWearDaemon.WEAR_DAEOMN);

        // start all daemon threads
        this.wearDaemonThread.start();
        this.authDaemonThread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();

        // interrupt daemon threads
        this.wearDaemonThread.interrupt();
        this.wearDaemonThread = null;
        this.authDaemonThread.interrupt();
        this.authDaemonThread = null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.card_emulation, menu);
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
}
