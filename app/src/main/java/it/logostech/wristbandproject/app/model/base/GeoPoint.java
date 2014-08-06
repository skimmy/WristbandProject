package it.logostech.wristbandproject.app.model.base;

/**
 * Created by michele.schimd on 06/08/2014.
 */
public class GeoPoint {
    private float latuitude;
    private float longitude;

    public GeoPoint(float latuitude, float longitude) {
        this.latuitude = latuitude;
        this.longitude = longitude;
    }

    public float getLatuitude() {
        return latuitude;
    }

    public void setLatuitude(float latuitude) {
        this.latuitude = latuitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
}
