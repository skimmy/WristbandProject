package it.logostech.wristbandproject.app.model.payment.protocol;

import it.logostech.wristbandproject.app.model.payment.PaymentChallenge;
import it.logostech.wristbandproject.app.model.payment.PaymentDetails;

/**
 * Created by michele.schimd on 02/09/2014.
 */
public class PaymentAuthorization extends PaymentMessageBase {
    PaymentDetails paymentDetails;
    PaymentChallenge paymentChallenge;

    public PaymentAuthorization(String senderId, String receiverId, String transactionId,
                                PaymentDetails details, PaymentChallenge challenge) {
        super(senderId, receiverId, transactionId);
        this.paymentDetails = details;
        this.paymentChallenge = challenge;
    }

    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public PaymentChallenge getPaymentChallenge() {
        return paymentChallenge;
    }

    @Override
    public int getChannel() {
        return HTTPS_CHANNEL;
    }
}
