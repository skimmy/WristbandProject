package it.logostech.wristbandproject.app.model.payment.protocol;

import it.logostech.wristbandproject.app.model.payment.PaymentDetails;

/**
 * Created by michele.schimd on 02/09/2014.
 */
public class PaymentRequestMerchant extends PaymentRequest {

    public PaymentRequestMerchant(String senderId, String receiverId, String transactionId, PaymentDetails details) {
        super(senderId, receiverId, transactionId, details);
    }
}
