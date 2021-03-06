package it.logostech.wristbandproject.app.model.payment.protocol;

import it.logostech.wristbandproject.app.model.payment.PaymentChallenge;
import it.logostech.wristbandproject.app.model.payment.PaymentChallengeResolved;
import it.logostech.wristbandproject.app.model.payment.PaymentDetails;

/**
 * Created by michele.schimd on 02/09/2014.
 */
public class PaymentOk  extends  PaymentMessageBase {

    public static final byte OP_CODE = (byte)'O';

    private PaymentDetails paymentDetails;
    private PaymentChallenge challengeResolved;

    public PaymentOk(String senderId, String receiverId, String transactionId,
                     PaymentDetails paymentDetails, PaymentChallenge challengeResolved) {
        super(senderId, receiverId, transactionId);
        this.paymentDetails = paymentDetails;
        this.challengeResolved = challengeResolved;
    }


    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public PaymentChallenge getChallenge() {
        return challengeResolved;
    }

    @Override
    public int getChannel() {
        return NFC_CHANNEL;
    }

    @Override
    public byte[] toByteArray() {
        return new byte[0];
    }
}
