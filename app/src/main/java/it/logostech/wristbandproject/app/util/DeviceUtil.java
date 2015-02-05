package it.logostech.wristbandproject.app.util;

import android.content.Context;
import android.location.LocationManager;

/**
 * This class contains utility methods to query device about its feature and to
 * get access to hardware and software features (<i>e.g.,</i> location services).
 *
 * @author Michele Schimd
 * @version 1.0
 * @since 02/03/15
 */
public class DeviceUtil {

    /**
     * Returns whether the Location Service is available on the current device
     *
     * @param ctx the current contex
     * @return <code>true</code> if device supports location services <code>false
     * otherwise</code>
     */
    public static boolean isLocationAvailable(Context ctx) {
        Object service = ctx.getSystemService(Context.LOCATION_SERVICE);
        return (service != null);
    }

    public static LocationManager getLocationManager(Context ctx) {
        return (LocationManager) ctx.getSystemService(Context.LOCATION_SERVICE);
    }


}
