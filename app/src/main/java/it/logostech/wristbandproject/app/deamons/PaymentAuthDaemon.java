package it.logostech.wristbandproject.app.deamons;

import android.util.Log;

import it.logostech.wristbandproject.app.backend.RemotePaymentWS;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentAuthorizationMerchant;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentMessageBase;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentRequestMerchant;

/**
 * This is the <i>daemon</i> used to access the (remote) <b>auth</b> backend.
 * <p/>
 * This is a singleton class and such property is ensured by making a single static
 * instance available ({@link #AUTH_DAEMON} and by making the only (no parameters)
 * constructor <code>private</code>.
 * <p/>
 * This class is used in any device operation mode (<i>i.e.</i> either <b>gate</b> or
 * <b>wear</b> and, therefore, should be properly initialized in either case.
 *
 * @author Michele Schimd
 * @version 1.0
 * @since 10/31/14
 */
public class PaymentAuthDaemon extends PaymentDaemonBase {

    public static final String TAG = PaymentAuthDaemon.class.getSimpleName();

    /**
     * The only available instance of {@link it.logostech.wristbandproject.app.deamons.PaymentAuthDaemon}
     */
    public static final PaymentAuthDaemon AUTH_DAEMON = new PaymentAuthDaemon();

    public static final String AUTH_ID = "AUTH";


    private PaymentAuthDaemon() {
        RemotePaymentWS.initService();
    }

    /**
     * This method is used to send any type of message to the backend. The method
     * itself is responsible for parsing the passed message so to invoke the proper
     * remote service, on the other hand the returned value is generic message and
     * the type depends on the input parameter
     *
     * @param message
     * @return a
     */
    public PaymentMessageBase sendMessage(PaymentMessageBase message) {
        Log.v(TAG, "Sending message " + message.toString());
        if (message instanceof PaymentRequestMerchant) {
            return this.onPaymentRequestMerhcand((PaymentRequestMerchant) message);
        }
        return null;
    }

    private PaymentAuthorizationMerchant onPaymentRequestMerhcand(PaymentRequestMerchant request) {
        Log.i(TAG, "Sending PaymentRequestMerchant for transaction " +
                request.getPaymentDetails().getTransactionId());
        // TODO: Here we must kee track of the sent messages so to avoid troubles
        RemotePaymentWS.paymentRequestMerchant(request.getPaymentDetails());
        // TODO: parse remote response and construct proper return message
        return null;
    }
}
