package it.logostech.wristbandproject.app.model.payment.protocol;

import it.logostech.wristbandproject.app.model.payment.PaymentChallengeResolved;

/**
 * Created by michele.schimd on 02/09/2014.
 */
public class TransitionConfirmed extends PaymentMessageBase {
    private PaymentChallengeResolved challengeResolved;

    public TransitionConfirmed(String senderId, String receiverId, String transactionId,
                               PaymentChallengeResolved resolved) {
        super(senderId, receiverId, transactionId);
        this.challengeResolved = resolved;
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
