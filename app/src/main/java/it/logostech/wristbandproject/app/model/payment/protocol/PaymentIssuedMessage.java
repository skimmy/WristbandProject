package it.logostech.wristbandproject.app.model.payment.protocol;

import it.logostech.wristbandproject.app.model.payment.PaymentDetails;

/**
 * Created by michele.schimd on 02/09/2014.
 */
public class PaymentIssuedMessage extends PaymentMessageBase {

    private PaymentDetails paymentDetails;

    public PaymentIssuedMessage(String senderId, String receiverId, String transactionId, PaymentDetails details) {
        super(senderId, receiverId, transactionId);
        this.paymentDetails = details;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public String getGateId() {
        return this.paymentDetails.getGateId();
    }

    public String getWearId() {
        return this.paymentDetails.getWearId();
    }

    public double getPaymentAmount() {
        return this.paymentDetails.getAmount();
    }

    @Override
    public int getChannel() {
        return PaymentMessageBase.NFC_CHANNEL;
    }
}
