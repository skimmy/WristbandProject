package it.logostech.wristbandproject.app;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import it.logostech.wristbandproject.app.deamons.PaymentWearDaemon;


public class CardEmulationActivity extends Activity {

    public static final String TAG = CardEmulationActivity.class.getSimpleName();

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
