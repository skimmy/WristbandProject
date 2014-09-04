package it.logostech.wristbandproject.app.model.payment;

import it.logostech.wristbandproject.app.model.payment.protocol.PaymentMessageBase;

/**
 * Created by michele.schimd on 03/09/2014.
 */
public class PaymentProtocolGate extends PaymentProtocolBase {

    /**
     * {@link #getStatus()} return value when the gate is idle and waiting for
     * new payments to be started.
     */
    public static final int GATE_STATE_IDLE = 0;

    /**
     * {@link #getStatus()} return value when a payment has been requested, but
     * no tag has been discovered
     */
    public static final int GATE_STATE_PAYMENT_STARTED = 1;

    /**
     * {@link #getStatus()} return value when tag has been discovered and both
     * <i>payment issued</i> and <i>payment request</i> have been sent (to wear
     * and auth respectively)
     */
    public static final int GATE_STATE_TAG_FOUND = 2;

    /**
     * {@link #getStatus()} return value when payment has been authorized by
     * auth and <i>payment ok</i> message has been sent to wear
     */
    public static final int GATE_STATE_PAYMENT_AUTHORIZED = 3;

    /**
     * {@link #getStatus()} return value while gate is waiting for <i>payment
     * success</i> message from auth
     */
    public static final int GATE_STATE_PAYMENT_OK = 4;

    /**
     * {@link #getStatus()} return value when the transaction has been successfully
     * concluded and the payment has been done. Gate is meant to stay in this
     * state for the short amount of time needed to clear all information about
     * the concluded transaction and to send <i>payment performed</i> message
     * to wear. At the end of such operations gate moves to {@link #GATE_STATE_IDLE}
     * state automatically (<i>i.e.</i> without the necessity of any further
     * message)
     */
    public static final int GATE_STATE_TRANSITION_SUCCESS = 5;

    private int status;
    private String id;

    private String authId;


    public PaymentProtocolGate(String id, String authId) {
        this.status = GATE_STATE_IDLE;
        this.id = id;
        this.authId = authId;
    }

    @Override
    public void onMessageReceived(PaymentMessageBase message) {

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

    public int getStatus() {
        return status;
    }

    private String generateTransactionId() {
        // TODO: generate random string
        return "";
    }
}
