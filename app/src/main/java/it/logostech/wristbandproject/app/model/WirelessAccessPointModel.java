package it.logostech.wristbandproject.app.model;

/**
 * Created by michele.schimd on 06/08/2014.
 */
public class WirelessAccessPointModel extends AccessPointModel {

    private String SSID;

    public WirelessAccessPointModel(String id, String SSID) {
        super(id);
        this.SSID = SSID;
    }

    public String getSSID() {
        return SSID;
    }
}
