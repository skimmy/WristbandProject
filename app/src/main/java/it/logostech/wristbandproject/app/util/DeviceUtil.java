package it.logostech.wristbandproject.app.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.util.Log;

/**
 * This class contains utility methods to query device about its feature and to
 * get access to hardware and software features (<i>e.g.,</i> location services).
 *
 * @author Michele Schimd
 * @version 1.0
 * @since 02/03/15
 */
public class DeviceUtil {

    public static final String TAG = DeviceUtil.class.getSimpleName();

    /**
     * Returns whether the Location Service is available on the current device
     *
     * @param ctx the current context
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

    public static int getAppVersion(Context ctx) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = ctx.getPackageManager().
                    getPackageInfo(ctx.getPackageName(), 0);
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "Unable to retrieve app version");
            return -1;
        }
        return packageInfo.versionCode;
    }


}
