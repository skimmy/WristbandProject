package it.logostech.wristbandproject.app.util;

import android.content.Context;
import android.os.AsyncTask;
import android.view.ContextThemeWrapper;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.gcm.GoogleCloudMessaging;

import it.logostech.wristbandproject.app.MainActivity;

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

    /**
     * Returns whether the Google Play Services are currently available.
     * @param ctx the context
     * @return <code>true</code> if Google Play Services are available,
     * <code>false</code> otherwise.
     */
    public static boolean arePlayServicesAvailable(Context ctx) {
        int resultCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(ctx);
        return (resultCode == ConnectionResult.SUCCESS);
    }

    public static GoogleCloudMessaging getGcmInstance(Context ctx) {
        return GoogleCloudMessaging.getInstance(ctx);
    }
}
