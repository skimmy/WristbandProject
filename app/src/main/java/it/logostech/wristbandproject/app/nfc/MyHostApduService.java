package it.logostech.wristbandproject.app.nfc;

import android.os.Bundle;
import android.util.Log;

import java.util.Arrays;

import it.logostech.wristbandproject.app.R;
import it.logostech.wristbandproject.app.util.TypeUtil;

/**
 * Created by michele.schimd on 19/09/2014.
 */
public class MyHostApduService extends android.nfc.cardemulation.HostApduService {

    private static final String TAG = MyHostApduService.class.getSimpleName();

    private String aid = "F0010203040506";

    @Override
    public void onCreate() {
        Log.v(TAG, "onCreate");
        super.onCreate();
        this.aid = getResources().getString(R.string.nfcAID);
    }

    @Override
    public byte[] processCommandApdu(byte[] bytes, Bundle bundle) {
        Log.i(TAG, "NFC: Command received " + TypeUtil.byteArrayToHexString(bytes));
        byte[] aidSelect = NfcUtil.buildSelectApdu(this.aid);
        if (Arrays.equals(bytes, aidSelect)) {
            Log.v(TAG, "Received SELECT for " + this.aid);
            byte payload[] = {'O', 'k'};
            return TypeUtil.concatArrays(payload, NfcUtil.SELECT_OK_SW);
        }
        Log.v(TAG, "Received SELECT for unsupported aid");
        return NfcUtil.UNKNOWN_CMD_SW;
    }

    @Override
    public void onDeactivated(int i) {
        Log.v(TAG, "onDeactivate");
    }

}
