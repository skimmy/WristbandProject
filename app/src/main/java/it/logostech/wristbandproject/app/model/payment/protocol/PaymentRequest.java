package it.logostech.wristbandproject.app.model.payment.protocol;

import it.logostech.wristbandproject.app.model.payment.PaymentDetails;

/**
 * Created by michele.schimd on 02/09/2014.
 */
public class PaymentRequest extends PaymentMessageBase {
    private PaymentDetails paymentDetails;


    public PaymentRequest(String senderId, String receiverId, String transactionId, PaymentDetails details) {
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
        return HTTPS_CHANNEL;
    }

    @Override
    public byte[] toByteArray() {
        return new byte[0];
    }

}
