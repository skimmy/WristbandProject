package it.logostech.wristbandproject.app;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;
import com.google.android.gms.maps.model.LatLng;

import it.logostech.wristbandproject.app.deamons.WristbandMonitorDaemon;
import it.logostech.wristbandproject.app.util.DeviceUtil;
import it.logostech.wristbandproject.app.util.GooglePlayUtil;

/**
 * @author Michele Schimd
 * @version 1.0
 * @since 02/16/15
 */

public class GcmIntentService extends IntentService {

    public static final String TAG = GcmIntentService.class.getSimpleName();

    public GcmIntentService() {
        super("GcmIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Bundle extras = intent.getExtras();
        GoogleCloudMessaging gcm = GooglePlayUtil.getGcmInstance(this);
        String messageType = gcm.getMessageType(intent);
        if (messageType == GoogleCloudMessaging.MESSAGE_TYPE_MESSAGE) {

            Log.v(TAG, "New GCM message " + extras.get("content") + " (" + extras.get("latitude")
                    + "," + extras.get("longitude") + ")");
            // TODO: Add check for content type to make sure it is a good position
            Double lat = Double.valueOf(extras.getString("latitude"));
            Double lon = Double.valueOf(extras.getString("longitude"));
            WristbandMonitorDaemon.DAEMON.setLastPosition(new LatLng(lat, lon));
            // check if some alarm is associated with the current location update
            Integer alarmsCount = Integer.valueOf(extras.getString("alarms"));
            if (alarmsCount > 0) {
                // TODO: trigger alarms
                Log.v(TAG, "GCM alerted " + alarmsCount.toString() + " trespasses");
                if (DeviceUtil.isVibrationAvailable(this)) {
                    Vibrator vibrator = DeviceUtil.getVibrator(this);
                    vibrator.vibrate(500);
                } else {
                    Log.e(TAG, "Vibrator service unavailable");
                }
            }


        } else {
            Log.v(TAG, "Message Type is: " + messageType);
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
}
