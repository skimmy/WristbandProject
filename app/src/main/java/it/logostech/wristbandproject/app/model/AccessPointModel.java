package it.logostech.wristbandproject.app.model;

import it.logostech.wristbandproject.app.model.base.GeoPoint;

/**
 * Created by michele.schimd on 06/08/2014.
 */
public class AccessPointModel {
    private GeoPoint position;
    private String id;

    public AccessPointModel(String id) {
        this.id = id;
    }

    public GeoPoint getPosition() {
        return position;
    }

    public void setPosition(GeoPoint position) {
        this.position = position;
    }

    public String getId() {
        return id;
    }

}
