package it.logostech.wristbandproject.app.nfc;

import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

import it.logostech.wristbandproject.app.R;
import it.logostech.wristbandproject.app.deamons.PaymentDaemonBase;
import it.logostech.wristbandproject.app.deamons.PaymentWearDaemon;
import it.logostech.wristbandproject.app.model.payment.PaymentProtocolWear;
import it.logostech.wristbandproject.app.model.payment.protocol.IdentityMessage;
import it.logostech.wristbandproject.app.util.TypeUtil;

/**
 * This is the service implementation for the <i>Host Card Emulation (HCE)</i>
 *
 * Created by Michele Schimd on 19/09/2014.
 *
 * @version 1.0
 */
public class MyHostApduService extends android.nfc.cardemulation.HostApduService {

    public static final String TAG = MyHostApduService.class.getSimpleName();

    public static String AID = "F0010203040506";

    @Override
    public void onCreate() {
        Log.v(TAG, "onCreate");
        super.onCreate();
        MyHostApduService.AID = getResources().getString(R.string.nfcAID);
    }

    @Override
    public byte[] processCommandApdu(byte[] bytes, Bundle bundle) {
        Log.i(TAG, "NFC: Command received " + TypeUtil.byteArrayToHexString(bytes));
        return PaymentWearDaemon.WEAR_DAEOMN.onCommand(bytes);
    }

    @Override
    public void onDeactivated(int i) {
        Log.v(TAG, "onDeactivate");
    }

}
