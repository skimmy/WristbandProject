package it.logostech.wristbandproject.app;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import it.logostech.wristbandproject.app.debug.WebServiceDebugActivity;
import it.logostech.wristbandproject.app.nfc.NfcUtil;
import it.logostech.wristbandproject.app.util.DeviceUtil;
import it.logostech.wristbandproject.app.util.GooglePlayUtil;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    // this indicates whether or not 'HCE' is available on the current device
    // (true by default and set to false if the CardEmulation instantiation fails)
    private boolean hceAvailable = true;
    private boolean playServicesAvail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check (eventually forcing) default service for AID
        String aid = getResources().getString(R.string.nfcAID);
        if (!NfcUtil.isHceAvailable(this)) {
            this.hceAvailable = false;
            Log.i(TAG, "Current device does not support HCE");
        } else {
            // this is just a fallback if (for whatever reason) cannot instantiate
            try {
                if (!NfcUtil.isDefaultServiceForAid(aid, this)) {
                    NfcUtil.setDefaultForAid(aid, this);
                }
            } catch (Exception e) {
                Log.e(TAG, "Cannot instantiate HCE " + e.getMessage());
                this.hceAvailable = false;
            }
        }

        // check availability of Google Play Services (used for location updating via GCM)
        playServicesAvail = GooglePlayUtil.arePlayServicesAvailable(this);
        if (!playServicesAvail) {
            Log.e(TAG, "Play Services not available");
        }


        // Start the wireless activity
        Button wirelessActivityButton = (Button) findViewById(R.id.wirelessActivityButton);
        wirelessActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WirelessActivity.class);
                startActivity(intent);
            }
        });

        // Start the card emulation activity
        Button cardEmulationActivityButton = (Button) findViewById(R.id.cardEmulationActivityButton);
        if (this.hceAvailable) {
            cardEmulationActivityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, CardEmulationActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            // if HCE is not supported the corresponding button is disabled
            // TODO: decide whether keep the 'Card Emulation' button disabled or remove it
            cardEmulationActivityButton.setEnabled(false);
        }

        Button cardReaderActivityButton = (Button) findViewById(R.id.cardReaderActivityButton);
        cardReaderActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, CardReaderActivity.class);
                startActivity(intent);
            }
        });

        Button wsDebugActivityButton = (Button) findViewById(R.id.wsDebugActivityButton);
        wsDebugActivityButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WebServiceDebugActivity.class);
                startActivity(intent);
            }
        });

        Button locationActivityButton = (Button) findViewById(R.id.locationActivityButton);
        if (!DeviceUtil.isLocationAvailable(this)) {
            // Location service is not available on the current device
            locationActivityButton.setEnabled(false);
        } else {
            locationActivityButton.setEnabled(true);
            locationActivityButton.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, LocationActivity.class);
                    startActivity(intent);
                }
            });
        }


    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }
}
