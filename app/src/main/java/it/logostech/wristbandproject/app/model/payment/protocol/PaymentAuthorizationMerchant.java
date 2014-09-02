package it.logostech.wristbandproject.app.model.payment.protocol;

import it.logostech.wristbandproject.app.model.payment.PaymentChallenge;
import it.logostech.wristbandproject.app.model.payment.PaymentDetails;

/**
 * Created by michele.schimd on 02/09/2014.
 */
public class PaymentAuthorizationMerchant extends PaymentAuthorization {
    public PaymentAuthorizationMerchant(String senderId, String receiverId, String transactionId,
                                        PaymentDetails details, PaymentChallenge challenge) {
        super(senderId, receiverId, transactionId, details, challenge);
    }
}
