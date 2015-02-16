package it.logostech.wristbandproject.app.util;

import android.content.Context;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;

/**
 * This class contains several utility methods for interacting with the <i>Google
 * Playe Services</i> library.
 *
 * @author Michele Schimd
 * @version 1.0
 * @since 02/16/15
 */

public class GooglePlayUtil {

    private static final int PLAY_SERVICES_RESOLUTION_REQUEST = 9000;

    public static boolean arePlayServicesAvailable(Context ctx) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctx);
        return (resultCode == ConnectionResult.SUCCESS);
    }
}
