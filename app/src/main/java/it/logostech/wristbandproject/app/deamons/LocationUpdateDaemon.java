package it.logostech.wristbandproject.app.deamons;

import android.location.Location;
import android.util.Log;

/**
 * This is a <i>daemon</i> that sends locations to the remote service
 *
 * @author Michele Schimd
 * @version 1.0
 * @since 02/05/15
 */
public class LocationUpdateDaemon {

    public static final String STOP_INFO_USER = "User";
    public static final String STOP_INFO_BATTERY = "Low battery";
    public static final String STOP_INFO_OTHER = "Other";

    // Singleton enforced via private constructor and static final instance
    public static final LocationUpdateDaemon LOCATION_DAEMON = new LocationUpdateDaemon();
    public static final String TAG = LocationUpdateDaemon.class.getSimpleName();

    private LocationUpdateDaemon() {}

    /**
     * This methods is used to send locations to send locations to a remote server.
     *
     * @param location the location to be sent
     */
    public void sendLocation(Location location) {
        Log.d(TAG, "Sending location + (" + location.getLatitude() +
                ", " + location.getLongitude() + ")");
    }

    /**
     * This method is used to
     */
    public void locationUpdateStopped(String info) {
        Log.d(TAG, "Sending location stopped because of '" + info + "'");
    }
}
