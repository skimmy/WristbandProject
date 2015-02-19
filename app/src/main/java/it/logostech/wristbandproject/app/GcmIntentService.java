package it.logostech.wristbandproject.app;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.gcm.GoogleCloudMessaging;

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
        } else {
            Log.v(TAG, "Message Type is: " + messageType);
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }
}
