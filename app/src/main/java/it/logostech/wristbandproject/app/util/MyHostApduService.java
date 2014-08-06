package it.logostech.wristbandproject.app.util;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;

/**
 * Created by michele.schimd on 24/07/2014.
 */
public class MyHostApduService extends HostApduService {
    @Override
    public byte[] processCommandApdu(byte[] bytes, Bundle bundle) {
        return new byte[0];
    }

    @Override
    public void onDeactivated(int i) {

    }
}
