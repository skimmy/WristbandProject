package it.logostech.wristbandproject.app;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import it.logostech.wristbandproject.app.deamons.WristbandMonitorDaemon;


public class MapActivity extends Activity implements OnMapReadyCallback {

    public static final String TAG = MapActivity.class.getSimpleName();
    private GoogleMap theMap = null;
    private Handler positionHandler = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.v(TAG, "Started...");
        setContentView(R.layout.activity_map);
        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.mapFragment);
        mapFragment.getMapAsync(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        WristbandMonitorDaemon.DAEMON.removeHandler(this.positionHandler);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (this.positionHandler != null) {
            WristbandMonitorDaemon.DAEMON.addHandler(this.positionHandler);

        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Log.v(TAG, "onMapReady");
        // save the instance to the map
        this.theMap = googleMap;
        // register the handler
        this.positionHandler = new PositionHandler(Looper.getMainLooper());
        WristbandMonitorDaemon.DAEMON.addHandler(this.positionHandler);
        // TODO: remove after tests performed
        googleMap.addMarker(new MarkerOptions().
                position(new LatLng(0, 0)).
                title("Hello"));

    }

    private class PositionHandler extends Handler {
        public PositionHandler(Looper mainLooper) {
            super(mainLooper);
        }

        @Override
        public void handleMessage(Message msg) {
            if (! (msg.obj instanceof LatLng)) {
                return;
            }
            LatLng newPosition = (LatLng) msg.obj;
            Log.v(TAG, "New Position " + newPosition.toString());
            if (theMap != null) {
                theMap.addMarker(new MarkerOptions().position(newPosition).title("Wristband"));
            }
        }
    }
}
