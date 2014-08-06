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

import it.logostech.wristbandproject.app.model.TagModel;
import it.logostech.wristbandproject.app.util.DialogResponder;


public class MainActivity extends ActionBarActivity implements DialogResponder {

    private TagModel selectedTag = null;
    private List<TagModel> tagsList;
    private TagModel readTag = null;

    private static final String savedTagFileName = "_saved_tags.dat";

    private ArrayAdapter<TagModel> mAdapter = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button saveButton = (Button) findViewById(R.id.saveTagIdButton);
//        saveButton.setOnClickListener();
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v("MainActivity", "Clicked!");
                if (readTag != null) {
                    selectedTag = readTag;
                }
                if (selectedTag != null) {
                    (new SaveTagDialogFragment(selectedTag)).show(getFragmentManager(), "AA");
                }
            }
        });

        Button wirelessActivityButton = (Button) findViewById(R.id.wirelessActivityButton);
        wirelessActivityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, WirelessActivity.class);
                startActivity(intent);
            }
        });


        this.tagsList = new LinkedList<TagModel>();

        // initialization of the list view
        ListView tagsListView = (ListView) findViewById(R.id.savedTagListView);
        tagsListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Object clicked = adapterView.getItemAtPosition(i);
                Log.v("MainActivity", "onItemClick " + clicked.toString());
                selectedTag = (TagModel) clicked;
            }
        });

        final TextView simulationTextView = (TextView) findViewById(R.id.simulationStateText);

        Button startSimulationButton = (Button) findViewById(R.id.startSimulationButton);
        startSimulationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = "None";
                if (selectedTag != null) {
                    id = selectedTag.getStringId();
                }
                Log.v("Simulation Start Button", "cliked");
                simulationTextView.setText("Simulating on " + id);
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

            this.readTag = tag;
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

        try {
            this.tagsList = this.loadSavedTags();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Log.v("onResume", "Loaded " + this.tagsList.size() + " tags!");

        TagModel[] tagsArray = new TagModel[this.tagsList.size()];
        tagsArray = this.tagsList.toArray(tagsArray);
        ListView tagsListView = (ListView) findViewById(R.id.savedTagListView);
        this.mAdapter = new ArrayAdapter<TagModel>(
                this, android.R.layout.simple_list_item_1, tagsArray);
        tagsListView.setAdapter(mAdapter);

    }

    @Override
    protected void onPause() {
        super.onPause();
        try {
            this.saveTags(this.tagsList);
        } catch (IOException e) {
            e.printStackTrace();
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

    private List<TagModel> loadSavedTags() throws IOException {
        FileInputStream fis = this.openFileInput(savedTagFileName);
        DataInputStream dis = new DataInputStream(fis);
        int tagCount = dis.readInt();
        List<TagModel> tags = new LinkedList<TagModel>();

        for (int i = 0; i < tagCount; ++i) {

            int length = dis.readInt();
            byte[] id = new byte[length];
            dis.read(id);

            int nameArrayLength = dis.readInt();
            byte[] name = new byte[nameArrayLength];
            dis.read(name);
            String tagName = new String(name);

            tags.add(new TagModel(id, tagName));

        }
        dis.close();
        fis.close();

        return tags;
    }

    private void saveTags(List<TagModel> tags) throws IOException {
        FileOutputStream fos = this.openFileOutput(savedTagFileName, MODE_PRIVATE);
        DataOutputStream dos = new DataOutputStream(fos);
        int tagCount = tags.size();
        dos.writeInt(tagCount);
        for (TagModel tag : tags) {
            dos.writeInt(tag.getId().length);
            dos.write(tag.getId());
            dos.writeInt(tag.getName().getBytes().length);
            dos.write(tag.getName().getBytes());
        }
        dos.close();
        fos.close();
    }

    @Override
    public void onDialogPositiveClick(DialogFragment dialog) {
        Log.v("SaveTagDialog", "Sure!!");
        TagModel tagToSave = ((SaveTagDialogFragment) dialog).getTagToSave();
        this.tagsList.add(tagToSave);
    }

    @Override
    public void onDialogNegativeClick(DialogFragment dialog) {
        Log.v("SaveTagDialog", "Naaah!!");
    }
}
