package it.logostech.wristbandproject.app.model.payment.protocol;

import it.logostech.wristbandproject.app.model.payment.PaymentChallengeResolved;

/**
 * Created by michele.schimd on 02/09/2014.
 */
public class TransitionConfirmedCustomer extends TransitionConfirmed {
    public TransitionConfirmedCustomer(String senderId, String receiverId, String transactionId,
                                       PaymentChallengeResolved resolved) {
        super(senderId, receiverId, transactionId, resolved);
    }
}
