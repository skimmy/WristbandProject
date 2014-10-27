package it.logostech.wristbandproject.app.deamons;

import android.nfc.tech.IsoDep;

import it.logostech.wristbandproject.app.model.payment.protocol.IdentityMessage;

/**
 * Created by skimmy on 10/27/14.
 */
public class PaymentDaemonBase {

    // This is the ID used which should be initialized in the onResume method
    // of the CardEmulatorActivity or CardReaderActivity. Note that because of the Android NFC
    // implementation, this ID (which is supposed to be uniquely associated with
    // the current device) is different from the ID used by the low level NFC
    // hardware (which may change and is not accessible from the APIs).
    public static String deviceNfcId = null;

    protected static void sendIdentity(IdentityMessage identityMessage, IsoDep isoDep) {

    }

}
