package it.logostech.wristbandproject.app.model.payment;

import it.logostech.wristbandproject.app.model.payment.protocol.PaymentIssuedMessage;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentMessageBase;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentMessageFactory;
import it.logostech.wristbandproject.app.model.payment.protocol.PaymentRequestCustomer;

/**
 * Created by michele.schimd on 03/09/2014.
 */
public class PaymentProtocolWear extends PaymentProtocolBase {

    /**
     * {@link #getStatus()} return value when no transaction is pending
     */
    public static final int WEAR_STATE_IDLE = 0;
    /**
     *{@link #getStatus()} return value when a payment has been issued
     */
    public static final int WEAR_STATE_PAYMENT_ISSUED = 1;

    private int status;
    private String wearId;

    public PaymentProtocolWear(String id) {
        this.wearId = id;
        this.status = WEAR_STATE_IDLE;
    }

    @Override
    public void onMessageReceived(PaymentMessageBase message) {
        if (message instanceof PaymentIssuedMessage) {
            this.onPaymentIssuedMessage((PaymentIssuedMessage) message);
        }
    }

    private void onPaymentIssuedMessage(PaymentIssuedMessage message) {
        this.addPendingTransaction(message);
        this.status = WEAR_STATE_PAYMENT_ISSUED;
        PaymentRequestCustomer paymentRequest =
                PaymentMessageFactory.createPaymentRequestCustomerMessage(message, this.wearId,
                        message.getSenderId());
        this.sendMessageToAuth(paymentRequest);
    }

    private void sendMessageToAuth(PaymentMessageBase message) {

    }

    /**
     * Returns the status
     * @return the status
     */
    public int getStatus() {
        return status;
    }
}
