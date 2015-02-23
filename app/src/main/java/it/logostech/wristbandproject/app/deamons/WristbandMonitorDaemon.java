package it.logostech.wristbandproject.app.deamons;


import android.os.Handler;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.LinkedList;
import java.util.Queue;

import it.logostech.wristbandproject.app.backend.RemoteLocationWS;

/**
 * This class represents the daemon that runs to monitoring the remote wristband
 *
 * @author Michele Schimd
 * @version 1.0
 * @since 02/17/15
 */
public class WristbandMonitorDaemon implements Runnable {

    public static final String TAG = WristbandMonitorDaemon.class.getSimpleName();

    public static final WristbandMonitorDaemon DAEMON = new WristbandMonitorDaemon();


    // 0: waiting for reg id, 1: reg id available registration needed, 2: registered
    private int registration = 0;
    private String gcmRegistrationId = null;
    private LatLng lastPosition = null;

    // queue of handlers to be notified
    Queue<Handler> handlersQueue = new LinkedList<Handler>();

    private WristbandMonitorDaemon() {

    }

    public void setGcmRegistrationId(String newGcm) {
        this.gcmRegistrationId = newGcm;
        this.registration = 1;
    }

    @Override
    public void run() {
        while(true) {
            try {
                if (registration == 1) {
                    String tutorId = "TUTOR";
                    String wbId = "WRISTBAND";
                    Log.v(TAG, "Registering for update: <" + tutorId + ", " +
                            wbId + ", " + this.gcmRegistrationId + ">");
                    RemoteLocationWS.initService();
                    RemoteLocationWS.
                            registerForUpdate(tutorId, wbId, this.gcmRegistrationId);
                    registration = 2;
                }

                Thread.sleep(200);
            } catch (InterruptedException e) {
                Log.v(TAG, "Monitoring daemon interrupted");
                // TODO: Add here whatever is needed to stop monitoring daemon
                return;
            }
        }
    }

    public LatLng getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(LatLng lastPosition) {
        this.lastPosition = lastPosition;
        for (Handler h : this.handlersQueue) {
            Message msg = new Message();
            msg.obj = this.getLastPosition();
            h.sendMessage(msg);
        }
    }

    public void addHandler(Handler handler) {
        this.handlersQueue.add(handler);
    }

    public void removeHandler(Handler handler) {
        this.handlersQueue.remove(handler);
    }

}
