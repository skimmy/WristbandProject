package it.logostech.wristbandproject.app.model.payment.protocol;

import it.logostech.wristbandproject.app.model.payment.PaymentChallenge;
import it.logostech.wristbandproject.app.model.payment.PaymentDetails;

/**
 * Created by michele.schimd on 02/09/2014.
 */
public class PaymentOkCustomer extends PaymentOk {

    public PaymentOkCustomer(String senderId, String receiverId, String transactionId,
                             PaymentDetails paymentDetails, PaymentChallenge challenge) {
        super(senderId, receiverId, transactionId, paymentDetails, challenge);
    }
}
