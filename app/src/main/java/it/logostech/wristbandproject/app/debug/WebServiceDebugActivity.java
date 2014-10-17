package it.logostech.wristbandproject.app.debug;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.Spinner;

import it.logostech.wristbandproject.app.R;


public class WebServiceDebugActivity extends Activity implements AdapterView.OnItemSelectedListener {

    public static final String TAG = WebServiceDebugActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_service_debug);
        Log.v(TAG, "onCreate");

        // -------------------- SELECT METHOD SPINNER --------------------
        Spinner methodSelectorSpinner = (Spinner) findViewById(R.id.wsDebugSelectMethodSpinner);
        // set the content (statically at the moment)
        methodSelectorSpinner.setAdapter(ArrayAdapter.createFromResource(this,
                R.array.wsMethodsName, android.R.layout.simple_spinner_item));
        // set the listener (this class at the moment)
        methodSelectorSpinner.setOnItemSelectedListener(this);

        ImageButton wsSendRequestButton = (ImageButton) findViewById(R.id.wsSendRequestButton);
        wsSendRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "WS Send Button Clicked");
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.web_service_debug, menu);
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
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        Log.v(TAG, "On Item Selected");
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}
