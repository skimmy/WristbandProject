package it.logostech.wristbandproject.app;

import android.location.LocationManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import it.logostech.wristbandproject.app.deamons.LocationUpdateDaemon;
import it.logostech.wristbandproject.app.location.MonitorLocationListener;
import it.logostech.wristbandproject.app.util.DeviceUtil;

/**
 * This activity contains all the controls for the location subsystem
 *
 * @author Michele Schimd
 * @version 1.0
 * @since 02/05/15
 */
public class LocationActivity extends ActionBarActivity {

    public static final String TAG = LocationActivity.class.getSimpleName();

    private boolean monitoring = true;
    private MonitorLocationListener locationListener = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.v(TAG, "onCreate()");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);

        // when the activity is first created, location updates are always enabled
        this.locationListener = new MonitorLocationListener(this);
        this.startLocationUpdates();

        // location toggle button
        final Button startStopLocationButton = (Button) findViewById(R.id.startStopLocationButton);
        startStopLocationButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // updates are currently enabled
                if (monitoring) {
                    stopLocationUpdates();
                    monitoring = false;
                    startStopLocationButton.setText(R.string.start_text);
                } else {
                    startLocationUpdates();
                    LocationUpdateDaemon.LOCATION_DAEMON.locationUpdateStopped(
                            LocationUpdateDaemon.STOP_INFO_USER);
                    monitoring = true;
                    startStopLocationButton.setText(R.string.stop_text);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_location, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void startLocationUpdates() {
        Log.d(TAG, "Starting location updates...");
        // init the location system
        LocationManager locationManager = DeviceUtil.getLocationManager(this);
        // we use the same listener for all the providers
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER, 0, 0, this.locationListener);
        locationManager.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER, 0, 0, this.locationListener);
    }

    private void stopLocationUpdates() {
        Log.d(TAG, "Stopping location updates...");
        LocationManager locationManager = DeviceUtil.getLocationManager(this);
        locationManager.removeUpdates(this.locationListener);
    }
}
