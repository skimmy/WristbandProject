package it.logostech.wristbandproject.app;

import android.app.IntentService;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;
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

    private static final int NOTIFICATION_ID = 505;

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
                this.addAlertNotification("Wristband trespassed fences");
            }


        } else {
            Log.v(TAG, "Message Type is: " + messageType);
        }
        GcmBroadcastReceiver.completeWakefulIntent(intent);
    }

    private void addAlertNotification(String content) {
        // construct the notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(this);
        mBuilder
                .setSmallIcon(R.drawable.ic_alarm_notification)
                .setContentTitle(getString(R.string.alert_notification_title))
                .setContentText(content)
                .setAutoCancel(true);

        // create intent for MapActivity and insert it in an artificial stack
        Intent resultIntent = new Intent(this, MapActivity.class);
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
        stackBuilder.addParentStack(MapActivity.class);
        stackBuilder.addNextIntent(resultIntent);
        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

        mBuilder.setContentIntent(resultPendingIntent);
        DeviceUtil.addNotification(this, mBuilder.build(), NOTIFICATION_ID);

        if (DeviceUtil.isVibrationAvailable(this)) {
            Vibrator vibrator = DeviceUtil.getVibrator(this);
            vibrator.vibrate(500);
        }

    }
}
