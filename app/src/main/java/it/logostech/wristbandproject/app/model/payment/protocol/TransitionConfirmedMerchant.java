package it.logostech.wristbandproject.app.model.payment.protocol;

import it.logostech.wristbandproject.app.model.payment.PaymentChallengeResolved;

/**
 * Created by michele.schimd on 02/09/2014.
 */
public class TransitionConfirmedMerchant extends TransitionConfirmed {
    public TransitionConfirmedMerchant(String senderId, String receiverId, String transactionId,
                                       PaymentChallengeResolved resolved) {
        super(senderId, receiverId, transactionId, resolved);
    }
}
