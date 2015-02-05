package it.logostech.wristbandproject.app.location;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.util.Log;

import it.logostech.wristbandproject.app.deamons.LocationUpdateDaemon;

/**
 * Implementation of <code>LocationListener</code> used to monitor position of
 * the wristband.
 *
 * @author Michele Schimd
 * @version 1.0
 * @since 02/05/15
 */
public class MonitorLocationListener implements LocationListener {

    public static final String TAG = MonitorLocationListener.class.getSimpleName();

    private Context context;

    public MonitorLocationListener(Context ctx) {
        this.context = ctx;
    }

    @Override
    public void onLocationChanged(Location location) {
        Log.d(TAG, "New Location " + location.toString());
        LocationUpdateDaemon.LOCATION_DAEMON.sendLocation(location);
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
