package it.logostech.wristbandproject.app.nfc;

import android.os.Bundle;

/**
 * Created by michele.schimd on 19/09/2014.
 */
public class HostApduService extends android.nfc.cardemulation.HostApduService {
    @Override
    public byte[] processCommandApdu(byte[] bytes, Bundle bundle) {
        return new byte[0];
    }

    @Override
    public void onDeactivated(int i) {

    }
}
