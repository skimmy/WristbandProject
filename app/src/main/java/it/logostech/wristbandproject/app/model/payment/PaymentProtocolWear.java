package it.logostech.wristbandproject.app.model.payment;

import android.util.Log;

import it.logostech.wristbandproject.app.model.payment.protocol.IdentityMessage;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentMessageBase;

/**
 * This class contains the protocol implemented by the wear entity during the
 * contactless payment procedures.
 *
 * <p>Created by Michele Schimd on 03/09/2014.</p>
 *
 * @version 1.1
 */
public class PaymentProtocolWear extends PaymentProtocolBase {

    public static final String TAG = PaymentProtocolWear.class.getSimpleName();

    private String id;
    private String authId;

    public PaymentProtocolWear(String id, String authId) {
        this.id = id;
        this.authId = authId;
    }

    @Override
    public void onMessageReceived(PaymentMessageBase message) {
        if (message instanceof IdentityMessage) {
            this.onIdentityMessage((IdentityMessage)message);
        }
    }

    private void onIdentityMessage(IdentityMessage identityMessage) {
        Log.v(TAG, "IdentityMessage from " + identityMessage.getSenderId());
        this.state = STATE_CONNECTED;
    }

}
