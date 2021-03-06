package it.logostech.wristbandproject.app.deamons;

import android.util.Log;

import java.util.Arrays;

import it.logostech.wristbandproject.app.model.payment.PaymentProtocolWear;
import it.logostech.wristbandproject.app.model.payment.protocol.IdentityMessage;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentIssuedMessage;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentMessageBase;
import it.logostech.wristbandproject.app.nfc.MyHostApduService;
import it.logostech.wristbandproject.app.nfc.NfcSession;
import it.logostech.wristbandproject.app.nfc.NfcUtil;
import it.logostech.wristbandproject.app.util.TypeUtil;

/**
 *
 * @author Michele Schimd
 * @since 10/27/14
 * @version 1.2
 */
public class PaymentWearDaemon extends PaymentDaemonBase {

    // Singleton enforced via private constructor and static final instance
    public static final PaymentWearDaemon WEAR_DAEOMN = new PaymentWearDaemon();
    public static final String TAG = PaymentWearDaemon.class.getSimpleName();

    // session and protocol info
    PaymentProtocolWear payProtocol = null;
    NfcSession currentSession = null;

    private final String aid;

    private PaymentWearDaemon() {
        this.aid = MyHostApduService.AID;
    }

    public byte[] onCommand(byte[] bytes) {
        Log.v(TAG, "Message: " + new String(bytes));
        byte[] aidSelect = NfcUtil.buildSelectApdu(this.aid);
        if (Arrays.equals(bytes, aidSelect)) {
            // we have received a SELECT message
            Log.v(TAG, "Received SELECT for " + this.aid);
            if (this.currentSession == null) {
                this.payProtocol = new PaymentProtocolWear("WEAR", null);
                byte payload[] = {'O', 'k'};
                return TypeUtil.concatArrays(payload, NfcUtil.SELECT_OK_SW);
            }
            else {
                // TODO: react properly
                return (new byte[0]);
            }
        }

        byte opCode = bytes[0];
        switch (opCode) {
            case IdentityMessage.OP_CODE:
                // Identity command received, reply back with device identity
                Log.v(TAG, "Identity (I) command received");
                this.payProtocol.onMessageReceived(IdentityMessage.fromBytes(bytes));
                return (new IdentityMessage(PaymentDaemonBase.deviceNfcId)).toByteArray();
            case PaymentIssuedMessage.OP_CODE:
                // Payment issued command received
                Log.v(TAG, "Payment issued (P) command received");
                this.payProtocol.onMessageReceived(PaymentIssuedMessage.fromBytes(bytes));

                return NfcUtil.ACK_BYTE_ARRAY;

            default:
                Log.v(TAG, "Received SELECT for unsupported aid");
                return NfcUtil.UNKNOWN_CMD_SW;
        }
    }

    @Override
    protected void processMessage(PaymentMessageBase message) {

    }

}
