package it.logostech.wristbandproject.app.model.payment;

import it.logostech.wristbandproject.app.model.TagModel;
import it.logostech.wristbandproject.app.model.payment.protocol.IdentityMessage;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentMessageBase;

/**
 * Created by Michele Schimd on 03/09/2014.
 *
 * @version  1.1
 */
public class PaymentProtocolGate extends PaymentProtocolBase {

//    /**
//     * {@link #getStatus()} return value when the gate is idle and waiting for
//     * new payments to be started.
//     */
//    public static final int GATE_STATE_IDLE = 0;
//
//    /**
//     * {@link #getStatus()} return value when a payment has been requested, but
//     * no tag has been discovered
//     */
//    public static final int GATE_STATE_PAYMENT_STARTED = 1;
//
//    /**
//     * {@link #getStatus()} return value when tag has been discovered and both
//     * <i>payment issued</i> and <i>payment request</i> have been sent (to wear
//     * and auth respectively)
//     */
//    public static final int GATE_STATE_TAG_FOUND = 2;
//
//    /**
//     * {@link #getStatus()} return value when payment has been authorized by
//     * auth and <i>payment ok</i> message has been sent to wear
//     */
//    public static final int GATE_STATE_PAYMENT_AUTHORIZED = 3;
//
//    /**
//     * {@link #getStatus()} return value while gate is waiting for <i>payment
//     * success</i> message from auth
//     */
//    public static final int GATE_STATE_PAYMENT_OK = 4;
//
//    /**
//     * {@link #getStatus()} return value when the transaction has been successfully
//     * concluded and the payment has been done. Gate is meant to stay in this
//     * state for the short amount of time needed to clear all information about
//     * the concluded transaction and to send <i>payment performed</i> message
//     * to wear. At the end of such operations gate moves to {@link #GATE_STATE_IDLE}
//     * state automatically (<i>i.e.</i> without the necessity of any further
//     * message)
//     */
//    public static final int GATE_STATE_TRANSITION_SUCCESS = 5;


    private String id;

    private String authId;


    public PaymentProtocolGate(String id, String authId) {
        this.state = STATE_IDLE;
        this.id = id;
        this.authId = authId;
    }

    @Override
    public void onMessageReceived(PaymentMessageBase message) {

    }

    /**
     * This method is called when a new connection is established with a remote
     * NFC device. The "normal" behaviour of this method is to construct a proper
     * {@link it.logostech.wristbandproject.app.model.payment.protocol.IdentityMessage}
     * that will be sent back to the NFC counterpart.
     *
     * This method may encounter some unexpected behaviour (<i>i.e.</i> something
     * breaking the normal behaviour), such events are:
     * <ul>
     *     <li>A new connection for an old transaction is established: this may
     *     happen either because previous connection has been lost or because of
     *     an attack to one of the device.</li>
     *
     *     <li>The current device is (or is supposed to be) involved in a
     *     transaction that is not yet finished and a new (valid) one has been
     *     issued</li>
     *
     *     <li>...</li>
     * </ul>
     * How the device should react on such events is still under definition.
     *
     * @param remoteTag
     */
    public IdentityMessage onNewConnection(TagModel remoteTag) {
        IdentityMessage identityMessage = null;
        // if everything is ok, than we just prepare the proper IdentityMessage
        // TODO: define the control for "regular" behaviour
        boolean validConnectionRequest = true;
        if (validConnectionRequest) {
            identityMessage = new IdentityMessage(this.id);
            this.state = STATE_CONNECTED;
        } else {
            // TODO define reaction to unexpected events
            this.state = STATE_ERROR;
        }
        return identityMessage;
    }

    /**
     * This method is called when a payment is first started (<i>e.g.</i> when
     * the operators confirms a purchase. This event marks the start of the
     * transaction between actual gate and the entity identified with the
     * <code>wearId</code> identifier.
     *
     * This methods receives a payment detail object that is used to create a
     * new one with a generated transaction id and the current gate identifier.
     *
     * This method also creates a payment issued message and inserts it into
     * the private messages map ready to be sent as soon as a tag will be
     * discovered.
     *
     * @param details
     */
    public void onPaymentStarted(PaymentDetails details) {

    }

}
