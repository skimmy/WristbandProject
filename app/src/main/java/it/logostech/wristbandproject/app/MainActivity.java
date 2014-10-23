package it.logostech.wristbandproject.app;

import android.app.DialogFragment;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.util.LinkedList;
import java.util.List;

import it.logostech.wristbandproject.app.debug.WebServiceDebugActivity;
import it.logostech.wristbandproject.app.model.TagModel;
import it.logostech.wristbandproject.app.nfc.NfcUtil;
import it.logostech.wristbandproject.app.util.DialogResponder;
import it.logostech.wristbandproject.app.util.TagUtility;


public class MainActivity extends ActionBarActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    // this indicates whether or not 'HCE' is available on the current device
    // (true by default and set to false if the CardEmulation instantiation fails)
    private boolean hceAvailable = true;

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
        Button cardEmulationAtivityButton = (Button) findViewById(R.id.cardEmulationActivityButton);
        if (this.hceAvailable) {
            cardEmulationAtivityButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(MainActivity.this, CardEmulationActivity.class);
                    startActivity(intent);
                }
            });
        } else {
            // if HCE is not supported the corresponding button is disabled
            // TODO: decide whether keep the 'Card Emulation' button disabled or remove it
            cardEmulationAtivityButton.setEnabled(false);
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
