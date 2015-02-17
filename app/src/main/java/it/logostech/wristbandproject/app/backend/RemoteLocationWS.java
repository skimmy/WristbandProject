package it.logostech.wristbandproject.app.backend;

import android.util.Log;

import com.appspot.wristband_unipd.locationremote.Locationremote;
import com.google.api.client.extensions.android.http.AndroidHttp;
import com.google.api.client.json.gson.GsonFactory;

import java.io.IOException;

/**
 * @author Michele Schimd
 * @version 1.0
 * @since 02/17/15
 */

public class RemoteLocationWS {
    public static final String TAG = RemoteLocationWS.class.getSimpleName();

    private static Locationremote service = null;

    public static void initService() {
        if (service == null) {
            Locationremote.Builder builder = new Locationremote.Builder(
                    AndroidHttp.newCompatibleTransport(), new GsonFactory(), null);
            service = builder.build();
        }
    }

    public static void freeService() {
        service = null;
    }

    public static void registerForUpdate(String tutorId, String wbId, String gcmRegId) {
        try {
            service.registertutor(tutorId, wbId, gcmRegId).execute();
        } catch (IOException e) {
            Log.v(TAG, "Unable to register for update " + e.getCause());
            e.printStackTrace();
        }

    }

}
