package it.logostech.wristbandproject.app;

import android.nfc.NfcAdapter;
import android.nfc.Tag;
import android.os.Environment;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.math.BigInteger;


public class MainActivity extends ActionBarActivity {

    private TagModel selectedTag = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button saveButton = (Button) findViewById(R.id.saveTagIdButton);
//        saveButton.setOnClickListener();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedTag != null) {
                    (new SaveTagDialogFragment(selectedTag)).show(getFragmentManager(), "AA");
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String p = Environment.getDataDirectory().getAbsolutePath();
        if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(getIntent().getAction())) {
            Tag t = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Log.v("MainActivity", "Tag Discovered");
            for (String tech : t.getTechList()) {
                Log.v("MainActivity", " -- " + tech);
            }
            TagModel tag = new TagModel(t.getId());
            TextView tw = (TextView) findViewById(R.id.idTextView);
            tw.setText(new BigInteger(tag.getId()).toString());
            TagUtility.saveTagToFile(tag, "test", this);
            TagModel loadedTag = TagUtility.loadTagFromFile("test", this);
            String tmp = (new BigInteger(loadedTag.getId()).toString());
            TextView loadedTW = (TextView) findViewById(R.id.loadTagTextView);
            loadedTW.setText(tmp);
        }
        if (NfcAdapter.ACTION_TECH_DISCOVERED.equals(getIntent().getAction())) {
            Tag t = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Log.v("MainActivity", "Tech Discovered");
            for (String tech : t.getTechList()) {
                Log.v("MainActivity", " -- " + tech);
            }
            for (String tech : t.getTechList()) {
                Log.v("MainActivity", " -- " + tech);
            }
        }

        if (NfcAdapter.ACTION_NDEF_DISCOVERED.equals(getIntent().getAction())) {
            Tag t = getIntent().getParcelableExtra(NfcAdapter.EXTRA_TAG);
            Log.v("MainActivity", "NDEF Discovered");
            for (String tech : t.getTechList()) {
                Log.v("MainActivity", " -- " + tech);
            }
        }

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
