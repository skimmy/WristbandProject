package it.logostech.wristbandproject.app;

import android.app.IntentService;
import android.content.Intent;

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

    }
}
