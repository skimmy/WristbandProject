package it.logostech.wristbandproject.app.model.payment.protocol;

import it.logostech.wristbandproject.app.model.payment.PaymentChallengeResolved;
import it.logostech.wristbandproject.app.model.payment.PaymentDetails;

/**
 * Created by michele.schimd on 02/09/2014.
 */
public class PaymentOk  extends  PaymentMessageBase {
    private PaymentDetails paymentDetails;
    private PaymentChallengeResolved challengeResolved;

    public PaymentOk(String senderId, String receiverId, String transactionId,
                     PaymentDetails paymentDetails, PaymentChallengeResolved challengeResolved) {
        super(senderId, receiverId, transactionId);
        this.paymentDetails = paymentDetails;
        this.challengeResolved = challengeResolved;
    }


    public PaymentDetails getPaymentDetails() {
        return paymentDetails;
    }

    public PaymentChallengeResolved getChallengeResolved() {
        return challengeResolved;
    }

    @Override
    public int getChannel() {
        return NFC_CHANNEL;
    }
}
